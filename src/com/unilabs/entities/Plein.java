package com.unilabs.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe modélisant un plein effectué par une voiture
 * 
 * @version 2.0
 * @author Ogier
 * @see Comparable
 * @see Date
 *
 */
public class Plein implements Comparable<Plein>, Serializable {

	/**
	 * Format de date utilisé pour formatter le jour des pleins
	 */
	public static final SimpleDateFormat daySdf = new SimpleDateFormat("d-M-y");
	
	/**
	 * Format de date utilisé pour formatter les heures de pleins
	 */
	public static final SimpleDateFormat hourSdf = new SimpleDateFormat("H:m");
	
	/**
	 * Caractères de séparation utilisé pour les dates
	 */
	public static final char SEPARATOR = '-';
	
	private static final long serialVersionUID = -8255744173521635807L;
	
	/**
	 * Date du plein
	 */
	private Date date;
	
	/**
	 * Calendrier pour utiliser les informations de la Date
	 */
	private Calendar calendar;
	
	/**
	 * Quantité d'essence du plein
	 */
	private double fuelAmount;
	
	/**
	 * Prix du plein
	 */
	private double price;
	
	/**
	 * Kilomètrage de la voiture lors du plein
	 */
	private int kilometers;
	
	/**
	 * Instancie un plein
	 * @param date
	 * 		La date du plein au format (JJ-MM-AA)
	 * @param hour
	 * 		L'heure du plein
	 * @param fuelAmount
	 * 		La quantité d'essence
	 * @param price
	 * 		Le prix
	 * @param kilometers
	 * 		Le kilomètrage
	 */
	public Plein(String date, String hour, double fuelAmount, double price, int kilometers) {
		//TODO Ajouter le support de l'heure
		int h = 0, minutes = 0;
		int firstDash = date.indexOf(SEPARATOR);
		int secDash = date.indexOf(SEPARATOR, firstDash + 1);
		if(firstDash == 0 || secDash == 0)  {
			throw new IllegalArgumentException(date + " n'est pas une date valide!");
		}
		int day = Integer.parseInt(date.substring(0, firstDash));
		int month = Integer.parseInt(date.substring(firstDash + 1, secDash));
		int year = Integer.parseInt(date.substring(secDash + 1));
		if(hour != null) {
			h = Integer.parseInt(hour.substring(0, hour.indexOf(':')));
			minutes = Integer.parseInt(hour.substring(hour.indexOf(':') + 1, hour.length()));
		}
		calendar = new GregorianCalendar(year, month - 1, day);
		calendar.set(Calendar.HOUR_OF_DAY, h);
		calendar.set(Calendar.MINUTE, minutes);
		this.date = new Date(calendar.getTimeInMillis());
		this.fuelAmount = fuelAmount;
		this.price = price;
		this.kilometers = kilometers;
	}

	/**
	 * Retourne l'objet Date modélisant la date de ce Plein
	 * @return 
	 *		Un objet date contenant la date du plein
	 */
	public Date getDate() {
		return (Date) date.clone();
	}
	
	/**
	 * Retourne la date du plein sous forme de String
	 * @return 
	 *		Une String contenant la date du plein au format dd-mm-yy
	 */
	public String getDateString() {
		return daySdf.format(date);
	}
	
	/**
	 * Retourne l'heure du plein sous forme de String
	 * @return 
	 */
	public String getHourString() {
		return hourSdf.format(date);
	}
	
	public Calendar getCalendar() {
		return (Calendar) calendar.clone();
	}

	/**
	 * Mutateur pour la date du plein
	 * @param date 
	 *		Nouvelle date du plein
	 */
	public void setDate(Date date) {
		if(date.after(new Date(System.currentTimeMillis()))) {
			throw new IllegalArgumentException("Vous ne pouvez pas enregistrer un plein dans le futur");
		}
		this.date = date;
	}

	/**
	 * Accesseur pour la quantité de fuel
	 * @return 
	 *		La quantité de fuel pour ce plein
	 */
	public double getFuelAmount() {
		return fuelAmount;
	}
	
	/**
	 * Mutateur pour le kilomètrage lors de ce Plein
	 * @param km 
	 *		Le nouveau kilomètrage pour ce Plein
	 */
	public void setKilometers(int km) {
		kilometers = km;
	}
	
	public int getTotalKilometers() {
		return kilometers;
	}
	
	/**
	 * Calcule le nombre de kilomètres parcourues entre le plein passé en paramètre et ce plein
	 * @param p
	 * @return
	 */
	public int getRelativeKilometers(Plein p) {
//		if(kilometers - p.kilometers < 0)
//			throw new RuntimeException("Les informations de plein sont incorrectes : kilomètrage diminuant avec le temps");
		return p.kilometers - kilometers;
	}

	/**
	 * Mutateur pour la quantité d'essence
	 * @param fuelAmount 
	 *		La nouvelle quantité de fuel du plein
	 */
	public void setFuelAmount(double fuelAmount) {
		if(fuelAmount < 0) {
			throw new IllegalArgumentException("La quantité d'essence ne peut pas être négative !");
		}
		this.fuelAmount = fuelAmount;
	}

	/**
	 * Accesseur pour le prix du plein
	 * @return 
	 *		Le prix du plein
	 */
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		if(price < 0) {
			throw new IllegalArgumentException("Le prix d'un plein ne peut pas être négatif !");
		}
		this.price = price;
	}
	
	public int getHour() {
		//TODO method stub
		return 0;
	}
	
	/**
	 * Vérifie si les deux pleins sont strictement identiques
	 * (même jour, heure, prix, kilomètrages, litre)
	 * @param p
	 * @return
	 *		true si les pleins sont identiques false sinon
	 */
	@Override
	public boolean equals(Object p) {
		Plein plein;
		if(p == null) {
			return false;
		}
		if( !(p instanceof Plein) ) {
			return false;
		}
		try {
			plein = (Plein) p;
		} catch (ClassCastException e) {
			return false;
		}
		if(fuelAmount != plein.fuelAmount) {
			return false;
		}
		if(price != plein.price) {
			return false;
		}
		if(calendar.get(Calendar.DAY_OF_MONTH) != plein.calendar.get(Calendar.DAY_OF_MONTH)) {
			return false;
		}
		if(calendar.get(Calendar.MONTH) != plein.calendar.get(Calendar.MONTH)) {
			return false;
		}
		if(calendar.get(Calendar.YEAR) != plein.calendar.get(Calendar.YEAR)) {
			return false;
		}
		if(kilometers != plein.kilometers)  {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + calendar.get(Calendar.DAY_OF_MONTH);
		hash = hash * 31 + calendar.get(Calendar.MONTH);
		hash = hash * 31 + calendar.get(Calendar.YEAR);
		hash = hash * 31 + new Double(fuelAmount).hashCode();
		hash = hash * 31 + new Double(price).hashCode();
		hash += kilometers;
		return hash;
	}

	@Override
	public int compareTo(Plein p) {
		int out = 0;
		if(p.equals(this)) {
			out = 0;
		}
		else if(p.date.after(date)) {
			out = -1;
		}
		else if(p.date.before(date)) {
			out = 1;
		}
		return out;
	}

	@Override
	public String toString() {
		return "Plein (" + super.toString() + ") [date=" + date + ", amount=" + fuelAmount + ", price="
				+ price + "]";
	}
}
