// Peter Idestam-Almquist, 2017-03-08.
// Accepts several simultaneous webclients.

package peteria.javaconcurrency.distribution;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class WebServer implements Runnable {
	private final static int PORT = 8080;
	private final static int MAX_CLIENTS = 5;
	private final static Executor executor = Executors.newFixedThreadPool(MAX_CLIENTS);
	
	private Socket socket = null;
	
	WebServer(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
		SocketAddress localSocketAddress = socket.getLocalSocketAddress();
		System.out.println("Accepted client " + remoteSocketAddress + " (" 
			+ localSocketAddress + ").");
	
		PrintWriter socketWriter = null;
		BufferedReader socketReader = null;
		try {
			socketWriter = new PrintWriter(socket.getOutputStream(), true);                   
            socketReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream())
			);

			String threadInfo = " (" + Thread.currentThread().getName() + ")";
			String request = "";
			while (socketReader.ready()) {
				request += socketReader.readLine() + "\r\n";
			}
			System.out.println("Received from " + remoteSocketAddress + threadInfo 
				+ ":\r\n" + request);
			
			String response = "HTTP/1.1 200 OK\r\n\r\n";
			response += "<!DOCTYPE html>\r\n";
			response += "<html>\r\n";
			response += "<head><title>WebServer</title></head>\r\n";
			response += "<body><h1>Hello browser!</h1></body>\r\n";
			response += "</html>\r\n";
			socketWriter.print(response);
			System.out.println("Sent to " + remoteSocketAddress + threadInfo 
				+ ":\r\n" + response);

			System.out.println("Closing connection " + remoteSocketAddress 
				+ " (" + localSocketAddress + ").");
		}
		catch (Exception exception) {
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
			}
			catch (Exception exception) {
				System.out.println(exception);
			}
		}
	}

    public static void main(String[] args) {
		System.out.println("WebServer started.");

		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		try {
            serverSocket = new ServerSocket(PORT);
			SocketAddress serverSocketAddress = serverSocket.getLocalSocketAddress();
			System.out.println("Listening (" + serverSocketAddress + ").");
            
			while (true) {
				clientSocket = serverSocket.accept();     
				executor.execute(new WebServer(clientSocket));
			}
        } 
		catch (Exception exception) {
            System.out.println(exception);
        }
		finally {
			try {
				if (serverSocket != null)
					serverSocket.close();
			}
			catch (Exception exception) {
				System.out.println(exception);
			}
		}
    }
}
