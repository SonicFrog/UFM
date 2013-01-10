package com.unilabs.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyReader {

	public static String COMMENT = "Fichier de configuration pour UFM\nN'éditez de fichier que si vous savez ce que vous faites, " +
			"vous risquez de corrompre votre installation!";
	private File propertyFile;
	private Properties prop;
	private OutputStream out;

	public PropertyReader(String path) throws IOException {
		propertyFile = new File(path);
		out = new BufferedOutputStream(new FileOutputStream(propertyFile));
		init();
	}

	public PropertyReader(File file) throws IOException {
		propertyFile = file;
		init();
	}

	public PropertyReader(Properties p) {
		prop = p;
	}

	private void init() throws IOException {
		prop = new Properties();
		prop.load(new FileInputStream(propertyFile));
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return prop.getProperty(key, defaultValue);
	}

	public void writeProperty(String key, String value) {
		System.out.println("Setting key " + key + " to " + value);
		prop.setProperty(key, value);
	}
	
	public void flush() throws IOException {
		prop.store(out, COMMENT);
	}
}
