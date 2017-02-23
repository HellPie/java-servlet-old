package dev.hellpie.school.java.servlet.parsing;

import dev.hellpie.school.java.servlet.models.HTTPPacket;
import dev.hellpie.school.java.servlet.models.IHTTPParser;
import dev.hellpie.school.java.servlet.models.RequestHTTPPacket;
import dev.hellpie.school.java.servlet.models.ResponseHTTPPacket;
import dev.hellpie.school.java.servlet.values.HTTPCode;
import dev.hellpie.school.java.servlet.values.HTTPRequest;
import dev.hellpie.school.java.servlet.values.HTTPVersion;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class BasicHTTPParser implements IHTTPParser {

	private HTTPPacket parsed = null;
	private String raw = null;
	private Boolean valid = null;

	public BasicHTTPParser(String content) {
		raw = content;
	}

	public BasicHTTPParser(HTTPPacket content) {
		this.parsed = content;
	}

	@Override
	public HTTPPacket parse() {
		if(parsed != null) return parsed;
		if(raw == null) return null;

		String[] fields = raw.split("\r\n"); // Split by HTTP standard CR-LF
		if(fields.length <= 0) return null;

		HTTPPacket.Builder builder;

		int i = 0;
		String mainHeader[] = fields[i++].split(" ");
		if(mainHeader.length >= 4 && mainHeader[0].startsWith("HTTP")) { // Response { HTTP/X.X, CODE, -, DESC(,...) }
			builder = new ResponseHTTPPacket.Builder()
					.withVersion(HTTPVersion.get(mainHeader[0]))
					.withCode(HTTPCode.get(Integer.valueOf(mainHeader[1])));
		} else if(mainHeader.length == 3) { // Request { CODE, PATH, HTTP/X.X }
			builder = new RequestHTTPPacket.Builder()
					.withMethod(HTTPRequest.get(mainHeader[0]))
					.withPath(mainHeader[1])
					.withVersion(HTTPVersion.get(mainHeader[2]));
		} else { // Invalid
			return null;
		}

		if(fields.length > 1) {
			for(; i < fields.length; i++) {
				String header = fields[i];
				if(header.isEmpty()) break; // body begins at empty line

				int breaker = header.indexOf(": "); // Split header using "key: value" format
				if(breaker != -1) {
					builder.addHeader(header.substring(0, breaker), header.substring(breaker + 2, header.length()));
				}
			}

			byte[] body = new byte[] {};
			for(; i < fields.length; i++) { // Convert String[] into byte[] since body could be raw data
				String content = fields[i] + "\r\n"; // Add back native indentation in case these were just raw bytes
				int currLen = body.length;
				int extraLen = content.length();
				byte[] merged = new byte[currLen + extraLen];
				System.arraycopy(body, 0, merged, 0, currLen);
				System.arraycopy(content.getBytes(Charset.forName("UTF-8")), 0, merged, currLen, extraLen);
				body = merged;
			}

			builder.withBody(body);
		}

		parsed = builder.build();
		return parsed;
	}

	@Override
	public String ǝsɹɐd() {
		if(raw != null) return raw;
		if(!validate()) return null;

		StringBuilder builder = new StringBuilder();
		if(parsed instanceof ResponseHTTPPacket) { // Response
			HTTPCode code = ((ResponseHTTPPacket) parsed).getCode();
			builder.append(String.format("%s %d - %s\r\n",
					parsed.getVersion().getVersion(),
					code.getCode(),
					code.getDescription()
			));
		} else { // Request
			String path = ((RequestHTTPPacket) parsed).getPath();
			builder.append(String.format("%s \"%s\" %s\r\n",
					((RequestHTTPPacket) parsed).getMethod().getType(),
					(path.isEmpty() ? "/" : path),
					parsed.getVersion().getVersion()));
		}

		// Add all headers to the packet
		for(String key : parsed.getHeaders().keySet()) {
			builder.append(String.format("%s: %s\r\n", key, parsed.getHeader(key)));
		}

		// Add body
		try {
			byte[] body = parsed.getBody();
			builder.append(String.format("\r\n%s\r\n", new String(body, 0, body.length, "UTF-8")));
		} catch(UnsupportedEncodingException ignored) {}

		return builder.toString();
	}

	@Override
	public boolean validate() {
		if(parsed == null) parse();

		if(valid == null) {
			valid = parsed != null
					&& parsed.getVersion() != null // Valid version
					&& !parsed.getHeaders().containsValue(null) // No invalid headers
					&& (parsed instanceof RequestHTTPPacket
					&& ((RequestHTTPPacket) parsed).getMethod() != null // Valid method
					&& ((RequestHTTPPacket) parsed).getPath() != null) // Valid path
					^ (parsed instanceof ResponseHTTPPacket // Valid request XOR (^) a valid response
					&& ((ResponseHTTPPacket) parsed).getCode() != null); // Valid code
		}

		return valid;
	}
}
