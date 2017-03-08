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

package dev.hellpie.school.java.servlet.values;

public enum HTTPMethod {
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
	HTTPMethod(String type) {
		this.type = type;
	}

	public final String getMethod() {
		return type;
	}

	public static HTTPMethod get(String string) {
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
