package Server;

import java.nio.channels.CompletionHandler;

public class ReadCompletionHandler implements CompletionHandler<Integer, Server> {

    @Override
    public void completed(Integer result, Server att) {
	// TODO Auto-generated method stub
	System.out.println("I completed in ReadCompletionHandler.");
    }

    @Override
    public void failed(Throwable exc, Server attachment) {
	// TODO Auto-generated method stub
	System.err.println("I failed in ReadCompletionHandler.");
    }

}
