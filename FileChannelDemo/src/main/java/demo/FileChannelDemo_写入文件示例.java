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
//			不能使用下面这个方法，因为将字符串转化成byte数组再使用ByteBuffer.put(bytes)方法放入
//			ByteBuffer的时候，ByteBuffer可能容量不够，比如48不够用，设置多大也不合适
//			ByteBuffer destinationBuff = ByteBuffer.allocate(48); 
			
			FileChannel destinationFileChannel = destinationRandomAccessFile.getChannel();
			ByteBuffer destinationBuff = ByteBuffer.wrap(sourceTxt.toString().getBytes());
			System.out.println(destinationBuff.limit());
			System.out.println(destinationBuff.capacity());
			System.out.println(destinationBuff.position());
			
//			destinationBuff.flip();
			/*
			 * 如果是使用ByteBuffer.wrap方法之后不需要调用flip方法
			 * 
			 * 因为channel.read(bytebuffer) 方法是将 limit设置为 = capacity
			 * 当读取一个字节就将position位置加一
			 * 当在调用flip方法的时候将limit设置到position的位置，position设置为0
			 * 
			 * 而ByteBuffer.wrap方法和Channel.read(byteBuffer)方法是不同的
			 * 调用wrap方法之后，capacity = byte数组的大小 = limit，position的大小 = 0
			 * （position不会变的）
			 * limit capacity 和 position的状态正好和channel.read(byteBuffer)之后在调用flip方法之后
			 * 各个值的状态一样，所以调用ByteBuffer.wrap之后不需要调用flip方法
			 */
			
			System.out.println(destinationBuff.limit());
			System.out.println(destinationBuff.capacity());
			System.out.println(destinationBuff.position());
			
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
                                                           