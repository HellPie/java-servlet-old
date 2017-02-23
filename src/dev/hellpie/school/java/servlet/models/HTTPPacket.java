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

		private HTTPVersion version = null;
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
