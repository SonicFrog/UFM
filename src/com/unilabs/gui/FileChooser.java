package com.unilabs.gui;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * FileChooser avec mise en mémoire du dernier fichier ouvert pour pouvoir
 * ouvrir directement le dernier dossier utilisé lors de la prochaine utilisation
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
	 * ou dans le dossier par défaut si c'est la première utilisation de ce constructeur.
	 */
	public FileChooser() {
		super(lastFile);
		setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
	
	/**
	 * Instancie une nouveau file chooser avec comme dossier de démarrage le chemin spécifié
	 * @param path 
	 *		Le chemin de départ pour ce file chooser
	 */
	public FileChooser(String path) {
		super(path);
		lastFile = new File(path);
		setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
	
	@Override
	public File getSelectedFile() {
		lastFile = super.getSelectedFile();
		return lastFile;
	}
}
