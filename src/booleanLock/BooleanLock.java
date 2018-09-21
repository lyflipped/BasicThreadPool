package booleanLock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class BooleanLock implements Lock{
	//当前拥有锁的线程
	private Thread currentThread;
	//false代表当前该锁没有被任何线程获得，true表示已经被某个线程获取
	private boolean  locked = false;
	//存储因为获取该锁时进入阻塞状态的线程
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
						throw new TimeoutException("不能获得锁 在此"+mills+" 时间内");
					}
					if(!blockedList.contains(Thread.currentThread())){
						blockedList.add(Thread.currentThread());
					}
					this.wait();
					//每次更新时间，看看有没有等够这些时长
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
			//只有获取到锁的线程才有资格解锁
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
