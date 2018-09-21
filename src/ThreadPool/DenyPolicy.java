package ThreadPool;
/**
 * �ܾ����ԣ��������������ʱ�򣬾������֪ͨ�ύ�ߡ�
 * @author liyang
 *
 */
@FunctionalInterface
public interface DenyPolicy {
	void reject(Runnable runnable,ThreadPool threadPool);
	//��������������
	class DiscardDenyPolicy implements DenyPolicy{

		@Override
		public void reject(Runnable runnable, ThreadPool threadPool) {
			// do things
			
		}
		
	}
	//�ܾ�ִ�в��׳��쳣
	class AbortDenyPolicy implements DenyPolicy{

		@Override
		public void reject(Runnable runnable, ThreadPool threadPool) {
			throw new RunnableDenyException("The runnable "+runnable+" will be abort.");
		}
		
	}
	//ʹ�������ύ�����ڵ��߳���ȥִ��,���ᱻ���ص��̳߳���
	class RunnerDenyPolicy implements DenyPolicy{

		@Override
		public void reject(Runnable runnable, ThreadPool threadPool) {
			if(!threadPool.isShutdown()){
				runnable.run();
			}		
		}
		
	}
}
