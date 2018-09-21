package ThreadPool;

public class InternalTask implements Runnable{
	private final RunnableQueue runnableQueue ;
	private volatile boolean running = true;
	public InternalTask(RunnableQueue runnableQueue) {
		this.runnableQueue = runnableQueue;
	}
	@Override
	public void run() {
		//如果当前任务为running且没有被中断，那么将不断的从queue中获取runnable。然后运行run方法
		while(running && !Thread.currentThread().isInterrupted()){
			try {
				Runnable task = runnableQueue.take();
				task.run();
			} catch (Exception e) {
				running = false;
				break;
			}
		}
	}
	public void stop(){
		this.running = false;
	}

}
