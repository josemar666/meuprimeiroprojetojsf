package br.com.cursojsf;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;

import br.com.dao.DaoGeneric;
import br.com.entidades.Cidades;
import br.com.entidades.Estados;
import br.com.entidades.Pessoa;
import br.com.jpautil.JpaUtil;
import br.com.repository.IDaoPessoa;
import br.com.repository.IDaoPessoaImple;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean implements Serializable {

      private static final long serialVersionUID = 1L;

	private Pessoa pessoa = new Pessoa ();
      
      private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
      
      private List<Pessoa> pessoas = new ArrayList<Pessoa>();
      
      private IDaoPessoa idaoPessoa = new IDaoPessoaImple();
      
      private List<SelectItem> estados;
      
      private List<SelectItem> cidades; 
      
      private Part arquivoFoto;//classe usada para carregar a foto - upload
      
      
      @Inject
  	private JpaUtil jpaUtil;
      
      
      public String salvar() throws IOException{
  		
  		/*
  		 //Processsar imagem
  			 byte[] imagemByte = getByte(arquivoFoto.getInputStream());
  			pessoa.setFotoIconBase640Original(imagemByte); //Salva imagem original
  			 //transformar em bufferimage para manipular a imagem nas classes
  			 BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));
  			 
  			
  			 
  		      
  			 //Pega o tipo da imagem 
  		   int type = bufferedImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
  			 
  		     int largura = 200;
  			 int altura = 200;
  			 
  			 //Criar a miniatura
  			 BufferedImage resizedImage = new BufferedImage(altura, altura, type);
  			 Graphics2D g = resizedImage.createGraphics();
  		     g.drawImage(bufferedImage, 0, 0, largura, altura, null);
  		     g.dispose();
  			  
  			  //Escrever novamente a imagem em tamanho menor
  			  ByteArrayOutputStream baos = new ByteArrayOutputStream();
  			  String extensao = arquivoFoto.getContentType().split("\\/")[1]; //image/png
  			  ImageIO.write(resizedImage, extensao, baos);
  			  
  			  String miniImagem = "data:" + arquivoFoto.getContentType() + ";base64," +
  			                       DatatypeConverter.printBase64Binary(baos.toByteArray());
  			 
  			//Processsar imagem
  	    	 pessoa.setFotoIconBase64(miniImagem);
  			 pessoa.setExtensao(extensao); 
  		      */
  			 
  			pessoa =  daoGeneric.merge(pessoa);
      	  
  	    	carregarPessoa();
  	    	mostrarMsg("cadastrado com sucesso !");
  	    	
  	    	  return "";
  		 
  		}
      
      
      
      
      
      
   

      private void mostrarMsg(String msg) {
    	  
    	  FacesContext context = FacesContext.getCurrentInstance();
    	  FacesMessage message = new FacesMessage(msg);
    	  context.addMessage(null, message);
		
		
	}

	public String novo() {
    	  pessoa = new Pessoa();
    	  
    	  return "";
    	  
    	  
      }
	
	public String limpar() {
	
  pessoa = new Pessoa();
  

    	  
    	  return "";
		
		
	}
      
      public String remove() {
    	  
    	     	  
    	  daoGeneric.deletebyId(pessoa);
    	  
    	  pessoa = new Pessoa ();
    	  
    	  carregarPessoa();
    	  
    	  mostrarMsg("removido com sucesso");
    	  
    	  return "";
      }
      
      
      public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	@PostConstruct //com essa notação sempre que for instaciado na memória será carregada a lista
      public void carregarPessoa() {
    	  
    	  pessoas =daoGeneric.getListEntity(Pessoa.class);
      }
      
   
      
      
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
			
	

	public JpaUtil getJpaUtil() {
		return jpaUtil;
	}

	public void setJpaUtil(JpaUtil jpaUtil) {
		this.jpaUtil = jpaUtil;
	}

	

	public void pesquisaCep(AjaxBehaviorEvent event) {
		
	  try {
		  
		  URL url = new URL("https://viacep.com.br/ws/" + pessoa.getCep()+"/json/");//monta a url pra consumir o web service
		  
		  URLConnection connection = url.openConnection(); //abre a conexão para url
		  
		  InputStream is = connection.getInputStream(); // buscar o retorno dos dados da url especificada
		  
		  BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8")); //armazena os dados do retorno e UTF-8 pra não dá problemas com acento nos dados
		  
		  String cep = "";// variavel que vai receber o buffer de dados
		  
		  StringBuilder jsonCep = new StringBuilder();//vai ser usado no método para ler a string na variavel cep
		  
		  // usa o while para varrer as linhas da string cep a qual vai receber o buffer dos dados
		  while ((cep = br.readLine()) != null) {
			
			  jsonCep.append(cep);		 
			  
			   
			
		}
		  
		  Pessoa gsonAuxiliar = new Gson().fromJson(jsonCep.toString(),Pessoa.class); //objeto criado para receber os dados da string e mostrar em tela
		  
		  
		  pessoa.setCep(gsonAuxiliar.getCep());
		  pessoa.setLogradouro(gsonAuxiliar.getLogradouro());
		  pessoa.setComplemento(gsonAuxiliar.getComplemento());
		  pessoa.setUnidade(gsonAuxiliar.getUnidade());
		  pessoa.setBairro(gsonAuxiliar.getBairro());
		  pessoa.setLocalidade(gsonAuxiliar.getLocalidade());
		  pessoa.setUf(gsonAuxiliar.getUf());
		  pessoa.setEstado(gsonAuxiliar.getEstado());
		  pessoa.setRegiao(gsonAuxiliar.getRegiao());
		  pessoa.setIbge(gsonAuxiliar.getIbge());
		  pessoa.setGuia(gsonAuxiliar.getGuia());
		  pessoa.setDdd(gsonAuxiliar.getDdd());
		  pessoa.setSiafi(gsonAuxiliar.getSiafi());
		  
		  
		 // System.out.println(gsonAuxiliar);
		  
		
		  
		  
		  
		
	} catch (Exception ex) {
		
		ex.printStackTrace();
		mostrarMsg("erro ao mostrar o cep !");
	}
		
	}
	
	
   public String deslogar() {
	   
	   FacesContext context = FacesContext.getCurrentInstance();
	   ExternalContext externalContext = context.getExternalContext();
	   externalContext.getSessionMap().remove("usuarioLogado");
	   
	    HttpServletRequest httpServletRequest = (HttpServletRequest) context.getCurrentInstance().getExternalContext().getRequest();
	    
	    httpServletRequest.getSession().invalidate();
	    
	   
	   
	   
	   return "index.jsf";
   }
	
   public String logar() {
	
	   
	   
	   Pessoa pessoaUser = idaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());

	    if (pessoaUser != null) { // encontrou o usuario
	        FacesContext context = FacesContext.getCurrentInstance();
	        ExternalContext externalContext = context.getExternalContext();
	        
	        // Adicionar na sessão JSF
	        externalContext.getSessionMap().put("usuarioLogado", pessoaUser);

	        return "pagina?faces-redirect=true";
	    } else {
	        FacesContext.getCurrentInstance().addMessage(null, 
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	            "Login ou senha inválidos", "Erro de autenticação"));
	        return "index";
	    }
	   
	   
	   
	   
	   /*
		Pessoa pessoaUser = idaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());
		
		if(pessoaUser != null){	//encontrou o usuario
			
			//adicionar o usuario na sessao
			
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
			HttpSession session = req.getSession();
			
			session.setAttribute("usuarioLogado", pessoaUser);
			
			//externalContext.getSessionMap().put("usuarioLogado", pessoaUser.getLogin());
			
			
			return "pagina.jsf";
			
		}	
		
		
		
		return "index.jsf";  */
	}
	
	public boolean permiteAcessoComponentes(String acesso) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		
		Pessoa pessoaUse =  (Pessoa)  externalContext.getSessionMap().get("usuarioLogado");
		
		return pessoaUse.getPerfilUser().equals(acesso);
		
		
		
		
	}
	
	public List<SelectItem> getEstados() {
		estados = idaoPessoa.listaEstados();
		return estados;
	}
	
	@SuppressWarnings("unchecked")
	public void carregaCidades(AjaxBehaviorEvent event) {
		
		 
		
		Estados estado =   (Estados)  ((HtmlSelectOneMenu) event.getSource()).getValue();
		
			
		   
				 
				   if(estado != null) {
				   
				  pessoa.setEstados(estado);
				  
				  List<Cidades> cidades = JpaUtil.getEntityManager().createQuery("from Cidades where estados.id = " + estado.getId()).getResultList();
				  
				  List<SelectItem>  selectItemscidades = new ArrayList<SelectItem>();
				  
				  for (Cidades cidade : cidades ) {
					
					  selectItemscidades.add(new SelectItem(cidade,cidade.getNome()));
				}
				  
				  setCidades(selectItemscidades);
				  
				   }
				    
				   
			  } 
	
	@SuppressWarnings("unchecked")
	public String editar() {
		
		if(pessoa.getCidades() != null) {
			
			Estados estado = pessoa.getCidades().getEstados();
			pessoa.setEstados(estado);
			
			 List<Cidades> cidades = JpaUtil.getEntityManager().createQuery("from Cidades where estados.id = " + estado.getId()).getResultList();
			  
			  List<SelectItem>  selectItemscidades = new ArrayList<SelectItem>();
			  
			  for (Cidades cidade : cidades ) {
				
				  selectItemscidades.add(new SelectItem(cidade,cidade.getNome()));
			}
			  
			  setCidades(selectItemscidades);
			   }
			
			return"";
			
		} 
	
		
	
	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}
	
		

	public IDaoPessoa getIdaoPessoa() {
		return idaoPessoa;
	}

	public void setIdaoPessoa(IDaoPessoa idaoPessoa) {
		this.idaoPessoa = idaoPessoa;
	}

	public Part getArquivoFoto() {
		return arquivoFoto;
	}

	public void setArquivoFoto(Part arquivoFoto) {
		this.arquivoFoto = arquivoFoto;
	}

	/*Metodo que converte inputStrem( que a classe que recebe arquivo de mídia) para array de bytes[]*/
	public byte[] getByte(InputStream is) throws IOException{
		
		int len;
		int size = 1024;
		byte[] buf = null;
		
		if (is instanceof ByteArrayInputStream){
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		}else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(); //objeto usado para saída de mídia (que pode ser uma foto, video ,etc)
			buf = new byte[size];
			
			while ((len = is.read(buf, 0, size)) != -1){
				bos.write(buf, 0, len);// a variavel "bos" criada no objetvo acima vai ler e escrever o tamanho do arquivo em bytes
			}
			
			buf = bos.toByteArray(); //esse comando tranforma o arquivo em um array de bytes
		}
		
		return buf; // retornando a variavel que armazena o arrays de bytes
		
	}
		
	

}
