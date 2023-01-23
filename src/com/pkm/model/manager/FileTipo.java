package com.pkm.model.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map.Entry;

import com.pkm.model.entity.Tipo;

public class FileTipo extends FileManager implements DAOManager<Tipo, Integer>  {

	
	private HashMap<Integer, Tipo> tipos;
	
	
	public FileTipo(String archivo) throws Exception {
		super(archivo);
		tipos = new HashMap<>();
		cargarDatos();
	}
	
	@Override
	protected void cargarDatos() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(archivo));

		String linea = "";
		while ((linea = br.readLine()) != null) {
			String[] info = linea.split("[|]"); // "|" es nuestro separador
			int id = Integer.parseInt(info[0]);
			String nombre = info[1];
			tipos.put(id, new Tipo(id, nombre));
		}

		br.close();
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
