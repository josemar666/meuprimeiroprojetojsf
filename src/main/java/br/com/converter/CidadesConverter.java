package br.com.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Cidades;
import br.com.jpautil.JpaUtil;

@FacesConverter(forClass = Cidades.class , value = "cidadeConverter")
public class CidadesConverter implements Converter, Serializable {


	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codigoCidade) {
		
	
		
EntityManager entityManager = JpaUtil.getEntityManager();
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		
		Cidades cidade = (Cidades) entityManager.find(Cidades.class, Long.parseLong(codigoCidade));
		
		return cidade; 
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object cidade) {
	
		if (cidade == null || (cidade.toString() != null && cidade.toString().isEmpty())){
			System.out.println("cidade conver vazio 2 ");
			return null;
		}
		
		if (cidade instanceof Cidades){
			System.out.println("cidade conver sdsd " + ((Cidades) cidade).getId().toString());
			return ((Cidades) cidade).getId().toString();
		}else {
			System.out.println("cidade conver sdsd to string " + cidade.toString());
			return cidade.toString();
		}
		
	}

}
