package thread;
public class DeadLock {
	private final Object MUTEX_READ = new Object();
	private final Object MUTEX_WRITE = new Object();
	public void read(){
		synchronized (MUTEX_READ) {
			System.out.println("进入读操作");
			synchronized (MUTEX_WRITE) {
				System.out.println("读操作里面的写操作");
			}
		}
		System.out.println("读操作完成");
	}
	public void write(){
		synchronized (MUTEX_WRITE) {
			System.out.println("进入写操作");
			synchronized (MUTEX_READ) {
				System.out.println("写操作里面的读操作");
			}
		}
		System.out.println("写操作完成");
	}
}
