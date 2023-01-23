package com.pkm.model.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import com.pkm.model.entity.Tipo;

public class DBTipo extends DBManager implements DAOManager<Tipo, Integer> {

	
	public DBTipo(String usuario, String pwd, String driver, String hostname, String db, String puerto)
			throws Exception {
		super(usuario, pwd, driver, hostname, db, puerto);
	}

	@Override
	public int create(Tipo item) throws Exception {
		// NO NEED TO BE IMPLEMENTED
		return 0;
	}

	@Override
	public Tipo read(Integer id) throws Exception {
		Tipo t = null;
		String sql = "SELECT * FROM tipo WHERE id=?";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			String nombre = rs.getString("nombre");
			t = new Tipo(id, nombre);
		}
		return t;
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
		HashMap<Integer, Tipo> tipos = new HashMap<>();
		String query = "SELECT * FROM pokemon";
		PreparedStatement ps = con.prepareStatement(query);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("id");
			String nombre = rs.getString("nombre");
			Tipo tipo = new Tipo(id, nombre);
			tipos.put(id, tipo);
		}

		return tipos;
	}

	@Override
	public void guardar() throws Exception {
		// NO NEED TO BE IMPLEMENTED
		return;
	}

}
