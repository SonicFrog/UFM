package com.unilabs.gui.action;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.unilabs.gui.Message;


public class HelpGUIAction implements GUIAction {

	private static final String HELP_URL = "http://ars3nic.proteck.ch/unilabs/";
	
	@Override
	public void execute() {
		try {
			Desktop.getDesktop().browse(new URI(HELP_URL));
		} catch (UnsupportedOperationException e) {
			Message.showErrorMessage("Erreur", "Impossible de detecter votre navigateur ! Pour voir l'aide : " + HELP_URL);
		} catch (IOException e) {
			Message.showErrorMessage("Erreur", e.getClass() + e.getLocalizedMessage());
		} catch (URISyntaxException e) {
			
		}
	}
}
