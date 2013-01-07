package com.unilabs.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.unilabs.entities.UnilabsCar;

/**
 * Dialogue permettant de sélectionner deux voitures pour les comparer
 * @version 1.0
 * @author Ogier
 *
 */
public class ComparisonSelectorDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -7877795987682688891L;
	private JButton ok = new JButton("Ok"), cancel = new JButton("Annuler");
	private JPanel buttonPanel = new JPanel();
	private JPanel selectorPanel = new JPanel();
	private JComboBox c1, c2;
	
	private ConcurrentHashMap<String, UnilabsCar> data;
	
	public ComparisonSelectorDialog(ConcurrentHashMap<String, UnilabsCar> data) {
		super();
		this.data = data;
		setModal(true);
		setTitle("Comparaison");
		setLocationRelativeTo(null);
		setSize(new Dimension(450, 320));
		build();
		setVisible(true);
	}
	
	private void build() {
		c1 = new JComboBox();
		c2 = new JComboBox();
		for( UnilabsCar c : data.values() ) {
			c1.addItem(c.getPlaque());
			c2.addItem(c.getPlaque());
		}
		ok.addActionListener(this);
		cancel.addActionListener(this);
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		selectorPanel.add(c1);
		selectorPanel.add(c2);
		setLayout(new BorderLayout());
		add(selectorPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ok) {
			new CarComparisonDialog(data.get(c1.getSelectedItem()), data.get(c2.getSelectedItem()));
		}
		dispose();
	}
}
