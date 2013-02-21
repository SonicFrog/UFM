package com.unilabs.io;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
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
	private byte[] data = new byte[0];

	private int lastRead = 0;

	private String[] columns;
	
	private int marking = 0;
	
	private int[] indexes;

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
		} else if(lastRead == data.length) {
			System.out.println(getClass().getName() + ": Reaching end of buffer at " + lastRead);
			throw new EOFException();
		}
		return (data[lastRead++] & 0xff);
	}

	public String[] getColumns() {
		return columns;
	}
	
	public int[] getColumnsIndex() {
		return indexes;
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
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		indexes = new int[columns.length];
		
		try {
			wb = WorkbookFactory.create(input);
		} catch (InvalidFormatException e) {
			throw new CorruptedFileException("Ce fichier n'est pas un fichier excel valide");
		}
		worksheet = wb.getSheetAt(0);
		
		titleRow = worksheet.getRow(0);
		
		for(Cell c : titleRow) {
			String cellValue = c.getRichStringCellValue().toString();
			if(i == indexes.length)
				break;
			if(cellValue.equals(columns[i])) {
				indexes[i] = c.getColumnIndex();
				i++;
			}
		}
		
		for(int j = 1 ; i < worksheet.getLastRowNum() ; j++) {
			Row currentRow = worksheet.getRow(j);
			Cell currentCell;
			String data;
			if(currentRow == null) {
			    break;
			}
			for(int col : indexes) {
				currentCell = currentRow.getCell(col);
				switch(currentCell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC :
						if(HSSFDateUtil.isCellDateFormatted(currentCell))
							data = dateFormatter.format(currentCell.getDateCellValue());
						else 
							data = String.valueOf(currentCell.getNumericCellValue());
						break;
						
					case Cell.CELL_TYPE_STRING :
						data = currentCell.getStringCellValue();
						break;
						
					default :
						throw new CorruptedFileException("Un plein invalide a été detecté à la ligne " + currentCell.getRowIndex());
				}
				dos.writeInt(data.length());
				dos.writeBytes(data);
			}
		}

		dos.flush();
		data = out.toByteArray();
		System.out.println(getClass().getName() + ": Fill read " + data.length + " bytes");
		lastRead = 0;
	}
}
