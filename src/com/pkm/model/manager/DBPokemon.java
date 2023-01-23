package com.pkm.model.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map.Entry;

import com.pkm.model.entity.Pokemon;
import com.pkm.model.entity.Tipo;

/**
 * 
 * @author ADPD
 *
 */
public class DBPokemon extends DBManager implements DAOManager<Pokemon, Integer> {

	private DBTipo dt;
	
	public DBPokemon(String usuario, String pwd, String driver, String hostname, String db, String puerto, DAOManager<Tipo, Integer> daoManagerc2)
			throws Exception {
		super(usuario, pwd, driver, hostname, db, puerto);
		dt = (DBTipo) daoManagerc2;
	}
	public DBPokemon(String usuario, String pwd, String driver, String hostname, String db, String puerto)
			throws Exception {
		super(usuario, pwd, driver, hostname, db, puerto);
	}
	public DBPokemon(String usuario, String pwd, String driver, String hostname, String db, String puerto, HashMap<Integer, Pokemon> exportacion)
			throws Exception {
		super(usuario, pwd, driver, hostname, db, puerto);
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
	
	/**
	 * creating a new pokemon that doesn't exists
	 */
	@Override
	public int create(Pokemon item) throws Exception {
		
		String query = "INSERT INTO pokemon(num_pokedex, nombre, tipo1, tipo2, shiny, url) VALUES(?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(query);

		ps.setInt(1, item.getNum_pokedex());
		ps.setString(2, item.getNombre());
		ps.setInt(3, item.getTipo1().getId());
		ps.setInt(4, item.getTipo2().getId());
		ps.setBoolean(5, item.isShiny());
		ps.setString(6, item.getUrl());

		return comprobarResult(ps.executeUpdate());
	}

	/**
	 * returns all the records from the database
	 */
	@Override
	public HashMap<Integer, Pokemon> readAll() throws Exception {

		HashMap<Integer, Pokemon> pokedex = new HashMap<>();
		String query = "SELECT * FROM pokemon";
		PreparedStatement ps = con.prepareStatement(query);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("id");
			int num_pokedex = rs.getInt("num_pokedex");
			String nombre = rs.getString("nombre");
			int t1 = rs.getInt("tipo1");
			int t2 = rs.getInt("tipo2");
			Tipo tipo1 = dt.read(t1);
			Tipo tipo2 = dt.read(t2);
			boolean shiny = rs.getBoolean("shiny");
			String url = rs.getString("url");
			Pokemon pkm = new Pokemon(id, num_pokedex, nombre, tipo1, tipo2, shiny, url);
			pokedex.put(id, pkm);
		}

		return pokedex;
	}
	
	/**
	 * return an specific pokemon from the database
	 */
	@Override
	public Pokemon read(Integer id) throws Exception {

		Pokemon pkm = null;
		String query = "SELECT * FROM pokemon WHERE num_pokedex=?";
		PreparedStatement ps = con.prepareStatement(query);

		ps.setInt(1, id);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			int id2 = rs.getInt("id");
			int num_pokedex = rs.getInt("num_pokedex");
			String nombre = rs.getString("nombre");
			int t1 = rs.getInt("tipo1");
			int t2 = rs.getInt("tipo2");
			Tipo tipo1 = dt.read(t1);
			Tipo tipo2 = dt.read(t2);
			boolean shiny = rs.getBoolean("shiny");
			String url = rs.getString("url");
			pkm = new Pokemon(id2, num_pokedex, nombre, tipo1, tipo2, shiny, url);
		}

		return pkm;
	}

	/**
	 * Changes the parameters from an specific pokemon
	 */
	@Override
	public int update(Pokemon item, Integer id) throws Exception {
		
		String query = "UPDATE pokemon SET nombre = ?, tipo1 = ?, tipo2 = ?, shiny=?, url=? WHERE num_pokedex = ?";
		PreparedStatement ps = con.prepareStatement(query);

		ps.setString(1, item.getNombre());
		ps.setInt(2, item.getTipo1().getId());
		ps.setInt(3, item.getTipo2().getId());
		ps.setBoolean(4, item.isShiny());
		ps.setString(5, item.getUrl());
		ps.setInt(6, item.getNum_pokedex());

		return comprobarResult(ps.executeUpdate());
	}

	/**
	 * Deletes an specific pokemon from the database
	 */
	@Override
	public int delete(Integer id) throws Exception {

		String query = "DELETE FROM pokemon WHERE id=?";
		PreparedStatement ps = con.prepareStatement(query);

		ps.setInt(1, id);

		return comprobarResult(ps.executeUpdate());
	}	
	
	public int comprobarResult(int comprobar) {

		if (comprobar < 0) {
			comprobar = -1;
		} else {
			comprobar = 0;
		}
		return comprobar;
	}
	/**
	 * save the progress
	 */
	@Override
	public void guardar() throws Exception {

		cerrarConexion();

	}
	

	
	
	
	/**
	 * checks if the pokemon exists
	 * @param comprobar
	 * @return integer
	 */

}
