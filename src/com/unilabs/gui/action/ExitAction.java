package com.unilabs.gui.action;

import com.unilabs.entities.UnilabsFleetManager;

public class ExitAction implements GUIAction {
	public void execute() {
		UnilabsFleetManager.getInstance().exit();
	}

}
