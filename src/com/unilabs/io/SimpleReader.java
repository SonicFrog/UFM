package com.unilabs.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;

/**
 * Lecteur de voiture depuis un InputStream dépendant de ObjectInputStream
 * 
 * @version 1.0
 * @author Ogier
 *
 */
public class SimpleReader implements Reader {

	private ObjectInputStream in;
	private InputStream underlying;

	public SimpleReader(InputStream in) {
		underlying = in;
	}

	/**
	 * Lit une voiture 
	 * @return
	 * 		UnilabsCar la voiture qui vient d'être lue depuis l'InputStream
	 * @throws
	 * 		IOException Lors d'une erreur dans l'InputStream sous-jacent
	 */
	public UnilabsCar readCar() throws IOException {
		UnilabsCar out = null;
		if(in == null)
			in = new ObjectInputStream(underlying);
		try {
			out = (UnilabsCar) in.readObject();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Erreur fatale lors de la lecture du fichier");
		}
		return out;
	}

	public Plein readPlein() throws IOException {
		Plein out;
		try {
			out = (Plein) in.readObject();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Erreur lors de la lecture des informations depuis le fichier : " + e.getLocalizedMessage());
		}
		return out;
	}
}
