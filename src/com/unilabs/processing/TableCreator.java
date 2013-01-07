package com.unilabs.processing;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jopendocument.dom.XMLFormatVersion;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;
import com.unilabs.gui.NonEditableTableModel;
import com.unilabs.gui.PleinLitersTableCellRenderer;

/**
 * Classe de création de tableau à partir des données de voiture
 * Permet aussi l'export des tableaux au format ODS
 * 
 * @author Ogier
 * @version 1.2
 * @see XMLFormatVersion
 * @see NonEditableTableModel
 *
 */
public class TableCreator {

	/**
	 * Intitulé des colonnes pour un TableModel contenant les informations des pleins
	 */
	public static final String[] COLUMNS = { "Client", "Plaque", "Date", "Prix" , "Litre" , "Kilomètrage" };

	/**
	 * Intitulé des colonnes pour un TableModel contenant les informations moyennes d'une flotte de voiture
	 */
	public static String[] AVG_COLUMNS = { "Numéro client" , "Plaque" , "L/100km", "Km/jour", "Prix du litre", "Cout par jour"};

	/**
	 * Le TableModel pour l'écriture du fichier de spreadsheet
	 */
	private NonEditableTableModel model;

	/**
	 * Fichier dans lequel on exporte l'ODS
	 */
	private File out;

	/**
	 * Format de date pour les sorties de date
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("d-M-y");

	public TableCreator(NonEditableTableModel model, File out) {
		this.model = model;
		this.out = out;
	}

	/**
	 * Instancie un tableWriter qui crée une spreadsheet avec toutes les données de plein fournies
	 * @param model
	 * @param out
	 */
	public TableCreator(UnilabsCar[] model, File out) {
		this.out = out;
		this.model = createAverageTableModel(model);
	}

	/**
	 * Ecrit les donnees contenues dans ce TableCreator dans un fichier au format OpenDocument Spreadsheet
	 * @see TableCreator#out
	 * @throws IOException
	 */
	public void write() throws IOException {
		SpreadSheet.export(model, out, XMLFormatVersion.getDefault());
	}

	/**
	 * Retourne le TableModel généré par le constructeur à l'initialisation <br />
	 * Pour générer un TableModel il faut mieux utiliser les méthodes statiques 
	 * @see TableCreator#createAverageTableModel(UnilabsCar[])
	 * @see TableCreator#createPleinTableModel(UnilabsCar[])
	 * @return
	 * 		Le TableModel prêt à être écrit dans un fichier ODS
	 */
	public NonEditableTableModel getTableModel() {
		return model;
	}

	/**
	 * Créée un TableModel contenant les informations des pleins des voitures fournies
	 * 
	 * @see TableCreator#COLUMNS
	 * @param data
	 * @return
	 * 		Un tableModel contenant les pleins des voitures fournies (voir COLUMS pour les intitulés de colonnes)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static NonEditableTableModel createPleinTableModel(UnilabsCar[] data) {
		NonEditableTableModel ctm = new NonEditableTableModel();
		for(String h : COLUMNS) {
			ctm.addColumn(h);
		}
		for(UnilabsCar car : data) {
			for(int i = 0 ; i < car.getPleins().size() ; i++) {
				Plein p = car.getPleins().get(i);
				Vector arg = new Vector();
				arg.add(car.getNumeroClient());
				arg.add(car.getPlaque());
				arg.add(sdf.format(p.getDate()));
				arg.add(p.getPrice());
				arg.add(p.getFuelAmount());
				arg.add(p.getTotalKilometers());
				ctm.addRow(arg);
			}
		}
		return ctm;
	}
	
	public static NonEditableTableModel createPleinTableModel(UnilabsCar data) {
		UnilabsCar[] temp = new UnilabsCar[1];
		temp[0] = data;
		return createPleinTableModel(temp);
		
	}
	
	/**
	 * Créé un TableModel avec les informations de pleins à la différence que celui-ci est éditable par l'utilisateur
	 * 
	 * @param data
	 * @return
	 * 		Un TableModel éditable contenant les informations des pleins 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static DefaultTableModel createEditableTableModel(UnilabsCar[] data) {
		DefaultTableModel dtm = new DefaultTableModel();
		for(String h : COLUMNS) {
			dtm.addColumn(h);
		}
		for(UnilabsCar car : data) {
			for(int i = 0 ; i < car.getPleins().size() ; i++) {
				Plein p = car.getPleins().get(i);
				Vector arg = new Vector();
				arg.add(car.getNumeroClient());
				arg.add(car.getPlaque());
				arg.add(sdf.format(p.getDate()));
				arg.add(p.getPrice());
				arg.add(p.getFuelAmount());
				arg.add(p.getTotalKilometers());
				dtm.addRow(arg);
			}
		}
		return dtm;
	}

	/**
	 * Créée un TableModel pour les informatiosn de consommation moyennes de la flotte
	 * @see TableCreator#AVG_COLUMNS
	 * @param data
	 * 		La flotte de voiture pour laquelle on veut générer un TableModel
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static NonEditableTableModel createAverageTableModel(UnilabsCar[] data) {
		NonEditableTableModel ctm = new NonEditableTableModel();
		Vector vec;
		AverageCalculator avg;
		for(String s : AVG_COLUMNS) {
			ctm.addColumn(s);
		}
		for(UnilabsCar car : data) {
			vec = new Vector();
			avg = new AverageCalculator(car);
			vec.add(car.getNumeroClient());
			vec.add(car.getPlaque());
			vec.add(avg.averageConso());
			vec.add(avg.averageKilometers());
			vec.add(avg.averageLiterPrice());
			vec.add(avg.averagePrice());
			ctm.addRow(vec);
		}
		return ctm;
	}
	
	/**
	 * Créé un JTable contenant les informations de pleins pour une voiture
	 * @param data
	 * 		La voiture pour laquelle on crée le tableau
	 * @return
	 */
	public static JTable createPleinTable(UnilabsCar data) {
		UnilabsCar[] out = new UnilabsCar[1];
		out[0] = data;
		return createPleinTable(out);
		
	}
	
	/**
	 * Crée un tableau contenant les informations des pleins passés en paramètres
	 * @param data
	 * @return
	 */
	public static JTable createPleinTable(UnilabsCar[] data) {
		JTable out;
		Enumeration<TableColumn> tc;
		out = new JTable(createPleinTableModel(data));
		tc = out.getColumnModel().getColumns();
		out.getTableHeader().setReorderingAllowed(false);
		for( ; tc.hasMoreElements() ; ) {
			tc.nextElement().setCellRenderer(new PleinLitersTableCellRenderer());
		}
		return out;
	}
}
