package com.unilabs.gui.action;

import javax.swing.JFileChooser;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.CarDataFilter;
import com.unilabs.gui.FileChooser;


/**
 * Element de GUI permettan d'executer l'action d'ouverture de fichier
 * @version 1.1
 * @see JFileChooser
 * @see UnilabsFleetManager#openODS(java.io.File)
 * @author Ogier
 *
 */
public class OpenODSGUIAction implements GUIAction {

	@Override
	public void execute() {
		UnilabsFleetManager ufm = UnilabsFleetManager.getInstance();
		FileChooser jfc = new FileChooser();
		jfc.setFileFilter(new CarDataFilter());
		jfc.setMultiSelectionEnabled(false);
		jfc.showOpenDialog(UnilabsFleetManager.getInstance().getGUI());
		if(jfc.getSelectedFile() != null) {
			ufm.openODS(jfc.getSelectedFile());
			ufm.getGUI().paintAll(ufm.getGUI().getGraphics());
		}
	}
}
