package br.com.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Estados;
import br.com.entidades.Pessoa;
import br.com.jpautil.JpaUtil;

@Named
public class IDaoPessoaImple implements IDaoPessoa ,Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager entityManager;
	
	
	

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Pessoa consultarUsuario(String login, String senha) {
		
		Pessoa pessoa = null;
		
		EntityManager entityManager = JpaUtil.getEntityManager();

		EntityTransaction entityTransaction = entityManager.getTransaction();

		entityTransaction.begin();
		
		pessoa = (Pessoa) entityManager.createQuery("select p from Pessoa p where p.login ='" + login + "' and p.senha = '" + senha + "'" ).getSingleResult();
		
		
		entityTransaction.commit();

		entityManager.close();
		
		
		
		return pessoa;
	}

	@Override
	public List<SelectItem> listaEstados() {

	    EntityManager entityManager = JpaUtil.getEntityManager();
	    EntityTransaction entityTransaction = entityManager.getTransaction();

	    List<SelectItem> selectItems = new ArrayList<SelectItem>();

	    try {
	        entityTransaction.begin();

	        List<Estados> estados = entityManager.createQuery("from Estados", Estados.class).getResultList();

	        for (Estados estado : estados) {
	            selectItems.add(new SelectItem(estado, estado.getNome()));
	        }

	        entityTransaction.commit();

	    } catch (Exception e) {
	        if (entityTransaction.isActive()) {
	            entityTransaction.rollback();
	        }
	        e.printStackTrace();

	    } finally {
	        entityManager.close();
	    }

	    return selectItems;
	}

}
