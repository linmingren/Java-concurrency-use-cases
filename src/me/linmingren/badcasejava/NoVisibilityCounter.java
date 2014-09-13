package me.linmingren.badcasejava;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个例子在两个线程中同时递增一个计数器，每个线程递增INC_COUNT次。
 * 期望计数器的值是INC_COUNT * 线程数，但是结果看起来差别很大。
 * 
 * 问题是由于线程1的对counter的修改线程2不会及时看到引起的。
 * 比如线程1已经把counter加到100了，还没有写回主内存
 * 那么线程2看到的值还是0，等线程2也把counter加到100，这时两个线程都把值写回主内存
 * 结果就是大家加了200次，但是主线程看到的值确是100.
 * 
 * 
 * 
 * 解决方法
 * 1. 给incCounter加上同步
 * 2. 把counter变成AtomicInteger, 注意加volatile对这种要递增的值没用
 * 
 * 
 * @author linmingren
 *
 */
public class NoVisibilityCounter {

	private static final int INC_COUNT = 10000;

	static int counter = 0;
	
	public static void incCounter() {
		++ counter;
	}

	public static void main(String[] args) throws Exception {

		System.out.println("开始递增计数器");
		
		List<Thread> threadList = new ArrayList<Thread>();
		int threadNum = 2;
		for (int i =0 ;i < threadNum; ++i) {
			 new Thread(new IncreaseCounterTask()).start();
		}
		
        for (Thread t : threadList) {
        	t.join();
        }
		

		int actualCounter = counter;
		int expectedCount = INC_COUNT * threadNum;
		if (actualCounter != expectedCount) {
			System.err.printf(
					"计数错误! 实际值 %s, 期望值: %s.",
					actualCounter, expectedCount);
		} else {
			System.out.println("技术正确!");
		}
	}

	private static class IncreaseCounterTask implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < INC_COUNT; ++i) {
				incCounter();
			}
		}
	}
}
