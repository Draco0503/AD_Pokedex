package com.pkm.model.manager;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.pkm.model.entity.Pokemon;
import com.pkm.model.entity.Tipo;
import com.pkm.utils.ApiRequests;

public class JSONPokemon extends JSONManager implements DAOManager<Pokemon, Integer> {

	private JSONTipo jt;

	public JSONPokemon(String server_path, String model_path, DAOManager<Tipo, Integer> jt) {
		super(server_path, model_path);
		this.jt = (JSONTipo) jt;
	}
	public JSONPokemon(String server_path, String model_path, HashMap<Integer, Pokemon> exportacion) throws Exception {
		super(server_path, model_path);
		guardarDatosImportados(exportacion);
	}

	private void guardarDatosImportados(HashMap<Integer, Pokemon> exportacion) throws Exception {
		for(Map.Entry<Integer, Pokemon> pkm : exportacion.entrySet()) {
			try {
				create(pkm.getValue());
			} catch (Exception e) {
				// update(pkm.getValue(), pkm.getKey());
				// DO NOTHING
			}
		}
	}
	
	@Override
	public int create(Pokemon item) throws Exception {

		int status = -1;

		String url = SERVER_PATH + MODEL_PATH; // sacadas de configuracion, vienen de la clase padre

		JSONObject json = new JSONObject();

		json.put("num_pokedex", item.getNum_pokedex());
		json.put("nombre", item.getNombre());
		json.put("tipo1", item.getTipo1().getId());
		json.put("tipo2", item.getTipo2().getId());
		json.put("shiny", item.isShiny());
		json.put("url", item.getUrl());

		String response = encargadoPeticiones.postRequest(url, json.toJSONString());
		JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString()); // parseamos la respuesta y la
																					// convertimos en un JSONObject

