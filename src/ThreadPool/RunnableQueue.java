package ThreadPool;
/**
 * ������нӿ�
 * @author liyang
 *
 */
public interface RunnableQueue {
	//�����������ʱ���Ȼ��浽������������
	void offer(Runnable runnable);
	//�����̴߳������б��л�ȡRunnable;
	Runnable take() throws InterruptedException;
	//��ǰ��������е���������
	int size();
}
