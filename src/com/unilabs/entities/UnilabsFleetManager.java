package com.unilabs.entities;

import java.awt.Cursor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;

import com.unilabs.gui.FileChooser;
import com.unilabs.gui.Message;
import com.unilabs.gui.UnilabsGUI;
import com.unilabs.gui.dialog.ExitDialog;
import com.unilabs.io.CompressedXMLReader;
import com.unilabs.io.CompressedXMLWriter;
import com.unilabs.io.ODSInputStream;
import com.unilabs.io.ODSReader;
import com.unilabs.io.PropertyReader;
import com.unilabs.io.Reader;
import com.unilabs.io.Writer;
import com.unilabs.security.PlateChecker;
import com.unilabs.security.SwissPlate;

/**
 * Singleton central contenant toutes les voitures actuellement en mémoire
 * 
 * @author Ogier
 * @version 1.2
 * @see UnilabsCar
 * @see ODSReader
 * @see ODSInputStream
 *
 */
public class UnilabsFleetManager {

	public static final String PROPERTIES_FILE = "config.properties";
	public static final String CURRENCY_KEY = "currencyString";
	public static final String PLATE_KEY = "PlateType";
	
	/**
	 * Extension de fichier utilisée par UFM
	 */
	public static final String FILE_EXTENSION = ".ufm";

	/**
	 * L'instance singleton du fleetmanager
	 */
	private static UnilabsFleetManager instance;

	/**
	 * La liste de toutes les voitures actuellement dans la flotte
	 */
	private ConcurrentHashMap<String, UnilabsCar> cars;

	/**
	 * Le vérificateur de plaque
	 */
	private PlateChecker pc;

	/**
	 * Référence vers l'interface graphique
	 */
	private UnilabsGUI gui;

	/**
	 * Booléen de contrôle de la sauvegarde du fichier de sortie
	 */
	private boolean fileSaved = true;

	/**
	 * Fichier de sortie pour les données
	 */
	private File output = null;

	private String currency = "CHF";

	private PropertyReader pr;

	public UnilabsFleetManager() {
		cars = new ConcurrentHashMap<String, UnilabsCar>();
		pc = new SwissPlate();
		try {
			pr = new PropertyReader(PROPERTIES_FILE);
			currency = (pr.getProperty(CURRENCY_KEY) == null) ? currency : pr.getProperty(CURRENCY_KEY);

		} catch (IOException e) {
			Message.showErrorMessage("Erreur", "Le fichier de paramètres " + PROPERTIES_FILE + " est introuvable !");
		}
	}

	/**
	 * Accesseur pour l'instance du singleton
	 * @return
	 * 		L'instance unique de l'UnilabsFleetManager de ce programme
	 */
	public static synchronized UnilabsFleetManager getInstance() {
		if(instance == null) {
			instance = new UnilabsFleetManager();
		}
		return instance;
	}

	/**
	 * Accesseur pour le vérificateur de plaque
	 * @return
	 */
	public PlateChecker getPlateChecker() {
		return pc;
	}

	/**
	 * Ajoute une voiture dans la liste
	 * @param c
	 */
	public void addCar(UnilabsCar c) {
		cars.put(c.getPlaque(), c);
		fileSaved = false;
		gui.repaint();
	}

	/**
	 * Enlève la voiture dont on a la référence de la liste
	 * @param c
	 * 		Une référence vers la voiture a enlevée
	 */
	public void removeCar(UnilabsCar c) {
		removeCar(c.getPlaque());
		fileSaved = false;
		gui.repaint();
	}

	/**
	 * Enlève une voiture de la liste à partir de sa plaque
	 * @param plaque
	 * 		La plaque de la voiture qu'on veut enlever
	 * @see UnilabsFleetManager#cars
	 */
	public void removeCar(String plaque) {
		if(cars.remove(plaque) != null) {
			fileSaved = false;
			gui.repaint();
		}
	}

	/**
	 * Retourne la liste des voitures
	 * @return
	 * 		Un tableau contenant toutes les voitures
	 * @see UnilabsFleetManager#cars
	 */
	public UnilabsCar[] getCars() {
		return cars.values().toArray(new UnilabsCar[0]);
	}

	public UnilabsGUI getGUI() {
		return gui;
	}

	/**
	 * Définit la référence vers la GUI que posséde le singleton
	 * @param unilabsGUI
	 */
	public void setGUI(UnilabsGUI unilabsGUI) {
		gui = unilabsGUI;
	}

