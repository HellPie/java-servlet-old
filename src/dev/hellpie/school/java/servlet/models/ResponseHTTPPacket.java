package dev.hellpie.school.java.servlet.models;

import dev.hellpie.school.java.servlet.values.HTTPCode;

public class ResponseHTTPPacket extends HTTPPacket {

	private HTTPCode code;

	private ResponseHTTPPacket() { /* DTO Class - Do Not Initialize */ }

	public HTTPCode getCode() {
		return code;
	}

	public static final class Builder extends HTTPPacket.Builder<ResponseHTTPPacket, ResponseHTTPPacket.Builder> {

		private HTTPCode code = HTTPCode.SERVER_501;

		public Builder() {}

		public Builder(ResponseHTTPPacket packet) {
			super(packet);
			code = packet.code;
		}

		public final Builder withCode(HTTPCode code) {
			if(code != null) this.code = code;
			return this;
		}

		@Override
		public ResponseHTTPPacket build() {
			ResponseHTTPPacket packet = inject(new ResponseHTTPPacket());
			packet.code = code;
			return packet;
		}

		@Override
		protected Builder getBuilder() {
			return this;
		}
	}
}
