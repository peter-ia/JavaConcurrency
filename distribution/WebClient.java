// Peter Idestam-Almquist, 2017-03-08.
// A very simple webclient. 
// Too simple to work for most websites.

package peteria.javaconcurrency.distribution;

import java.net.URL;
import java.net.Socket;
import java.net.SocketAddress;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class WebClient {
	private final static int DEFAULT_PORT = 80;
	private final static String PROMPT = "WebClient>";
	private final static int MAX_LINES = 50;
	
	public static void main(String[] args) {
		System.out.println("WebClient started.");
		
		Socket socket = null;
		PrintWriter socketWriter = null;
		BufferedReader socketReader = null;
		BufferedReader consoleReader = null;
		try {
			consoleReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print(PROMPT);
			String strURL = consoleReader.readLine();
			while (strURL != null && !strURL.equals("close")) {
				if (strURL != null && !strURL.isEmpty()) {
					URL url = new URL(strURL);
					System.out.println("Protocol: " + url.getProtocol());
					System.out.println("Host: " + url.getHost());
					System.out.println("File: " + url.getFile());
					System.out.println("Port: " + url.getPort());
					System.out.println("Default port: " + url.getDefaultPort());
					
					int port = url.getPort();
					if (port == -1)
						port = url.getDefaultPort();
					socket = new Socket(url.getHost(), port);
					SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
					SocketAddress localSocketAddress = socket.getLocalSocketAddress();
					System.out.println("Connected to server " + remoteSocketAddress 
						+ " (" + localSocketAddress + ").");
					
					socketWriter = new PrintWriter(socket.getOutputStream(), true);
					String request = "GET " + url.toString() + " HTTP/1.1\r\n\r\n";
					System.out.print("Request: " + request);
					socketWriter.print(request);
					
					socketReader = new BufferedReader(
						new InputStreamReader(socket.getInputStream())
					);
					String line = socketReader.readLine();
					int counter = 0;
					while (line != null && counter < MAX_LINES) {
						System.out.println(line);
						counter++;
						line = socketReader.readLine();
					}
				}
				System.out.print(PROMPT);
				strURL = consoleReader.readLine();
			}
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
				if (consoleReader != null)
					consoleReader.close();
			}
			catch (Exception exception) {
				System.out.println(exception);
			}
		}
	}
}
