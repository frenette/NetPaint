package Server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

import model.PaintObject;

public class ReadCompletionHandler implements CompletionHandler<Integer, Server> {

    @SuppressWarnings("static-access")
    @Override
    public void completed(Integer result, Server att) {
	// TODO Auto-generated method stub
	System.out.println("I completed in ReadCompletionHandler.");

	ByteBuffer byteBuf = att.serverPaintObjectCollection.getClient(att.serverPaintObjectCollection.getFirst()).buf;

	if (byteBuf.position() != 0) {
	    /*
	     * Read the object from the client
	     */
	    try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteBuf.array()))) {
		PaintObject o = (PaintObject) ois.readObject();
		System.out.println(o);
		att.serverPaintObjectCollection.addToPaintObjects(o);
	    } catch (IOException | ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	    byteBuf.clear();
	    att.serverPaintObjectCollection.getFirst().read(
		    att.serverPaintObjectCollection.getClient(att.serverPaintObjectCollection.getFirst()).buf, att,
		    new ReadCompletionHandler());
	} else {
	    System.out.println("I left ReadCompletionHandler.");

	    /*
	     * TODO : remove the Client from the ServerPaintObjectCollection
	     */
	}
    }

    @Override
    public void failed(Throwable exc, Server attachment) {
	// TODO Auto-generated method stub
	System.err.println("I failed in ReadCompletionHandler.");
    }

}
