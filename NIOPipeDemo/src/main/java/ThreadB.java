import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class ThreadB implements Runnable {

	@Override
	public void run() {
		Pipe pipe = null;
		StringBuilder sb = new StringBuilder("");
		
		try {
			pipe = Pipe.open();
			Pipe.SourceChannel sourceChannel = pipe.source();
			ByteBuffer buf = ByteBuffer.allocate(48);
			int bytesRead = -1;
			while ( (bytesRead = sourceChannel.read(buf)) != -1 ) {
				buf.flip();
				while ( buf.hasRemaining() ) {
					byte[] dst = new byte[ buf.remaining() ];
					buf.get(dst);
					String temp = new String(dst, "utf-8");
					sb.append(temp);
				}
				
				buf.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(sb.toString());
	}

}
