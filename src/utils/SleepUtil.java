package utils;

import java.util.concurrent.TimeUnit;

public class SleepUtil {
	public static void shortSleep () {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
