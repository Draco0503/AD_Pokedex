package com.pkm.model.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.pkm.model.entity.Pokemon;
import com.pkm.model.entity.Tipo;

/**
 * 
 * @author ADPD
 *
 */
public class XMLPokemon extends XMLManager implements DAOManager<Pokemon, Integer> {

	private static int id;
	// Attributes
	private HashMap<Integer, Pokemon> pokedex;
	private XMLTipo xt;

	// Constructors
	public XMLPokemon(String archivo, DAOManager<Tipo, Integer> daoManagerc2) throws Exception {
		super(archivo);
		this.xt = (XMLTipo) daoManagerc2;
		pokedex = new HashMap<>();
		cargarDatos();
	}

	public XMLPokemon(String archivo, HashMap<Integer, Pokemon> exportacion) throws Exception {
		super(archivo);
		pokedex = exportacion;
	}

	/**
	 * Gets the data from the XMLfile and adds it to the HashMap
	 * 
	 * @throws Exception
	 */
	protected void cargarDatos() throws Exception {
		try {
			SAXBuilder sax = new SAXBuilder();
			arbol = sax.build(archivo);
			Element raiz = arbol.getRootElement();
			List<Element> lista = raiz.getChildren();

			for (Element e : lista) {
				int id = Integer.parseInt(e.getAttributeValue("id"));
				if (id > this.id) this.id = id;
				int num_pokedex = Integer.parseInt(e.getChild("num_pokedex").getText());
				String nombre = e.getChild("nombre").getText();
				int t1 = Integer.parseInt(e.getChild("tipo1").getText());
				Tipo tipo1 = xt.read(t1);
				int t2 = Integer.parseInt(e.getChild("tipo2").getText());
				Tipo tipo2 = xt.read(t2);
				boolean shiny = false;
				String aux = e.getChild("shiny").getText();
				if (aux.equals("true"))
					shiny = true;
				String url = e.getChild("url").getText();
				Pokemon pkm = new Pokemon(id, num_pokedex, nombre, tipo1, tipo2, shiny, url);
				pokedex.put(num_pokedex, pkm);
			}
		} catch (JDOMException | IOException e) {
			guardarRaiz(new Element("pokedex"));
			cargarDatos();
			throw new Exception("Archivo vacio, se ha creado una nueva raiz");
		}

	}



	@Override
	public int create(Pokemon item) throws Exception {
		int exitValue = 0;
		id++;
		if (pokedex.get(item.getNum_pokedex()) != null)
			exitValue = -1;
		else {
			item.setId(id);
			pokedex.put(item.getNum_pokedex(), item);
			guardar();
		}
		return exitValue;
	}

	@Override
	public Pokemon read(Integer id) throws Exception {
		return pokedex.get(id);
	}

	@Override
	public int update(Pokemon item, Integer id) throws Exception {
		pokedex.put(id, item);
		guardar();
		// En este caso siempre retornara 0
		return 0;
	}

	/**
	 * If the pokemon exists, is deleted from the HashMap, and its eliminated from
	 * the XMLfile
	 * 
	 * @return Integer
	 */
	@Override
	public int delete(Integer id) throws Exception {
		int exitValue = 0;
		if (pokedex.get(id) == null)
			exitValue = -1;
		else {
			pokedex.remove(id);
			guardar();
		}
		return exitValue;
	}

	/**
	 * It returns the data from the Hashmap
	 * 
	 * @return pokedex
	 */
	@Override
	public HashMap<Integer, Pokemon> readAll() throws Exception {
		// TODO Auto-generated method stub
		return pokedex;
	}

	@Override
	public void guardar() throws Exception {
		Element raiz = new Element("pokedex");

		pokedex.forEach((k, pkm) -> {
			guardarUno(pkm, raiz);

		});

		guardarRaiz(raiz);

	}

	/**
	 * Adds to the root element a new pokemon tag
	 * 
	 * @param Pokemon
	 * @param Element
	 */
	private void guardarUno(Pokemon pkm, Element e) {
		Element pokemon = new Element("pokemon");
		e.addContent(pokemon);
		Attribute id = new Attribute("id", String.valueOf(pkm.getId()));
		pokemon.setAttribute(id);

		Element num_pokedex = new Element("num_pokedex");
		num_pokedex.setText(String.valueOf(pkm.getNum_pokedex()));
		pokemon.addContent(num_pokedex);
		
		Element nombre = new Element("nombre");
		nombre.setText(pkm.getNombre());
		pokemon.addContent(nombre);

		Element tipo1 = new Element("tipo1");
		tipo1.setText(String.valueOf(pkm.getTipo1().getId()));
		pokemon.addContent(tipo1);

		Element tipo2 = new Element("tipo2");
		tipo2.setText(String.valueOf(pkm.getTipo2().getId()));
		pokemon.addContent(tipo2);

		Element shiny = new Element("shiny");
		shiny.setText(String.valueOf(pkm.isShiny()));
		pokemon.addContent(shiny);

		Element url = new Element("url");
		url.setText(pkm.getUrl());
		pokemon.addContent(url);
	}

}
