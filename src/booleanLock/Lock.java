package booleanLock;

import java.util.List;
import java.util.concurrent.TimeoutException;
/**
 * 
 * @author liyang
 *
 */
public interface Lock {
	/**
	 * �÷�����ȡ�������ǿ��Ա��ж��׳��ж��쳣
	 * @throws InterruptedException
	 */
	public void lock() throws InterruptedException;
	/**
	 * ��ʱʱ������������������¼����Լ��ж�
	 * @param mills
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void lock(long mills) throws InterruptedException,TimeoutException;
	/**
	 * �������ͷŶ���
	 */
	public void unlock();
	/**
	 * ��ȡ�������߳�
	 * @return
	 */
	public List<Thread> getBlockedThreads();
}
