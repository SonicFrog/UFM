package com.unilabs.gui.action;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.Message;
import com.unilabs.gui.dialog.ComparisonSelectorDialog;


public class CompareGUIAction implements GUIAction {

	@Override
	public void execute() {
		if(UnilabsFleetManager.getInstance().getCars().length < 2) {
			Message.showErrorMessage(UnilabsFleetManager.getInstance().getGUI(), "Erreur", "Aucune comparaison possible : moins de 2 voitures chargées en mémoire");
			return;
		}
		new ComparisonSelectorDialog(UnilabsFleetManager.getInstance().getRawCars());
	}

}
