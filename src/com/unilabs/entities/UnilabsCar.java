package com.unilabs.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import com.unilabs.security.PlateChecker;
import java.util.Objects;

/**
 * Classe représentant une voiture du parc unilabs
 * 
 * @version 1.0
 * @author Ogier
 * @see Plein
 * @see PlateChecker
 *
 */
public class UnilabsCar {

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
	
	/**
	 * Constructeur de copie pour UnilabsCar
	 * @param car 
	 *		La voiture dont on veux instanticier une copie
	 */
	public UnilabsCar(UnilabsCar car) {
		plaque = car.plaque;
		numeroClient = car.numeroClient;
	}
	
	/**
	 * Ajoute un plein dans la liste des pleins de cette voiture si ce Plein n'est pas déjà présent présent
	 * @param p
	 *		Le plein à ajouter
	 * @return 
	 *		true si le plein a été ajouté false si il est déjà présent
	 */
	public boolean addPlein(Plein p) {
		if(pleins.contains(p)) {
			duplicateCount++;
			return false;
		}
		pleins.add(p);
		return true;
	}

	/**
	 * Accesseur pour le numéro client de cette voiture
	 * @return 
	 *		Le numéro du client auquel est associé cette voiture
	 */
	public int getNumeroClient() {
		return numeroClient;
	}

	/**
	 * Accesseur pour la plaque de cette voiture
	 * @return
	 *		La plaque de la voiture
	 */
	public String getPlaque() {
		return plaque;
	}
	
	/**
	 * Retourne le nombre de pleins enregistrés pour cette voiture
	 * @return 
	 *		Un entier pour le nombre de pleins de cette voiture
	 */
	public int size() {
		return pleins.size();
	}
	
	/**
	 * Accesseur pour le nombre de pleins en double qui ont été donnés à cette voiture
	 * @return 
	 */
	public int getDuplicateCount() {
		return duplicateCount;
	}

	/**
	 * Mutateur pour la plaque de cette voiture
	 * @param plaque 
	 *		La nouvelle plaque de cette voiture
	 * @throws IllegalArgumentException
	 */
	public void setPlaque(String plaque) {
		if(!checker.validatePlate(plaque)) {
			throw new IllegalArgumentException("La plaque " + plaque + " n'est pas un format valide !");
		}
		this.plaque = plaque;
	}

	/**
	 * Retourne la liste de pleins de cette voiture
	 * @return 
	 *		Une ArrayList contenant toutes les références pour les pleins de cette voiture
	 */
	public ArrayList<Plein> getPleins() {
		ArrayList<Plein> out = new ArrayList<>();
		for(Plein p : pleins) {
			out.add(p);
		}
		return out;
	}
	
	/**
	 * Retourne la liste des pleins de cette voiture
	 * @return 
	 *		Une tableau contenant les pleins de cette voiture
	 */
	public Plein[] getPleinsArray() {
		return pleins.toArray(new Plein[0]);
	}

	/**
	 * Mutateur pour le numéro client de cette voiture
	 * @param numeroClient 
	 *		Le nouveau numéro client de cette voiture
	 */
	public void setNumeroClient(int numeroClient) {
		this.numeroClient = numeroClient;
	}
	
	/**
	 * Méthode pour échanger le numéro client entre deux voitures
	 * @param c 
	 *			La voiture avec lequel cette voiture doit échanger son numéro client
	 */
	public boolean swap(UnilabsCar c) {
		if(numeroClient == c.numeroClient) {
			return false;
		}
		int buf = c.numeroClient;
		c.numeroClient = numeroClient;
		numeroClient = buf;
		return true;
	}
	
	@Override
	public boolean equals(Object c) {
		if( !(c instanceof UnilabsCar)) {
			return false;
		}
		UnilabsCar car = (UnilabsCar) c;
		Iterator<Plein> i1 = pleins.iterator(), i2 = car.pleins.iterator();
		if(!plaque.equals(car.plaque)) {
			return false;
		}
		if(numeroClient != car.numeroClient) {
			return false;
		}
		if(pleins.size() != car.pleins.size()) {
			return false;
		}
		while(i1.hasNext()) {
			if(!i1.next().equals(i2.next())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + this.numeroClient;
		hash = 79 * hash + Objects.hashCode(this.plaque);
		hash = 79 * hash + Objects.hashCode(this.pleins);
		return hash;
	}
}
