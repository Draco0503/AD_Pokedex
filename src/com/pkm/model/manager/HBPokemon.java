package com.pkm.model.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.TypedQuery;

import com.pkm.model.entity.Pokemon;

public class HBPokemon extends HBManager implements DAOManager<Pokemon, Integer>{

	private HashMap<Integer, Pokemon> hmPokemon;
	
	public HBPokemon() {
		super();
		hmPokemon = new HashMap<>();
		cargarDatos();
	}
	
	public HBPokemon(HashMap<Integer, Pokemon> exportacion) throws Exception {
		super();
		hmPokemon = new HashMap<>();
		cargarDatos();
		export(exportacion);
	}
	
	private void export(HashMap<Integer, Pokemon> exportacion) throws Exception {
		for (Entry<Integer, Pokemon> pkm : exportacion.entrySet()) {
			Pokemon aux = read(pkm.getKey());
			if (aux == null) {
				create(pkm.getValue());
			} else {
				update(pkm.getValue(), pkm.getKey());
			}
		}
	}
	
	@Override
	public void cargarDatos() {
		Pokemon pkm = null;
		TypedQuery<Pokemon> q = session.createQuery("SELECT pkms FROM Pokemon pkms");
		List<Pokemon> results = q.getResultList();

		Iterator<Pokemon> pkmIterator = results.iterator();
		while (pkmIterator.hasNext()) {
			pkm = pkmIterator.next();
			hmPokemon.put(pkm.getNum_pokedex(), pkm);
		}
	}
	
	@Override
	public int create(Pokemon item) throws Exception {
		try {
			hmPokemon.put(item.getNum_pokedex(), item);
			
			session.beginTransaction();
			session.save(item);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Pokemon read(Integer id) throws Exception {
		Pokemon pkm = null;
		pkm = hmPokemon.get(id);
		
		return pkm;
	}

	@Override
	public int update(Pokemon item, Integer id) throws Exception {
		hmPokemon.put(id, item);

		session.beginTransaction();
		session.update(item);
		session.getTransaction().commit();

		return 0;
	}

	@Override
	public int delete(Integer id) throws Exception {
		Pokemon pkm = hmPokemon.get(id);

		hmPokemon.remove(id);

		session.beginTransaction();
		session.delete(pkm);
		session.getTransaction().commit();

		return 0;
	}

	@Override
	public HashMap<Integer, Pokemon> readAll() throws Exception {
		return hmPokemon;
	}

	@Override
	public void guardar() throws Exception {
		session.close();
	}



}
