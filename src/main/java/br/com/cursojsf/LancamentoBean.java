package br.com.cursojsf;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.dao.DaoGeneric;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoLancamento;
import br.com.repository.IDaoLancamentoImpl;

@ViewScoped
@ManagedBean(name = "lancamentoBean")
public class LancamentoBean {

	private Lancamento lancamento = new Lancamento();
	
	private DaoGeneric<Lancamento> daoGenerico = new DaoGeneric<Lancamento>();
	
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	
	private IDaoLancamento daoLancamento = new IDaoLancamentoImpl() ;
	
	
	
	
	

	public String Salvar() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		
		Pessoa pessoaUse =  (Pessoa)  externalContext.getSessionMap().get("usuarioLogado");
		
		lancamento.setUsuario(pessoaUse);
		
		lancamento = daoGenerico.merge(lancamento);
		
		carregarLancamentos();
		
		
		return "";
	}
	
	@PostConstruct
	private void carregarLancamentos() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		
		Pessoa pessoaUse =  (Pessoa)  externalContext.getSessionMap().get("usuarioLogado");
		
		lancamentos = daoLancamento.consultar(pessoaUse.getId()); 
		
		
		
	}

	public IDaoLancamento getDaoLancamento() {
		return daoLancamento;
	}

	public void setDaoLancamento(IDaoLancamento daoLancamento) {
		this.daoLancamento = daoLancamento;
	}

	public String novo() {
		
		lancamento = new Lancamento();
		
		
		
		return"";
	}
	
	public String remove() {
		
		daoGenerico.deletebyId(lancamento);
		
		lancamento = new Lancamento();
		
		carregarLancamentos();
		
		return "";
	}
	
	public String limpar() {
		
		lancamento = new Lancamento();
		
		return "";
	}
	
	

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public DaoGeneric<Lancamento> getDaoGenerico() {
		return daoGenerico;
	}

	public void setDaoGenerico(DaoGeneric<Lancamento> daoGenerico) {
		this.daoGenerico = daoGenerico;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	
	
	
	
}
