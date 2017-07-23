package selectordemo2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class SelectorDemo2 {

	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();
		
		RandomAccessFile raf = 
				new RandomAccessFile("/home/holdbelief/Repository/git仓库/gitDemo/user2/gitrepository/readme.txt", "rw");
		FileChannel fileChannel = raf.getChannel();
		
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress("192.168.1.109", 9999));
		serverSocketChannel.configureBlocking(false);
		SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_CONNECT);
		int interestSet = selectionKey.interestOps();
		boolean isInterestedInAccept = ((interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT);
	}

}













