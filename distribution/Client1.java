// Peter Idestam-Almquist, 2018-03-06.
// Client, single-threaded, non-responsive to incoming messages.

package peteria.javaconcurrency.distribution;

import java.net.Socket;
import java.net.SocketAddress;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class Client1 {
	private final static String HOST = "127.0.0.1";
	private final static int PORT = 8000;
	
	private static BufferedReader socketReader = null;
	
	// Receive messages.
	private static void run() {
		try {
			String threadInfo = " (" + Thread.currentThread().getName() + ").";
			String line = socketReader.readLine();
			System.out.println("Received: \"" + line + "\"" + threadInfo);
		}
		catch (Exception exception) {
			System.out.println(exception);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Client1 started.");
		
		Socket socket = null;
		PrintWriter socketWriter = null;
		BufferedReader consoleReader = null;
		try {
			socket = new Socket(HOST, PORT);
			SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
			SocketAddress localSocketAddress = socket.getLocalSocketAddress();
			System.out.println("Connected to server " + remoteSocketAddress 
				+ " (" + localSocketAddress + ").");

			socketWriter = new PrintWriter(socket.getOutputStream(), true);
			socketReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream())
			);
			
			// Send and receive messages.
			String threadInfo = " (" + Thread.currentThread().getName() + ").";
			consoleReader = new BufferedReader(new InputStreamReader(System.in));
			String message = consoleReader.readLine();
			while (message != null && !message.equals("close")) {
				if (message != null && !message.isEmpty()) {
					socketWriter.println(message);
					System.out.println("Sent: \"" + message + "\"" + threadInfo);
					run();
				}
				message = consoleReader.readLine();
			}
			
			System.out.println("Closing connection " + remoteSocketAddress 
				+ " (" + localSocketAddress + ").");
		}
		catch(Exception exception) {
			System.out.println(exception);
		}
		finally {
			try {
				if (socketWriter != null)
					socketWriter.close();
				if (socketReader != null)
					socketReader.close();
				if (socket != null)
					socket.close();
				if (consoleReader != null)
					consoleReader.close();
			}
			catch (Exception exception) {
				System.out.println(exception);
			}
		}
	}
}
