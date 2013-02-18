package com.unilabs.io;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



/**
 * Convertit les données depuis un fichier XLS (Excel 95-2003) en une suite de bytes suivant le même format que ODSInputStream
 * 
 * @author ars3nic
 * @see ODSInputStream
 * @version 1.0
 * 
 */
public class XLSInputStream extends InputStream {

	public static final String[] DEFAULT_COLUMNS = ODSInputStream.DEFAULT_COLUMNS;
	private File input;

	boolean read = false;

	/**
	 * Le tableau contenant les données lues depuis le fichier XLS dans le format standard
	 */
	private byte[] data;

	private int lastRead = 0;

	private String[] columns;
	
	private int marking = 0;

	public XLSInputStream(String path, String[] columns) {
		input = new File(path);
		this.columns = (columns == null) ? DEFAULT_COLUMNS : columns;
	}

	public XLSInputStream(File input, String[] columns) {
		this.input = input;
		this.columns = (columns == null) ? DEFAULT_COLUMNS : columns;
	}

	public int read() throws IOException {
		if(lastRead == data.length && !read) {
			fill();
			read = true;
		} else {
			throw new EOFException();
		}
		return (data[lastRead] & 0xff);
	}

	
	public void mark(int readlimit) {
		marking = lastRead;
	}
	
	public void reset() {
		lastRead = marking;
		marking = 0;
	}
	
	public boolean markSupported() {
		return true;
	}
	
	public void fill() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(out);
		Sheet worksheet;
		Workbook wb;
		Row titleRow;
		int i = 0;
		int columnsIndex[] = new int[columns.length];
		
		try {
			wb = WorkbookFactory.create(input);
		} catch (InvalidFormatException e) {
			throw new CorruptedFileException("Ce fichier n'est pas un fichier excel valide");
		}
		worksheet = wb.getSheetAt(0);
		
		titleRow = worksheet.getRow(0);
		
		for(Cell c : titleRow) {
			String cellValue = c.getRichStringCellValue().toString();
			if(cellValue.equals(columns[i])) {
				columnsIndex[i] = c.getColumnIndex();
				i++;
			}
		}
		
		for(int j = 1 ; i < worksheet.getLastRowNum() ; i++) {
			Row currentRow = worksheet.getRow(j);
			for(int col : columnsIndex) {
				dos.writeInt(currentRow.getCell(col).getStringCellValue().length());
				dos.writeBytes(currentRow.getCell(col).getStringCellValue());
			}
		}

		dos.flush();
		data = out.toByteArray();
		lastRead = 0;
	}
}
