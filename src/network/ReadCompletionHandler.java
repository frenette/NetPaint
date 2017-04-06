package network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.CompletionHandler;
import java.util.Vector;

import model.PaintObject;
import model.PaintObjectCollection;

public class ReadCompletionHandler implements CompletionHandler<Integer, PaintObjectCollection> {

    @SuppressWarnings("unchecked")
    @Override
    public void completed(Integer result, PaintObjectCollection attachment) {
	/*
	 * Read from the attachment.byteBuffer ByteBuffer and then add the
	 * PaintObject to the PaintObjectCollection
	 */
	ByteArrayInputStream bytes = new ByteArrayInputStream(attachment.byteBuffer.array());
	try (ObjectInputStream ois = new ObjectInputStream(bytes)) {
	    Vector<PaintObject> objs = (Vector<PaintObject>) ois.readObject();
	    
	    for (PaintObject o : objs) {
		System.out.println(o);
	    }
	    
	    attachment.setPaintObjects(objs);
	} catch (IOException | ClassNotFoundException e) {
	    e.printStackTrace();
	}
	
	// reset the ByteBuffer
	attachment.byteBuffer.clear();
	
	/*
	 * Recursively create ReadCompletionHandler
	 */
	attachment.channel.read(attachment.byteBuffer, attachment, new ReadCompletionHandler());

    }

    @Override
    public void failed(Throwable exc, PaintObjectCollection attachment) {
	// TODO Auto-generated method stub

    }

}
