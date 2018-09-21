package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import utils.SleepUtil;

public class ThreadJoin {
	public static void main(String[] args) throws InterruptedException{
//		List<Thread> threads = new ArrayList<>();
//		threads.add(new myThread());
//		threads.add(new myThread());
//		//threads.add(new myThread());
//		
//		for(Thread t:threads){
//			t.start();
//		}
//		for(Thread t:threads){
//			t.join();
//		}
//		for(int i=0;i<10;i++){
//			System.out.println(Thread.currentThread().getName()+"#"+i);
//			SleepUtil.shortSleep();
//		}
		ExitThread thread = new ExitThread();
		thread.start();
		SleepUtil.shortSleep();
		//thread.join();
		thread.close();
		
	}
}
