package Controller;

import java.nio.channels.CompletionHandler;

public class ReadCompletionHandler implements CompletionHandler<Integer, Attachment> {

    @Override
    public void completed(Integer result, Attachment attachment) {
	// TODO Auto-generated method stub
	
	/*
	 * Recursively create completion handler
	 */
	
	
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {
	// TODO Auto-generated method stub
	
    }

}
