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

package dev.hellpie.school.java.servlet.models;

import dev.hellpie.school.java.servlet.values.HTTPMethod;

public class RequestHTTPPacket extends HTTPPacket {

	private HTTPMethod method = null;
	private String path = null;

	private RequestHTTPPacket() { /* DTO Class - Do Not Initialize */ }

	public HTTPMethod getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(String.format(
				"\t- Method: %s\n\t- Path: %s\n\t- Version: %s\n\t- Headers:",
				method.getMethod(),
				path,
				version.getVersion()
		));

		for(String key : headers.keySet()) builder.append(String.format("\n\t\t- %s: %s", key, headers.get(key)));
		if(headers.size() == 0) builder.append("\n\t\t- No Headers Found");

		return builder.append(String.format("\n\t- Body:\n%s", new String(body))).toString();
	}

	public static final class Builder extends HTTPPacket.Builder<RequestHTTPPacket, RequestHTTPPacket.Builder> {

		private HTTPMethod method = HTTPMethod.GET;
		private String path = "";

		public Builder() {}

		public Builder(RequestHTTPPacket packet) {
			super(packet);
			method = packet.method;
			path = packet.path;
		}

		public final Builder withMethod(HTTPMethod method) {
			if(this.method != null) this.method = method;
			return this;
		}

		public final Builder withPath(String path) {
			if(path == null) path = "";
			this.path = path;
			return this;
		}

		@Override
		public RequestHTTPPacket build() {
			RequestHTTPPacket packet = inject(new RequestHTTPPacket());
			packet.method = method;
			packet.path = path;
			return packet;
		}

		@Override
		protected Builder getBuilder() {
			return this;
		}
	}
}
