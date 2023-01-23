package com.pkm.model.manager;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.pkm.model.entity.Tipo;

public class JSONTipo extends JSONManager implements DAOManager<Tipo, Integer> {

	public JSONTipo(String server_path, String model_path) {
		super(server_path, model_path);
	}

	@Override
	public int create(Tipo item) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Tipo read(Integer id) throws Exception {
		
		Tipo nuevoTipo = null;
		
		String url = SERVER_PATH + MODEL_PATH + "?id=" + id;

		String response = encargadoPeticiones.getRequest(url);

		JSONObject respuesta = (JSONObject) JSONValue.parse(response);

		if (respuesta == null) { // si hay algun error de parseo (JSON incorrecto porque hay algun caracter raro,
									// etc.) la respuesta va a ser null
			System.out.println("El json recibido no es correcto. Finaliza la ejecucion");
			System.exit(-1);

		} else { // JSON recibido es correcto. Sera "ok" si todo ha ido bien o "error" si hay
					// algun problema
			String estado = (String) respuesta.get("estado");

			if (estado.equals("ok")) {// si ok, obtenemos array de tipo para recorrer y generar hashmap
				JSONArray array = (JSONArray) respuesta.get("tipos");

				if (array.size() > 0) {
					// Declaramos variables
					

					int idTipo;
					String nombreTipo;

					
					JSONObject row = (JSONObject) array.get(0);

					idTipo = Integer.parseInt(row.get("id").toString());
					nombreTipo = (row.get("nombre").toString());

					nuevoTipo = new Tipo(idTipo, nombreTipo);
					
					
					System.out.println("Acceso JSON Remoto - Leidos datos correctamente y generado hashmap");
					System.out.println();

				} else { // el array de pokemon esta vacio
					System.out.println("Acceso JSON Remoto - No hay datos que tratar");
					System.out.println();
				}

			} else { // hemos recibido el json, pero en el estado se nos indica que ha habido algun
						// error

				System.out.println("Ha ocurrido un error en la busqueda de datos");
				System.out.println("Error: " + (String) respuesta.get("error"));
				System.out.println("Consulta: " + (String) respuesta.get("query"));
				System.exit(-1);
			}
		}

		return nuevoTipo;
	}

	@Override
	public int update(Tipo item, Integer id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * pasar los datos de un JSON (tipo) a un HashMap
	 */
	@Override
	public HashMap<Integer, Tipo> readAll() throws Exception {
		HashMap<Integer, Tipo> aux = new HashMap<>();

		try {

			System.out.println("---------- Leemos datos de JSON --------------------");
			System.out.println("Lanzamos peticion JSON para depositos");
			String url = SERVER_PATH + MODEL_PATH; // sacadas de configuracion, vienen de la clase padre
			System.out.println("La url a la que lanzamos la peticion es " + url); // traza para pruebas

			String response = encargadoPeticiones.getRequest(url);
			System.out.println(response);
			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString()); // parseamos la respuesta y la
																						// convertimos en un JSONObject

			if (respuesta == null) { // si hay algun error de parseo (JSON incorrecto porque hay algun caracter raro,
										// etc.) la respuesta va a ser null
				System.out.println("El json recibido no es correcto. Finaliza la ejecucion");
				System.exit(-1);

			} else { // JSON recibido es correcto. Sera "ok" si todo ha ido bien o "error" si hay
						// algun problema
				String estado = (String) respuesta.get("estado");

				if (estado.equals("ok")) {// si ok, obtenemos array de tipo para recorrer y generar hashmap
					JSONArray array = (JSONArray) respuesta.get("tipos");

					if (array.size() > 0) {
						// Declaramos variables
						Tipo nuevoTipo;

						int idTipo;
						String nombreTipo;

						for (int i = 0; i < array.size(); i++) {
							JSONObject row = (JSONObject) array.get(i);

							idTipo = Integer.parseInt(row.get("id").toString());
							nombreTipo = (row.get("nombre").toString());

							nuevoTipo = new Tipo(idTipo, nombreTipo);
							aux.put(idTipo, nuevoTipo);
						}
						System.out.println("Acceso JSON Remoto - Leidos datos correctamente y generado hashmap");
						System.out.println();

					} else { // el array de pokemon esta vacio
						System.out.println("Acceso JSON Remoto - No hay datos que tratar");
						System.out.println();
					}

				} else { // hemos recibido el json, pero en el estado se nos indica que ha habido algun
							// error

					System.out.println("Ha ocurrido un error en la busqueda de datos");
					System.out.println("Error: " + (String) respuesta.get("error"));
					System.out.println("Consulta: " + (String) respuesta.get("query"));
					System.exit(-1);
				}
			}

		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en la busqueda de datos");
			e.printStackTrace();
			System.exit(-1);
		}
		return aux;
	}

	@Override
	public void guardar() throws Exception {
		// TODO Auto-generated method stub

	}

}
