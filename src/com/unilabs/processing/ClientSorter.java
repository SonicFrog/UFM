package com.unilabs.processing;

import java.util.ArrayList;

import com.unilabs.entities.UnilabsCar;

/**
 * Traitement des données par numéro client
 * @author Ogier
 * @version 1.0
 *
 */
public class ClientSorter {

	private UnilabsCar[] data;
	private UnilabsCar[] sorted;
	
	public ClientSorter(UnilabsCar[] list, int client) {
		data = list;
		sorted = getByClientNumber(client);
	}
	
	public static Integer[] getClientNumbers(UnilabsCar[] list) {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for(UnilabsCar c : list) {
			if(!numbers.contains(c.getNumeroClient())) {
				numbers.add(c.getNumeroClient());
			}
		}
		return numbers.toArray(new Integer[0]);
	}
	
	private UnilabsCar[] getByClientNumber(int client) {
		ArrayList<UnilabsCar> out = new ArrayList<UnilabsCar>();
		for(UnilabsCar c : data) {
			if(c.getNumeroClient() == client) {
				out.add(c);
			}
		}
		return out.toArray(new UnilabsCar[0]);
	}
	
	public UnilabsCar[] getClientsCar() {
		return sorted;
	}
}
