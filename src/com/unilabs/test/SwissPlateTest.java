package com.unilabs.test;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.Random;

import org.junit.Test;

import com.unilabs.security.SwissPlate;

public class SwissPlateTest {

	@Test
	public void validePlateChecker() {
		String plate = "GE 233546";
		assertTrue(new SwissPlate().validatePlate(plate));
	}
	
	@Test
	public void invalidPlateChecker() {
		Random rnd = new Random();
		String plate;
		byte[] faked = new byte[rnd.nextInt(12)];
		rnd.nextBytes(faked);
		plate = new String(faked, Charset.forName("ASCII"));
		System.err.println("Checking validity of plate " + plate);
		assertFalse(new SwissPlate().validatePlate(plate));
	}
}
