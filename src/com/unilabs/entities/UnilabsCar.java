package com.unilabs.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import com.unilabs.security.PlateChecker;

/**
 * Classe représentant une voiture du parc unilabs
 * 
 * @version 1.0
 * @author Ogier
 * @see Plein
 * @see PlateChecker
 *
 */
public class UnilabsCar implements Serializable {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 6933228811491643143L;

	/**
	 * Numero de client pour cette voiture
	 */
	private int numeroClient;
	
	/**
	 * Plaque de la voiture
	 */
	private String plaque;
	
	/**
	 * Collection des pleins associes a cette voiture
	 */
	private TreeSet<Plein> pleins = new TreeSet<Plein>();
	
	/**
	 * Total des pleins en doubles qu'on a tenter d'ajouter a cette voiture
	 */
	private int duplicateCount = 0;
	
	/**
	 * Vérificateur de plaque
	 */
	private PlateChecker checker;
	
	/**
	 * Constructeur d'une voiture avec la plaque le numéro client et le platechecker
	 * 
	 * @param plaque
	 * @param numeroClient
	 * @param pc
	 */
	public UnilabsCar(String plaque, int numeroClient, PlateChecker pc) {
		checker = pc;
		this.numeroClient = numeroClient;
		setPlaque(plaque);
	}
	
	public UnilabsCar(UnilabsCar car) {
		plaque = car.plaque;
		numeroClient = car.numeroClient;
	}
	
	public boolean addPlein(Plein p) {
		if(pleins.contains(p)) {
			duplicateCount++;
			return false;
		}
		pleins.add(p);
		return true;
	}

	public int getNumeroClient() {
		return numeroClient;
	}

	public String getPlaque() {
		return plaque;
	}
	
	public int size() {
		return pleins.size();
	}
	
	public int getDuplicateCount() {
		return duplicateCount;
	}

	public void setPlaque(String plaque) {
		if(!checker.validatePlate(plaque))
			throw new IllegalArgumentException("La plaque " + plaque + " n'est pas un format valide !");
		this.plaque = plaque;
	}

	public ArrayList<Plein> getPleins() {
		ArrayList<Plein> out = new ArrayList<Plein>();
		for(Plein p : pleins) {
			out.add(p);
		}
		return out;
	}
	
	public Plein[] getPleinsArray() {
		return pleins.toArray(new Plein[0]);
	}

	public void setNumeroClient(int numeroClient) {
		this.numeroClient = numeroClient;
	}
	
	public boolean equals(UnilabsCar c) {
		Iterator<Plein> i1 = pleins.iterator(), i2 = c.pleins.iterator();
		if(!plaque.equals(c.plaque))
			return false;
		if(numeroClient != c.numeroClient)
			return false;
		if(pleins.size() != c.pleins.size())
			return false;
		while(i1.hasNext()) {
			if(!i1.next().equals(i2.next())) {
				return false;
			}
		}
		return true;
	}
}
