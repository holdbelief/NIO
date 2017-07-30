import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelSender {
	public static void main(String[] args) {
		try {
			send();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	private static void send() throws IOException {
		DatagramChannel channel = null;
		
		try {
			channel.open();
			ByteBuffer buffer = ByteBuffer.wrap("下雨的夜晚很安静".getBytes("utf-8"));
			channel.send(buffer, new InetSocketAddress("localhost", 10022));
		} finally {
			channel.close();
		}
		
	}
}
