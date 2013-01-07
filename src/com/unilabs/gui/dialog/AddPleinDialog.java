package com.unilabs.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.unilabs.entities.Plein;
import com.unilabs.gui.Message;

/**
 * Dialogue d'ajout et d'édition de plein
 * 
 * @version 2.0
 * @since 2.0
 * @author Ogier
 * @see Plein
 *
 */
public class AddPleinDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 8292274750327776933L;

	public static final int FUEL_INDEX = 0;
	public static final int PRICE_INDEX = 1;
	public static final int DATE_INDEX = 2;
	public static final int MILEAGE_INDEX = 3;

	public static final String FUEL = "Quantité d'essence";
	public static final String PRICE = "Prix";
	public static final String DATE = "Date";
	public static final String MILEAGE = "Kilomètrage";

	private Plein data;

	/**
	 * Panel contenant les boutons
	 */
	private JPanel button = new JPanel();

	/**
	 * Panel contenant les informations du plein et les JtextField
	 */
	private JPanel infos = new JPanel();
	/**
	 * Panel pour les JComboBox de la Date
	 */
	private JPanel datePanel = new JPanel();

	/**
	 * JTextField contenant les entrées utilisateurs
	 */
	private JTextField[] jtf = { new JTextField(15), new JTextField(15), new JTextField(15), new JTextField(15) };

	/**
	 * JLabel contenant les intitulés des JTextField
	 * @see AddPleinDialog#jtf
	 */
	private JLabel[] label = { new JLabel(FUEL) , new JLabel(PRICE), new JLabel(DATE), new JLabel(MILEAGE) };

	/**
	 * Les boutons de la GUI
	 */
	private JButton ok = new JButton("OK"), cancel = new JButton("Annuler");

	/**
	 * ComboxBox pour le jour
	 */
	private JComboBox day = new JComboBox();

	/**
	 * ComboBox pour le mois
	 */
	private JComboBox month = new JComboBox();

	/**
	 * ComboBox pour l'année
	 */
	private JComboBox year = new JComboBox();

	private boolean edit = false;

	/**
	 * Les arguments de ce constructeur sont facultatifs
	 * @param gui
	 * 		La fenêtre parente
	 * @param title
	 * 		Inutilisé
	 */
	public AddPleinDialog(Frame gui, String title) {
		super(gui, "Ajouter un plein");
		init();
		setVisible(true);
	}

	/**
	 * Constructeur d'un dialogue pour édition du plein passé en paramètre
	 * @param start
	 */
	public AddPleinDialog(Plein start) {
		super();
		int temp;
		String index = new String();
		setTitle("Modifier un plein");
		init();
		jtf[FUEL_INDEX].setText(String.valueOf(start.getFuelAmount()));
		jtf[PRICE_INDEX].setText(String.valueOf(start.getPrice()));
		jtf[MILEAGE_INDEX].setText(String.valueOf(start.getTotalKilometers()));
		temp = start.getCalendar().get(Calendar.DAY_OF_MONTH);
		if(temp < 10)
			index = "0";
		index += temp;
		day.setSelectedItem(String.valueOf(index));
		System.out.println("Selected index " + index + " for day");
		index = "";
		temp = start.getCalendar().get(Calendar.MONTH);
		if(temp < 0)
			index = "0";
		index += temp;
		month.setSelectedItem(String.valueOf(index));
		System.out.println("Selected index " + index + " for month");
		year.setSelectedItem(String.valueOf(start.getCalendar().get(Calendar.YEAR)));
		data = start;
		edit = true;
		setVisible(true);
	}

	private void init() {
		setLayout(new BorderLayout());
		infos.setLayout(new GridLayout(jtf.length , 2));
		setSize(new Dimension(450, 320));
		for(int i = 0 ; i < jtf.length ; i++) {
			JPanel p = new JPanel();
			p.add(label[i]);
			infos.add(p);
			if(i != DATE_INDEX) {
				p = new JPanel();
				p.add(jtf[i]);
				infos.add(p);
			} else {
				buildComboBoxPanel();
				infos.add(datePanel);
			}
		}
		ok.addActionListener(this);
		cancel.addActionListener(this);
		button.add(ok);
		button.add(cancel);
		add(infos, BorderLayout.CENTER);
		add(button, BorderLayout.SOUTH);
		setModal(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	/**
	 * Construit les ComboBox qui permettent d'entrer la Date
	 */
	private void buildComboBoxPanel() {
		Calendar today = new GregorianCalendar();
		for(int i = 1 ; i < 32 ; i++) {
			if(i < 10)
				day.addItem("0" + i);
			else
				day.addItem(String.valueOf(i));
		}
		for(int i = 1 ; i < 13 ; i++) {
			if(i < 10) 
				month.addItem("0" + i);
			else
				month.addItem(String.valueOf(i));
		}
		for(int i = today.get(Calendar.YEAR) ; i > today.get(Calendar.YEAR) - 5 ; i--)
			year.addItem(String.valueOf(i));
		datePanel.add(day);
		datePanel.add(month);
		datePanel.add(year);
	}

	/**
	 * Retourne le plein créé par ce dialogue avec les données utilisateur ou null si l'utilisateur a fermé le dialogue
	 * @return
	 */
	public Plein getPlein() {
		return data;
	}

	/**
	 * Edite le plein dont on a la référence dans l'objet
	 */
	public void editPlein() {
		if(!edit)
			return;
		Calendar date = new GregorianCalendar();
		date.set(Calendar.YEAR, Integer.parseInt((String) year.getSelectedItem()));
		date.set(Calendar.MONTH, Integer.parseInt((String) month.getSelectedItem()) - 1);
		date.set(Calendar.DAY_OF_MONTH, Integer.parseInt((String) day.getSelectedItem()));
		data.setFuelAmount(Double.parseDouble(jtf[FUEL_INDEX].getText()));
		data.setPrice(Double.parseDouble(jtf[PRICE_INDEX].getText()));
		data.setDate(new Date(date.getTimeInMillis()));
		data.setKilometers(Integer.parseInt(jtf[MILEAGE_INDEX].getText()));
	}

	public void actionPerformed(ActionEvent e) {
		double price;
		int mileage;
		double fuel;
		String date = new String();
		if(e.getSource() == ok) {
			try {
				price = Double.parseDouble(jtf[PRICE_INDEX].getText());
				mileage = Integer.parseInt(jtf[MILEAGE_INDEX].getText());
				fuel = Double.parseDouble(jtf[FUEL_INDEX].getText());
				date += String.valueOf(day.getSelectedItem()) + "-" + String.valueOf(month.getSelectedItem()) + "-" + String.valueOf(year.getSelectedItem());
				data = new Plein(date, null, fuel, price, mileage);
				editPlein();
				dispose();
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
				Message.showErrorMessage("Erreur", "Les informations de ce plein sont invalides : \n " + e1.getLocalizedMessage());
			} catch (IllegalArgumentException e1) {
				Message.showErrorMessage("Erreur", e1.getMessage());
			}
		} else {
			dispose();
		}
	}
}
