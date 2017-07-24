package selectordemo2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo4_附加对象 {

	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();
		
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 9999));
		serverSocketChannel.configureBlocking(false);
		SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT | SelectionKey.OP_WRITE);
		
		BeAttachment attachment1 = new BeAttachment();
		selectionKey.attach(attachment1);
		
		BeAttachment attachment2 = new BeAttachment();
		SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, attachment2);
	
		while ( true ) {
			int readyChannels = selector.select();
			if ( readyChannels == 0 ) continue;
			
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
			
			while ( keyIterator.hasNext() ) {
				SelectionKey temp_key = keyIterator.next();
				
				if ( temp_key.isAcceptable() ) {
					// a connection was accepted by a ServerSocketChannel.
				} else if ( temp_key.isConnectable() ) {
					// a connection was established with a remote server.
				} else if ( temp_key.isReadable() ) {
					// a channel is ready for reading
				} else if ( temp_key.isWritable() ) {
					// a channel is ready for wirting
				}
				
				keyIterator.remove();
			}
		}
	}

}













