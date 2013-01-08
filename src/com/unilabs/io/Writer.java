package com.unilabs.io;

import java.io.IOException;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;

/**
 * Classe abstraite d'écriture des données binaires sur des flux de sorties
 * @author Ogier
 * @version 1.0
 * @see UnilabsCar
 * @see Plein
 *
 */
public interface Writer {
	/**
	 * Ecrit une voiture sur un OutputStream
	 * @param c
	 * @param out
	 * @throws IOException
	 */
	public void writeCar(UnilabsCar c) throws IOException;
	public void flush() throws IOException;
}