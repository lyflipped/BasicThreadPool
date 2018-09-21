package ThreadPool.Impl;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import ThreadPool.DenyPolicy;
import ThreadPool.InternalTask;
import ThreadPool.RunnableQueue;
import ThreadPool.ThreadFactory;
import ThreadPool.ThreadPool;

public class BasicThreadPool extends Thread implements ThreadPool{
	private final int initSize;
	private final int maxSize;
	private final int coreSize;
	private int activeCount;
	private final ThreadFactory threadFactory;
	private final RunnableQueue runnableQueue;
	private volatile boolean isShutdown = false;
	private final Queue<ThreadTask> threadQueue = new ArrayDeque<>();
	private final static DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();
	private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();
	private final long keepAliveTime;
	private final TimeUnit timeUnit;
	public BasicThreadPool(int initSize,int maxSize,int coreSize,int queueSize) {
		this(initSize,maxSize,coreSize,DEFAULT_THREAD_FACTORY,queueSize,DEFAULT_DENY_POLICY,10,TimeUnit.SECONDS);
	}
	public BasicThreadPool(int initSize,int maxSize,int coreSize,ThreadFactory threadFactory,
					int queueSize,DenyPolicy denyPolicy,long keepAliveTime,TimeUnit timeUnit) {
		this.initSize = initSize;
		this.coreSize = coreSize;
		this.maxSize = maxSize;
		this.threadFactory = threadFactory;
		this.runnableQueue = new LinkedRunnableQueue(queueSize,denyPolicy,this);
		this.keepAliveTime = keepAliveTime;
		this.timeUnit = timeUnit;
		this.init();
	}
	private void init(){
		//初始化线程池并启动
		start();
		for(int i = 0;i<initSize;i++){
			newThread();
		}
	}
	private void newThread(){
		//创建新任务并启动
		InternalTask internalTask = new InternalTask(runnableQueue);
		Thread thread = this.threadFactory.createThread(internalTask);
		ThreadTask threadTask = new ThreadTask(thread,internalTask);
		threadQueue.offer(threadTask);
		this.activeCount++;
		thread.start();
	}
	private void removeThread(){
		ThreadTask threadTask = threadQueue.remove();
		threadTask.internalTask.stop();
		this.activeCount--;
	}
	@Override
	public void getThreadPoolInfo(){
		System.out.println("ActiveCount:"+this.getActiveCount());
		System.out.println("QueueSize:"+this.getQueueSize());
		System.out.println("=========================");
	}
	@Override
	public void excute(Runnable runnable) {
		if(this.isShutdown()){
			throw new IllegalStateException("The thread pool has been destoryed!");
		}
		this.runnableQueue.offer(runnable);
		
	}
	
	@Override
	public void run() {
		//该方法继承自thread，主要是用来维护线程数量，扩容，回收等工作
		while(!isShutdown && !isInterrupted()){
			try {
				timeUnit.sleep(keepAliveTime);
			} catch (InterruptedException e) {
				isShutdown  = true;
				break;
			}
			synchronized (this) {
				if(isShutdown){
					break;
				}
				if(runnableQueue.size()>0 && activeCount<coreSize){
					//扩容
					for(int i=initSize;i<coreSize;i++){
						newThread();
					}
					continue;
				}
				if(runnableQueue.size()>0 && activeCount<maxSize){
					//继续扩容
					for(int i=coreSize;i<maxSize;i++){
						newThread();
					}
				}
				if(runnableQueue.size()==0 && activeCount>coreSize){
					//去掉一些线程
					for(int i=coreSize;i<activeCount;i++){
						removeThread();
					}
				}
			}
		}
	}
	@Override
	public void shutdown() {
		//保证所有线程全部中断
		synchronized (this) {
			if(isShutdown)
				return;
			isShutdown = true;
			threadQueue.forEach(threadTask->{
				threadTask.internalTask.stop();
				threadTask.thread.interrupt();
			});
			this.interrupt();
		}
		
	}

	@Override
	public int getInitSize() {
		if(isShutdown)
			throw new IllegalStateException("The thread pool has been destoryed!");
		return this.initSize;
	}

	@Override
	public int getMaxSize() {
		if(isShutdown)
		throw new IllegalStateException("The thread pool has been destoryed!");
		return this.maxSize;
	}

	@Override
	public int getCoreSize() {
		if(isShutdown)
		throw new IllegalStateException("The thread pool has been destoryed!");
		return this.coreSize;
	}

	@Override
	public int getQueueSize() {
		if(isShutdown)
		throw new IllegalStateException("The thread pool has been destoryed!");
		return this.runnableQueue.size();
	}

	@Override
	public int getActiveCount() {
		synchronized (this) {
			return this.activeCount;
		}		
	}

	@Override
	public boolean isShutdown() {
		return this.isShutdown;
	}
	public static class ThreadTask{
		Thread thread;
		InternalTask internalTask;
		public ThreadTask(Thread thread,InternalTask internalTask){
			this.thread = thread;
			this.internalTask = internalTask;
		}
	}
	private static class DefaultThreadFactory implements ThreadFactory{
		private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);
		private static final ThreadGroup group = new ThreadGroup("MyThreadPool-"+GROUP_COUNTER.getAndDecrement());
		private static final AtomicInteger COUNTER = new AtomicInteger(0);
		@Override
		public Thread createThread(Runnable runnable) {
			
			return new Thread(group,runnable,"thread-pool-"+COUNTER.getAndDecrement());
		}
		
	}
}
