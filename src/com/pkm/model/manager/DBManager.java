package com.pkm.model.manager;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {

	protected String usuario;
	protected String pwd;
	protected String driver;
	protected String hostname;
	protected String db;
	protected String puerto;
	protected Connection con;
	
	public DBManager(String usuario, String pwd, String driver, String hostname, String db, String puerto)
			throws Exception {
		this.usuario = usuario;
		this.pwd = pwd;
		this.driver = driver;
		this.hostname = hostname;
		this.db = db;
		this.puerto = puerto;
		this.con = abrirConexion();
	}
	/*
	 * connecting with the database
	 */
	protected Connection abrirConexion() throws Exception {
		Class.forName(driver);
		String url = "jdbc:mysql://" + hostname + ":" + puerto + "/" + db + "?useSSL=false";
		con = DriverManager.getConnection(url, usuario, pwd);
		if (con != null) {
			return con;
		} else {
			throw new Exception("Conexion Fallida");
		}
	}
	/*
	 * connection is closed
	 */
	protected void cerrarConexion() throws Exception {
		con.close();
	}
}
