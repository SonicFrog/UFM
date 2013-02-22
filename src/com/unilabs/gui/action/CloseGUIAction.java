package com.unilabs.gui.action;

import com.unilabs.entities.UnilabsFleetManager;

/**
 * Action de fermeture du fichier en cours
 * @author Ogier
 */
public class CloseGUIAction implements GUIAction {

	@Override
	public void execute() {
		UnilabsFleetManager.getInstance().close();
	}
}
