package com.pkm.model.manager;

import java.util.HashMap;

/**
 * 
 * @author ADPD
 *
 * @param <T> is a generic type (it is used to be the model type, pokemon in
 *            this case)
 * @param <K> is the key that is going to be used for finding in the system
 */
public interface DAOManager<T, K> {

	int create(T item) throws Exception;

	T read(K id) throws Exception;

	int update(T item, K id) throws Exception;

	int delete(K id) throws Exception;

	HashMap<K, T> readAll() throws Exception;

	/*
	 * NOT USED HashMap<K, T> readBy(String query) throws Exception;
	 */
	void guardar() throws Exception;
}
