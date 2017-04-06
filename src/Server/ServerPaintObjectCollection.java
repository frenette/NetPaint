package Server;

import java.awt.Color;
import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.HashMap;
import java.util.Observable;
import java.util.Vector;

import model.PaintObject;
import model.Rectangle;

public class ServerPaintObjectCollection extends Observable {

    /*
     * We need a list of all of the connections that have been made
     */
    private HashMap<AsynchronousSocketChannel, Client> clients = new HashMap<>();

    public synchronized AsynchronousSocketChannel getFirst() {
	return this.clients.keySet().iterator().next();
    }

    public synchronized void addClient(Client client) {
	this.clients.put(client.asynchronousSocketChannel, client);
    }

    public synchronized void removeClient(AsynchronousSocketChannel channel) {
	this.clients.remove(channel);
    }

    public synchronized Client getClient(AsynchronousSocketChannel channel) {
	return this.clients.get(channel);
    }

    // public synchroni
    /*
     * End testing
     */

    private Vector<PaintObject> paintObjects;

    public ServerPaintObjectCollection() {
	this.paintObjects = new Vector<>();

//	paintObjects.add(new Rectangle(Color.RED, new Point(0, 0), new Point(10, 100)));
//	paintObjects.add(new Rectangle(Color.BLUE, new Point(100, 100), new Point(980, 0)));
//	paintObjects.add(new Rectangle(Color.GREEN, new Point(20, 82), new Point(34, 0)));
//	paintObjects.add(new Rectangle(Color.BLACK, new Point(40, 73), new Point(40, 0)));
//	paintObjects.add(new Rectangle(Color.CYAN, new Point(320, 93), new Point(70, 0)));
//	paintObjects.add(new Rectangle(Color.ORANGE, new Point(430, 42), new Point(0, 90)));
    }

    public Vector<PaintObject> getPaintObjects() {
	return this.paintObjects;
    }

    public void addToPaintObjects(PaintObject o) {
	this.paintObjects.addElement(o);

	/*
	 * Notify all clients
	 */
	// serialize all of the data
	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	try (ObjectOutputStream oos = new ObjectOutputStream(bytes);) {
	    oos.writeObject(this.getPaintObjects());
	} catch (IOException e) {
	    e.printStackTrace();
	}

	/*
	 * Testing
	 */
	System.out.println("=================================");
	System.out.println("Size of buffer: " + bytes.size());
	for (PaintObject obj : this.paintObjects) {
	    System.out.println(obj);
	}
	System.out.println("=================================");
	/*
	 * End testing
	 */

	System.out.println("I should be notifying al of the new clients of the change");

	this.clients.forEach((AsynchronousSocketChannel key, Client client) -> {
	    client.asynchronousSocketChannel.write(ByteBuffer.wrap(bytes.toByteArray()), this,
		    new CompletionHandler<Integer, ServerPaintObjectCollection>() {

			@Override
			public void completed(Integer result, ServerPaintObjectCollection attachment) {
			    // TODO Auto-generated method stub
			    System.out.println("I just sent a msg to the client of the new Vector<PaintObject>");
			}

			@Override
			public void failed(Throwable exc, ServerPaintObjectCollection attachment) {
			    // TODO Auto-generated method stub
			    System.out.println(
				    "I just FAILED to send a msg to the client of the new Vector<PaintObject>");
			}
		    });
	});
	/*
	 * End testing
	 */
    }
}
