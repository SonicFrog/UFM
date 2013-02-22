package com.unilabs.entities;

import java.awt.Cursor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
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
import com.unilabs.io.Reader;
import com.unilabs.io.Writer;
import com.unilabs.options.OptionStorage;
import com.unilabs.security.PlateChecker;
import com.unilabs.security.SwissPlate;
import com.unilabs.utils.FileHandler;
import java.io.InputStream;

/**
 * Singleton central contenant toutes les voitures actuellement en m�moire
 * 
 * @author Ogier
 * @version 1.2
 * @see UnilabsCar
 * @see ODSReader
 * @see ODSInputStream
 *
 */
public class UnilabsFleetManager {

	/**
	 * Le nom du fichier dans lequel est stock� la configuration de UFM
	 */
	public static final String PROPERTIES_FILE = "config.properties";
	
	/**
	 * Nom de clef contenant la monnaie dans le .properties
	 */
	public static final String CURRENCY_KEY = "currencyString";
	
	/**
	 * Nom de la clef contenant le format de plaque dans le .properties
	 */
	public static final String PLATE_KEY = "PlateType";
	
	/**
	 * Extension de fichier utilis�e par UFM
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
	 * Le v�rificateur de plaque
	 */
	private PlateChecker pc;

	/**
	 * R�f�rence vers l'interface graphique
	 */
	private UnilabsGUI gui;

	/**
	 * Bool�en de contr�le de la sauvegarde du fichier de sortie
	 */
	private boolean fileSaved = true;

	/**
	 * Fichier de sortie pour les donn�es
	 */
	private File output = null;

	/**
	 * L'objet de stockage des param�tres
	 */
	private OptionStorage os;
	
