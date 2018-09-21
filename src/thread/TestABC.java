package thread;

public class TestABC {
	public static void main(String[] args){
		final Print print = new Print();
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
		new Thread(){
			@Override
			public void run(){
				print.printC();
			}
		}.start();
	}
}
