package network;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

import model.PaintObjectCollection;

public class Attachment {
    
    /*
     * 
     */
    public PaintObjectCollection paintObjectCollection;
    /*
     * 
     */
    
    public AsynchronousSocketChannel channel;
    public boolean isReadMode;
    public ByteBuffer buffer;
    public Thread mainThd;
}