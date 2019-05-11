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

/**
 * Класс, представляющий сервер
 * @author NikiTer
 */
public class Server {

    private final int port;
    private GameScene gameScene;

    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    ChannelFuture channelFuture;

    /**
     * Конструктор
     * @param port -- номер порта
     * @param gameScene -- игровая сцена
     */
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

    /**
     * Метод отключения сервера
     * @see EventLoop#shutdownGracefully()
     * @see ChannelFuture#sync()
     */
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

    /**
     * Метод для записи сообщения в поток
     * @param message -- сообщение
     * @see Channel#writeAndFlush(Object)
     */
    public void writeMessage(String message) {
        for (Channel c : ServerHandler.getChannels())
            c.writeAndFlush("<M>" + gameScene.getNickname() + "</MN>" + message);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameScene.print("[" + gameScene.getNickname() + "] " + message);
            }
        });
    }

    public void writeHost(String cmd) {
        for (Channel c : ServerHandler.getChannels())
            c.writeAndFlush("<H>" + cmd);
    }
}

/**
 * Класс-инициализатор сервера
 * @author NikiTer
 */
class ServerInitializer extends ChannelInitializer <SocketChannel> {

    private GameScene gameScene;

    /**
     * Конструктор
     * @param gameScene -- игровая сцена
     */
    ServerInitializer(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    /**
     * Метод, запусквющийся при создании канала.
     * Добавляет к пайплайну обработчики.
     * @param socketChannel -- канал
     * @throws Exception -- на всякий случай
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        pipeline.addLast("handler", new ServerHandler(gameScene));
    }
}

/**
 * Класс-обработчик сервера
 * @author NikiTer
 */
class ServerHandler extends SimpleChannelInboundHandler <String> {

    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private GameScene gameScene;

    /**
     * Клонструктор
     * @param gameScene -- игровая сцена
     */
    ServerHandler(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    /**
     * Метод, запускающийся при добавлении нового канала.
     * Пишет в чат, что кто-то подключился и добавляет
     * новый канал в список активных каналов.
     * @param ctx -- подключившийся канал
     * @throws Exception -- на всякий случай
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        /*Channel incoming = ctx.channel();
        for (Channel c : channels)
            c.writeAndFlush(incoming.remoteAddress() + " has joined!\n");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameScene.print(incoming.remoteAddress() + " has joined!\n");
            }
        });*/
        channels.add(ctx.channel());
    }

    /**
     * Метод, запускающийся при удалении/отключении канала.
     * Пишет в чат, что кто-то отключился и удаляет отключившийся
     * канал из списка активных каналов.
     * @param ctx -- отключившийся канал
     * @throws Exception -- на всякий случай
     */
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

    /**
     * Метод, запускающийся при получении сообщения.
     * Сервер пересылает это сообщения всем каналам,
     * кроме того, от которого пришло сообщение.
     * @param chc -- канал, от которого пришло сообщение
     * @param s -- сообщение
     * @throws Exception -- на всякий случай
     */
    @Override
    protected void channelRead0(ChannelHandlerContext chc, String s) throws Exception {
        Channel incoming = chc.channel();
        String[] strings = Parser.parse(s);

        switch (strings[0]) {
            case "M":
                for (Channel c : channels) {
                    if (c != incoming)
                        c.writeAndFlush(s);
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        gameScene.print("[" + strings[1] + "] " + strings[2] + "\r\n");
                    }
                });
                break;

            case "A":
                break;

            case "I":
                break;

            default:
                break;
        }
    }

    /**
     * Метод, возвращающий список всех активных каналов.
     * @return -- список всех активных каналов
     */
    static ChannelGroup getChannels() {
        return channels;
    }
}