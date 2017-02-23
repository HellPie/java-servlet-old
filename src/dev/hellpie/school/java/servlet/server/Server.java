package dev.hellpie.school.java.servlet.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

	private final ServerSocket server;

	public Server(int port) throws IOException {
		server = new ServerSocket(port, 10, InetAddress.getByName("10.0.74.84"));
	}

	public void run() throws IOException {
		while(true) {

			Socket inbound = server.accept();
			System.out.println(String.format("[INFO] New Socket: %s:%d", inbound.getInetAddress(), inbound.getPort()));

			new Connection(inbound).start();
		}
	}

	private final class Connection extends Thread {

		private final Socket socket;
		private BufferedReader reader;
		private DataOutputStream output;

		/*package*/ Connection(Socket socket) throws IOException {
			this.socket = socket;
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new DataOutputStream(socket.getOutputStream());
		}

		@Override
		public void run() {

			StringBuilder scoop = new StringBuilder();
			try {
				String string;
				while((string = reader.readLine()) != null) {
					scoop.append(string).append('\n'); // TODO: Check if \r is kept inside the string
				}
			} catch(IOException e) {
				e.printStackTrace();
			}

			System.out.println(String.format("[DEBUG] Received Request: %s", scoop.toString()));
		}
	}
}

