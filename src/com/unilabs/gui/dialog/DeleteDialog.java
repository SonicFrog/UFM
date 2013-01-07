package com.unilabs.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.unilabs.entities.UnilabsFleetManager;

/**
 * Classe pour les bo�tes de dialogue de suppression de voiture
 * 
 * @version 1.0
 * @author Ogier
 *
 */
@SuppressWarnings("serial")
public class DeleteDialog extends JDialog implements ActionListener {

	private String carPlate;
	private JButton ok = new JButton("OK"), cancel = new JButton("Annuler");
	private JPanel up = new JPanel(), down = new JPanel();
	
	/**
	 * Instancie une bo�te de dialogue de confirmation de suppression
	 * @param plate
	 */
	public DeleteDialog(String plate) {
		carPlate = plate;
		setTitle("Suppression de voiture");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		add(up, BorderLayout.CENTER);
		add(down, BorderLayout.SOUTH);
		init();
		setSize(new Dimension(320, 100));
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	private void init() {
		ok.addActionListener(this);
		cancel.addActionListener(this);
		down.add(ok);
		down.add(cancel);
		up.add(new JLabel("Voulez vous vraiment supprimer la voiture " + carPlate + " ?"));
	}
	
	/**
	 * Supprime la voiture concern�e dans le cas d'une validation
	 * sinon ne fait rien
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ok) {
			UnilabsFleetManager.getInstance().removeCar(carPlate);
		}
		dispose();
	}
}
