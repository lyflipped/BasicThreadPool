package thread;

public class TestDeadLock {
	public static void main(String[] args){
		final DeadLock deadLock = new DeadLock();
		new Thread(()->{
			while(true){
				deadLock.read();
			}
		},"READ-THREAD").start();;
		new Thread(()->{
			while(true){
				deadLock.write();
			}
		},"WRITE-THREAD").start();;
	}
}
