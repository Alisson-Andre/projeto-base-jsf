package br.com.projetobasejsf.pesistence.daointerface;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.com.projetobasejsf.pesistence.model.enums.Condition;

public interface DAO<T> extends Serializable {

	T save(T entity);

	T update(T entity);

	List<T> listAll();

	List<T> findByHQLQuery(String queryID, List<Object> params, int maxResults);

	List<T> findByHQLQuery(String queryId, int maxResults);
	
	void remove(T entity);
	
	T findById(Serializable id);

	List<T> findByAttributes(Map<String, Object> mapAttributeValue, List<Condition> conditions);
}
