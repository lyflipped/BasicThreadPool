package ThreadPool.Impl;

import java.util.concurrent.TimeUnit;

import ThreadPool.ThreadPool;

public class ThreadPoolTest {
	public static void main(String[] args) throws InterruptedException{
		//定义一个线程池，初始化线程为2，最大为6，核心为4，最多可允许提交1000个任务
		final ThreadPool threadPool = new BasicThreadPool(2,6,4,1000);
		//定义二十个任务并提交给线程池
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
			//不断输出线程池的信息
			threadPool.getThreadPoolInfo();
			TimeUnit.SECONDS.sleep(5);
		}
	}
}
