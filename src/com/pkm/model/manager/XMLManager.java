package com.pkm.model.manager;

import java.io.FileOutputStream;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public abstract class XMLManager {

	protected String archivo;
	protected Document arbol;
	
	public XMLManager(String archivo) {
		this.archivo = archivo;
		
	}
	
	protected abstract void cargarDatos() throws Exception;
	
	protected void guardarRaiz(Element e) throws Exception {
		/*
		 * SAXBuilder sax = new SAXBuilder(); Document doc = sax.build(archivo);
		 * doc.setRootElement(e);
		 */

		Format f = Format.getPrettyFormat();
		f.setEncoding("gtk");
		f.setOmitDeclaration(false);

		XMLOutputter xmlOut = new XMLOutputter();
		xmlOut.output(e, new FileOutputStream(archivo));
	}
	
}
