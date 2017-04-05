package Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ConnectionHandler implements CompletionHandler<AsynchronousSocketChannel, Attachment> {
    @Override
    public void completed(AsynchronousSocketChannel channelClient, Attachment att) {
	try {
	    SocketAddress clientAddr = channelClient.getRemoteAddress();
	    System.out.printf("Accepted connection from %s%n", clientAddr);

	    /*
	     * Testing
	     */
	    ServerPaintObjectCollection serverPaintObjectCollection = new ServerPaintObjectCollection();
	    
	    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(bytes);
	    oos.writeObject(serverPaintObjectCollection.getAllPaintObjects());
	    channelClient.write(ByteBuffer.wrap(bytes.toByteArray()));
	    /*
	     * End testing
	     */

	    // // create more handlers
	    // att.channelServer.accept(att, this);
	    // Attachment newAtt = new Attachment();
	    //
	    // newAtt.channelServer = att.channelServer;
	    // newAtt.channelClient = channelClient;
	    // newAtt.isReadMode = true;
	    // newAtt.buffer = ByteBuffer.allocate(2048);
	    // newAtt.clientAddr = clientAddr;
	    //
	    //
	    // ReadWriteHandler rwh = new ReadWriteHandler();
	    // channelClient.read(newAtt.buffer, newAtt, rwh);
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}
    }

    @Override
    public void failed(Throwable t, Attachment att) {
	System.out.println("Failed to accept connection");
    }
}