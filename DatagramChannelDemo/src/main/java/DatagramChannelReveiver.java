import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

public class DatagramChannelReveiver {

	public static void main(String[] args) {
		try {
			receive();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	private static void receive() throws IOException {
		DatagramChannel channel = null;
		
		try {
			channel = DatagramChannel.open();
			channel.socket().bind(new InetSocketAddress(10022));
			
			ByteBuffer buffer = ByteBuffer.allocate(60);
			
			while ( channel.receive(buffer) == null ) {
				try {
					Thread.sleep(1000);
				} catch ( InterruptedException e ) {
					e.printStackTrace();
				}
			}
			
			buffer.flip();
			
			String recStr = Charset.forName("utf-8").newDecoder().decode(buffer).toString();
			System.out.println(recStr);
		}  finally {
			if ( channel != null ) {
				channel.close();
			}
		}
	}

}
