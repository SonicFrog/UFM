package com.unilabs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;
import com.unilabs.security.SwissPlate;

/**
 * Classe de test de UnilabsCar et de Plein
 * @author ars3nic
 * @version 2.0
 *
 */
public class CarTest {

	private static String invalidPlate;
	private static String validPlate = "GE 345345";
	private static int number = 12371;
	private static String date = "20-12-2012";
	private static String oDate = "21-12-2012";
	
	Plein plein = new Plein(date, null, 10, 10, 10);
	Plein plein2 = new Plein(date, null, 10, 10, 10);
	Plein dPlein = new Plein(oDate, null, 10, 10, 10);
	Plein dPlein2 = new Plein(oDate, null, 11, 10, 10);

	@BeforeClass
	public static void setUp() {
		Random rnd = new Random();
		byte[] src = new byte[9];
		rnd.nextBytes(src);
		invalidPlate = new String(src);
	}
	
	@Test
	public void instantiateTest() {
		UnilabsCar car = new UnilabsCar(validPlate, number, new SwissPlate());
		assertEquals(car.getPlaque(), validPlate);
		assertEquals(car.getNumeroClient(), number);
	}
	
	@Test
	public void multiPleinTest() {
		UnilabsCar car = new UnilabsCar(validPlate, number, new SwissPlate());
		assertTrue("Le premier plein n'a pas été ajouté", car.addPlein(new Plein(date, null, 10, 10, 10)));
		assertFalse("Le plein identique a été ajouté", car.addPlein(new Plein(date, null, 10, 10, 10)));
		assertEquals("Le plein identique a été ajouté", 1, car.size());
		assertEquals("Le compte des doublons n'est pas bon", 1, car.getDuplicateCount());
	}

	@Test
	public void pleinSameRefContainTest() {
		ArrayList<Plein> p = new ArrayList<Plein>();
		Plein plein = new Plein(date, null, 10, 10, 10);
		p.add(plein);
		assertTrue(p.contains(plein));
	}
	
	@Test
	public void pleinDiffRefContainTest() {
		ArrayList<Plein> p = new ArrayList<Plein>();
		p.add(plein);
		assertTrue(p.contains(plein2));
		assertTrue(p.contains(plein));
		assertFalse(p.contains(dPlein));
	}
	
	@Test
	public void pleinEqualsTest() {
		assertTrue(plein.equals(plein2));
		assertFalse(dPlein.equals(plein));
		assertFalse(dPlein.equals(plein2));
		assertFalse(dPlein.equals(dPlein2));
		assertTrue(plein.equals(plein));
		assertFalse(plein.equals(false));
		assertEquals(0, plein.compareTo(plein2));
		assertEquals(1, dPlein.compareTo(plein));
		assertEquals(-1, plein.compareTo(dPlein));
		assertEquals(plein.hashCode(), plein2.hashCode());
		assertFalse("Same hashcodes for differents pleins", plein.hashCode() == dPlein.hashCode());
		assertFalse("Same hashcodes for differents pleins", dPlein.hashCode() == dPlein2.hashCode());
	}
	
	@Test(expected=java.lang.IllegalArgumentException.class)
	public void failInstantiateTest() {
		new UnilabsCar(invalidPlate, number, new SwissPlate());
	}
}
