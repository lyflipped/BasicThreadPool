package ThreadPool;
/**
 * 拒绝策略，当任务队列满的时候，决定如何通知提交者。
 * @author liyang
 *
 */
@FunctionalInterface
public interface DenyPolicy {
	void reject(Runnable runnable,ThreadPool threadPool);
	//不运行任务，抛弃
	class DiscardDenyPolicy implements DenyPolicy{

		@Override
		public void reject(Runnable runnable, ThreadPool threadPool) {
			// do things
			
		}
		
	}
	//拒绝执行并抛出异常
	class AbortDenyPolicy implements DenyPolicy{

		@Override
		public void reject(Runnable runnable, ThreadPool threadPool) {
			throw new RunnableDenyException("The runnable "+runnable+" will be abort.");
		}
		
	}
	//使任务在提交者所在的线程中去执行,不会被加载到线程池中
	class RunnerDenyPolicy implements DenyPolicy{

		@Override
		public void reject(Runnable runnable, ThreadPool threadPool) {
			if(!threadPool.isShutdown()){
				runnable.run();
			}		
		}
		
	}
}
