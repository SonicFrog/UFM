package com.unilabs.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

import com.unilabs.security.PlateChecker;

/**
 * Ecriture d'un fichier XML dans un flux compressé
 * @version 1.0
 * @author ogier
 * @see XMLReader
 * @see DeflaterInputStream
 *
 */
public class CompressedXMLReader extends XMLReader {

	public CompressedXMLReader(InputStream in, PlateChecker pc) throws IOException {
		super(new GZIPInputStream(in), pc);
	}
	
	public CompressedXMLReader(File in, PlateChecker pc) throws IOException {
		super(new GZIPInputStream(new FileInputStream(in)), pc);
	}
}
