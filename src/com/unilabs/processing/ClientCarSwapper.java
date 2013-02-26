package com.unilabs.processing;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.processing.PrevisionCalculator;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe de calcul des échanges possible entre les clients pour uniformiser les différences de kilomètrages
 * @author Ogier
 */
public class ClientCarSwapper {
	
	private static int swapThreshold = 10_000;
	
	private UnilabsCar[] data;
	private ArrayList<CarPrevisionSortable> pValue = new ArrayList<>();
	
	private int months;
	
	private ArrayList<CarSwap> swapList = new ArrayList<>();

	public ClientCarSwapper(UnilabsCar[] data) {
		this.data = data;
		this.months = PrevisionCalculator.DEFAULT_PREVISION_MONTHS;
	}

	public ClientCarSwapper(UnilabsCar[] data, int months) {
		this.data = data;
		this.months = months;
	}
	
	private void sortByPlannedKilometers() {
		for(UnilabsCar c : data) {
			pValue.add(new CarPrevisionSortable(c, new PrevisionCalculator(c, months).averageKilometers()));			
		}
		Collections.sort(pValue);
	}
	
	public static void setThreshold(int threshold) {
		swapThreshold = threshold;
	}
	
	private void createSwapList() {
		CarPrevisionSortable refOne, refTwo;
		sortByPlannedKilometers();
		for(int i = 0 ; i < pValue.size() / 2 ; i++) {
			refOne = pValue.get(i);
			refTwo = pValue.get(pValue.size() - i - 1);
			if(refTwo.getKilometersPlanned() - refTwo.getKilometersPlanned() > swapThreshold) {
				swapList.add(new CarSwap(refTwo.getRef(), refOne.getRef()));
			}
		}
	}
	
	public ArrayList<CarSwap> getSwapList() {
		createSwapList();
		return swapList;
	}
	
	/**
	 * Structue utilisé pour stocker deux références vers des voitures qui doivent être échangées
	 */
	public static class CarSwap {
		
		UnilabsCar one, two;
		
		public CarSwap(UnilabsCar one, UnilabsCar two) {
			this.one = one;
			this.two = two;
		}
		
		public UnilabsCar getOne() {
			return one;
		}
		
		public UnilabsCar getTwo() {
			return two;
		}
	}
	
	private static class CarPrevisionSortable implements Comparable<CarPrevisionSortable> {
		
		private double value;
		private UnilabsCar ref;
		
		public CarPrevisionSortable (UnilabsCar ref, double value) {
			this.value = value;
			this.ref = ref;
		}
		
		public UnilabsCar getRef() {
			return ref;
		}
		
		public double getKilometersPlanned() {
			return value;
		}
		
		@Override
		public int compareTo(CarPrevisionSortable comp) {
			if(comp.value > value) {
				return -1;
			}
			if(comp.value < value) {
				return 1;
			}
			return 0;
		}
	}
}
