package dev.hellpie.school.java.servlet.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileProvider {

	private static final String[] BLACKLIST_FILES = new String[] {
			"favicon.ico" // Some browser request automatically favicon for websites. Servlet can't send images, yet.
	};

	private final File base;

	public FileProvider(File base) throws IOException {
		if(!base.exists() || !base.isDirectory()) throw new IOException("Unable to access folder.");
		this.base = base;
	}

	public byte[] read(String path) throws IOException {
		// Check if blacklisted file, eventually return null (Should mean 204 No Content)
		for(String file : BLACKLIST_FILES) if(path.substring(path.lastIndexOf('/')).contains(file)) return null;

		File input = new File(base.getAbsolutePath() + "/" + (path != null ? path : ""));
		if(!input.exists()) throw new IOException("File does not exist: " + input.toString());
		if(input.isDirectory()) return read(path + "/index.html"); // Default to index as per HTTP standards

		if(input.isFile()) return Files.readAllBytes(Paths.get(base.getAbsolutePath() + "/" + path));
		throw new IOException("Unable to read file: " + input.toString());
	}
}
