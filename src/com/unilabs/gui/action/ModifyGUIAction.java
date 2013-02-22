package com.unilabs.gui.action;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.Message;
import com.unilabs.gui.dialog.CarInfoDialog;


public class ModifyGUIAction implements GUIAction {

	@Override
	public void execute() {
		UnilabsFleetManager ufm = UnilabsFleetManager.getInstance();
		if(ufm.hasCars() && ufm.getSelectedCar() != -1) {
			new CarInfoDialog(ufm.getGUI(), ufm.getRawCars().get(ufm.getSelectedCarPlate()));
		}
		else {
			Message.showWarningMessage("Aucune voiture selectionnée");
		}
	}
}
