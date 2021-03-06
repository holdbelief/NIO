package demo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo_读取文件示例_And_ByteBufferString转换 {
	public static void main(String[] args) throws IOException {
		RandomAccessFile aFile = null;
		FileChannel inChannel = null;
		
		try {
			aFile = new RandomAccessFile("data/nio-data.txt", "rw");
			inChannel = aFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(1024);
			int bytesRead = inChannel.read(buf);
			
			while ( bytesRead != -1 ) {
				buf.flip();
				
				byte[] bytes = new byte[buf.remaining()];
				buf.get(bytes);
				String txt = new String(bytes, "UTF-8");
				System.out.print(txt);
				
				buf.clear();
				bytesRead = inChannel.read(buf);
			}
		} finally {
			if ( inChannel != null ) {
				inChannel.close();
			}
			if ( aFile != null ) {
				aFile.close();
			}
		}
	}
}
