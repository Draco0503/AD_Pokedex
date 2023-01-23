package com.pkm.model.manager;


public abstract class FileManager {
	
	protected String archivo;

	public FileManager() {}
	
	public FileManager(String archivo) {
		this.archivo = archivo;
	}
	
	protected abstract void cargarDatos() throws Exception;
	
}
