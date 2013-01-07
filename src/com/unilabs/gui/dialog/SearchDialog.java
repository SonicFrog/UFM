package com.unilabs.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.processing.BadCarFinder;

/**
 * Boîte de dialogue pour rechercher des voitures parmi celle fournies
 * @version 1.0
 * @since 1.0
 * @see UnilabsCar
 * @see UnilabsFleetManager
 * @see JDialog
 * @see ActionListener
 * @see BadCarFinder
 * @author Ogier
 *
 */
public class SearchDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -5889397837815027576L;
	
	private JButton okAfterMidday = new JButton("Pleins après midi");
	private JButton ok20Liters = new JButton("Pleins de plus de 20L");
	private JButton cancel = new JButton("Annuler");
	private JPanel buttons = new JPanel();
	private JLabel label = new JLabel("Appuyez sur rechercher pour trouver les \"mauvaises voitures\"");
	
	private UnilabsCar[] cars;


	/**
	 * Instancie une boîte de dialogue permettant de rechercher des voitures
	 * @param cars
	 * 		La liste des voitures parmi laquelle on recherche
	 */
	public SearchDialog(UnilabsCar[] cars) {
		this.cars = cars;
		setTitle("Rechercher ...");
		setSize(new Dimension(600, 150));
		setLocationRelativeTo(null);
		setModal(true);
		init();
		setVisible(true);
	}
	
	/**
	 * Construit les composants de la fenêtre
	 */
	private void init() {
		getContentPane().setLayout(new BorderLayout());
		buttons.setLayout(new GridLayout(1, 2));
		okAfterMidday.addActionListener(this);
		ok20Liters.addActionListener(this);
		cancel.addActionListener(this);
		buttons.add(okAfterMidday);
		buttons.add(ok20Liters);
		buttons.add(cancel);
		getContentPane().add(label, BorderLayout.CENTER);
		getContentPane().add(buttons, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okAfterMidday) {
			new ResultDialog(BadCarFinder.getBadCars(cars), "Voitures ayant effectué au moins un plein après midi");
		} else if(e.getSource() == ok20Liters) {
			new ResultDialog(cars, "Voitures ayant effectué au moins un plein de plus de 20L");
		}
		dispose();
	}
}
