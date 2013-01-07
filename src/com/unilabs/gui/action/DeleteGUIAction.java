package com.unilabs.gui.action;

import javax.swing.JOptionPane;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.Message;


public class DeleteGUIAction implements GUIAction {

	@Override
	public void execute() {
		try {
			UnilabsFleetManager fm = UnilabsFleetManager.getInstance();
			System.out.println("Removing car " + fm.getGUI().getSelectedCar());
			if(JOptionPane.showConfirmDialog(fm.getGUI(), "Voulez vous vraiment supprimer la voiture " + fm.getGUI().getSelectedCar(), 
					"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				fm.removeCar(fm.getGUI().getSelectedCar());
			}
		} catch (NullPointerException e) {
			Message.showWarningMessage("Aucune voiture chargée en mémoire !");
		}
	}
}
