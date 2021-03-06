package com.unilabs.gui;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * FileChooser avec mise en m�moire du dernier fichier ouvert pour pouvoir
 * ouvrir directement le dernier dossier utilis� lors de la prochaine utilisation
 * d'un FileChooser
 * 
 * @author Ogier
 * @see JFileChooser
 * @version 1.0
 */
public class FileChooser extends JFileChooser {
	
	private static final long serialVersionUID = -2714285051219120198L;
	private static File lastFile = null;
	
	/**
	 * Initialise un FileChooser dans le dernier dossier ouvert par l'utilisateur
	 * ou dans le dossier par d�faut si c'est la premi�re utilisation de ce constructeur.
	 */
	public FileChooser() {
		super(lastFile);
		setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
	
	public File getSelectedFile() {
		lastFile = super.getSelectedFile();
		return lastFile;
	}
}
