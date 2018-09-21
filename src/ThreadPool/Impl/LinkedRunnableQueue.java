package ThreadPool.Impl;

import java.util.LinkedList;

import ThreadPool.DenyPolicy;
import ThreadPool.RunnableQueue;
import ThreadPool.ThreadPool;

public class LinkedRunnableQueue implements RunnableQueue{
	//任务队列的最大容量
	private final int limit;
	//任务队列已满的时候需要执行的拒绝策略
	private final DenyPolicy denyPolicy;
	//存放任务的队列
	private final LinkedList<Runnable> runnableList = new LinkedList<>();
	private final ThreadPool threadPool;
	public LinkedRunnableQueue(int limit,DenyPolicy denyPolicy,ThreadPool threadPool) {
		this.limit = limit;
		this.denyPolicy = denyPolicy;
		this.threadPool = threadPool;
	}
	@Override
	public void offer(Runnable runnable) {
		synchronized(runnableList){
			if(runnableList.size() >= limit){
				denyPolicy.reject(runnable, threadPool);
			}else{
				runnableList.addLast(runnable);
				//通知所有要使用runnableList的线程
				runnableList.notifyAll();
			}
		}
		
	}

	@Override
	public Runnable take() throws InterruptedException {
		synchronized (runnableList) {
			while(runnableList.isEmpty()){
				try {
					runnableList.wait();
				} catch (InterruptedException e) {
					throw e;
				}
			}
			return runnableList.removeFirst();
		}
	}

	@Override
	public int size() {
		synchronized (runnableList) {
			return runnableList.size();
		}
	}
	

}
