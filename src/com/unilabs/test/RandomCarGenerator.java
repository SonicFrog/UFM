package com.unilabs.test;

import java.util.ArrayList;
import java.util.Random;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;
import com.unilabs.security.SwissPlate;

public class RandomCarGenerator {

	public static UnilabsCar[] generateCarArray() {
		ArrayList<UnilabsCar> data = new ArrayList<UnilabsCar>();
		int bound = new Random().nextInt(25);
		for(int i = 0 ; i < bound ; i++) 
			data.add(generateCar());
		return data.toArray(new UnilabsCar[0]);
	}
	
	public static UnilabsCar generateCar() {
		UnilabsCar car;
		ArrayList<Plein> data;
		int bound = new Random().nextInt(100);
		int km = 0;
		String date = "";
		String hour = "";
		data = new ArrayList<Plein>();
		for(int i = 0 ; i < bound ; i++) {
			int buf;
			buf = new Random().nextInt(30) + 1;
			if(buf < 10) {
				date += "0";
			}
			date += buf + "-";
			buf = new Random().nextInt(11) + 1;
			if(buf < 10)
				date += "0";
			date += buf + "-";
			date += new Random().nextInt(5) + 2005;
			hour += new Random().nextInt(23) + ":" + new Random().nextInt(59);
			System.out.println("Adding plein at " + date + " " + hour);
			data.add(new Plein(date, hour, new Random().nextInt(25), new Random().nextInt(50), new Random().nextInt(100)));
			data.get(i).setKilometers(km);
			km += new Random().nextInt(59);
			date = "";
			hour = "";
		}
		car = new UnilabsCar(generatePlaque(), new Random().nextInt(20000), new SwissPlate());
		for(int i = 0 ; i < data.size() ; i++) 
			car.addPlein(data.get(i));
		return car;
	}
	
	public static String generatePlaque() {
		String out = "";
		char buf;
		for(int i = 0 ; i < 2 ; i++) {
			while (!Character.isLetter((buf = (char) (new Random().nextInt() + 49))));
			out += buf;
		}
		out += " ";
		out += new Random().nextInt(666666);
		return out;
	}
}
