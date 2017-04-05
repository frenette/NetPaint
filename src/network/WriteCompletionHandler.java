package network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.CompletionHandler;

public class WriteCompletionHandler implements CompletionHandler<Integer, Attachment> {

    @Override
    public void completed(Integer result, Attachment attachment) {
	
	/*
	 * Send the tempObject to the server
	 */
	
	
	
	if (result == -1) {
	    try {
		att.channelClient.close();
		System.out.printf("Stopped listening to client %s%n", att.clientAddr);
	    } catch (IOException ioe) {
		ioe.printStackTrace();
	    }
	    return;
	}

	if (att.isReadMode) {
	    /*
	     * Testing
	     */
	    att.buffer.flip();

	    try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(att.buffer.array()));) {
		System.out.println(ois.readObject());
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	    att.isReadMode = false;
	    att.channelClient.write(att.buffer, att, this);
	    /*
	     * End Testing
	     */
	    // att.buffer.flip();
	    // int limit = att.buffer.limit();
	    // byte bytes[] = new byte[limit];
	    // att.buffer.get(bytes, 0, limit);
	    // System.out.printf("Client at %s sends message: %s%n",
	    // att.clientAddr, new String(bytes, CSUTF8));
	    //
	    // att.isReadMode = false;
	    //
	    // att.buffer.rewind();
	    // att.channelClient.write(att.buffer, att, this);
	} else {
	    att.isReadMode = true;

	    att.buffer.clear();
	    att.channelClient.read(att.buffer, att, this);
	}
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {
	System.out.println("Connection with client broken");
    }

}
