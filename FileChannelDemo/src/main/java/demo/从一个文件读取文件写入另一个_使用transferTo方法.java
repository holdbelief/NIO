package demo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class 从一个文件读取文件写入另一个_使用transferTo方法 {
	public static void fileChannelCopy() {
		File sourceFile = new File("data/nio-data.txt"); 
		File destinationFile = new File("data/从一个文件读取文件写入另一个_使用transferTo方法.txt");
		
		RandomAccessFile sourceRandomAccessFile = null;
		RandomAccessFile destinationRandomAccessFile = null;
		
		try {
			sourceRandomAccessFile = new RandomAccessFile(sourceFile, "rw");
			destinationRandomAccessFile = new RandomAccessFile(destinationFile, "rw");
			
			FileChannel sourceFileChannel = sourceRandomAccessFile.getChannel();
			FileChannel destinationFileChannel = destinationRandomAccessFile.getChannel();
			
			sourceFileChannel.transferTo(0, sourceFileChannel.size(), destinationFileChannel);
			
		} catch ( IOException e ) {
			e.printStackTrace();
		} finally {
			if ( sourceRandomAccessFile != null ) {
				try {
					sourceRandomAccessFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if ( destinationRandomAccessFile != null ) {
				try {
					destinationRandomAccessFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		fileChannelCopy();
	}
}
