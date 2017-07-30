import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;

public class ThreadA implements Runnable {

	@Override
	public void run() {
		Pipe pipe = null;
		try {
			pipe = Pipe.open();
			SinkChannel sinkChannel = pipe.sink();
			String newData = "New String to write to file..." + System.currentTimeMillis();
			ByteBuffer buf = ByteBuffer.allocate(48);
			buf.clear();
			
			buf.put(newData.getBytes());
			
			buf.flip();
			
			while ( buf.hasRemaining() ) {
				sinkChannel.write(buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
