package Server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

import model.PaintObject;

public class ReadCompletionHandler implements CompletionHandler<Integer, Client> {

    @Override
    public void completed(Integer result, Client att) {
	System.out.println("I completed in ReadCompletionHandler.");

	ByteBuffer byteBuf = att.buf;

	if (byteBuf.position() != 0) {
	    /*
	     * Read the object from the client
	     */
	    try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteBuf.array()))) {
		PaintObject o = (PaintObject) ois.readObject();
		System.out.println(o);
		att.serverPaintObjectCollection.addToPaintObjects(o);
	    } catch (IOException | ClassNotFoundException e) {
		e.printStackTrace();
	    }

	    byteBuf.clear();
	    att.asynchronousSocketChannel.read(att.buf, att, new ReadCompletionHandler());
	} else {
	    System.out.println("I left ReadCompletionHandler.");
	    att.serverPaintObjectCollection.removeClient(att.asynchronousSocketChannel);
	}
    }

    @Override
    public void failed(Throwable exc, Client attachment) {
	System.err.println("I failed in ReadCompletionHandler.");
    }

}
