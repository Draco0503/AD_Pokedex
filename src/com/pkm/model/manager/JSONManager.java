package com.pkm.model.manager;

import java.util.HashMap;

import com.pkm.utils.ApiRequests;

public abstract class JSONManager {
	ApiRequests encargadoPeticiones;
	protected String SERVER_PATH; // Datos de la conexion, OJO PIOJO
	protected String MODEL_PATH;

	public JSONManager(String server_path, String model_path) {
		encargadoPeticiones = new ApiRequests();
		SERVER_PATH = server_path;
		MODEL_PATH = model_path;
	}
}
