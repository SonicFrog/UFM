package com.unilabs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.io.ODSInputStream;
import com.unilabs.io.ODSReader;
import com.unilabs.io.XMLReader;
import com.unilabs.io.XMLWriter; 
import com.unilabs.security.PlateChecker;
import com.unilabs.security.SwissPlate;

public class XMLReadWriteTest {
	private static PlateChecker pc = new SwissPlate();
	private static UnilabsCar[] car;
	private static final int singleTestIndex = 10;

	@BeforeClass
	public static void setupData() throws Exception {
		InputStream in = new ODSInputStream(ODSInputStreamTest.TEST_FILE, null);
		ODSReader r = new ODSReader(in);
		car = new UnilabsCar[r.getCarNumber()];
		for(int i = 0 ; i < r.getCarNumber() ; i++) {
			System.out.println(i);
			car[i] = r.getCar(r.getNextPlaque(), pc);
		}
		assertTrue(singleTestIndex > 1);
	}

	@Test
	public void xmlReadEmptyTest() throws Exception {
		File io;
		OutputStream out = new FileOutputStream(io = File.createTempFile("ufm", ".xml"));
		InputStream in = new FileInputStream(io);
		UnilabsCar read;
		XMLWriter w = new XMLWriter(out);
		XMLReader r;
		w.writeCar(car[singleTestIndex]);
		w.flush();
		r = new XMLReader(in, pc);
		read = r.readCar();
		assertEquals(0, read.getDuplicateCount());
		assertEquals(car[singleTestIndex].getPleins().size(), read.getPleins().size());
		assertEquals(car[singleTestIndex].getPlaque(), read.getPlaque());
		for(int i = 0 ; i < car[singleTestIndex].getPleins().size() ; i++) {
			assertTrue(car[singleTestIndex].getPleins().get(i).equals(read.getPleins().get(i)));
		}
	}

	@Test
	public void multipleCarReadWriteTest() throws Exception {
		File io = File.createTempFile("ufm", ".xml");
		OutputStream out = new FileOutputStream(io);
		InputStream in = new FileInputStream(io);
		XMLWriter w = new XMLWriter(out);
		XMLReader r;
		ArrayList<UnilabsCar> read = new ArrayList<UnilabsCar>();
		for(UnilabsCar c : car) {
			w.writeCar(c);
		}
		w.flush();
		r = new XMLReader(in, pc);
		try {
			while(true)
				read.add(r.readCar());
		} catch (EOFException e) {
			
		}
		assertEquals(car.length, read.size());
		for(int i = 0 ; i < car.length ; i++)
			assertTrue(car[i].equals(read.get(i)));
	}

	@Test
	public void multiplePleinReadWriteTest() throws Exception {
		File io = File.createTempFile("ufm", ".xml");
		UnilabsCar read;
		XMLWriter w = new XMLWriter(io);
		XMLReader r;
		w.writeCar(car[singleTestIndex]);
		w.flush();
		r = new XMLReader(io, pc);
		read = r.readCar();
		assertEquals("Plein count different", car[singleTestIndex].getPleins().size(), read.getPleins().size());
		for(int i = 0 ; i < car[singleTestIndex].getPleins().size() ; i++) {
			assertTrue("Plein n°" + i + " is different", car[singleTestIndex].getPleins().get(i).equals(read.getPleins().get(i)));
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
