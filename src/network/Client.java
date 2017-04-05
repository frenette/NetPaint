package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import model.PaintObject;

public class Client implements Observer {

    public void connectToServer() {
	AsynchronousSocketChannel channel;
	
	try {
	    channel = AsynchronousSocketChannel.open();
	} catch (IOException ioe) {
	    System.err.println("Unable to open client socket channel");
	    return;
	}

	try {
	    // blocking
	    channel.connect(new InetSocketAddress("localhost", 9090)).get();
	    System.out.printf("Client at %s connected%n", channel.getLocalAddress());
	} catch (ExecutionException | InterruptedException eie) {
	    System.err.println("Server not responding");
	    return;
	} catch (IOException ioe) {
	    System.err.println("Unable to obtain client socket channel’s " + "local address");
	    return;
	}
	
	/*
	 * Once we have connected, constantly check to see if there is anything to read.
	 */

	/*
	 * Testing
	 */
	try {
	    ByteBuffer bytes = ByteBuffer.allocate(2048);

	    Future<Integer> fut = channel.read(bytes);

	    while (!fut.isDone()) {
		Thread.sleep(500);
	    }

	    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes.array()));
	    Vector<PaintObject> vec = (Vector<PaintObject>) ois.readObject();

	    for (PaintObject o : vec) {
		System.out.println(o);
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	/*
	 * End testing
	 */

	// Attachment att = new Attachment();
	// att.channel = channel;
	// att.isReadMode = false;
	// att.buffer = ByteBuffer.allocate(2048);
	// att.mainThd = Thread.currentThread();
	//
	// byte[] data = "Hello".getBytes(CSUTF8);
	// att.buffer.put(data);
	// att.buffer.flip();
	// channel.write(read);

	// try {
	// att.mainThd.join();
	// } catch (InterruptedException ie) {
	// System.out.println("Client terminating");
	// }
    }

    @Override
    public void update(Observable o, Object arg) {
	// TODO write to the server
	System.out.println("Sender Start");

	ServerSocketChannel ssChannel;
	try {
	    ssChannel = ServerSocketChannel.open();
	    ssChannel.configureBlocking(false);
	    int port = 12345;
	    ssChannel.socket().bind(new InetSocketAddress(port));

	    String obj = "testtext";

	    while (true) {
		SocketChannel sChannel = ssChannel.accept();

		ObjectOutputStream oos = new ObjectOutputStream(sChannel.socket().getOutputStream());
		oos.writeObject(obj);
		oos.close();

		System.out.println("Connection ended");
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /*
     * Testing
     */

    private void testingNIO() {
	int PORT = 9090;
	String HOST = "localhost";

	AsynchronousSocketChannel channel;
	try {
	    channel = AsynchronousSocketChannel.open();
	} catch (IOException ioe) {
	    System.err.println("Unable to open client socket channel");
	    return;
	}

	try {
	    channel.connect(new InetSocketAddress(HOST, PORT)).get();
	    System.out.printf("Client at %s connected%n", channel.getLocalAddress());

	    /*
	     * I need to convert the object to a byte array, to then give to the
	     * channel which will then send it over the network
	     */

	    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(bytes);

	    byte[] arrayOfBytes = bytes.toByteArray();
	    ByteBuffer buff = ByteBuffer.wrap(arrayOfBytes);
	    channel.write(buff, new Object(), new CompletionHandler<Integer, Object>() {

		@Override
		public void completed(Integer result, Object attachment) {
		    // TODO Auto-generated method stub
		    System.out.println("I write");
		}

		@Override
		public void failed(Throwable exc, Object attachment) {
		    // TODO Auto-generated method stub

		}

	    });

	} catch (ExecutionException | InterruptedException eie) {
	    System.err.println("Server not responding");
	    return;
	} catch (IOException ioe) {
	    System.err.println("Unable to obtain client socket channel’s " + "local address");
	    return;
	}
    }
}
