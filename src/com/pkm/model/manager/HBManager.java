package com.pkm.model.manager;


import org.hibernate.Session;

import com.pkm.utils.HibernateUtil;


public abstract class HBManager {
	
	protected Session session;

	public HBManager() {
		HibernateUtil util = new HibernateUtil();
		session = util.getSession();
		
	}
	
	public abstract void cargarDatos();
	
	
	
	/*
	public HBManager() {

		HibernateUtil util = new HibernateUtil();
		session = util.getSession();
		hmpokemon = new HashMap<>();
		cargarDatos();
	}
	
	public HBManager(HashMap<Integer, Pokemon> exportacion) throws Exception {

		HibernateUtil util = new HibernateUtil();
		session = util.getSession();
		hmpokemon = new HashMap<>();
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

	public void cargarDatos() {
		
		Pokemon pkm = null;
		TypedQuery<Pokemon> q = session.createQuery("SELECT pkms FROM Pokemon pkms");
		List<Pokemon> results = q.getResultList();

		Iterator<Pokemon> pkmIterator = results.iterator();
		while (pkmIterator.hasNext()) {
			pkm = pkmIterator.next();
			hmpokemon.put(pkm.getId(), pkm);
		}
	}
	
	@Override
	public int create(Pokemon item) throws Exception {

		hmpokemon.put(item.getId(), item);
		
		session.beginTransaction();
		session.save(item);
		session.getTransaction().commit();

		return 0;
	}

	@Override
	public Pokemon read(Integer id) throws Exception {

		Pokemon pkm = null;
		pkm = hmpokemon.get(id);
		
		return pkm;
	}

	@Override
	public int update(Pokemon item, Integer id) throws Exception {

		hmpokemon.put(id, item);

		session.beginTransaction();
		session.update(id);
		session.getTransaction().commit();

		return 0;
	}

	@Override
	public int delete(Integer id) throws Exception {

		Pokemon pkm = hmpokemon.get(id);

		hmpokemon.remove(id);

		session.beginTransaction();
		session.delete(pkm);
		session.getTransaction().commit();

		return 0;
	}

	@Override
	public HashMap<Integer, Pokemon> readAll() throws Exception {
		
		return hmpokemon;
	}
	
	public HashMap<Integer, Pokemon> readBy(String query) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardar() throws Exception {
		session.close();

	}
*/
}
