package App.Models;

import App.Controllers.GameScene;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import javafx.application.Platform;

import java.io.IOException;
import java.net.InetAddress;

public class Server {

    private final int port;
    private GameScene gameScene;

    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    ChannelFuture channelFuture;

    public Server(int port, GameScene gameScene) {
        this.port = port;
        this.gameScene = gameScene;
    }

    public void run() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        channelFuture = null;

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer(gameScene));

            channelFuture = bootstrap.bind(InetAddress.getLocalHost(), port).sync().channel().closeFuture();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void shutdown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        if (channelFuture != null) {
            try {
                channelFuture.sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(String message) {
        for (Channel c : ServerHandler.getChannels())
            c.writeAndFlush("[" + gameScene.getNickname() + "] " + message + "\r\n");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameScene.print("[" + gameScene.getNickname() + "] " + message);
            }
        });
    }
}

class ServerInitializer extends ChannelInitializer <SocketChannel> {

    private GameScene gameScene;

    public ServerInitializer(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        pipeline.addLast("handler", new ServerHandler(gameScene));
    }
}

class ServerHandler extends SimpleChannelInboundHandler <String> {

    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private GameScene gameScene;

    public ServerHandler(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel c : channels)
            c.writeAndFlush(incoming.remoteAddress() + " has joined!\n");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameScene.print(incoming.remoteAddress() + " has joined!\n");
            }
        });
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel c : channels)
            c.writeAndFlush(incoming.remoteAddress() + " has left!\n");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameScene.print(incoming.remoteAddress() + " has left!\n");
            }
        });
        channels.remove(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, String s) throws Exception {
        Channel incoming = chc.channel();
        for (Channel c : channels)
            if (c != incoming)
                c.writeAndFlush(s + "\n");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gameScene.print(s);
                }
            });
    }

    public static ChannelGroup getChannels() {
        return channels;
    }
}