package com.pkm.model.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.TypedQuery;

import com.pkm.model.entity.Tipo;

public class HBTipo extends HBManager implements DAOManager<Tipo, Integer>{

	private HashMap<Integer, Tipo> hmTipo;
	
	public HBTipo() {
		super();
		hmTipo = new HashMap<>();
		cargarDatos();
	}
	
	@Override
	public void cargarDatos() {
		Tipo t = null;
		TypedQuery<Tipo> q = session.createQuery("SELECT t FROM Tipo t");
		List<Tipo> results = q.getResultList();

		Iterator<Tipo> tIterator = results.iterator();
		while (tIterator.hasNext()) {
			t = tIterator.next();
			hmTipo.put(t.getId(), t);
		}
	}
	
	@Override
	public int create(Tipo item) throws Exception {
		return 0;
	}

	@Override
	public Tipo read(Integer id) throws Exception {
		return hmTipo.get(id);
	}

	@Override
	public int update(Tipo item, Integer id) throws Exception {
		return 0;
	}

	@Override
	public int delete(Integer id) throws Exception {
		return 0;
	}

	@Override
	public HashMap<Integer, Tipo> readAll() throws Exception {
		return hmTipo;
	}

	@Override
	public void guardar() throws Exception {
		session.close();
	}


}
