package dev.hellpie.school.java.servlet.values;

public enum HTTPRequest {
	GET("GET"),
	POST("POST"),
	PUT("PUT"),
	DELETE("DELETE"),
	OPTIONS("OPTIONS"),
	HEAD("HEAD"),
	TRACE("TRACE"),
	CONNECT("CONNECT")
	;

	private final String type;
	HTTPRequest(String type) {
		this.type = type;
	}

	public final String getType() {
		return type;
	}

	public static HTTPRequest get(String string) {
		switch(string) {
			case "GET":
				return GET;
			case "POST":
				return POST;
			case "PUT":
				return PUT;
			case "DELETE":
				return DELETE;
			case "OPTIONS":
				return OPTIONS;
			case "HEAD":
				return HEAD;
			case "TRACE":
				return TRACE;
			case "CONNECT":
				return CONNECT;
			default:
				return null;
		}
	}
}
