/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilabs.utils;

import com.unilabs.io.ODSInputStream;
import com.unilabs.io.XLSInputStream;
import java.io.File;
import java.io.InputStream;

/**
 *
 * @author Ogier
 */
public class FileHandler {
    
    public static InputStream handleFile(File f) {
	return handleFile(f.getName());
    }
    
    public static InputStream handleFile(String fname) {
	int lastDot = fname.lastIndexOf('.');
	InputStream retVal;
	if(lastDot == -1) {
	    throw new RuntimeException("Le format de fichier est invalide");
	}
	switch(fname.substring(lastDot)) {
	    case ".xls" :
		retVal = new XLSInputStream(fname, null);
		break;
		
	    case ".ods" :
		retVal = new ODSInputStream(fname, null);
		break;
		
	    default :
		throw new RuntimeException("Le format de fichier est invalide");
	}
	System.out.println("File handled successfully");
	return retVal;
    }
}