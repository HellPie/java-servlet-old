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

import dev.hellpie.school.java.servlet.values.HTTPVersion;

import java.util.HashMap;
import java.util.Map;

public abstract class HTTPPacket {

	protected HTTPVersion version;

	protected Map<String, String> headers = new HashMap<>();
	protected byte[] body;

	protected HTTPPacket() { /* DTO Class - Do Not Initialize */ }

	public HTTPVersion getVersion() {
		return version;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getHeader(String header) {
		return headers.get(header);
	}

	public byte[] getBody() {
		return body;
	}

	public abstract static class Builder<T extends HTTPPacket, B extends Builder> {

		private HTTPVersion version = HTTPVersion.HTTP_1_1;
		private Map<String, String> headers = new HashMap<>();
		private byte[] body = new byte[] {};

		public Builder() {}

		public Builder(HTTPPacket clone) {
			this.version = clone.version;
			this.headers.putAll(clone.headers);
			this.body = clone.body.clone();
		}

		public final B withVersion(HTTPVersion version) {
			if(this.version != null) this.version = version;
			return getBuilder();
		}

		public final B addHeader(String name, String value) {
			if(name != null) this.headers.put(name, (value == null ? "" : value));
			return getBuilder();
		}

		public final B withHeaders(HashMap<String, String> headers) {
			if(headers != null) this.headers = headers;
			return getBuilder();
		}

		public final B withBody(byte[] body) {
			if(this.body != null) this.body = body;
			return getBuilder();
		}

		protected final T inject(T packet) {
			packet.version = version;
			packet.headers.putAll(headers);
			packet.body = body;
			return packet;
		}

		public abstract T build();
		protected abstract B getBuilder();
	}
}
