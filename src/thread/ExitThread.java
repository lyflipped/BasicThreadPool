package thread;

import org.omg.Messaging.SyncScopeHelper;

public class ExitThread extends Thread{
	private volatile boolean closed = false;
	private int x = 0;
	@Override
	public void run() {
		while(!closed && !isInterrupted()){
			System.out.println("我在运行..."+(x++));
		}
		System.out.println("中断了，我要结束了");
	}
	
	public void close(){
		this.closed = true;
		this.interrupt();
	}
	
}
