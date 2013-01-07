package com.unilabs.gui.action;

import java.io.File;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.dialog.ExportDialog;



public class SaveGUIAction implements GUIAction {

	@Override
	public void execute() {
		UnilabsFleetManager ufm = UnilabsFleetManager.getInstance();
		if(!ufm.hasCars())
			return;
		File out = new ExportDialog(ufm.getGUI(), null).getFile();
		if(out == null)
			return;
		if(!out.getName().endsWith(ufm.getFileExtension())) {
			out = new File(out.getAbsolutePath() + ufm.getFileExtension());
		}
		ufm.save(out);
	}
}
