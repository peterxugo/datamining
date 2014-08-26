/**
 * 
 */
package datamining;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 * 
 */
public class MyRunableTest {
	static Count count = new Count();



	public static void main(String[] agrs) {
		ExecutorService threadpool = Executors.newFixedThreadPool(100);
		for (int i = 0; i < 100; i++) {
			threadpool.execute(new MyRunablle(count));
		}
		threadpool.shutdown();
		while (!threadpool.isTerminated()) {

		}
		System.out.println(count.geta());
	}
}
class MyRunablle implements Runnable{
	private Count count ;

	MyRunablle(Count count) {
		this.count = count;
	}
	@Override
	public void run() {
		count.seta(1);
	}
	
}
class Count {
	private int a = 0;

	public int geta() {
		return a;
	}

	public synchronized void  seta(int x) {
		int c = a + x;
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		a = c;
	}
}