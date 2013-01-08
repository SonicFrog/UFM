package com.unilabs.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;

public class XMLWriter implements Writer {

	private OutputStream os;
	private Document xmlDoc;
	private DocumentBuilder db;
	private DocumentBuilderFactory dbf;
	private Transformer tf;
	private TransformerFactory tff;
	
	private Element rootNode;
	
	public XMLWriter(OutputStream os) throws IOException {
		this.os = os;
		init();
	}

	public XMLWriter(File out) throws IOException {
		os = new BufferedOutputStream(new FileOutputStream(out));
		init();
	}

	private void init() throws IOException {
		dbf = DocumentBuilderFactory.newInstance();
		tff = TransformerFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
			tf = tff.newTransformer();
			xmlDoc = db.newDocument();
			rootNode = xmlDoc.createElement(XMLReader.ROOT_TAGNAME);
			xmlDoc.appendChild(rootNode);
		} catch (Exception e) {
			throw new CorruptedFileException("Le format XML n'est pas supporté sur votre plateforme  : " + e.getLocalizedMessage());
		}
	}
	
	@Override
	public void writeCar(UnilabsCar c) throws IOException {
		Element carNode = xmlDoc.createElement(XMLReader.CAR_TAGNAME);
		Element plein;
		Attr plaque = xmlDoc.createAttribute(XMLReader.CAR_PLATE_ATTRIB), num = xmlDoc.createAttribute(XMLReader.CAR_CLIENT_NUM_ATTRIB);
		plaque.setValue(c.getPlaque());
		num.setValue(String.valueOf(c.getNumeroClient()));
		carNode.setAttributeNode(plaque);
		carNode.setAttributeNode(num);
		for(Plein p : c.getPleins()) {
			plein = xmlDoc.createElement(XMLReader.PLEIN_TAGNAME);
			plein.setAttribute(XMLReader.PLEIN_DATE_ATTRIB, p.getDateString());
			plein.setAttribute(XMLReader.PLEIN_FUEL_ATTRIB, String.valueOf(p.getFuelAmount()));
			plein.setAttribute(XMLReader.PLEIN_HOUR_ATTRIB, p.getHourString());
			plein.setAttribute(XMLReader.PLEIN_MILEAGE_ATTRIB, String.valueOf(p.getTotalKilometers()));
			plein.setAttribute(XMLReader.PLEIN_PRICE_ATTRIB, String.valueOf(p.getPrice()));
			carNode.appendChild(plein);
		}
		rootNode.appendChild(carNode);
	}
	
	/**
	 * Ecrit le document XML dans son état actuel sur son flux de sortie
	 * @throws IOException
	 */
	public void flush() throws IOException {
		StreamResult result = new StreamResult(os);
		DOMSource source = new DOMSource(xmlDoc);
		try {
			tf.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
			throw new CorruptedFileException("Le format XML n'est pas supporté sur votre plateforme");
		}
	}
}
