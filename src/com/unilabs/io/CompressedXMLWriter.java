package com.unilabs.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class CompressedXMLWriter extends XMLWriter {

	public CompressedXMLWriter(File out) throws IOException {
		super(new GZIPOutputStream(new FileOutputStream(out), true));
	}
	
	public CompressedXMLWriter(OutputStream out) throws IOException {
		super(new GZIPOutputStream(out, true));
	}
}
