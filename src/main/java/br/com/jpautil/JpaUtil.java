package br.com.jpautil;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	private static EntityManagerFactory factory = null;
	
	static {
		
		if (factory == null) {
		factory = Persistence.createEntityManagerFactory("meuprimeiroprojetojsf");
		
		}
	}
	
	public static EntityManager getEntityManager() {
		
		return factory.createEntityManager();
	}
	
	public static Object getPrimaryKey(Object entity) {
		
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
		
	}
	
}
