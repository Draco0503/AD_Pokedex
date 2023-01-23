package com.pkm.model.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.pkm.model.entity.Tipo;

public class XMLTipo extends XMLManager implements DAOManager<Tipo, Integer> {

	private HashMap<Integer, Tipo> tipos;
	
	public XMLTipo(String archivo) throws Exception {
		super(archivo);
		tipos = new HashMap<>();
		cargarDatos();
	}

	@Override
	protected void cargarDatos() throws Exception {
		try {
			SAXBuilder sax = new SAXBuilder();
			arbol = sax.build(archivo);
			Element raiz = arbol.getRootElement();
			List<Element> lista = raiz.getChildren();

			for (Element e : lista) {
				int id = Integer.parseInt(e.getAttributeValue("id"));
				String nombre = e.getChild("nombre").getText();
				Tipo tipo = new Tipo(id, nombre);
				tipos.put(id, tipo);
			}
		} catch (JDOMException | IOException e) {
			guardarRaiz(new Element("tipos"));
			cargarDatos();
			throw new Exception("Archivo vacio, se ha creado una nueva raiz");
		}
		
	}
	
	@Override
	public int create(Tipo item) throws Exception {
		// NO NEED TO BE IMPLEMENTED
		return 0;
	}

	@Override
	public Tipo read(Integer id) throws Exception {
		Tipo tipo = null;
		for (Entry<Integer, Tipo> t : tipos.entrySet()) {
			if (t.getKey() == id) tipo = t.getValue();
		}
		return tipo;
	}

	@Override
	public int update(Tipo item, Integer id) throws Exception {
		// NO NEED TO BE IMPLEMENTED
		return 0;
	}

	@Override
	public int delete(Integer id) throws Exception {
		// NO NEED TO BE IMPLEMENTED
		return 0;
	}

	@Override
	public HashMap<Integer, Tipo> readAll() throws Exception {
		return tipos;
	}

	@Override
	public void guardar() throws Exception {
		// NO NEED TO BE IMPLEMENTED
		return;
	}



}
