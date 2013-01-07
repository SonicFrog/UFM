package com.unilabs.io;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.unilabs.entities.UnilabsCar;

/**
 * Classe simple permettant le stockage des voitures dans un format binaire.7
 * 
 * @author Ogier
 * @version 1.0
 * @see UnilabsCar
 *
 */
public class SimpleWriter implements Writer {

	private ObjectOutputStream out;
	
	public SimpleWriter(OutputStream out) throws IOException {
		this.out = new ObjectOutputStream(out);
		out.flush();
	}
	
	public void writeCar(UnilabsCar car) throws IOException {
		out.writeObject(car);
		out.flush();
	}
}