// Peter Idestam-Almquist, 2018-02-25.

package peteria.javaconcurrency.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.Random;

class Consumer implements Runnable {
	private BlockingQueue<Task> queue;
	private Random random = new Random();

	Consumer(BlockingQueue<Task> queue) { 
		this.queue = queue; 
	}
	
	public void run() {
		while (true) {
			try {
				Task task = queue.take();
				consume(task);
			}
			catch (Exception exception) {
				System.out.println(exception);
			}
		}
	}
	
	void consume(Task task) {
		// It takes some time to consume a task.
		try {
			Thread.sleep(random.nextInt(1000));
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
		System.out.println(task.getData());
	}
}
	
