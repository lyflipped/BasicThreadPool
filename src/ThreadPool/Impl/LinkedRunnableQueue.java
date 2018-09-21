package ThreadPool.Impl;

import java.util.LinkedList;

import ThreadPool.DenyPolicy;
import ThreadPool.RunnableQueue;
import ThreadPool.ThreadPool;

public class LinkedRunnableQueue implements RunnableQueue{
	//������е��������
	private final int limit;
	//�������������ʱ����Ҫִ�еľܾ�����
	private final DenyPolicy denyPolicy;
	//�������Ķ���
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
				//֪ͨ����Ҫʹ��runnableList���߳�
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
