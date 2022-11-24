package br.com.projetobasejsf.pesistence.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.projetobasejsf.fileservice.FileXMLService;
import br.com.projetobasejsf.pesistence.daointerface.DAO;
import br.com.projetobasejsf.pesistence.model.enums.Condition;

public class DAOImpl<T> implements DAO<T> {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;
	private final Class<T> classe;
	private static final FileXMLService hqlQuery;
	private static final FileXMLService sqlQuery;

	static {
		hqlQuery = new FileXMLService("hql.xml");
		sqlQuery = new FileXMLService("sql.xml");
	}

	public DAOImpl(Class<T> classe, EntityManager em) {
		this.classe = classe;
		this.em = em;
	}

	@Override
	public T save(T entity) {
		em.persist(entity);
		em.flush();
		return entity;
	}

	@Override
	public T update(T entity) {
		em.merge(entity);
		em.flush();
		return entity;
	}

	@Override
	public List<T> listAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("Select e from ").append(this.classe.getSimpleName()).append(" e");
		TypedQuery<T> query = em.createQuery(sql.toString(), classe);
		return query.getResultList();
	}

	@Override
	public List<T> findByHQLQuery(String queryId, int maxResults) {
		String hql = hqlQuery.findValue(queryId);
		TypedQuery<T> query = em.createQuery(hql, this.classe);
		return maxResults == 0 ? query.getResultList() : query.setMaxResults(maxResults).getResultList();

	}

	@Override
	public List<T> findByHQLQuery(String queryId, List<Object> values, int maxResults) {
		String hql = hqlQuery.findValue(queryId);
		Pattern pattern = Pattern.compile("(:\\w+)");
		Matcher matcher = pattern.matcher(hql);
		List<String> params = new ArrayList<>();

		while (matcher.find()) {
			params.add(matcher.group().replace(":", ""));
		}

		TypedQuery<T> query = em.createQuery(hql, this.classe);
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(params.get(i), values.get(i));
		}
		return maxResults == 0 ? query.getResultList() : query.setMaxResults(maxResults).getResultList();
	}

	@Override
	public List<T> findByAttributes(Map<String, Object> mapAttributeValue, List<Condition> conditions) {
		StringBuilder hql = new StringBuilder(80);
		hql.append("Select e from ").append(this.classe.getSimpleName()).append(" e");
		int i = 0;
		for (Map.Entry<String, Object> entry : mapAttributeValue.entrySet()) {
			if (i == 0) {
				// select e from Projeto e where e.nome LIKE :value0
				hql.append(" WHERE e.").append(entry.getKey()).append(" ").append(conditions.get(i).getCondition())
						.append(" :value").append(i);
			} else {
				// select e from Projeto e where e.nome LIKE :value0 and e.id = :value1
				hql.append(" AND e.").append(entry.getKey()).append(" ").append(conditions.get(i).getCondition())
						.append(" :value").append(i);
			}
			i++;
		}
		i = 0;
		TypedQuery<T> query = em.createQuery(hql.toString(), this.classe);
		for (Map.Entry<String, Object> entry : mapAttributeValue.entrySet()) {
			query.setParameter("value" + i, entry.getValue());
			i++;
		}
		return query.getResultList();
	}

	@Override
	public void remove(T entity) {
		em.remove(em.contains(entity) ? entity : em.merge(entity));
		em.flush();
	}

	@Override
	public T findById(Serializable id) {
		return em.find(this.classe, id);
	}

}
