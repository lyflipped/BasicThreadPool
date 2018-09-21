package ThreadPool;
/**
 * 线程池接口
 * @author liyang
 *
 */
public interface ThreadPool {
	void excute(Runnable runnable);
	void shutdown();
	int getInitSize();
	int getMaxSize();
	int getCoreSize();
	int getQueueSize();
	int getActiveCount();
	boolean isShutdown();
	void getThreadPoolInfo();
}
