package com.unilabs.gui.dialog;

import javax.swing.JOptionPane;

import com.unilabs.entities.UnilabsFleetManager;

/**
 * Boîte de dialogue pour la sauvegarde du fichier en fin de programme
 * @author Ogier
 *
 */
public class ExitDialog {
	
	private int result;
	
	public ExitDialog(boolean fileSaved) {
		if(fileSaved) {
			result = JOptionPane.NO_OPTION;
		} else {
			result = JOptionPane.showConfirmDialog(UnilabsFleetManager.getInstance().getGUI(), "Les modifications n'ont pas été sauvegardées. Voulez vous enregistrer avant de quitter ?", "Attention", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public int getResult() {
		return result;
	}
}
