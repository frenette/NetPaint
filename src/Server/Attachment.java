package Server;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;

public class Attachment {
    public AsynchronousServerSocketChannel channelServer;
    public AsynchronousSocketChannel channelClient;
    public ByteBuffer buffer;
    public SocketAddress clientAddr;
    
    public ServerPaintObjectCollection serverPaintObjectCollection;
}
