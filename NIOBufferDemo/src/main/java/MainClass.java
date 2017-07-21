import java.nio.ByteBuffer;

public class MainClass {

	public static void main(String[] args) throws Exception {
		ByteBuffer b = ByteBuffer.allocate(15);
		System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
		+ " position=" + b.position());
		
		for ( int i = 0; i < 10; i++ ) {
			// 存入10个字节的数据
			b.put((byte) i);
		}
		
		System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
		+ " position=" + b.position());
		
		b.flip(); // 重置position
		
		System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
		+ " position=" + b.position());
		
		for (int i = 0; i < 5; i++) {
			System.out.print(b.get());
		}
		
		System.out.println();
		
		System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
		+ " position=" + b.position());
		
		b.flip(); // 重置position
		
		System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
		+ " position=" + b.position());
		
		for ( int i = 20; i < 24; i++ ) {
			b.put((byte) i);
		}
		
		b.flip();
		
		System.out.println("limit=" + b.limit() + " capacity=" + b.capacity()
		+ " position=" + b.position());
		
		for ( int i = 0; i < 4; i++ ) {
			System.out.println(b.get());
		}
	}

}
