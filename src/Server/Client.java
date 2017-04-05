package Server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class Client {

    public AsynchronousSocketChannel asynchronousSocketChannel;
    public ByteBuffer buf;
    
    public Client(AsynchronousSocketChannel asynchronousSocketChannel) {
	buf = ByteBuffer.allocate(4096);
	this.asynchronousSocketChannel = asynchronousSocketChannel;
    }
}
