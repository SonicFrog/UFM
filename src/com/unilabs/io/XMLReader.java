package com.unilabs.io;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;
import com.unilabs.security.PlateChecker;

/**
 * Lecteur de fichier UFM depuis un fichier XML
 * @author Ogier
 * @version 1.0
 *
 */
public class XMLReader implements Reader {

	/**
	 * Nom du tag racine contenant les informations de tout les pleins
	 */
	public static final String ROOT_TAGNAME = "UnilabsFleetManager";
	
	/**
	 * Nom des tags contenant une voiture dans le fichier xml
	 */
	public static final String CAR_TAGNAME = "car";
	/**
	 * Nom des tags contenant un plein dans le fichier xml
	 */
	public static final String PLEIN_TAGNAME = "plein";

	public static final String CAR_PLATE_ATTRIB = "plaque";
	public static final String CAR_CLIENT_NUM_ATTRIB = "client";

	public static final String PLEIN_FUEL_ATTRIB = "fuel";
	public static final String PLEIN_MILEAGE_ATTRIB = "mileage";
	public static final String PLEIN_DATE_ATTRIB = "date";
	public static final String PLEIN_HOUR_ATTRIB = "hour";
	public static final String PLEIN_PRICE_ATTRIB = "price";

	private PlateChecker pc;
	private InputStream in;
	private Document xmlDoc;
	private DocumentBuilder db;
	private DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	private int lastReadCar = 0;
	private ArrayList<UnilabsCar> readCars = new ArrayList<UnilabsCar>();
	private int lastReadPlein = 0;
	private ArrayList<Plein> readPlein = new ArrayList<Plein>();

	/**
	 * Construit un XMLReader depuis un objet File
	 * @param file
	 * @param pc
	 * @throws IOException
	 */
	public XMLReader(File file, PlateChecker pc) throws IOException {
		this.pc = pc;
		in = new BufferedInputStream(new FileInputStream(file));
		parse();
	}

	public XMLReader(InputStream in, PlateChecker pc) throws IOException {
		this.pc = pc;
		this.in = in;
		parse();
	}

	/**
	 * Construit un XMLReader depuis un chemin d'accès à un fichier
	 * @param path
	 * @param pc
	 * @throws IOException
	 */
	public XMLReader(String path, PlateChecker pc) throws IOException {
		this.pc = pc;
		in = new BufferedInputStream(new FileInputStream(new File(path)));
		parse();
	}

	/**
	 * Initialise les sous systèmes nécessaires au parser XML java
	 * @param f
	 * @throws IOException
	 */
	private void parse() throws IOException {
		try {
			db = dbf.newDocumentBuilder();
			xmlDoc = db.parse(in);
			buildCarList();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new CorruptedFileException("Ce format n'est pas supporté sur votre plateforme");
		} catch (SAXException e) {
			e.printStackTrace();
			throw new CorruptedFileException("Ce fichier n'est pas un fichier XML valide");
		} catch(IllegalArgumentException e) {
			throw new CorruptedFileException("Ce fichier est illisible : " + e.getLocalizedMessage());
		}
	}

	/**
	 * Construit la liste des voitures contenues dans le fichier xml
	 * @throws IOException
	 */
	private void buildCarList() throws IOException {
		Element docRoot = xmlDoc.getDocumentElement();
		NodeList carList = docRoot.getElementsByTagName(CAR_TAGNAME);
		NodeList pleinList;
		String plaque;
		int num;

		if(carList.getLength() == 0 || carList == null) {
			throw new CorruptedFileException("Ce fichier n'est pas un fichier de pleins ufm");
		}
		for(int i = 0 ; i < carList.getLength() ; i++) {
			Element car = (Element) carList.item(i);
			Element plein;
			Plein p;
			
			UnilabsCar carRef;
			plaque = car.getAttribute(CAR_PLATE_ATTRIB);
			num = Integer.parseInt(car.getAttribute(CAR_CLIENT_NUM_ATTRIB));
			pleinList = car.getElementsByTagName(PLEIN_TAGNAME);
			carRef = new UnilabsCar(plaque, num, pc);
			for(int j = 0 ; j < pleinList.getLength() ; j++) {
				plein = (Element) pleinList.item(j);
				p = getPlein(plein);
				carRef.addPlein(p);
				readPlein.add(p);
			}
			readCars.add(carRef);
		}
	}

	/**
	 * Instancie un plein avec les données contenues dans un élément XML
	 * @param e
	 * 		Element xml contenant les informations du plein
	 * @return
	 */
	private Plein getPlein(Element e) {
		String date, hour;
		double fuelAmount, price;
		int kilometers;
		Plein out = null;
		date = e.getAttribute(PLEIN_DATE_ATTRIB);
		hour = e.getAttribute(PLEIN_HOUR_ATTRIB);
		try  {
			fuelAmount = Double.parseDouble(e.getAttribute(PLEIN_FUEL_ATTRIB));
			price = Double.parseDouble(e.getAttribute(PLEIN_PRICE_ATTRIB));
			kilometers = Integer.parseInt(e.getAttribute(PLEIN_MILEAGE_ATTRIB));
		} catch (NumberFormatException e1) {
			throw new IllegalArgumentException();
		}
		if(date.isEmpty() || hour.isEmpty())
			throw new IllegalArgumentException();
		out = new Plein(date, hour, fuelAmount, price, kilometers);
		return out;
	}



	/**
	 * Retourne la prochaine voiture lue depuis le fichier xml
	 * @return
	 * @throws IOException
	 */
	@Override
	public UnilabsCar readCar() throws IOException {
		if(lastReadCar == readCars.size())
			throw new EOFException();
		return readCars.get(lastReadCar++);
	}

	/**
	 * Retourne le prochain plein lu depuis le fichier xml <br />
	 * (Attention cette méthode est indépendante de readCar !)
	 * @return
	 * @throws IOException
	 */
	@Override
	public Plein readPlein() throws IOException {
		if(lastReadPlein == readPlein.size())
			throw new EOFException();
		return readPlein.get(lastReadPlein++);
	}
}
