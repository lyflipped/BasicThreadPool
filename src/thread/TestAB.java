package thread;

public class TestAB {
	public static void main(String[] args){
		final PrintAB print = new PrintAB();
		new Thread(){
			@Override
			public void run(){
				print.printA();
			}
		}.start();
		new Thread(){
			@Override
			public void run(){
				print.printB();
			}
		}.start();
	}
}
