package com.unilabs.gui.action;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.dialog.FleetInformationDialog;
import com.unilabs.processing.ClientSorter;
import com.unilabs.processing.FleetAverageCalculator;

/**
 * Action pour afficher des informations pour les voitures associées à un certains client
 * au travers d'un menu déroulant contenant tout les numéro client
 * 
 * @author Ogier
 */
public class ClientAverageDropdownAction extends JDialog implements GUIAction, ActionListener {

	private static final long serialVersionUID = 2909546065420153392L;
	private UnilabsFleetManager ufm = UnilabsFleetManager.getInstance();
	private JComboBox<Integer> cbox;
	private JPanel up, down;
	private JButton ok = new JButton("Ok"), cancel = new JButton("Annuler");

	/**
	 * Instancie l'action
	 */
	public ClientAverageDropdownAction() {
		setTitle("Selectionnez votre numéro client");
		setLocationRelativeTo(null);
		setSize(new Dimension(300, 100));
		setResizable(false);
		setLayout(new BorderLayout());
	}

	/**
	 * Construit le selectionneur de numéro client
	 */
	private void init() {
		cbox = new JComboBox<>();
		if(up != null) {
			remove(up);
			remove(down);
		}
		up = new JPanel();
		down = new JPanel();
		for(UnilabsCar c : UnilabsFleetManager.getInstance().getCars()) {
			cbox.addItem(c.getNumeroClient());
		}
		down.add(ok);
		down.add(cancel);
		up.add(new JLabel("Numéro client :"));
		up.add(cbox);
		add(up, BorderLayout.NORTH);
		add(down, BorderLayout.SOUTH);
		cancel.addActionListener(this);
		ok.addActionListener(this);
	}

	@Override
	public void execute() {
		init();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
		if(e.getSource() == ok) {
			ClientSorter sort = new ClientSorter(UnilabsFleetManager.getInstance().getCars(), (Integer) cbox.getSelectedItem());
			JDialog dialog = new FleetInformationDialog(ufm.getGUI(), new FleetAverageCalculator(sort.getClientsCar()), ufm.getCurrency());
			dialog.setTitle("Moyenne pour le client numéro " + cbox.getSelectedItem());
		}
	}
}
