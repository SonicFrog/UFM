package com.unilabs.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.gui.Message;

/**
 * Panneau d'informations sur une voiture avec en options l'affichage des pleins
 * 
 * @see PleinPanel
 * @see UnilabsCar
 * @version 1.0
 * @since 2.0
 * @author Ogier
 *
 */
public class CarInfoDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JButton add = new JButton("Ajouter un plein");
	private JButton viewP = new JButton("Voir les pleins");
	
	private JPanel infoPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private UnilabsCar car;
	
	public CarInfoDialog(JFrame gui, UnilabsCar c) {
		super(gui, c.getPlaque());
		car = c;
		init();
		setModal(false);
		setSize(new Dimension(450, 200));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void init() {
		setLayout(new BorderLayout());
		infoPanel.setLayout(new GridLayout(3, 2));
		add.addActionListener(this);
		viewP.addActionListener(new ShowPleinListener(car));
		buttonPanel.add(add);
		buttonPanel.add(viewP);
		infoPanel.add(new JLabel("Numéro client :"));
		infoPanel.add(new JLabel(String.valueOf(car.getNumeroClient())));
		infoPanel.add(new JLabel("Plaque :"));
		infoPanel.add(new JLabel(car.getPlaque()));
		infoPanel.add(new JLabel("Pleins identiques ignorés :"));
		infoPanel.add(new JLabel(String.valueOf(car.getDuplicateCount())));
		add(infoPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent e) {
		AddPleinDialog adp = new AddPleinDialog(null, "");
		if(adp.getPlein() != null)
			if(!car.addPlein(adp.getPlein())){
				Message.showErrorMessage("Erreur", "Ce plein est déjà présent dans cette voiture");
			}
	}
	
	private class ShowPleinListener implements ActionListener {

		private UnilabsCar data;
		
		public ShowPleinListener(UnilabsCar c) {
			data = c;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			new PleinViewer(data);
		}	
	}
}
