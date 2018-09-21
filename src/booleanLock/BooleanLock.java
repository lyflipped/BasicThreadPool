package booleanLock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class BooleanLock implements Lock{
	//��ǰӵ�������߳�
	private Thread currentThread;
	//false����ǰ����û�б��κ��̻߳�ã�true��ʾ�Ѿ���ĳ���̻߳�ȡ
	private boolean  locked = false;
	//�洢��Ϊ��ȡ����ʱ��������״̬���߳�
	private final List<Thread> blockedList = new ArrayList<>();
	@Override
	public void lock() throws InterruptedException {
		synchronized(this){
			while(locked){
				blockedList.add(Thread.currentThread());
				this.wait();
			}
			blockedList.remove(Thread.currentThread());
			this.locked = true;
			this.currentThread = Thread.currentThread();
		}
		
	}

	@Override
	public void lock(long mills) throws InterruptedException, TimeoutException {
		synchronized (this) {
			if(mills <= 0){
				this.lock();
			}else{
				long remainingMills = mills;
				long endMills = System.currentTimeMillis()+remainingMills;
				while(locked){
					if(remainingMills <= 0){
						throw new TimeoutException("���ܻ���� �ڴ�"+mills+" ʱ����");
					}
					if(!blockedList.contains(Thread.currentThread())){
						blockedList.add(Thread.currentThread());
					}
					this.wait();
					//ÿ�θ���ʱ�䣬������û�еȹ���Щʱ��
					remainingMills = endMills - System.currentTimeMillis();
				}
				blockedList.remove(Thread.currentThread());
				this.locked = true;
				this.currentThread = Thread.currentThread(); 
			}
		}
		
	}

	@Override
	public void unlock() {
		synchronized (this) {
			//ֻ�л�ȡ�������̲߳����ʸ����
			if(currentThread == Thread.currentThread()){
				this.locked = false;
				this.notifyAll();
			}
		}
		
	}

	@Override
	public List<Thread> getBlockedThreads() {
		return Collections.unmodifiableList(blockedList);
	}

}
