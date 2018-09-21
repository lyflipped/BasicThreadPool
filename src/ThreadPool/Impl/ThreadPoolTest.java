package ThreadPool.Impl;

import java.util.concurrent.TimeUnit;

import ThreadPool.ThreadPool;

public class ThreadPoolTest {
	public static void main(String[] args) throws InterruptedException{
		//����һ���̳߳أ���ʼ���߳�Ϊ2�����Ϊ6������Ϊ4�����������ύ1000������
		final ThreadPool threadPool = new BasicThreadPool(2,6,4,1000);
		//�����ʮ�������ύ���̳߳�
		for(int i=0;i<20;i++){
			threadPool.excute(()->{
				try{
					TimeUnit.SECONDS.sleep(10);
					System.out.println(Thread.currentThread().getName()+" is running and done.");
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		for(;;){
			//��������̳߳ص���Ϣ
			threadPool.getThreadPoolInfo();
			TimeUnit.SECONDS.sleep(5);
		}
	}
}
