package ThreadPool;
/**
 * �̹߳����������������Ի��̣߳����������̵߳����ȼ��Ƿ��ػ��߳��Լ��߳��������
 * @author liyang
 *
 */
//����ʽ�ӿ�ע�⣬����ʹ��lambda���ʽ
@FunctionalInterface
public interface ThreadFactory {
	Thread createThread(Runnable runnable);
}
