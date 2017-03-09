/*
 * Copyright 2017 Diego Rossi (@_HellPie)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package dev.hellpie.school.java.servlet.server;

import dev.hellpie.school.java.servlet.models.HTTPPacket;
import dev.hellpie.school.java.servlet.models.RequestHTTPPacket;
import dev.hellpie.school.java.servlet.models.ResponseHTTPPacket;
import dev.hellpie.school.java.servlet.parsing.BasicHTTPParser;
import dev.hellpie.school.java.servlet.storage.FileProvider;
import dev.hellpie.school.java.servlet.utils.Log;
import dev.hellpie.school.java.servlet.values.HTTPCode;
import dev.hellpie.school.java.servlet.values.HTTPVersion;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static final String TAG = Log.getTag(Server.class);

	private final FileProvider provider;
	private final ServerSocket server;

	public Server(int port, String path) throws IOException {
		server = new ServerSocket(port);
		provider = new FileProvider(new File(path));
	}

	public void run() throws IOException {
		while(true) { // It will either crash or be closed manually, otherwise it shall forever suffe- run.

			Socket inbound = server.accept();
			Log.i(TAG, String.format("New Socket: %s:%d", inbound.getInetAddress(), inbound.getPort()));

			new Connection(inbound).start();
		}
	}

	private final class Connection extends Thread {
		private final String TAG = Log.getTag(this);

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

			Log.i(TAG, "Received Raw Request");
			Log.i(TAG, "Parsing HTTP Request");
			HTTPPacket packet = new BasicHTTPParser(input.toString()).parse();
			Log.i(TAG, "Parsed HTTP Request");

			// Prepare variables to default (error) values
			ResponseHTTPPacket.Builder builder = new ResponseHTTPPacket.Builder()
					.withVersion(packet == null ? HTTPVersion.HTTP_1_1 : packet.getVersion()); // Default to most common
			byte[] body = null; // This will store the body field, if the requested path is valid

			if(packet == null || !(packet instanceof RequestHTTPPacket)) { // Invalid request, send request error
				Log.e(TAG, "HTTP Request was Invalid!");
				reply(builder.withCode(HTTPCode.CLIENT_400).build()); // Cut connection ASAP, don't wait parser
				return;

			} else { // Valid request, need to analyze it and build a response with a valid HTTP code and maybe body
				RequestHTTPPacket request = (RequestHTTPPacket) packet;

				// Log the whole content of the request to help with debugging
				Log.d(TAG, String.format("Parsed Request:\n%s", request.toString()));

				Log.i(TAG, "Reading requested File");
				try {
					body = provider.read(request.getPath());
				} catch(IOException e) {
					Log.e(TAG, "Failed to read requested File");
					e.printStackTrace();

					reply(builder.withCode(HTTPCode.CLIENT_404).build()); // Assume file doesn't exist
					return;
				}
				Log.i(TAG, "Finished reading requested File");
			}

			Log.i(TAG, "Building HTTP Response");
			if(body != null && body.length > 0) {
				builder.withCode(HTTPCode.SUCCESS_200)
						.addHeader("Content-Length", String.valueOf(body.length))
						.withBody(body);
			} else {
				builder.withCode(HTTPCode.SUCCESS_204); // 404 would have returned after reading, assume empty file
			}
			Log.i(TAG, "Finished building HTTP Response");

			ResponseHTTPPacket response = builder.build();
			if(response == null) {
				Log.e(TAG, "HTTP Response was Invalid");
				reply(new ResponseHTTPPacket.Builder().withCode(HTTPCode.SERVER_500).build()); // Should never reach
				return;
			} else {
				// Log the whole content of the response to help with debugging
				Log.d(TAG, String.format("Parsed Response: \n%s", response.toString()));
			}

			reply(response);
		}

		private void reply(HTTPPacket packet) {
			Log.i(TAG, "Sending HTTP Response");
			try {
				output.writeUTF(new BasicHTTPParser(packet).ǝsɹɐd());
				Log.i(TAG, "Sent HTTP Response");
			} catch(IOException e) {
				Log.e(TAG, "Sending HTTP Response Failed");
				e.printStackTrace();
			} finally {
				Log.i(TAG, "Closing Socket");
				try {
					socket.close();
					Log.i(TAG, "Closed Socket");
				} catch(IOException e) {
					Log.e(TAG, "Closing Socket Failed");
					e.printStackTrace();
				}
			}
		}
	}
}
