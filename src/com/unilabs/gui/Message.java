package com.unilabs.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.unilabs.entities.UnilabsFleetManager;

/**
 * Classe d'affichage de message d'erreur
 * @see JOptionPane
 * @version 1.1
 * @author Ogier
 *
 */
public class Message {

	/**
	 * Affiche un message d'erreur
	 * @param parent
	 * 		Le composant graphique parent du message d'erreur
	 * @param title
	 * 		Le titre du message d'erreur
	 * @param message
	 * 		Le contenu du message
	 */
	public static void showErrorMessage(JFrame parent, String title, String message) {
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Affiche un message d'erreur sans conteneur parent
	 * @param title
	 * 		Le titre du message d'erreur
	 * @param message
	 * 		Le contenu du message d'erreur
	 */
	public static void showErrorMessage(String title, String message) {
		showErrorMessage(null, title, message);
	}
	
	/**
	 * Affiche un message de confirmation d'écrasement de fichier
	 * @see JOptionPane
	 * @return
	 * 		Le code de retour du dialogue
	 */
	public static int fileOverwriteConfirmationMessage() {
		return JOptionPane.showConfirmDialog(UnilabsFleetManager.getInstance().getGUI(), "Ce fichier existe déjà. Voulez vous l'écraser ?", "Ecrasez un fichier", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
	}
	
	/**
	 * Affiche un message de succès
	 * @see JOptionPane
	 */
	public static void showSuccessMessage(String message) {
		JOptionPane.showMessageDialog(UnilabsFleetManager.getInstance().getGUI(), message, "Succès", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Affiche un message d'avertissement apparanté à la GUI principale du programme
	 * @param message 
	 *		Le texte du message
	 */
	public static void showWarningMessage(String message) {
		JOptionPane.showMessageDialog(UnilabsFleetManager.getInstance().getGUI(), message, "Attention", JOptionPane.WARNING_MESSAGE);	
	}
	
	/**
	 * Message d'erreur lors de l'accès à une fonction encore non présente dans le programme
	 */
	public static void showUnimplementedMessage() {
		showWarningMessage("Cette fonction n'est pas encore disponible");
	}
}
