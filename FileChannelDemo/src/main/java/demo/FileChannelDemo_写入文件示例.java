package demo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo_写入文件示例 {
	public static void main(String[] args) throws IOException {
		RandomAccessFile sourceFile = null;
		StringBuffer sourceTxt = new StringBuffer("");

		try {
			sourceFile = new RandomAccessFile("data/nio-data.txt", "r");
			ByteBuffer sourceBuff = ByteBuffer.allocate(1024);
			FileChannel sourceFc = sourceFile.getChannel();
			int readedBytes = sourceFc.read(sourceBuff);
			while ( readedBytes != -1 ) {
				sourceBuff.flip();
				while ( sourceBuff.hasRemaining() ) {
					byte[] bytes = new byte[sourceBuff.remaining()];
					sourceBuff.get(bytes);
					String temp = new String(bytes, "utf-8");
					sourceTxt.append(temp);
				}
				
				sourceBuff.clear();
				readedBytes = sourceFc.read(sourceBuff);
			}
		} finally {
			if ( sourceFile != null ) {
				sourceFile.close();
			}
		}
		
		RandomAccessFile destinationRandomAccessFile = null;
		
		try {
			File destintionFile = new File("data/nio-writedemo.txt");
			if ( destintionFile.exists() == false ) {
				destintionFile.createNewFile();
			}
			
			destinationRandomAccessFile = new RandomAccessFile(destintionFile, "rw");
			ByteBuffer destinationBuff = ByteBuffer.allocate(48);
			FileChannel destinationFileChannel = destinationRandomAccessFile.getChannel();
			
			sourceTxt.toString()
			destinationBuff.put(sourceTxt.toString().getBytes());
			destinationBuff.flip();
			while ( destinationBuff.hasRemaining() ) {
				destinationFileChannel.write(destinationBuff);
			}
		} finally {
			if ( destinationRandomAccessFile != null ) {
				destinationRandomAccessFile.close();
			}
		}
	}
}
                                                           