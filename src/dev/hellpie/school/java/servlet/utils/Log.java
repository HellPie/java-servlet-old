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

package dev.hellpie.school.java.servlet.utils;

public final class Log {

	private enum Level {
		DEBUG(0),
		INFO(1),
		ERROR(2),
		NOTHING(Integer.MAX_VALUE)
		;
		public final int level;
		Level(int level) { this.level = level; }
	}

	private final static boolean MERGE_STREAMS = false; // Error messages go into System.out instead of System.err
	private final static Level CURRENT_LEVEL = Level.DEBUG; // Don't log below this level

	private Log() { /* Utils - Do Not Instantiate */ }

	public static String getTag(Class clazz) {
		String name = clazz.getSimpleName();
		return (name.length() > 16 ? name.substring(0, 16) : name);
	}

	public static String getTag(Object object) {
		String tag = getTag(object.getClass());
		return (tag.length() >= 16 ? tag : String.format("%s-%d", tag, object.hashCode()));
	}

	public static void d(String tag, String msg) {
		log(Level.DEBUG, tag, msg);
	}

	public static void i(String tag, String msg) {
		log(Level.INFO, tag, msg);
	}

	public static void e(String tag, String msg) {
		log(Level.ERROR, tag, msg);
	}

	private synchronized static void log(Level lvl, String tag, String msg) {
		if(lvl == null || tag == null || msg == null || tag.trim().isEmpty() || CURRENT_LEVEL.level > lvl.level) return;
		if(tag.length() > 16) tag = tag.substring(0, 16);
		(lvl.level < Level.ERROR.level || MERGE_STREAMS ? System.out : System.err).println(String.format(
				"[%-8s->  %-12s: %s",
				lvl.name() + "]",
				tag,
				msg
		));
	}
}
