package Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ConnectionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {
    @SuppressWarnings("static-access")
    @Override
    public void completed(AsynchronousSocketChannel channelClient, Server att) {
	try {
	    /*
	     * Recursively create more connection handlers
	     */
	    att.channelServer.accept(att, new ConnectionHandler());
	    /*
	     * Testing Writing to the client
	     */

	    SocketAddress clientAddr = channelClient.getRemoteAddress();
	    System.out.println("In ConnectionHandler");
	    System.out.printf("Accepted connection from %s%n", clientAddr);

	    /*
	     * Sending an initial Vector<PaintObjects>
	     */
	    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(bytes);
	    oos.writeObject(att.serverPaintObjectCollection.getPaintObjects());
	    channelClient.write(ByteBuffer.wrap(bytes.toByteArray()));

	    try {
		Thread.sleep(500);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	    // create a Client and add it to the map of clients
	    Client newClient = new Client(channelClient, att.serverPaintObjectCollection);
	    att.serverPaintObjectCollection.addClient(newClient);

	    try {
		Thread.sleep(500);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }

	    channelClient.read(newClient.buf, newClient, new ReadCompletionHandler());
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}
    }

    @Override
    public void failed(Throwable t, Server att) {
	System.err.println("Failed to accept connection");
    }
}