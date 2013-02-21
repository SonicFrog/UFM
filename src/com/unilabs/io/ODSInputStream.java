package com.unilabs.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * Une classe qui convertit une source d'entrée au format OpenDocument Spreadsheet en
 * InputStream java.
 * 
 * @version 1.1
 * @see jOpenDocument
 * @see InputStream
 * @author Ogier
 *
 */
public class ODSInputStream extends InputStream {

	public static final String[] DEFAULT_COLUMNS = { "Client" , "Registration no.", "Invoice date" , 
		"Fuel Amount" , "Liters" , "Registered mileage"};
	/**
	 * Référence vers le fichier en cours de lecture
	 */
	private File ODFFile;

	/**
	 * Tableau contenant les intitulés des colonnes contenant les données
	 */
	private String[] columns;

	/**
	 * Chemin d'accès vers le fichier
	 */
	private String file;

	/**
	 * 
	 */
	private Sheet sheet;

	/**
	 * Buffer contenant les données lues depuis le fichier ODS
	 */
	private ArrayList<String> buffer = new ArrayList<String>();

	/**
	 * Buffer contenant les tailles données de l'autre buffer
	 * @see ODSInputStream#buffer
	 */
	private ArrayList<Integer> sbuffer = new ArrayList<Integer>();

	/**
	 * Les données brutes les une à la suite des autres avec les tailles entre chaque
	 */
	private byte[] data;

	/**
	 * Index du dernier byte lu depuis data
	 * @see ODSInputStream#data
	 */
	private int lastRead = 0;

	/**
	 * Booléen indiquant si le stream est marqué ou non
	 */
	private int markIndex;
	
	private boolean initialized = false;

	/**
	 * Instantie un ODSInputStream à partir d'un fichier
	 * 
	 * @param file
	 * 		Le chemin d'accès vers le fichier
	 * @param columns
	 * 		Les intitulés des colonnes dont on veut récupérer les données
	 * @throws IOException
	 */
	public ODSInputStream(String file, String[] columns) {
		this.columns = ((columns == null) ? DEFAULT_COLUMNS : columns);
		this.file = file;
	}

	@Override
	public void mark(int readlimit) {
		markIndex = lastRead;
	}

	@Override
	public void reset() {
		lastRead = markIndex;
	}

	public boolean markSupported() {
		return true;
	}

	/**
	 * Permet de lire les données contenues dans cet InputStream dans l'ordre suivant <br />
	 * Taille -> Numéro client <br />
	 * Taille -> Plaque la voiture <br />
	 * Taille -> Date du plein <br />
	 * Taille -> Heure du plein <br />
	 * Taille -> Prix du plein <br />
	 * Taille -> Nombres de litres <br />
	 * Taille -> Kilomètrage
	 * 
	 * @return 
	 * 		Le prochain byte de la donnée en cours
	 */
	@Override
	public int read() throws IOException {
		if(!initialized) {
		    init();
		}
		if(lastRead == data.length)
			throw new EOFException();
		return (data[lastRead++] & 0xff);
	}

	/**
	 * Remplit le buffer du flux avec les données provenant du tableau au format OpenDocument
	 * 
	 * @throws IOException
	 */
	private void init() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream os = new DataOutputStream(bos);
		char cell = 'A';
		int i = 0, j = 2;
		String cellcontent;
		ODFFile = new File(file);
		if(!ODFFile.exists())
			throw new FileNotFoundException(ODFFile.getAbsolutePath());
		if(!ODFFile.getAbsoluteFile().getName().endsWith(".ods"))
			throw new CorruptedFileException("Ce fichier n'est pas un fichier ODS valide");

		if(columns == null || columns.length == 0)
			throw new IllegalArgumentException();
		try {
			sheet = SpreadSheet.createFromFile(ODFFile).getSheet(0);
		} catch (NoSuchElementException e) {
			throw new CorruptedFileException("Ce fichier n'est pas un fichier ODS valide");
		}
		do {
			System.out.println("Reading column " + cell);
			cellcontent = sheet.getCellAt(cell + "1").getTextValue();
			if(cellcontent.equals(columns[i])) {
				columns[i] = "" + cell;
				i++;
			}
			cell++;
		} while(!cellcontent.isEmpty() && i < columns.length);
		i = 0;
		do {
			do {
				cellcontent = sheet.getCellAt(columns[i] + j).getTextValue();
				if(cellcontent.isEmpty())
					break;
				sbuffer.add(cellcontent.length());
				buffer.add(cellcontent);
				i++;
			} while( i < columns.length);
			i = 0;
			j++;
		} while(!cellcontent.isEmpty());
		for(i = 0 ; i < buffer.size() ; i++) {
			os.writeInt(sbuffer.get(i));
			for(j = 0 ; j < buffer.get(i).length() ; j++) {
				os.writeByte(buffer.get(i).charAt(j));
			}
		}
		data = bos.toByteArray();
		initialized = true;
	}

	/**
	 * Méthode de test uniquement retourne le tableau contenant les intitulés de colonne
	 * 
	 * @return
	 */
	public String[] getColumns() {
		return columns;
	}
}
