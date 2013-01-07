package com.unilabs.gui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.Message;

/**
 * Boîte de dialogue pour l'ajout de voiture aux données
 * 
 * @version 1.0
 * @author Ogier
 *
 */
public class AddCarDialog extends JDialog implements ActionListener {

	/**
	 * Default serial version
	 */
	private static final long serialVersionUID = 1L;
	
	private Pattern clientP = Pattern.compile("^[0-9]+$");
	
	private Pattern plateP = Pattern.compile("^[A-Z]{2} [0-9]+$");

	/**
	 * Le text field pour entrer la plaque de la voiture
	 */
	private JTextField plate;
	
	/**
	 * Le text field pour le numéro client
	 */
	private JTextField client;
	
	/**
	 * Bouton de confirmation et d'annulation
	 */
	private JButton ok = new JButton("OK"), cancel = new JButton("Annuler");

	public AddCarDialog() {
		super(UnilabsFleetManager.getInstance().getGUI(), "Ajouter une voiture");
		init();
		setSize(new Dimension(400, 150));
		setLayout(new GridLayout(3, 2));
		add(new JLabel("Plaque : "));
		add(plate);
		add(new JLabel("Client : "));
		add(client);
		add(ok);
		add(cancel);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setVisible(true);
	}

	private void init() {
		plate = new JTextField();
		client = new JTextField();
		ok.addActionListener(this);
		cancel.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == ok) {
			if(plate.getText().isEmpty() || client.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Veuillez entrer le numéro client et le numéro de plaque de cette voiture");
				return;
			}
			if(!clientP.matcher(client.getText()).matches()) {
				Message.showWarningMessage("Le numéro client est incorrect");
				return;
			}
			if(!plateP.matcher(plate.getText()).matches()) {
				Message.showWarningMessage("Le format de plaque est incorrecte");
				return;
			}
			String plaque = plate.getText();
			int c = Integer.parseInt(client.getText());
			UnilabsFleetManager.getInstance().addCar(new UnilabsCar(plaque, c, UnilabsFleetManager.getInstance().getPlateChecker()));
		}
		dispose();
	}
}
