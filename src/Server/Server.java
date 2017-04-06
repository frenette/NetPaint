package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

public class Server {
    
    private final static int PORT = 9090;
    private final static String HOST = "localhost";
    
    public static AsynchronousServerSocketChannel channelServer;
        
    public static ServerPaintObjectCollection serverPaintObjectCollection;

    public static void main(String[] args) {
	serverPaintObjectCollection = new ServerPaintObjectCollection();
	
	/*
	 * Continuously accept incoming connections
	 */
	
	try {
	    channelServer = AsynchronousServerSocketChannel.open();
	    channelServer.bind(new InetSocketAddress(HOST, PORT));
	    System.out.printf("Server listening at %s%n", channelServer.getLocalAddress());
	} catch (IOException ioe) {
	    System.err.println("Unable to open or bind server socket channel");
	    return;
	}

	channelServer.accept(new Server(), new ConnectionHandler());

	try {
	    Thread.currentThread().join();
	} catch (InterruptedException ie) {
	    System.out.println("Server terminating");
	}
    }

}
