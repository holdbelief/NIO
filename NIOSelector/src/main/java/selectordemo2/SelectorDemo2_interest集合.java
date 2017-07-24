package selectordemo2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class SelectorDemo2_interest集合 {

	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();
		
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 9999));
		serverSocketChannel.configureBlocking(false);
		SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		int interestSet = selectionKey.interestOps();
		boolean isInterestedInAccept = ((interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT);
		boolean isInterestedInConnect = (interestSet & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT;
		boolean isInterestInRead = (interestSet & SelectionKey.OP_READ) == SelectionKey.OP_READ;
		boolean isInterestInWrite = (interestSet & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE;
		
		System.out.println("isInterestedInAccept = " + isInterestedInAccept);
		System.out.println("isInterestedInConnect = " + isInterestedInConnect);
		System.out.println("isInterestInRead = " + isInterestInRead);
		System.out.println("isInterestInWrite = " + isInterestInWrite);
		
		int i = 10 | 15;
		System.out.println(i & 15);
		
		int y = 246 | 76;
		System.out.println(y & 246);
		
		int z = 132;
		System.out.println(z & 132);
	}

}













