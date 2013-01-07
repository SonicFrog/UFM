package com.unilabs.io;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;
import com.unilabs.security.PlateChecker;

/**
 * Classe de lecture depuis un InputStream fonctionnant selon le mode standard du programme :
 * Taille > Numéro client <br />
 * Taille > Plaque la voiture <br />
 * Taille > Date du plein <br />
 * Taille > Prix du plein <br />
 * Taille > Nombres de litres <br />
 * Taille > Kilomètrage
 * @version 2.0
 * @author Ogier
 * @see ODSInputStream
 *
 */
public class ODSReader {

	/**
	 * Contient la plaque du dernier plein lue depuis le inputstream
	 * @see ODSReader#getCar(InputStream, String, PlateChecker)
	 */
	private String last_plaque = null;

	/**
	 * Contient le dernier numéro client lue depuis le fichier en cours
	 * @see ODSReader#readPlein(InputStream)
	 * @see ODSReader#getCar(InputStream, String, PlateChecker)
	 */
	private int last_client = 0;

	/**
	 * La liste des plaques lues depuis le dernier inputstream passé en paramètres de getNextPlaque
	 * @see ODSReader#getNextPlaque(InputStream)
	 */
	private ArrayList<String> plaques = null;

	/**
	 * Index de la dernière plaque retourné par getNextPlaque
	 * @see ODSReader#getNextPlaque(InputStream)
	 */
	private int lastReadPlaque = 0;

	private InputStream in;


	/**
	 * Constructeur du ODSReader
	 * @since 2.0
	 * @param in
	 * 		Le flux d'entrée depuis lequel on va lire toutes les données
	 */
	public ODSReader(InputStream in) throws IOException {
		this.in = in;
		getNextPlaque();
		lastReadPlaque = 0;
	}

	/**
	 * Instancie un plein à partir des données d'un InputStream <br />
	 * Le format est :<br />
	 * Taille > Numéro client
	 * Taille > Plaque 
	 * Taille > 
	 * @param in
	 * @return
	 * @since 1.0
	 */
	public Plein readPlein() throws IOException {
		Plein out = null;
		String date = null;
		String hour = null;
		double fuel, price;
		int kilometers;
		DataInputStream dis = new DataInputStream(in);
		try {
			byte[] buffer = new byte[dis.readInt()];
			dis.read(buffer);
			last_client = Integer.parseInt(new String(buffer, Charset.forName("ASCII")));
			buffer = new byte[dis.readInt()];
			dis.read(buffer);
			last_plaque = new String(buffer, Charset.forName("ASCII"));
			buffer = new byte[dis.readInt()];
			dis.read(buffer);
			date = new String(buffer, Charset.forName("ASCII"));
			//		buffer = new byte[dis.readInt()];
			//		dis.read(buffer);
			//		hour = new String(buffer, Charset.forName("ASCII"));
			buffer = new byte[dis.readInt()];
			dis.read(buffer);
			price = Double.parseDouble(new String(buffer, Charset.forName("ASCII")));
			buffer = new byte[dis.readInt()];
			dis.read(buffer);
			fuel = Double.parseDouble(new String(buffer, Charset.forName("ASCII")));
			buffer = new byte[dis.readInt()];
			dis.read(buffer);
			kilometers = Integer.parseInt(new String(buffer, Charset.forName("ASCII")));

			out = new Plein(date, hour, fuel, price, kilometers);
		} catch (NumberFormatException e) {
			System.out.println("Couldn't convert it to a number");
			throw new CorruptedFileException("Les informations du pleins sont invalides (pas un nombre)");
		}
		return out;
	}

	/**
	 * Lit une voiture avec tout ses pleins depuis un InputStream suivant le format standard <br />
	 * puis remet le stream à la position où celui ci se trouvait au début
	 * 
	 * @param in
	 * 		L'inputStream depuis lequel on lit
	 * @param plaque
	 * 		La plaque de la voiture à lire
	 * @param chk
	 * 		Le vérificateur de plaque
	 * @return
	 * 		La voiture lu depuis le stream
	 * @throws IOException
	 * @throws RuntimeException
	 * 
	 * @see {@link ODSReader#readPlein(InputStream)};
	 * @see ODSReader#getNextPlaque(InputStream)
	 * @since 1.1
	 */
	public UnilabsCar getCar(String plaque, PlateChecker chk) throws IOException {
		UnilabsCar car = null;
		if(!in.markSupported()) 
			throw new RuntimeException("Can't rewind stream.");
		in.mark(in.available());
		try {
			while (true) {
				Plein buf = readPlein();
				if(plaque.equals(last_plaque)) {
					if(car == null)
						car = new UnilabsCar(plaque, last_client, chk);
					car.addPlein(buf);
				}
			}
		} catch (EOFException e) {

		}
		in.reset();
		if(car == null) 
			throw new FileNotFoundException("Couldn't find car " + plaque);
		return car;
	}

	/**
	 * Retourne la plaque de la voiture suivante contenue dans le fichier
	 * @param in
	 * @return
	 * @throws IOException
	 * @see {@link ODSReader#plaques}
	 * @since 1.2
	 */
	public String getNextPlaque() throws IOException {
		DataInputStream dis;
		byte[] buffer;
		in.mark(in.available());
		if(plaques == null) {
			plaques = new ArrayList<String>();
			dis = new DataInputStream(in);
			try {
				while(true) {
					dis.read(new byte[dis.readInt()]); //Discarding useless client number
					buffer = new byte[dis.readInt()]; //Allocating space for Plate
					dis.read(buffer);
					if(!plaques.contains(new String(buffer, Charset.forName("ASCII"))))
						plaques.add(new String(buffer, Charset.forName("ASCII")));
					for(int i = 0 ; i < 4 ; i++) {
						dis.read(new byte[dis.readInt()]);
					}
				}
			} catch (EOFException e) {

			}
		}
		in.reset();
		return (lastReadPlaque < plaques.size()) ? plaques.get(lastReadPlaque++) : null;
	}

	/**
	 * Ajoute les pleins contenus dans un InputStream à une voiture déjà existante
	 * 
	 * @param car
	 * @param in
	 * @throws IOException
	 * @see {@link ODSReader#last_plaque}
	 * @since 1.1
	 */
	public void refreshCar(UnilabsCar car) throws IOException {
		in.mark(in.available());
		int pAdded = 0;
		System.out.println("Refreshing car " + car.getPlaque());
		try {
			while (true) {
				Plein b = readPlein();
				if(last_plaque.equals(car.getPlaque())) {
					car.addPlein(b);
					pAdded++;
				}
			}
		} catch (EOFException e) {
			System.out.println("Done refreshing " + car.getPlaque() + " ! Added " + pAdded + " datas");
		}
		in.reset();
	}
	
	/**
	 * Accesseur pour connaitre le nombre de voitures contenues dans le flux de ce Reader
	 * @since 2.0
	 * @see ODSReader#plaques
	 * @return
	 * 		Le nombre de voitures
	 */
	public int getCarNumber() {
		return plaques.size();
	}
}
