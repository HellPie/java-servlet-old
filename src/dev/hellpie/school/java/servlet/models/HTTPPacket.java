package dev.hellpie.school.java.servlet.models;

import dev.hellpie.school.java.servlet.values.HTTPCode;
import dev.hellpie.school.java.servlet.values.HTTPRequest;
import dev.hellpie.school.java.servlet.values.HTTPVersion;

import java.util.HashMap;
import java.util.Map;

public class HTTPPacket {

	private HTTPVersion version = null;
	private HTTPRequest method = null;
	private HTTPCode code = null;
	private String path = null;

	private Map<String, String> headers = new HashMap<>();
	private byte[] body;

	private HTTPPacket() { /* DTO Class - Do Not Initialize */ }

	public HTTPVersion getVersion() {
		return version;
	}

	public HTTPRequest getMethod() {
		return method;
	}

	public HTTPCode getCode() {
		return code;
	}

	public String getPath() {
		return path;
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

	public static final class Builder {
		private HTTPPacket packet = new HTTPPacket();

		public Builder() {}
		public Builder(HTTPPacket clone) {
			packet = clone;
		}

		public Builder withVersion(HTTPVersion version) {
			packet.version = version;
			return this;
		}

		public Builder withMethod(HTTPRequest method) {
			packet.method = method;
			return this;
		}

		public Builder withCode(HTTPCode code) {
			packet.code = code;
			return this;
		}

		public Builder withPath(String path) {
			if(path == null) path = ""; // Path shall never be null
			packet.path = path;
			return this;
		}

		public Builder addHeader(String name, String value) {
			if(name != null) packet.headers.put(name, (value == null ? "" : value));
			return this;
		}

		public Builder withHeaders(HashMap<String, String> headers) {
			if(headers != null) packet.headers = headers;
			return this;
		}

		public Builder withBody(byte[] body) {
			packet.body = body;
			return this;
		}

		public HTTPPacket build() {
			HTTPPacket built = new HTTPPacket(); // Clone to avoid modifying this one on next build() on same Builder
			built.version = packet.version;
			built.method = packet.method;
			built.code = packet.code;
			built.path = packet.path;
			built.headers = new HashMap<>(packet.headers);
			built.body = packet.body;
			return built;
		}
	}
}
