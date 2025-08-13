package br.com.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Estados;
import br.com.jpautil.JpaUtil;


@FacesConverter(forClass = Estados.class, value = "estadoConverter")
public class EstadoConverter implements Converter, Serializable {
	


	private static final long serialVersionUID = 1L;

	@Override/* retornar um objeto inteiro*/
	public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {
		
	
		
		
		
		EntityManager entityManager = JpaUtil.getEntityManager();
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		
		entityTransaction.begin();
		
		Estados estados = (Estados) entityManager.find(Estados.class, Long.parseLong(codigoEstado));
		
		return estados;
	}

	@Override /* retornar o codigo em String*/
	public String getAsString(FacesContext context, UIComponent component, Object estado) {
		
		if (estado == null) {
			System.out.println("estado null");
			return null;
		}

		if (estado instanceof Estados) {
			System.out.println("--- 555" + ((Estados) estado).getId().toString());
			return ((Estados) estado).getId().toString();

		} else {
			System.out.println("--- dd" +estado.toString());
			return estado.toString();
		}
	}


}
