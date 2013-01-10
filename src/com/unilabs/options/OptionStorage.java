package com.unilabs.options;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.Vector;

import com.unilabs.io.PropertyReader;
import com.unilabs.io.Reader;
import com.unilabs.io.Writer;

/**
 * Classe de stockage des options du programme
 * @version 1.0
 * @author ogier
 *
 */
public class OptionStorage {

	public static final String READER_CLASS_KEY = "reader";
	public static final String WRITER_CLASS_KEY = "writer";
	public static final String CURRENCY_KEY = "currencyString";
	
	public static final String DEFAULT_READER = "com.unilabs.io.CompressedXMLReader";
	public static final String DEFAULT_WRITER = "com.unilabs.io.CompressedXMLWriter";
	public static final String DEFAULT_CURRENCY = "CHF";
	
	/**
	 * Nom de classe utilisée pour le Writer
	 */
	protected String writerClass;
	
	/**
	 * Nom de classe utilisée pour le Reader
	 */
	protected String readerClass;
	
	protected String currencyString;
	
	protected PropertyReader pr;
	

	public OptionStorage() {

	}

	public OptionStorage(File configFile) throws IOException {
		pr = new PropertyReader(configFile);
		init();
	}

	public OptionStorage(Properties p) {
		pr = new PropertyReader(p);
		init();
	}
	
	private void init() {
		writerClass = pr.getProperty(WRITER_CLASS_KEY, DEFAULT_WRITER);
		readerClass = pr.getProperty(READER_CLASS_KEY, DEFAULT_READER);
		currencyString = pr.getProperty(CURRENCY_KEY, DEFAULT_CURRENCY);
	}

	/**
	 * Instancie dynamiquement un Writer d'un type déterminé par le fichier de configuration
	 * @param args
	 * 		La liste de paramètres du constructeur
	 * @return
	 * @throws ConfigurationException
	 */
	public Writer getWriter(Vector<?> args) throws IOException {
		Class<?> wClass = null;
		try {
			wClass = Class.forName(writerClass);
			Class<?>[] constructorP = new Class[args.size()];
			for(int i = 0 ; i < constructorP.length ; i++) {
				Class superclass = args.get(i).getClass();
				while(!superclass.getSuperclass().equals(Object.class)) {
					System.out.println("Changing runtime class of " + superclass.getName() + " to " + superclass.getSuperclass().getName());
					superclass = superclass.getClass().getSuperclass();
				}
				constructorP[i] = superclass;
			}
			Constructor<?> c = wClass.getConstructor(constructorP);
			return (Writer) c.newInstance(args.toArray(new Object[0]));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigurationException();
		}
	}

	public Reader getReader(Vector<?> args) throws IOException {
		Class<?> wClass = null;
		try {
			wClass = Class.forName(readerClass);
			Class<?>[] constructorP = new Class[args.size()];
			for(int i = 0 ; i < constructorP.length ; i++) {
				Class superclass = args.get(i).getClass();
				if(!superclass.getSuperclass().equals(Object.class)) {
					superclass = superclass.getSuperclass();
				}
				constructorP[i] = superclass;
			}
			Constructor<?> c = wClass.getConstructor(constructorP);
			return (Reader) c.newInstance(args.toArray(new Object[0]));
		} catch (InvocationTargetException e) {
			throw (IOException) e.getCause();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigurationException();
		}
	}
	
	
	public String getCurrency() {
		return currencyString;
	}
}
