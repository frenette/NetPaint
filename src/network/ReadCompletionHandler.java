package network;

import java.nio.channels.CompletionHandler;

import model.PaintObjectCollection;

public class ReadCompletionHandler implements CompletionHandler<Integer, PaintObjectCollection> {

    @Override
    public void completed(Integer result, PaintObjectCollection attachment) {
	// TODO Auto-generated method stub
	
	/*
	 * Read from the 
	 */

	/*
	 * Recursively create ReadCompletionHandler
	 */

    }

    @Override
    public void failed(Throwable exc, PaintObjectCollection attachment) {
	// TODO Auto-generated method stub

    }

}
