package com.quest.etna.service;

import java.util.List;

public interface IModelService<T> {
	
	public List<T> getAll();
	public T getById(Integer id);
	public T create(T entity);
	public T update (Integer id, T entity);
	public Boolean delete(Integer id);

}
