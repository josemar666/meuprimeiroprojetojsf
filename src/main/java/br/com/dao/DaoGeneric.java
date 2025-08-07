package br.com.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.jpautil.JpaUtil;

public class DaoGeneric<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	public void salvar(T entidade) {

		EntityManager entityManager = JpaUtil.getEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		entityManager.persist(entidade);

		entityTransaction.commit();

		entityManager.close();

	}

	public T merge(T entidade) {

		EntityManager entityManager = JpaUtil.getEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		T retorno = entityManager.merge(entidade);

		entityTransaction.commit();

		entityManager.close();

		return retorno;

	}

	public void deletebyId(T entidade) {

		EntityManager entityManager = JpaUtil.getEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();

		Object id = JpaUtil.getPrimaryKey(entidade);

		entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id = " + id)
				.executeUpdate();

		entityTransaction.commit();

		entityManager.close();

	}

	public List<T> getListEntity(Class<T> entidade) {

		EntityManager entityManager = JpaUtil.getEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();
		
		List<T> retorno = entityManager.createQuery(" from " + entidade.getName()).getResultList();
		
		entityTransaction.commit();

		entityManager.close();

        return retorno;
	}

}
