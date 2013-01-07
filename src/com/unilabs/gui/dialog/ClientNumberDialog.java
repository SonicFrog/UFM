package com.unilabs.gui.dialog;

import java.awt.Frame;

import javax.swing.JComboBox;
import javax.swing.JDialog;

public class ClientNumberDialog extends JDialog {

	private JComboBox selector = new JComboBox();
	public ClientNumberDialog (Frame parent, Integer[] clients) {
		super(parent);
		initComboBox(clients);
		init();
	}
	
	private void initComboBox(Integer[] clients) {
		for(Integer i : clients) {
			selector.addItem(i);
		}
	}
	
	private void init() {
		setTitle("Sélectionner un numéro client");
		add(selector);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
