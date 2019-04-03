package App.Models;

import App.Controllers.GameScene;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import javafx.application.Platform;

/**
 * Класс представляющий клиента
 * @author NikiTer
 */
public class Client {

    private final int port;
    private final String host;
    private GameScene gameScene;
    private Channel channel;

    private EventLoopGroup group;

    /**
     * Конструктор
     * @param port -- номер порта хоста
     * @param host -- ip адрес хоста
     * @param gameScene -- игровая сцена
     */
    public Client(int port, String host, GameScene gameScene) {
        this.port = port;
        this.host = host;
        this.gameScene = gameScene;
    }

    public void run() {
        group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer(gameScene));

            channel = bootstrap.connect(host, port).sync().channel();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Метод для отключения пользователя
     * @see EventLoop#shutdownGracefully()
     */
    public void shutdown() {
        group.shutdownGracefully();
    }

    /**
     * Метод для отправки сообщения по каналу
     * @param message -- сообщение
     * @see Channel#writeAndFlush(Object)
     */
    public void write(String message) {
        channel.writeAndFlush("[" + gameScene.getNickname() + "] " + message + "\r\n");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameScene.print("[" + gameScene.getNickname() + "] " + message);
            }
        });
    }
}

/**
 * Класс для инициализации обработчиков
 * @author NikiTer
 */
class ClientInitializer extends ChannelInitializer<SocketChannel> {

    private GameScene gameScene;

    /**
     * Конструктор
     * @param gameScene -- игровая сцена
     */
    ClientInitializer(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    /**
     * Метод, запускающийся при инициализации канала.
     * Добавляет обработчики к пайплайну.
     * @param socketChannel -- канал
     * @throws Exception -- на всякий
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("handler", new ClientHandler(gameScene));
    }
}

/**
 * Класс-обработчик
 * @author NikiTer
 */
class ClientHandler extends SimpleChannelInboundHandler<String> {

    private GameScene gameScene;

    /**
     * Конструктор
     * @param gameScene -- игровая сцена
     */
    ClientHandler(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    /**
     * Метод, вызывающийся при получениии сообщения.
     * Выводит его на экран.
     * @param chc -- канал, который прислал сообщение
     * @param s -- сообщение
     * @throws Exception -- на всякий
     * @see GameScene#print(String)
     */
    @Override
    protected void channelRead0(ChannelHandlerContext chc, String s) throws Exception {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameScene.print(s);
            }
        });
    }
}