		if (respuesta == null) { // si hay algun error de parseo (JSON incorrecto porque hay algun caracter raro,
									// etc.) la respuesta va a ser null
			throw new Exception("El json recibido no es correcto. Finaliza la ejecucion");
		} else { // JSON recibido es correcto. Sera "ok" si todo ha ido bien o "error" si hay
					// algun problema
			String estado = (String) respuesta.get("estado");
			if (estado.equals("ok")) {// si ok, obtenemos array de pokemon para recorrer y generar hashmap
				status = 0;
			} else { // el array de pokemon esta vacio
				throw new Exception("Acceso JSON Remoto - No hay datos que tratar");
			}
		}
		return status;
	}

	@Override
	public Pokemon read(Integer id) throws Exception {
		Pokemon nuevoPokemon = null;

		String url = SERVER_PATH + MODEL_PATH + "?num_pokedex=" + id;

		String response = encargadoPeticiones.getRequest(url);

		JSONObject respuesta = (JSONObject) JSONValue.parse(response);

		if (respuesta == null) { // si hay algun error de parseo (JSON incorrecto porque hay algun caracter raro,
									// etc.) la respuesta va a ser null
			throw new Exception("El json recibido no es correcto. Finaliza la ejecucion");

		} else { // JSON recibido es correcto. Sera "ok" si todo ha ido bien o "error" si hay
					// algun problema
			String estado = (String) respuesta.get("estado");

			if (estado.equals("ok")) {// si ok, obtenemos array de tipo para recorrer y generar hashmap
				JSONArray array = (JSONArray) respuesta.get("pokedex");

				if (array.size() > 0) {
					// Declaramos variables

					int numPokedex;
					String nombre;
					Tipo tipo1;
					Tipo tipo2;
					boolean shiny;
					String urlPokemon;

					JSONObject row = (JSONObject) array.get(0);

					numPokedex = Integer.parseInt(row.get("num_pokedex").toString());
					nombre = (row.get("nombre").toString());
					int t1 = Integer.parseInt(row.get("tipo1").toString());
					int t2 = Integer.parseInt(row.get("tipo2").toString());
					tipo1 = jt.read(t1);
					tipo2 = jt.read(t2);
					shiny = Boolean.parseBoolean(row.get("shiny").toString());
					urlPokemon = (row.get("url").toString());

					nuevoPokemon = new Pokemon(numPokedex, nombre, tipo1, tipo2, shiny, urlPokemon);

				} else { // el array de pokemon esta vacio
					throw new Exception("Acceso JSON Remoto - No hay datos que tratar");
				}

			} else { // hemos recibido el json, pero en el estado se nos indica que ha habido algun
						// error

				throw new Exception("Ha ocurrido un error en la busqueda de datos" + 
									"\nError: " + (String) respuesta.get("error"));
			}
		}
		return nuevoPokemon;
	}

	@Override
	public int update(Pokemon item, Integer id) throws Exception {

		int status = -1;

		String url = SERVER_PATH + MODEL_PATH; // sacadas de configuracion, vienen de la clase padre

		JSONObject json = new JSONObject();

		json.put("num_pokedex", item.getNum_pokedex());
		json.put("nombre", item.getNombre());
		json.put("tipo1", item.getTipo1().getId());
		json.put("tipo2", item.getTipo2().getId());
		json.put("shiny", item.isShiny());
		json.put("url", item.getUrl());

		String response = encargadoPeticiones.putRequest(url, json.toJSONString());
		System.out.println(response);
		JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString()); // parseamos la respuesta y la
																					// convertimos en un JSONObject

		if (respuesta == null) { // si hay algun error de parseo (JSON incorrecto porque hay algun caracter raro,
									// etc.) la respuesta va a ser null
			throw new Exception("El json recibido no es correcto. Finaliza la ejecucion");

		} else { // JSON recibido es correcto. Sera "ok" si todo ha ido bien o "error" si hay
					// algun problema
			String estado = (String) respuesta.get("estado");
			if (estado.equals("ok")) {// si ok, obtenemos array de pokemon para recorrer y generar hashmap
				status = 0;
			} else { // el array de pokemon esta vacio
				throw new Exception("Acceso JSON Remoto - No hay datos que tratar");
			}
		}
		return status;
	}

	@Override
	public int delete(Integer id) throws Exception {

		int status = -1;

		System.out.println("---------- Leemos datos de JSON --------------------");
		System.out.println("Lanzamos peticion JSON para depositos");
		String url = SERVER_PATH + MODEL_PATH; // sacadas de configuracion, vienen de la clase padre
		System.out.println("La url a la que lanzamos la peticion es " + url); // traza para pruebas

		JSONObject json = new JSONObject();

		json.put("num_pokedex", id);

		String response = encargadoPeticiones.deleteRequest(url, json.toJSONString());
		System.out.println(response);
		JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString()); // parseamos la respuesta y la
																					// convertimos en un JSONObject

		if (respuesta == null) { // si hay algun error de parseo (JSON incorrecto porque hay algun caracter raro,
									// etc.) la respuesta va a ser null
			throw new Exception("El json recibido no es correcto. Finaliza la ejecucion");

		} else { // JSON recibido es correcto. Sera "ok" si todo ha ido bien o "error" si hay
					// algun problema
			String estado = (String) respuesta.get("estado");
			if (estado.equals("ok")) {// si ok, obtenemos array de pokemon para recorrer y generar hashmap
				status = 0;
			} else { // el array de pokemon esta vacio
				throw new Exception("Acceso JSON Remoto - No hay datos que tratar");
			}
		}
		return status;
	}

	/**
	 * pasamos los datos (pokemon) de un JSON a un HashMap para recorrerlo y leerlo
	 */
	@Override
	public HashMap<Integer, Pokemon> readAll() throws Exception {
		HashMap<Integer, Pokemon> aux = new HashMap<>();
		
		String url = SERVER_PATH + MODEL_PATH; // sacadas de configuracion, vienen de la clase padre

		String response = encargadoPeticiones.getRequest(url);
		JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString()); // parseamos la respuesta y la
																					// convertimos en un JSONObject

		if (respuesta == null) { // si hay algun error de parseo (JSON incorrecto porque hay algun caracter raro,
									// etc.) la respuesta va a ser null
			throw new Exception("El json recibido no es correcto. Finaliza la ejecucion");

		} else { // JSON recibido es correcto. Sera "ok" si todo ha ido bien o "error" si hay
					// algun problema
			String estado = (String) respuesta.get("estado");

			if (estado.equals("ok")) {// si ok, obtenemos array de pokemon para recorrer y generar hashmap
				JSONArray array = (JSONArray) respuesta.get("pokedex");

				if (array.size() > 0) {
					// Declaramos variables
					Pokemon nuevoPokemon;

					int numPokedex;
					String nombre;
					Tipo tipo1;
					Tipo tipo2;
					boolean shiny;
					String urlPokemon;

					for (int i = 0; i < array.size(); i++) {
						JSONObject row = (JSONObject) array.get(i);

						numPokedex = Integer.parseInt(row.get("num_pokedex").toString());
						nombre = (row.get("nombre").toString());
						int t1 = Integer.parseInt(row.get("tipo1").toString());
						int t2 = Integer.parseInt(row.get("tipo2").toString());
						tipo1 = jt.read(t1);
						tipo2 = jt.read(t2);
						if (row.get("shiny").toString().equals("1")) {
							shiny = true;
						} else {
							shiny = false;
						}
						urlPokemon = (row.get("url").toString());

						nuevoPokemon = new Pokemon(numPokedex, nombre, tipo1, tipo2, shiny, urlPokemon);
						aux.put(numPokedex, nuevoPokemon);
					}
				} else { // el array de pokemon esta vacio
					throw new Exception("Acceso JSON Remoto - No hay datos que tratar");
				}

			} else { // hemos recibido el json, pero en el estado se nos indica que ha habido algun
						// error

				throw new Exception("Ha ocurrido un error en la busqueda de datos" + 
						"\nError: " + (String) respuesta.get("error"));
			}
		}

		return aux;
	}

	@Override
	public void guardar() throws Exception {
		// NOT NEEDED
	}

}
