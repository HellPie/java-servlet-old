package dev.hellpie.school.java.servlet.server;

import dev.hellpie.school.java.servlet.models.HTTPPacket;
import dev.hellpie.school.java.servlet.models.RequestHTTPPacket;
import dev.hellpie.school.java.servlet.models.ResponseHTTPPacket;
import dev.hellpie.school.java.servlet.parsing.BasicHTTPParser;
import dev.hellpie.school.java.servlet.values.HTTPCode;
import dev.hellpie.school.java.servlet.values.HTTPVersion;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private final ServerSocket server;

	public Server(int port) throws IOException {
		server = new ServerSocket(port);
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

			StringBuilder input = new StringBuilder();
			try {
				String string;
				while((string = reader.readLine()) != null) {
					if(string.isEmpty()) break; // Expecting only GET Requests without body, no plans to live-parse data
					input.append(string).append('\n');
				}
			} catch(IOException e) {
				e.printStackTrace();
			}

			System.out.println("[Info] Received Raw Request");
			System.out.println("[INFO] Parsing HTTP Request");
			HTTPPacket packet = new BasicHTTPParser(input.toString()).parse();
			System.out.println("[INFO] Parsed HTTP Request");

			if(packet == null) {
				System.out.println("[ERROR] HTTP Request was Invalid!");
			} else if(packet instanceof RequestHTTPPacket){
				RequestHTTPPacket request = (RequestHTTPPacket) packet;
				System.out.println("[DEBUG] Parsed Request:");
				System.out.println(String.format("\t- Method: %s\n\t- Path: %s\n\t- Version: %s\n\t- Headers:",
						request.getMethod().getType(),
						request.getPath(),
						request.getVersion().getVersion()
				));

				for(String key : packet.getHeaders().keySet()) {
					System.out.println(String.format("\t\t- %s: %s", key, packet.getHeader(key)));
				}

				if(packet.getHeaders().size() == 0) System.out.println("\t\t- No Headers Found");

				System.out.println(String.format("\t- Body:\n%s", new String(packet.getBody())));
			}

			System.out.println("[INFO] Building HTTP Response");
			HTTPPacket response = new ResponseHTTPPacket.Builder()
					.withVersion(packet != null ? packet.getVersion() : HTTPVersion.HTTP_1_1)
					.withCode(HTTPCode.SERVER_501)
					.addHeader("Content-Length", String.valueOf(0))
					.build();
			System.out.println("[INFO] Finished building HTTP Response");

			if(response == null) {
				System.out.println("[ERROR] HTTP Response was Invalid");
			} else {
				ResponseHTTPPacket responsePacket = (ResponseHTTPPacket) response;
				System.out.println("[DEBUG] Parsed Request:");
				System.out.println(String.format("\t- Version: %s\n\t- Code: %s\n\t- Description: %s\n\t- Headers:",
						responsePacket.getVersion().getVersion(),
						responsePacket.getCode().getCode(),
						responsePacket.getCode().getDescription()
				));

				for(String key : responsePacket.getHeaders().keySet()) {
					System.out.println(String.format("\t\t- %s: %s", key, responsePacket.getHeader(key)));
				}

				if(responsePacket.getHeaders().size() == 0) System.out.println("\t\t- No Headers Found");

				System.out.println(String.format("\t- Body:\n%s", new String(responsePacket.getBody())));
			}

			System.out.println("[INFO] Sending HTTP Response");
			try {
				output.writeUTF(new BasicHTTPParser(response).ǝsɹɐd());
				System.out.println("[INFO] Sent HTTP Response");
			} catch(IOException e) {
				System.out.println("[ERROR] Sending HTTP Response Failed");
				e.printStackTrace();
			} finally {
				System.out.println("[INFO] Closing Socket");
				try {
					socket.close();
					System.out.println("[INFO] Closed Socket");
				} catch(IOException e) {
					System.out.println("[ERROR] Closing Socket Failed");
					e.printStackTrace();
				}
			}

		}
	}
}
