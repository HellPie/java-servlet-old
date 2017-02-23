package dev.hellpie.school.java.servlet.values;

public enum HTTPVersion {
	HTTP_1_0("HTTP/1.0"),
	HTTP_1_1("HTTP/1.1"),
	HTTP_2("HTTP/2.0")
	;

	private final String version;
	HTTPVersion(String version) {
		this.version = version;
	}

	public final String getVersion() {
		return version;
	}

	public static HTTPVersion get(String string) {
		switch(string) {
			case "HTTP/1.0":
				return HTTP_1_0;
			case "HTTP/1.1":
				return HTTP_1_1;
			case "HTTP/2.0":
				return HTTP_2;
			default:
				return null;
		}
	}
}
