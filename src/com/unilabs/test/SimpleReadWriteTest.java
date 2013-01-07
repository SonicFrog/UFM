package com.unilabs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;
import com.unilabs.io.Reader;
import com.unilabs.io.SimpleReader;
import com.unilabs.io.SimpleWriter;
import com.unilabs.io.Writer;
import com.unilabs.security.DummyPlate;
import com.unilabs.security.PlateChecker;
import com.unilabs.security.SwissPlate;

public class SimpleReadWriteTest {

	private static int numero = 12346;
	private static String plaque = "GE 123456";
	private static PlateChecker pc = new SwissPlate();
	private static String date = "10-10-2012";
	private static UnilabsCar car = new UnilabsCar(plaque, numero, pc);

	public static Plein[] generatePlein() {
		Plein[] out = new Plein[new Random().nextInt(25)];
		Random rnd = new Random();
		for(int i = 0 ; i < out.length ; i++) {
			out[i] = new Plein(date, null, rnd.nextDouble(), rnd.nextDouble(), rnd.nextInt());
		}
		return out;
	}

	public static UnilabsCar generateCar() {
		byte[] plate = new byte[8];
		Random rnd = new Random();
		rnd.nextBytes(plate);
		UnilabsCar out = new UnilabsCar(new String(plate), rnd.nextInt(), new DummyPlate());
		for(Plein p : generatePlein()) {
			out.addPlein(p);
		}
		return out;
	}

	@Test
	public void writeOneCarTest() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ByteArrayInputStream bis;
		UnilabsCar local = new UnilabsCar(car);
		Writer wr = new SimpleWriter(bos);
		Reader rd;
		Plein[] data = generatePlein();
		for(Plein p : data) {
			local.addPlein(p);
		}
		wr.writeCar(local);
		bis = new ByteArrayInputStream(bos.toByteArray());
		rd = new SimpleReader(bis);
		assertTrue(local.equals(rd.readCar()));
	}

	@Test
	public void RWRandomCarNumberTest() throws Exception {
		ArrayList<UnilabsCar> in_buf = new ArrayList<UnilabsCar>();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in;
		UnilabsCar[] buf = new UnilabsCar[new Random().nextInt(50)];
		Writer w = new SimpleWriter(out);
		Reader r;
		for(int i = 0 ; i < buf.length ; i++)
			buf[i] = generateCar();
		for(UnilabsCar c : buf) {
			w.writeCar(c);
		}
		in = new ByteArrayInputStream(out.toByteArray());
		r = new SimpleReader(in);
		try {
			while(true) {
				in_buf.add(r.readCar());
			}
		} catch (EOFException e) {

		}
		assertEquals(in_buf.size(), buf.length);
		for(int i = 0 ; i < buf.length ; i++) {
			assertTrue(in_buf.get(i).equals(buf[i]));
		}
	}
}
