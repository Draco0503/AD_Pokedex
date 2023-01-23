package com.pkm.model.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.pkm.model.entity.Pokemon;
import com.pkm.model.entity.Tipo;

/**
 * 
 * @author ADPD
 *
 */
public class FilePokemon extends FileManager implements DAOManager<Pokemon, Integer> {
	// Attributes
	private static int id;		// id AUTO_INCREMENT
	private HashMap<Integer, Pokemon> pokedex;
	private FileTipo ft;
	// Constructors
	public FilePokemon(String archivo, DAOManager<Tipo, Integer> daoManagerc2) throws Exception {
		super(archivo);
		this.ft = (FileTipo) daoManagerc2;
		this.pokedex = new HashMap<>();
		cargarDatos();
	}

	public FilePokemon(String archivo, HashMap<Integer, Pokemon> exportacion) throws Exception {
		super(archivo);
		pokedex = exportacion;
	}

	/**
	 * Gets the data from the file and adds it to the HashMap
	 * 
	 * @throws Exception
	 */
	protected void cargarDatos() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(archivo));
		String linea = "";
		while ((linea = br.readLine()) != null) {
			String[] info = linea.split("[|]"); // "|" es nuestro separador
			int id = Integer.parseInt(info[0]);
			if (id > this.id) this.id = id;
			int num_pokedex = Integer.parseInt(info[1]);
			String nombre = info[2];
			int t1 = Integer.parseInt(info[3]);
			Tipo tipo1 = ft.read(t1);
			int t2 = Integer.parseInt(info[4]);
			Tipo tipo2 = ft.read(t2);
			boolean shiny = false;
			if (info[5].equals("true") || info[5].equals("True") || info[5].equals("TRUE"))
				shiny = true;
			String url = info[6];
			pokedex.put(num_pokedex, new Pokemon(id, num_pokedex, nombre, tipo1, tipo2, shiny, url));
		}
		br.close();
	}

	/**
	 * Rewrites the file with the data from the HashMap
	 */
	@Override
	public void guardar() throws Exception {
		BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
		ArrayList<Pokemon> aux = new ArrayList<>();
		pokedex.forEach((k, v) -> {
			aux.add(v);
		});

		for (Pokemon pkm : aux)
			bw.write(pkm.toFileManager() + "\n");

		bw.close();

	}

	/**
	 * If the pokemon does not exists it is added to the HashMap, and writes it on
	 * the file
	 * 
	 * @return Integer
	 */
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

	/**
	 * Returns the pokemon from it's NumPokedex
	 * 
	 * @return Pokemon
	 */
	@Override
	public Pokemon read(Integer id) throws Exception {
		return pokedex.get(id);
	}

	/**
	 * Adds the modified pokemon to the HashMap, and writes it on the file
	 * 
	 * @return Integer
	 */
	@Override
	public int update(Pokemon item, Integer id) throws Exception {
		pokedex.put(id, item);
		guardar();
		// En este caso siempre retornara 0
		return 0;
	}

	/**
	 * If the pokemon exists, is deleted from the HashMap, and its eliminated from
	 * the file
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
		return pokedex;
	}


}
