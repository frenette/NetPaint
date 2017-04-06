package Server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class Client {

    public AsynchronousSocketChannel asynchronousSocketChannel;
    public ByteBuffer buf;
    public ServerPaintObjectCollection serverPaintObjectCollection;
    
    public Client(AsynchronousSocketChannel asynchronousSocketChannel, ServerPaintObjectCollection serverPaintObjectCollection) {
	buf = ByteBuffer.allocate(32768);
	this.asynchronousSocketChannel = asynchronousSocketChannel;
	this.serverPaintObjectCollection = serverPaintObjectCollection;
    }
}
