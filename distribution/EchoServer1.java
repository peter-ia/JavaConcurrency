// Peter Idestam-Almquist, 2018-03-06.
// Server, single-threaded, accepting one client at the time.

package peteria.javaconcurrency.distribution;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;

class EchoServer1 {
	private final static int PORT = 8000;
	private final static int MAX_CLIENTS = 5;
	
	private static Socket clientSocket = null;
	private static String clientName = "";
	
	static void run() {
		SocketAddress remoteSocketAddress = clientSocket.getRemoteSocketAddress();
		SocketAddress localSocketAddress = clientSocket.getLocalSocketAddress();
		System.out.println("Accepted client " + remoteSocketAddress 
			+ " (" + localSocketAddress + ").");
	
		PrintWriter socketWriter = null;
		BufferedReader socketReader = null;
		try {
			socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);                   
            socketReader = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream())
			);

			String threadInfo = " (" + Thread.currentThread().getName() + ").";
            String inputLine = socketReader.readLine();
			System.out.println("Received: \"" + inputLine + "\" from " 
				+ remoteSocketAddress + threadInfo);
				
			// First message is client name.
			clientName = inputLine;
			
            while (inputLine != null) {
				socketWriter.println(inputLine);
				System.out.println("Sent: \"" + inputLine + "\" to " 
					+ clientName + " " + remoteSocketAddress + threadInfo);
				inputLine = socketReader.readLine();
				System.out.println("Received: \"" + inputLine + "\" from " 
					+ clientName + " " + remoteSocketAddress + threadInfo);
            }
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
				if (clientSocket != null)
					clientSocket.close();
			}
			catch (Exception exception) {
				System.out.println(exception);
			}
		}
	}

    public static void main(String[] args) {
		System.out.println("EchoServer1 started.");

		ServerSocket serverSocket = null;
		try {
            serverSocket = new ServerSocket(PORT);
			SocketAddress serverSocketAddress = serverSocket.getLocalSocketAddress();
			System.out.println("Listening (" + serverSocketAddress + ").");
            
			while (true) {
				clientSocket = serverSocket.accept();     
				run();
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
