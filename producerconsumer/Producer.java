// Peter Idestam-Almquist, 2018-02-25.

package peteria.javaconcurrency.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.Random;

class Producer implements Runnable {
	private BlockingQueue<Task> queue;
	private Random random = new Random();

	Producer(BlockingQueue<Task> queue) { 
		this.queue = queue; 
	}
	
	public void run() {
		while (true) {
			try {
				Task task = produce();
				queue.put(task);
			}
			catch (Exception exception) {
				System.out.println(exception);
			}
		}
	}
	
	Task produce() {
		// It takes some time to produce a task.
		try {
			Thread.sleep(random.nextInt(1000));
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
		return new Task("Hello!");
	}
}
