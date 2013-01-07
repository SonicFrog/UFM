package com.unilabs.gui.action;

import com.unilabs.entities.UnilabsFleetManager;


public class CloseGUIAction implements GUIAction {

	@Override
	public void execute() {
		UnilabsFleetManager.getInstance().close();
	}
}
