// Peter Idestam-Almquist, 2017-03-08.

package peteria.javaconcurrency.distribution;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// Supports protocol PARADIS/1.0.
// Request:
// ADD int1 int2
// SUB int1 int2
// MULT int1 int2
// DIV int1 int2
// Response:
// RESULT int
// ERR string
class MathServer implements Runnable {
	private final static int PORT = 8000;
	private final static int MAX_CLIENTS = 5;
	private final static Executor executor = Executors.newFixedThreadPool(MAX_CLIENTS);
	
	private Socket socket = null;
	
	MathServer(Socket socket) {
		this.socket = socket;
	}
	
	private String processRequest(String request) {
		String[] parts = request.split(" ");
		if (parts == null || parts.length < 4 || !parts[0].equals("PARADIS/1.0")) {
			return "ERR Unknown request";
		}
		String operator = parts[1];
		int operand1;
		int operand2;
		try {
			operand1 = Integer.parseInt(parts[2]);
			operand2 = Integer.parseInt(parts[3]);
		}
		catch (NumberFormatException exception) {
			return "ERR Unknown operand";
		}
		switch (operator) {
			case "ADD": return "RESULT " + (operand1 + operand2);
			case "SUB": return "RESULT " + (operand1 - operand2);
			case "MULT": return "RESULT " + (operand1 * operand2);
			case "DIV": return "RESULT " + (operand1 / operand2);
			default: return "ERR Unknown operator";
		}
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

			String threadInfo = " (" + Thread.currentThread().getName() + ").";
            String request = socketReader.readLine();
			System.out.println("Received: \"" + request + "\" from " 
				+ remoteSocketAddress + threadInfo);
            while (request != null) {
				String response = processRequest(request);
				socketWriter.println(response);
				System.out.println("Sent: \"" + response + "\" to " 
					+ remoteSocketAddress + threadInfo);
				request = socketReader.readLine();
				System.out.println("Received: \"" + request + "\" from " 
					+ remoteSocketAddress + threadInfo);
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
				if (socket != null)
					socket.close();
			}
			catch (Exception exception) {
				System.out.println(exception);
			}
		}
	}

    public static void main(String[] args) {
		System.out.println("MathServer started.");

		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		try {
            serverSocket = new ServerSocket(PORT);
			SocketAddress serverSocketAddress = serverSocket.getLocalSocketAddress();
			System.out.println("Listening (" + serverSocketAddress + ").");
            
			while (true) {
				clientSocket = serverSocket.accept();     
				executor.execute(new MathServer(clientSocket));
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
