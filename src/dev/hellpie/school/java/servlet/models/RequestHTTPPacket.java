package dev.hellpie.school.java.servlet.models;

import dev.hellpie.school.java.servlet.values.HTTPRequest;

public class RequestHTTPPacket extends HTTPPacket {

	private HTTPRequest method = null;
	private String path = null;

	private RequestHTTPPacket() { /* DTO Class - Do Not Initialize */ }

	public HTTPRequest getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	public static final class Builder extends HTTPPacket.Builder<RequestHTTPPacket, RequestHTTPPacket.Builder> {

		private HTTPRequest method = HTTPRequest.GET;
		private String path = "";

		public Builder() {}

		public Builder(RequestHTTPPacket packet) {
			super(packet);
			method = packet.method;
			path = packet.path;
		}

		public final Builder withMethod(HTTPRequest method) {
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
