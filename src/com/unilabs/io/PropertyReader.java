package com.unilabs.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyReader {

	public static String COMMENT = "N'";
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
	
	private void init() throws IOException {
		prop = new Properties();
		prop.load(new FileInputStream(propertyFile));
	}
	
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
	
	public void writeProperty(String key, String value) throws IOException {
		prop.setProperty(key, value);
		prop.store(out, COMMENT);
	}
}
