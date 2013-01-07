package com.unilabs.io;

import java.io.IOException;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;

/**
 * Classe de lecture depuis un binaire d'une collection de voitures
 * @version 1.0
 * @see Writer
 * @author Ogier
 *
 */
public interface Reader {


	/**
	 * Lit une voiture depuis un flux
	 * 
	 * @param in
	 * 		InputStream depuis lequel on lit
	 * @param pc
	 * 		Le vérificateur de plaques pour la voiture lue
	 * @return
	 * 		La prochaine voiture contenue dans l'is
	 * @throws IOException
	 */
	public UnilabsCar readCar() throws IOException;

	/**
	 * Lit un plein depuis un InputStream
	 * @param in
	 * 		Le flux depuis lequel lire
	 * @return
	 * 		Le plein lu depuis l'is
	 * @throws IOException
	 */
	public Plein readPlein() throws IOException;
}
