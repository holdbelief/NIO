package test;

import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadNIOEchoServer {
	public static Map<Socket, Long> geym_time_start = new HashMap<>();
	private Selector selector;
	private ExecutorService tp = Executors.newCachedThreadPool();
	
	class EchoClient {
		private LinkedList<ByteBuffer> outq;
		
		EchoClient() {
			this.outq = new LinkedList<ByteBuffer>();
		}
		
		public LinkedList<ByteBuffer> getOutputQueue() {
			return this.outq;
		}
		
		public void enqueue(ByteBuffer bb) {
			this.outq.addFirst(bb);
		}
	}
	
	class HandleMsg implements Runnable {
		SelectionKey sk;
		ByteBuffer bb;
		
		public HandleMsg(SelectionKey sk, ByteBuffer bb) {
			super();
			this.sk = sk;
			this.bb = bb;
		}
		
		@Override
		public void run() {
			EchoClient echoClient = (EchoClient) sk.attachment();
			echoClient.enqueue(bb);
			sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			selector.wakeup();
		}
		
	}
	
	private void startServer() throws Exception {
		this.selector = SelectorProvider.provider().openSelector(); // 等同于 Selector.open(); 实际上 Selector.open(); 内部调用的就是 SelectorProvider.provider().openSelector();
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		
		// 注册感兴趣的事件，此处对accept事件感兴趣
		SelectionKey acceptKey = ssc.register(this.selector, SelectionKey.OP_ACCEPT);
		for (;;) {
			this.selector.select();
			Set readyKeys = this.selector.selectedKeys();
			Iterator i_readyKeys = readyKeys.iterator();
			long e = 0;
			while ( i_readyKeys.hasNext() ) {
				SelectionKey sk = (SelectionKey) i_readyKeys.next();
				i_readyKeys.remove();
				
				if ( sk.isAcceptable() ) {
					doAccept(sk);
				} else if ( sk.isValid() && sk.isReadable() ) {
					if ( !geym_time_start.containsKey(((SocketChannel) sk.channel()).socket()) ) {
						geym_time_start.put(((SocketChannel) sk.channel()).socket(), System.currentTimeMillis());
					}
					doRead(sk);
				} else if ( sk.isValid() && sk.isWritable() ) {
					doWrite(sk);
					e = System.currentTimeMillis();
					long b = geym_time_start.remove(((SocketChannel)sk.channel()).socket());
					System.out.println("spend:" + (e -b) + "ms");
				}
			}
		}
	}

	private void doWrite(SelectionKey sk) {
		SocketChannel channel = (SocketChannel) sk.channel();
		EchoClient echoClient = (EchoClient) sk.attachment();
		LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();
		ByteBuffer bb = outq.getLast();
		try {
			int len = channel.write(bb);
			if ( len == -1 ) {
				disconnect(sk);
				return;
			}
			if ( bb.remaining() == 0 ) {
				outq.removeLast();
			}
		} catch ( Exception e ) {
			disconnect(sk);
		}
		
		if ( outq.size() == 0 ) {
			sk.interestOps(SelectionKey.OP_READ);
		}
	}

	private void doRead(SelectionKey sk) {
		SocketChannel channel = (SocketChannel) sk.channel();
		ByteBuffer bb = ByteBuffer.allocate(8192);
		int len;
		try {
			len = channel.read(bb);
			if ( len < 0 ) {
				disconnect(sk);
				return;
			}
		} catch ( Exception e ) {
			disconnect(sk);
			return;
		}
	}

	private void disconnect(SelectionKey sk) {
		// TODO Auto-generated method stub
		
	}

	private void doAccept(SelectionKey sk) {
		ServerSocketChannel server = (ServerSocketChannel) sk.channel();
		SocketChannel clientChannel;
		try {
			clientChannel = server.accept();
			clientChannel.configureBlocking(false);
			SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
			EchoClient echoClient = new EchoClient();
			clientKey.attach(echoClient);
			InetAddress clientAddress = clientChannel.socket().getInetAddress();
			System.out.println("Accepted connection from " + clientAddress.getHostAddress());
		} catch ( Exception e ) {
			
		}
	}
	
	public static void main(String[] args) {
		MultiThreadNIOEchoServer echoServer = new MultiThreadNIOEchoServer();
		try {
			echoServer.startServer();
		} catch ( Exception e ) {
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
