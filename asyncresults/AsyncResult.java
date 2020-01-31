// Peter Idestam-Almquist, 2017-03-02.

package peteria.javaconcurrency.asyncresults;

import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

class AsyncResult<V> implements Future<V> {
	private Thread thread;
	private V result = null;
	
	AsyncResult(Callable<V> task) {
		thread = new Thread(new Runnable() {
			public void run() {
				try {
					result = task.call();
				}
				catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});
		thread.start();
	}
	
	public V get() {
		try {
			thread.join();
		}
		catch (InterruptedException exception) {
			System.out.println(exception);
		}
		return result;
	}
	
	public boolean isDone() {
		return (thread.getState() == Thread.State.TERMINATED);
	}
	
	public boolean cancel(boolean mayInterruptIfRunning) {
		throw new UnsupportedOperationException();
	}
	
	public V get(long timeout, TimeUnit unit) {
		throw new UnsupportedOperationException();
	}
	
	public boolean isCancelled() {
		throw new UnsupportedOperationException();
	}
}