	/**
	 * Ouvre le fichier passé en paramètre pour ajouter toutes les données qu'il contient à la flotte
	 * @param f
	 * 		Le fichier dans lequel on doit sauvegarder
	 */
	public void openODS(File f) {
		int duplicates = 0;
		gui.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try {
			ODSInputStream in = new ODSInputStream(f.getAbsolutePath(), null);
			ODSReader reader = new ODSReader(in);
			String plate = reader.getNextPlaque();

			while(plate != null){				
				UnilabsCar c = reader.getCar(plate, pc);
				if(cars.containsKey(c.getPlaque())) {
					reader.refreshCar(cars.get(c.getPlaque()));
				} else {
					cars.put(c.getPlaque(), c);
				}
				plate = reader.getNextPlaque();
			}
		} catch(EOFException e) {
			System.out.println("Read all cars from " + f.getAbsolutePath());
		} catch (IOException e){
			Message.showErrorMessage(gui, "Erreur", e.getLocalizedMessage());
			e.printStackTrace();
			gui.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
			return;
		} catch (Exception e) {
			Message.showErrorMessage(gui, "Erreur", "Ce fichier n'est pas un fichier contenant des informations de pleins : " + 
					e.getLocalizedMessage());
			gui.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
			return;
		}
		gui.repaint();
		fileSaved = false;
		for(UnilabsCar c : cars.values()) {
			duplicates += c.getDuplicateCount();			
		}
		gui.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		Message.showSuccessMessage(duplicates + " doublons ont été ignoré !");
	}

	/**
	 * Ouvre un fichier binaire pour ajouter ses informations dans les données
	 * @param f
	 * 		Le fichier depuis lequel on lit
	 */
	public void openBinary(File f) {
		BufferedInputStream bis;
		Reader r;
		gui.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try {
			bis = new BufferedInputStream(new FileInputStream(f));
			r = new CompressedXMLReader(bis, pc);
			while(true) {
				addCar(r.readCar());
			}
		} catch (EOFException e) {
			System.out.println("Read all cars from " + f.getAbsolutePath() + ". Car count is now " + cars.size());
		} catch (IOException e) {
			e.printStackTrace();
			Message.showErrorMessage(gui, "Erreur lors de la lecture", e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorMessage(gui, "Erreur inconnue", "Une erreur de type" + e.getClass().toString() + " s'est produite");
		}
		gui.repaint();
		gui.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Sauve les données en RAM dans le fichier donné en paramètre
	 * @param f
	 */
	public void save(File f) {
		gui.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try {
			output = f;
			if(output.exists()) {
				int value = JOptionPane.showConfirmDialog(gui, "Ce fichier (" + f.getAbsolutePath() + ") existe déjà voulez vous écraser les modifications ?", "Overwrite ?", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE);
				if(value == JOptionPane.NO_OPTION) {
					output = null;
					return;
				}
			}
			OutputStream bfis = new BufferedOutputStream(new FileOutputStream(f));
			Writer w = new CompressedXMLWriter(bfis);
			output = f;
			System.out.println("Writing " + cars.values().size()+ " cars");
			int i = 0;
			for(UnilabsCar c : cars.values()) {
				w.writeCar(c);
				i++;
			}
			w.flush();
			System.out.println("Wrote " + i + " cars");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Message.showErrorMessage(gui, "Erreur lors de la sauvegarde", "Une erreur s'est produite lors de la sauvegarde : " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		fileSaved = true;
		gui.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Ferme le fichier en cours
	 */
	public boolean close() {
		switch(new ExitDialog(fileSaved).getResult()) {
		case JOptionPane.CANCEL_OPTION :
			return false;
		case JOptionPane.YES_OPTION :
			FileChooser jfc = new FileChooser();
			jfc.showSaveDialog(gui);
			if(jfc.getSelectedFile() == null)
				return false;
			save(output = jfc.getSelectedFile());			
		case JOptionPane.NO_OPTION :
			output = null;
			cars = new ConcurrentHashMap<String, UnilabsCar>();
			fileSaved = true;
			gui.repaint();
		}
		gui.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		return true;
	}

	public File getFile() {
		return output;
	}

	/**
	 * Gère la sortie du programme avec la sauvegarde du fichier
	 */
	public void exit() {
		if(!close())
			return;
		saveProperty();
		System.exit(0);
	}

	public int getSelectedCar() {
		String plaque = gui.getSelectedCar();
		UnilabsCar[] tab = cars.values().toArray(new UnilabsCar[0]);
		int i;
		for(i = 0 ; i < tab.length ; i++) {
			if(tab[i].getPlaque().equals(plaque))
				return i;
		}
		throw new RuntimeException("Impossible de déterminer quelle voiture est selectionnée !");
	}

	public ConcurrentHashMap<String, UnilabsCar> getRawCars() {
		return cars;
	}

	public String getSelectedCarPlate() {
		return gui.getSelectedCar();
	}

	public String getCurrency() {
		return currency;
	}

	public boolean hasCars() {
		return cars.size() > 0;
	}

	private void saveProperty() {
		try {
			pr.writeProperty(CURRENCY_KEY, currency);
		} catch (IOException e) {
			Message.showErrorMessage("Erreur", "Impossible de sauver les parametres : " + e.getLocalizedMessage());
		}
	}

	public String getFileExtension() {
		return FILE_EXTENSION;
	}
}
