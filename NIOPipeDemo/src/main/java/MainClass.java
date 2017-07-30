
public class MainClass {

	public static void main(String[] args) {
		Thread a = new Thread(new ThreadA());
		a.start();
		
		Thread b = new Thread(new ThreadB());
		b.start();
	}

}
