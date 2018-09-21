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
	 * 该方法获取锁，但是可以被中断抛出中断异常
	 * @throws InterruptedException
	 */
	public void lock() throws InterruptedException;
	/**
	 * 超时时长，如果阻塞超过该事件，自己中断
	 * @param mills
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public void lock(long mills) throws InterruptedException,TimeoutException;
	/**
	 * 解锁，释放对象
	 */
	public void unlock();
	/**
	 * 获取阻塞的线程
	 * @return
	 */
	public List<Thread> getBlockedThreads();
}
