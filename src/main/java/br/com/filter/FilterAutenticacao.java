package br.com.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.entidades.Pessoa;
import br.com.jpautil.JpaUtil;

@WebFilter(urlPatterns = {"/*"})//anotação que indica que todas as paginas podem ser acessadas por meio dessa classe
public class FilterAutenticacao implements Filter , Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// método que é executado quando aplicação sob ao servidor
		
		   JpaUtil.getEntityManager();
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//método que é executado em todas as requisições
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
		
		String url = req.getServletPath();
		
		if (!url.equalsIgnoreCase("index.jsf") && usuarioLogado == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsf");
			dispatcher.forward(request, response);
			
			return;			
			
		}else {
			
			chain.doFilter(request, response);//essa variaveis que são criadas no parametro do método devem ser deixadas por ultimo no método.
		}
		
		
		
		
	
		
		
		
	}

	@Override
	public void destroy() {
		// não é usado
		
	}

}
