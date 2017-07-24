package selectordemo2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class SelectorDemo4_∏Ωº”∂‘œÛ {

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
	}

}













