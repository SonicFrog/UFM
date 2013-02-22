package com.unilabs.gui.action;

import com.unilabs.entities.UnilabsFleetManager;

/**
 * Action de fermeture du programme
 * @author Ogier
 */
public class ExitAction implements GUIAction {
	@Override
	public void execute() {
		UnilabsFleetManager.getInstance().exit();
	}

}
