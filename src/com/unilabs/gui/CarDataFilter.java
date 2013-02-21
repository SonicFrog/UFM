package com.unilabs.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Filtre de fichiers à utiliser dans JFileChooser pour les données de tableur
 * @author Ogier
 * @version 1.0
 */
public class CarDataFilter extends FileFilter {

    public static final String[] ACCEPTED_EXTENSIONS = { ".xls" , ".ods" };
    
    @Override
    public boolean accept(File file) {
	int lastDot = file.getName().lastIndexOf('.');
	if(lastDot == -1)
	    return false;
	String extension = file.getName().substring(lastDot);
	for(String s : ACCEPTED_EXTENSIONS) {
	    if(s.equals(extension)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public String getDescription() {
	return "Données de pleins (.xls ou .ods)";
    }
}
