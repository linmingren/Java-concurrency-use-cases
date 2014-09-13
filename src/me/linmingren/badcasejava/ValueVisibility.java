package me.linmingren.badcasejava;


/**
 * 这个例子跟一般的同步不太一样，因为我们理解中的多线程问题都是某一时刻的状态没有同步引起的。
 * 而这个例子是状态永远都不会同步。
 * 
 * 解决方法：
 * 1.给stop加上volatile
 * 2. 把 stop 声明为AtomicBoolean
 * 
 * @author linmingren
 *
 */
public class ValueVisibility {
	static boolean stop = false;
	
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!stop) {
				}
			}
			
		});
        thread.start();

        //这里的休眠很重要，不然stop的的值会被看到。
        //参看<Java 并发编程实践》中文版第34页的例子，那个例子在我的机器上跑不出来错误的结果,就是因为没加这句话
        Thread.sleep(1000);
        stop = true;
        System.out.println("主线程的stop已经是true");
	}

}
