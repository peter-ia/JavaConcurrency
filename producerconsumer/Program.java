// Peter Idestam-Almquist, 2018-02-25.

package peteria.javaconcurrency.producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Program {
	private static BlockingQueue<Task> queue = new ArrayBlockingQueue<Task>(10);
	
	public static void main(String[] args) {
		(new Thread(new Producer(queue))).start();
		(new Thread(new Producer(queue))).start();
		(new Thread(new Producer(queue))).start();
		(new Thread(new Producer(queue))).start();
		(new Thread(new Consumer(queue))).start();
		(new Thread(new Consumer(queue))).start();
		(new Thread(new Consumer(queue))).start();
		(new Thread(new Consumer(queue))).start();
	}
}
