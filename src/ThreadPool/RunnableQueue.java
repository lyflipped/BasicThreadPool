package ThreadPool;
/**
 * 任务队列接口
 * @author liyang
 *
 */
public interface RunnableQueue {
	//当有新任务的时候先缓存到这个任务队列中
	void offer(Runnable runnable);
	//工作线程从任务列表中获取Runnable;
	Runnable take() throws InterruptedException;
	//当前任务队列中的任务数量
	int size();
}
