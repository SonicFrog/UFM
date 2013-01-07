package com.unilabs.gui.action;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.Message;
import com.unilabs.gui.dialog.SearchDialog;


public class SearchGUIAction implements GUIAction {

	@Override
	public void execute() {
		UnilabsFleetManager ufm = UnilabsFleetManager.getInstance();
		if(ufm.hasCars()) {
			new SearchDialog(ufm.getCars());
		} else {
			Message.showWarningMessage("Aucune voiture chargée");
		}
	}

}
