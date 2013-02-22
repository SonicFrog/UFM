package com.unilabs.gui.action;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.FileChooser;

/**
 * Action pour importer un fichier binaire dans UFM
 * @author Ogier
 */
public class OpenBinaryGUIAction implements GUIAction {

	@Override
	public void execute() {
		UnilabsFleetManager ufm = UnilabsFleetManager.getInstance();
		FileChooser jfc = new FileChooser();
		jfc.setFileFilter(new FileNameExtensionFilter("Fichier UFM (.ufm)", "ufm"));
		jfc.setMultiSelectionEnabled(false);
		jfc.showOpenDialog(ufm.getGUI());
		if(jfc.getSelectedFile() != null) {
			ufm.openBinary(jfc.getSelectedFile());
			ufm.getGUI().paintAll(ufm.getGUI().getGraphics());
		}
	}
}