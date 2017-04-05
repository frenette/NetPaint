package Testing;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

import Server.ReadWriteHandler;
import model.PaintObject;
import model.Rectangle;
import network.Attachment;

public class Testing {

    private final static int PORT = 9090;

    private final static String HOST = "localhost";

    public static void main(String[] args) {

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
	} catch (ExecutionException | InterruptedException eie) {
	    System.err.println("Server not responding");
	    return;
	} catch (IOException ioe) {
	    System.err.println("Unable to obtain client socket channel’s " + "local address");
	    return;
	}

	// try {
	// att.mainThd.join();
	// } catch (InterruptedException ie) {
	// System.out.println("Client terminating");
	// }

	// TODO Auto-generated method stub
	PaintObject obj = new Rectangle(Color.RED, null, null);
	ByteBuffer buff = ByteBuffer.allocate(2048);

	/*
	 * I need to convert the object to a byte array, to then give to the
	 * channel which will then send it over the network
	 */
	try {
	    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(bytes);
	    oos.writeObject(obj);
	    oos.writeObject(new String("Hello"));

	    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()));

	    PaintObject o = (PaintObject) ois.readObject();
	    String s = (String) ois.readObject();

	    System.out.println(o);
	    System.out.println(s);

	    System.out.println("Size of bytes: " + bytes.size());
	    
	    /*
	     * Shit
	     */
	    
		Attachment att = new Attachment();
		att.channel = channel;
		att.isReadMode = false;
		att.buffer = ByteBuffer.allocate(2048);
		att.mainThd = Thread.currentThread();


		channel.write(ByteBuffer.wrap(bytes.toByteArray()));
//		channel.write(ByteBuffer.wrap(bytes.toByteArray()), att, new ReadWriteHandler());
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
