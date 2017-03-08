package dev.hellpie.school.java.servlet.models;

import dev.hellpie.school.java.servlet.values.HTTPCode;

public class ResponseHTTPPacket extends HTTPPacket {

	private HTTPCode code;

	private ResponseHTTPPacket() { /* DTO Class - Do Not Initialize */ }

	public HTTPCode getCode() {
		return code;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(String.format(
				"\t- Version: %s\n\t- Code: %s\n\t- Description: %s\n\t- Headers:",
				version.getVersion(),
				code.getCode(),
				code.getDescription()
		));

		for(String key : headers.keySet()) System.out.println(String.format("\n\t\t- %s: %s", key, headers.get(key)));
		if(headers.size() == 0) System.out.println("\n\t\t- No Headers Found");

		return builder.append(String.format("\n\t- Body:\n%s", new String(body))).toString();
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
