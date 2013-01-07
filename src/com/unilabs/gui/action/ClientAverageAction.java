package com.unilabs.gui.action;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.dialog.FleetInformationDialog;
import com.unilabs.processing.ClientSorter;
import com.unilabs.processing.FleetAverageCalculator;

/**
 * 
 * @author Ogier
 *
 */
public class ClientAverageAction implements GUIAction {

	public void execute() {
		int client = 0;
		try {
			String ret = JOptionPane.showInputDialog(UnilabsFleetManager.getInstance().getGUI(), "Tapez le numéro client :");
			if(ret == null)
				return;
			client = Integer.parseInt(ret);
		} catch (NumberFormatException e) {
			throw new RuntimeException("Veuillez taper un nombre");
		}
		ClientSorter sort = new ClientSorter(UnilabsFleetManager.getInstance().getCars(), client);
		JDialog dialog = new FleetInformationDialog(UnilabsFleetManager.getInstance().getGUI(), 
				new FleetAverageCalculator(sort.getClientsCar()), UnilabsFleetManager.getInstance().getCurrency());
		dialog.setTitle("Moyennes pour le client n°" + client);
	}
}
