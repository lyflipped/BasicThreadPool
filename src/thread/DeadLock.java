package thread;
public class DeadLock {
	private final Object MUTEX_READ = new Object();
	private final Object MUTEX_WRITE = new Object();
	public void read(){
		synchronized (MUTEX_READ) {
			System.out.println("���������");
			synchronized (MUTEX_WRITE) {
				System.out.println("�����������д����");
			}
		}
		System.out.println("���������");
	}
	public void write(){
		synchronized (MUTEX_WRITE) {
			System.out.println("����д����");
			synchronized (MUTEX_READ) {
				System.out.println("д��������Ķ�����");
			}
		}
		System.out.println("д�������");
	}
}
