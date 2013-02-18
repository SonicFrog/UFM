package com.unilabs.test;

import static org.junit.Assert.assertEquals;

import java.io.DataInputStream;
import java.nio.charset.Charset;

import org.junit.Test;

import com.unilabs.io.XLSInputStream;

public class XLSInputStreamTest {

	public static final String TEST_FILE = "test.xls";
	
	@Test
	public void instantiateTest() throws Exception {
		XLSInputStream in = new XLSInputStream(TEST_FILE, null);
		in.close();
	}

	@Test
	public void columnFindTest() throws Exception {
		XLSInputStream in = new XLSInputStream(TEST_FILE, null);
		in.fill();
		int[] columns = in.getColumnsIndex();
		int[] expected = { 1 , 2, 4, 5, 6, 10 };
		assertEquals(expected.length, columns.length);
		for(int i = 0 ; i < columns.length ; i++) {
			assertEquals(expected[i], columns[i]);
		}
		in.close();
	}

	@Test
	public  void firstColumnTest() throws Exception {
		XLSInputStream in = new XLSInputStream(TEST_FILE, null);
		DataInputStream dis = new DataInputStream(in);
		byte[] buffer;
		String[] expected = { "347.0", "FR 30307", "29-02-2012", "77.29" , "43.3", "630.0" };
		for(int i = 0 ; i < expected.length ; i++) {
			buffer = new byte[dis.readInt()];
			for(int j = 0 ; j < buffer.length ; j++) {
				buffer[j] = dis.readByte();
			}
			assertEquals(expected[i], new String(buffer , Charset.forName("ASCII")));
		}
		dis.close();
	}

	@SuppressWarnings("resource")
	@Test(expected=java.io.EOFException.class)
	public void endTest() throws Exception {
		XLSInputStream in = new XLSInputStream(TEST_FILE, null);
		while(true) {
			in.read();
		}
	}

	@SuppressWarnings("resource")
	@Test(expected=java.io.EOFException.class)
	public void dataFlowTest() throws Exception {
		XLSInputStream in = new XLSInputStream(TEST_FILE, null);
		DataInputStream dis = new DataInputStream(in);
		byte[] buffer;
		int size;
		while(true) {
			size = dis.readInt();
			buffer = new byte[size];
			dis.read(buffer);
		}
	}
}
