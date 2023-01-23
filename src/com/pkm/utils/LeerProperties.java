package com.pkm.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * 
 * @author ADPD
 *
 */
public class LeerProperties {

	private HashMap<String, String> collector;
	private String archivo;
	private Properties prop;


	public LeerProperties() {
		this.collector = new HashMap<>();
		this.prop = new Properties();
	}

	public LeerProperties(String archivo) throws IOException {
		this.collector = new HashMap<>();
		this.archivo = archivo;
		this.prop = new Properties();
		leerArchivo();
	}

	public String getArchivo() {
		return archivo;
	}


	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	
	
	public HashMap<String, String> getCollector() {
		return collector;
	}



	public void setCollector(HashMap<String, String> collector) {
		this.collector = collector;
	}

	/**
	 * the Hashmap collects all the Properties file data
	 * @throws IOException
	 */
	public void leerArchivo() throws IOException {
		
		InputStream input =  new FileInputStream(archivo);
		prop.load(input);
		
		Enumeration<?> e = prop.propertyNames();
		while(e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = prop.getProperty(key);
			//System.out.println("Key : " + key + ", Value : " + value);
			collector.put(key, value);
		}
		
		if(input != null) {
			input.close();
		}
		
	}
	
	
	
	
	
	
}