	/**
	 * Construit un nouveau UnilabsFleetManager vide
	 */
	public UnilabsFleetManager() {
		cars = new ConcurrentHashMap<>();
		pc = new SwissPlate();
		
		try {
			os = new OptionStorage(PROPERTIES_FILE);
			new FileChooser(UnilabsFleetManager.class.getProtectionDomain().getCodeSource().getLocation().getFile());
		} catch (IOException e) {
			Message.showErrorMessage("Erreur", "Le fichier de param�tres " + PROPERTIES_FILE + " est introuvable !");
			e.printStackTrace(System.out);
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
	 * Accesseur pour le v�rificateur de plaque
	 * @return
	 */
	public PlateChecker getPlateChecker() {
		return pc;
	}

	/**
	 * Ajoute une voiture dans la liste
	 * ou si la voiture est d�j� contenue dans la liste ajoute les pleins de la voiture pass�e en param�tres � la voiture de la liste
	 * @param c
	 */
	public void addCar(UnilabsCar c) {
		if(cars.contains(c)){
		    System.out.println("Car " + c.getPlaque() + " is already loaded adding data!");
		    UnilabsCar ref = cars.get(c.getPlaque());
		    for(Plein p : c.getPleins()) {
			ref.addPlein(p);
		    }
		} else {
		    cars.put(c.getPlaque(), c);
		    fileSaved = false;
		}
		gui.repaint();
	}

	/**
	 * Enl�ve la voiture dont on a la r�f�rence de la liste
	 * @param c
	 * 		Une r�f�rence vers la voiture a enlev�e
	 */
	public void removeCar(UnilabsCar c) {
		removeCar(c.getPlaque());
		fileSaved = false;
		gui.repaint();
	}

	/**
	 * Enl�ve une voiture de la liste � partir de sa plaque
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
		UnilabsCar[] out = new UnilabsCar[cars.size()];
		cars.values().toArray(out);
		return out;
	}

	/**
	 * Retourne la GUI de UFM
	 * @return 
	 *		Une r�f�rence vers la fen�tre principale de UFM
	 */
	public UnilabsGUI getGUI() {
		return gui;
	}

	/**
	 * D�finit la r�f�rence vers la GUI que poss�de le singleton
	 * @param unilabsGUI
	 */
	public void setGUI(UnilabsGUI unilabsGUI) {
		gui = unilabsGUI;
	}

	/**
	 * Ouvre le fichier pass� en param�tre pour ajouter toutes les donn�es qu'il contient � la flotte
	 * @param f
	 * 		Le fichier dans lequel on doit sauvegarder
	 */
	public void openODS(File f) {
		int duplicates = 0;
		gui.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try {
			
			InputStream in = FileHandler.handleFile(f);
			
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
			e.printStackTrace(System.out);
			gui.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
			return;
		} catch (Exception e) {
			Message.showErrorMessage(gui, "Erreur", "Ce fichier n'est pas un fichier contenant des informations de pleins : " + 
					e.getLocalizedMessage());
			gui.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
			e.printStackTrace(System.out);
			return;
		}
		gui.repaint();
		fileSaved = false;
		for(UnilabsCar c : cars.values()) {
			duplicates += c.getDuplicateCount();			
		}
		gui.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		Message.showSuccessMessage(duplicates + " doublons ont �t� ignor� !");
	}

	/**
	 * Ouvre un fichier binaire pour ajouter ses informations dans les donn�es
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
			e.printStackTrace(System.out);
			Message.showErrorMessage(gui, "Erreur lors de la lecture", e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace(System.out);
			Message.showErrorMessage(gui, "Erreur inconnue", "Une erreur de type" + e.getClass().toString() + " s'est produite");
		}
		gui.repaint();
		gui.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Sauve les donn�es en RAM dans le fichier donn� en param�tre
	 * @param f
	 */
	public void save(File f) {
		gui.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try {
			output = f;
			if(output.exists()) {
				int value = JOptionPane.showConfirmDialog(gui, "Ce fichier (" + f.getAbsolutePath() + ") existe d�j� voulez vous �craser les modifications ?", "Overwrite ?", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE);
				if(value == JOptionPane.NO_OPTION) {
					output = null;
					return;
				}
			}
			OutputStream bfis = new BufferedOutputStream(new FileOutputStream(f));
			Writer w = new CompressedXMLWriter(bfis);
			output = f;
			for(UnilabsCar c : cars.values()) {
				w.writeCar(c);
			}
			w.flush();
		} catch (IOException e) {
			Message.showErrorMessage(gui, "Erreur lors de la sauvegarde", "Une erreur s'est produite lors de la sauvegarde : " + e.getLocalizedMessage());
			e.printStackTrace(System.out);
			return;
		}
		fileSaved = true;
		gui.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Ferme le fichier en cours
	 * @return 
	 *		true si l'utilisateur accepte la fermeture false sinon
	 */
	public boolean close() {
		switch(new ExitDialog(fileSaved).getResult()) {
		case JOptionPane.CANCEL_OPTION :
			return false;
		case JOptionPane.YES_OPTION :
			FileChooser jfc = new FileChooser();
			jfc.showSaveDialog(gui);
			if(jfc.getSelectedFile() == null) {
				return false;
			}
			save(output = jfc.getSelectedFile());
			break;
		case JOptionPane.NO_OPTION :
			output = null;
			cars = new ConcurrentHashMap<>();
			fileSaved = true;
			gui.repaint();
			break;
			
		default :
			fileSaved = false;
			return false;
		}
		gui.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		return true;
	}

	/**
	 * Retourne le fichier de sortie pour cette instance de UFM
	 * @return
	 */
	public File getFile() {
		return output;
	}

	/**
	 * G�re la sortie du programme avec la sauvegarde du fichier
	 */
	public void exit() {
		if(!close()) {
			return;
		}
		try {
			os.store();
		} catch (IOException e) {
			System.out.println("Impossible de sauvegarder vos pr�f�rences! " + e.getLocalizedMessage());
		}
		System.exit(0);
	}

	/**
	 * Retourne l'index de la voiture selectionn�e dans le tableau de toutes les voitures
	 * @return 
	 *		Un entier contenant l'index de la voiture selectionn�e dans le HashMap
	 * @see UnilabsFleetManager#cars
	 */
	public int getSelectedCar() {
		String plaque = gui.getSelectedCar();
		UnilabsCar[] tab = getCars();
		int i;
		for(i = 0 ; i < tab.length ; i++) {
			if(tab[i].getPlaque().equals(plaque)) {
				return i;
			}
		}
		throw new RuntimeException("Impossible de d�terminer quelle voiture est selectionn�e !");
	}

	/**
	 * Retourne une r�f�rence vers la HashMap contenant toutes les voitures
	 * @return 
	 */
	public ConcurrentHashMap<String, UnilabsCar> getRawCars() {
		return cars;
	}

	/**
	 * Retourne la plaque de la voiture s�lectionn�e dans l'interface graphique
	 * @return 
	 */
	public String getSelectedCarPlate() {
		return gui.getSelectedCar();
	}
	
	/**
	 * Retourne l'objet qui g�re les options de cette instance d'UFM
	 * @return 
	 */
	public OptionStorage getOptions() {
		return os;
	}

	/**
	 * Retourne la String contenant la monnaie utilis� dans cette instance de UFM
	 * @return 
	 */
	public String getCurrency() {
		return os.getCurrency();
	}

	/**
	 * Accesseur pour savoir si cette instance de UFM contient des voitures
	 * @return 
	 */
	public boolean hasCars() {
		return cars.size() > 0;
	}

	/**
	 * Retourne l'extension de fichiers pour les fichiers binaires UFM
	 * @return 
	 *		Une string contenant l'extension des fichiers binaires UFM
	 */
	public String getFileExtension() {
		return FILE_EXTENSION;
	}
}
