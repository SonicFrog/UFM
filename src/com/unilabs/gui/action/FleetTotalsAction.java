package com.unilabs.gui.action;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.Message;
import com.unilabs.gui.dialog.FleetInformationDialog;
import com.unilabs.processing.FleetQuantityCalculator;

/**
 * Action pour afficher les totaux de la flotte
 * @author Ogier
 */
public class FleetTotalsAction implements GUIAction {

	@Override
	public void execute() {
		if(!UnilabsFleetManager.getInstance().hasCars()) {
			Message.showWarningMessage("Aucune voiture chargée !");
			return;
		}
		new FleetInformationDialog(UnilabsFleetManager.getInstance().getGUI(), 
				new FleetQuantityCalculator(UnilabsFleetManager.getInstance().getCars()), 
					UnilabsFleetManager.getInstance().getCurrency());
	}
}
