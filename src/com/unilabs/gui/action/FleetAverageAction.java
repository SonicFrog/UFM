package com.unilabs.gui.action;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.Message;
import com.unilabs.gui.dialog.FleetInformationDialog;
import com.unilabs.processing.FleetAverageCalculator;

/**
 * Action pour l'affichage des moyennes de toute la flotte
 * @author Ogier
 */
public class FleetAverageAction implements GUIAction {

	@Override
	public void execute() {
		if(!UnilabsFleetManager.getInstance().hasCars()) {
			Message.showWarningMessage("Aucune voiture chargée !");
			return;
		}
		new FleetInformationDialog(UnilabsFleetManager.getInstance().getGUI(), 
				new FleetAverageCalculator(UnilabsFleetManager.getInstance().getCars()),
				UnilabsFleetManager.getInstance().getCurrency());
	}
}
