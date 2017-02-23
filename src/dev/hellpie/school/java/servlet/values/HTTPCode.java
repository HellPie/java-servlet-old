package dev.hellpie.school.java.servlet.values;

public enum HTTPCode {
	INFO_100(100, "Continue"),
	INFO_101(101, "Switching Protocols"),
	INFO_102(102, "Processing"),

	SUCCESS_200(200, "OK"),
	SUCCESS_201(201, "Created"),
	SUCCESS_202(202, "Accepted"),
	SUCCESS_203(203, "Non-authoritative Information"),
	SUCCESS_204(204, "No Content"),
	SUCCESS_205(205, "Reset Content"),
	SUCCESS_206(206, "Partial Content"),
	SUCCESS_207(207, "Multi-Status"),
	SUCCESS_208(208, "Already Reported"),
	SUCCESS_226(226, "IM Used"),

	REDIRECT_300(300, "Multiple Choices"),
	REDIRECT_301(301, "Moved Permanently"),
	REDIRECT_302(302, "Found"),
	REDIRECT_303(303, "See Other"),
	REDIRECT_304(304, "Not Modified"),
	REDIRECT_305(305, "Use Proxy"),
	REDIRECT_307(307, "Temporary Redirect"),
	REDIRECT_308(308, "Permanent Redirect"),

	CLIENT_400(400, "Bad Request"),
	CLIENT_401(401, "Unauthorized"),
	CLIENT_402(402, "Payment Required"),
	CLIENT_403(403, "Forbidden"),
	CLIENT_404(404, "Not Found"),
	CLIENT_405(405, "Method Not Allowed"),
	CLIENT_406(406, "Not Acceptable"),
	CLIENT_407(407, "Proxy Authentication Required"),
	CLIENT_408(408, "Request Timeout"),
	CLIENT_409(409, "Conflict"),
	CLIENT_410(410, "Gone"),
	CLIENT_411(411, "Length Required"),
	CLIENT_412(412, "Precondition Failed"),
	CLIENT_413(413, "Payload Too Large"),
	CLIENT_414(414, "Request-URI Too Long"),
	CLIENT_415(415, "Unsupported Media Type"),
	CLIENT_416(416, "Requested Range Not Satisfiable"),
	CLIENT_417(417, "Expectation Failed"),
	CLIENT_418(418, "I'm a teapot"),
	CLIENT_421(421, "Misdirected Request"),
	CLIENT_422(422, "Unprocessable Entity"),
	CLIENT_423(423, "Locked"),
	CLIENT_424(424, "Failed Dependency"),
	CLIENT_426(426, "Upgrade Required"),
	CLIENT_428(428, "Precondition Required"),
	CLIENT_429(429, "Too Many Requests"),
	CLIENT_431(431, "Request Header Fields Too Large"),
	CLIENT_444(444, "Connection Closed Without Response"),
	CLIENT_451(451, "Unavailable For Legal Reasons"),
	CLIENT_499(499, "Client Closed Request"),

	SERVER_500(500, "Internal Server Error"),
	SERVER_501(501, "Not Implemented"),
	SERVER_502(502, "Bad Gateway"),
	SERVER_503(503, "Service Unavailable"),
	SERVER_504(504, "Gateway Timeout"),
	SERVER_505(505, "HTTP Version Not Supported"),
	SERVER_506(506, "Variant Also Negotiates"),
	SERVER_507(507, "Insufficient Storage"),
	SERVER_508(508, "Loop Detected"),
	SERVER_510(510, "Not Extended"),
	SERVER_511(511, "Network Authentication Required"),
	SERVER_599(599, "Network Connect Timeout Error")
	;

	private final int code;
	private final String description;
	HTTPCode(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static HTTPCode get(int code) {
		switch(code) {
			case 100:
				return INFO_100;
			case 101:
				return INFO_101;
			case 102:
				return INFO_102;
			case 200:
				return SUCCESS_200;
			case 201:
				return SUCCESS_201;
			case 202:
				return SUCCESS_202;
			case 203:
				return SUCCESS_203;
			case 204:
				return SUCCESS_204;
			case 205:
				return SUCCESS_205;
			case 206:
				return SUCCESS_206;
			case 207:
				return SUCCESS_207;
			case 208:
				return SUCCESS_208;
			case 226:
				return SUCCESS_226;
			case 300:
				return REDIRECT_300;
			case 301:
				return REDIRECT_301;
			case 302:
				return REDIRECT_302;
			case 303:
				return REDIRECT_303;
			case 304:
				return REDIRECT_304;
			case 305:
				return REDIRECT_305;
			case 307:
				return REDIRECT_307;
			case 308:
				return REDIRECT_308;
			case 400:
				return CLIENT_400;
			case 401:
				return CLIENT_401;
			case 402:
				return CLIENT_402;
			case 403:
				return CLIENT_403;
			case 404:
				return CLIENT_404;
			case 405:
				return CLIENT_405;
			case 406:
				return CLIENT_406;
			case 407:
				return CLIENT_407;
			case 408:
				return CLIENT_408;
			case 409:
				return CLIENT_409;
			case 410:
				return CLIENT_410;
			case 411:
				return CLIENT_411;
			case 412:
				return CLIENT_412;
			case 413:
				return CLIENT_413;
			case 414:
				return CLIENT_414;
			case 415:
				return CLIENT_415;
			case 416:
				return CLIENT_416;
			case 417:
				return CLIENT_417;
			case 418:
				return CLIENT_418;
			case 421:
				return CLIENT_421;
			case 422:
				return CLIENT_422;
			case 423:
				return CLIENT_423;
			case 424:
				return CLIENT_424;
			case 426:
				return CLIENT_426;
			case 428:
				return CLIENT_428;
			case 429:
				return CLIENT_429;
			case 431:
				return CLIENT_431;
			case 444:
				return CLIENT_444;
			case 451:
				return CLIENT_451;
			case 499:
				return CLIENT_499;
			case 500:
				return SERVER_500;
			case 501:
				return SERVER_501;
			case 502:
				return SERVER_502;
			case 503:
				return SERVER_503;
			case 504:
				return SERVER_504;
			case 505:
				return SERVER_505;
			case 506:
				return SERVER_506;
			case 507:
				return SERVER_507;
			case 508:
				return SERVER_508;
			case 510:
				return SERVER_510;
			case 511:
				return SERVER_511;
			case 599:
				return SERVER_599;
			default:
				return null;
		}
	}
}
