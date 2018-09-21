package thread;

import utils.SleepUtil;

public class myThread extends Thread {
	@Override
	public void run() {
		
		for(int i= 0;i<10;i++){
			System.out.println(Thread.currentThread().getName()+"#"+i);
			SleepUtil.shortSleep();
		}
	}
	
}
