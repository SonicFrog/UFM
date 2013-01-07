package com.unilabs.gui.action;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.graph.PriceGraph;
import com.unilabs.gui.GraphFrame;
import com.unilabs.gui.Message;

public class PriceGraphAction implements GUIAction {

	@Override
	public void execute() {
		try {
			if(UnilabsFleetManager.getInstance().hasCars() && UnilabsFleetManager.getInstance().getSelectedCar() != -1)
				new GraphFrame(new PriceGraph(UnilabsFleetManager.getInstance().getCars()[UnilabsFleetManager.getInstance().getSelectedCar()]));
			else
				Message.showWarningMessage("Aucune voiture séléctionnée");
		} catch(NullPointerException e) {
			Message.showErrorMessage(UnilabsFleetManager.getInstance().getGUI(), "Erreur", "Aucune voiture séléctionnée");
		}
	}
}
