package com.unilabs.gui.action;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.dialog.FleetInformationDialog;
import com.unilabs.processing.AverageCalculator;

public class SingleCarAverageAction implements GUIAction {

	public void execute() {
		UnilabsFleetManager ufm = UnilabsFleetManager.getInstance();
		UnilabsCar selected = ufm.getCars()[ufm.getSelectedCar()];
		new FleetInformationDialog(ufm.getGUI(), new AverageCalculator(selected), ufm.getCurrency());
	}
}
