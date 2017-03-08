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
