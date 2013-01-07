package com.unilabs.gui.action;

import java.io.IOException;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.Message;
import com.unilabs.gui.dialog.ExportDialog;
import com.unilabs.processing.TableCreator;

public class ExportGUIAction implements GUIAction {

	@Override
	public void execute() {
		TableCreator tc;
		if(!UnilabsFleetManager.getInstance().hasCars()) {
			Message.showWarningMessage("Aucune voiture chargée en mémoire !");
			return;
		}
		ExportDialog jfc = new ExportDialog(UnilabsFleetManager.getInstance().getGUI(), null);
		if(jfc.getFile() == null) 
			return;
		tc = new TableCreator(TableCreator.createAverageTableModel(UnilabsFleetManager.getInstance().getCars()), jfc.getFile());
		try {
			tc.write();
		} catch (IOException e) {
			e.printStackTrace();
			Message.showErrorMessage(UnilabsFleetManager.getInstance().getGUI(), "Erreur lors de la sauvegarde", e.getLocalizedMessage());
		}
		Message.showSuccessMessage("Export réussi");
	}

}
