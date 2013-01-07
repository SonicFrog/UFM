package com.unilabs.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import org.junit.Test;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;
import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.io.XMLReader;
import com.unilabs.io.XMLWriter;
import com.unilabs.security.PlateChecker;
import com.unilabs.security.SwissPlate;

public class XMLReadWriteTest {
	private static int numero = 12346;
	private static String plaque = "GE 123456";
	private static PlateChecker pc = new SwissPlate();
	private static String date = "10-10-2012";
	private static UnilabsCar car = new UnilabsCar(plaque, numero, pc);
	
	@Test
	public void xmlReadEmptyTest() throws Exception {
		File io;
		OutputStream out = new FileOutputStream(io = File.createTempFile("ufm", ".xml"));
		InputStream in = new FileInputStream(io);
		UnilabsCar read;
		XMLWriter w = new XMLWriter(out);
		XMLReader r;
		w.writeCar(car);
		r = new XMLReader(in, UnilabsFleetManager.getInstance().getPlateChecker());
		read = r.readCar();
		assertTrue(car.equals(read));
	}
	
	@Test
	public void multiplePleinReadWriteTest() throws Exception {
		File io = File.createTempFile("ufm", ".xml");
		UnilabsCar read, car = new UnilabsCar(XMLReadWriteTest.car);
		Plein[] data = SimpleReadWriteTest.generatePlein();
		XMLWriter w = new XMLWriter(io);
		XMLReader r;
		for(Plein p : data)
			car.addPlein(p);
		w.writeCar(car);
		r = new XMLReader(io, pc);
		read = r.readCar();
		assertEquals("Plein count different", car.getPleins().size(), read.getPleins().size());
		for(int i = 0 ; i < car.getPleins().size() ; i++) {
			assertTrue("Plein n°" + i + " is different", car.getPleins().get(i).equals(read.getPleins().get(i)));
		}
	}
	
	@Test(expected=com.unilabs.io.CorruptedFileException.class)
	public void xmlReadInvalidTest() throws Exception {
		File io = File.createTempFile("ufm", ".garbage");
		InputStream in = new FileInputStream(io);
		OutputStream out = new FileOutputStream(io);
		XMLReader r = new XMLReader(in, pc);
		byte[] rnd = new byte[1024];
		new Random().nextBytes(rnd);
		out.write(rnd);
		r.readCar();
		out.close();
	}
}
