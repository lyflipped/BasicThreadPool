package thread;

import org.omg.Messaging.SyncScopeHelper;

public class ExitThread extends Thread{
	private volatile boolean closed = false;
	private int x = 0;
	@Override
	public void run() {
		while(!closed && !isInterrupted()){
			System.out.println("��������..."+(x++));
		}
		System.out.println("�ж��ˣ���Ҫ������");
	}
	
	public void close(){
		this.closed = true;
		this.interrupt();
	}
	
}
