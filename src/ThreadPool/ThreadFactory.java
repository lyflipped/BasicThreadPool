package ThreadPool;
/**
 * 线程工厂，用来创建个性化线程，可以设置线程的优先级是否守护线程以及线程组等属性
 * @author liyang
 *
 */
//函数式接口注解，方便使用lambda表达式
@FunctionalInterface
public interface ThreadFactory {
	Thread createThread(Runnable runnable);
}
