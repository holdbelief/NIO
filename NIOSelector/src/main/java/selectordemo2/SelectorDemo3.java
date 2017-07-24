package selectordemo2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class SelectorDemo3 {

	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();
		
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 9999));
		serverSocketChannel.configureBlocking(false);
		SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT | SelectionKey.OP_WRITE);
		int interestSet = selectionKey.interestOps();
		boolean isInterestedInAccept = ((interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT);
		boolean isInterestedInConnect = (interestSet & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT;
		boolean isInterestInRead = (interestSet & SelectionKey.OP_READ) == SelectionKey.OP_READ;
		boolean isInterestInWrite = (interestSet & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE;
		
		System.out.println("isInterestedInAccept = " + isInterestedInAccept);
		System.out.println("isInterestedInConnect = " + isInterestedInConnect);
		System.out.println("isInterestInRead = " + isInterestInRead);
		System.out.println("isInterestInWrite = " + isInterestInWrite);
	}

}













