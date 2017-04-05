package model;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Observable;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import Server.ServerPaintObjectCollection;

public class PaintObjectCollection extends Observable {

    private final static int PORT = 9090;
    private final static String HOST = "localhost";

    public AsynchronousSocketChannel channel;
    public ByteBuffer byteBuffer;

    private Vector<PaintObject> paintObjects;
    private PaintObject tempPaintObject;

    public PaintObjectCollection() {
	this.paintObjects = new Vector<>();
	this.byteBuffer = ByteBuffer.allocate(2048);
	connectToServer();
    }

    public Vector<PaintObject> getAllPaintObjects() {
	Vector<PaintObject> allPaintObjects = new Vector<>();
	allPaintObjects.addAll(this.paintObjects);
	allPaintObjects.add(this.tempPaintObject);
	return allPaintObjects;
    }

    public Vector<PaintObject> getPaintObjects() {
	return paintObjects;
    }

    /*
     * When the server sends over a Vector<PaintObjects> we will then call this
     * method
     */
    public void setPaintObjects(Vector<PaintObject> paintObjects) {
	this.paintObjects = paintObjects;
	this.setChanged();
	this.notifyObservers();
    }

    public PaintObject getTempPaintObject() {
	return this.tempPaintObject;
    }

    /*
     * The server only needs to know when we add the final object.
     */
    public void setTempPaintObject(PaintObject tempPaintObject) {
	this.tempPaintObject = tempPaintObject;

	/*
	 * TODO : remove "this.paintObjects.addElement(tempPaintObject);" only
	 * here for testing
	 */
	this.paintObjects.addElement(tempPaintObject);
	/*
	 * END TODO
	 */

	this.setChanged();
	this.notifyObservers();
    }

    public void setTempPaintObjectEnd(Point end) {
	this.tempPaintObject.setEnd(end);
	this.setChanged();
	this.notifyObservers();
    }

    /*
     * When this method is called we need to contact the server and let it know
     * we added a PaintObject
     */
    public void setTempPaintObjectAsPermenant() {
	/*
	 * Notify server
	 */

	// get the tempPaintObject
	PaintObject serializeTemp = this.tempPaintObject;

	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	try (ObjectOutputStream oos = new ObjectOutputStream(bytes)) {
	    oos.writeObject(serializeTemp);
	    // TODO
	    this.channel.write(ByteBuffer.wrap(bytes.toByteArray()));
	} catch (IOException e) {
	    e.printStackTrace();
	}

	// this.setChanged();
	// this.notifyObservers();
    }

    /*
     * Networking stuff
     */

    public void connectToServer() {
	System.out.println("connectToServer()");

	try {
	    this.channel = AsynchronousSocketChannel.open();
	} catch (IOException ioe) {
	    System.err.println("Unable to open client socket channel");
	    return;
	}

	try {
	    // blocking
	    this.channel.connect(new InetSocketAddress(HOST, PORT)).get();
	    System.out.printf("Client at %s connected%n", channel.getLocalAddress());
	} catch (ExecutionException | InterruptedException eie) {
	    System.err.println("Server not responding");
	    return;
	} catch (IOException ioe) {
	    System.err.println("Unable to obtain client socket channel’s " + "local address");
	    return;
	}

	/*
	 * Once we have connected, constantly check to see if there is anything
	 * to read.
	 */

	System.out.println("null check");
	while (this.byteBuffer == null) {
	    try {
		System.out.println("null check...");
		Thread.sleep(500);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	System.out.println("I am about to read!");
	this.channel.read(this.byteBuffer, this, new CompletionHandler<Integer, PaintObjectCollection>() {

	    @Override
	    public void completed(Integer result, PaintObjectCollection attachment) {
		// TODO Auto-generated method stub
		System.out.println("I finished reading!");
		ByteArrayInputStream bytes = new ByteArrayInputStream(attachment.byteBuffer.array());
		try (ObjectInputStream ois = new ObjectInputStream(bytes)) {
		    Vector<PaintObject> objs = (Vector<PaintObject>) ois.readObject();
		    for (PaintObject o : objs) {
			System.out.println(o);
		    }

		    setPaintObjects(objs);
		    // this.channel.write(ByteBuffer.wrap(bytes.toByteArray()));
		} catch (IOException | ClassNotFoundException e) {
		    e.printStackTrace();
		}
	    }

	    @Override
	    public void failed(Throwable exc, PaintObjectCollection attachment) {
		// TODO Auto-generated method stub
		System.out.println("I just FAILED to send a msg to the client of the new Vector<PaintObject>");
	    }
	});

	// // TODO
	// Future<Integer> fut = this.channel.read(this.byteBuffer);
	//
	// while (!fut.isDone()) {
	// try {
	// Thread.sleep(500);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// System.out.println("I finished reading!");
	//
	//
	// // ObjectInputStream
	// ByteArrayInputStream bytes = new
	// ByteArrayInputStream(this.byteBuffer.array());
	// try (ObjectInputStream ois = new ObjectInputStream(bytes)) {
	// Vector<PaintObject> objs = (Vector<PaintObject>) ois.readObject();
	// for (PaintObject o : objs) {
	// System.out.println(o);
	// }
	//
	// setPaintObjects(objs);
	// // this.channel.write(ByteBuffer.wrap(bytes.toByteArray()));
	// } catch (IOException | ClassNotFoundException e) {
	// e.printStackTrace();
	// }
    }

}
