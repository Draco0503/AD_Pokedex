package com.pkm.model.entity;

/**
 * 
 * @author ADPD
 *
 */

public class Pokemon {

	// Attributes
	private int id;
	private int num_pokedex;
	private String nombre;
	private Tipo tipo1;
	private Tipo tipo2;
	private boolean shiny;

	// Not useful in Console
	private String url;

	// Constructors
	public Pokemon(int id, int num_pokedex, String name, Tipo tipo1, Tipo tipo2, boolean shiny, String url) {
		this.id = id;
		this.num_pokedex = num_pokedex;
		this.nombre = name;
		this.tipo1 = tipo1;
		this.tipo2 = tipo2;
		this.shiny = shiny;
		this.url = url;
	}
	public Pokemon(int num_pokedex, String name, Tipo tipo1, Tipo tipo2, boolean shiny, String url) {
		id = 0;
		this.num_pokedex = num_pokedex;
		this.nombre = name;
		this.tipo1 = tipo1;
		this.tipo2 = tipo2;
		this.shiny = shiny;
		this.url = url;
	}

	public Pokemon() {

	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getNum_pokedex() {
		return num_pokedex;
	}

	public void setNum_pokedex(int num_pokedex) {
		this.num_pokedex = num_pokedex;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tipo getTipo1() {
		return tipo1;
	}

	public void setTipo1(Tipo tipo1) {
		this.tipo1 = tipo1;
	}

	public Tipo getTipo2() {
		return tipo2;
	}

	public void setTipo2(Tipo tipo2) {
		this.tipo2 = tipo2;
	}

	public boolean isShiny() {
		return shiny;
	}

	public void setShiny(boolean shiny) {
		this.shiny = shiny;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {

		return "*********************\nID: " + id + "\nUrl: " + url + "\nNºPokedex: " + num_pokedex + "\nNombre: " + nombre + "\nShiny: " + shiny
				+ "\nTipos: " + tipo1.getNombre() + " / " + tipo2.getNombre();
	}

	/**
	 * Format pokemon into file
	 */
	public String toFileManager() {
		return id + "|" + num_pokedex + "|" + nombre + "|" + tipo1.getId() + "|" + tipo2.getId() + "|" + shiny + "|" + url;
	}
}
