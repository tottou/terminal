package br.tottou.terminal.bean.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import br.tottou.terminal.util.enums.EnumOperacao;
import br.tottou.terminal.util.exception.NegocioException;
import lombok.Getter;
import lombok.Setter;


@SuppressWarnings("serial")
public abstract class AbstractGenericCRUDMBean<TEntidadeNegocio, TObjPK extends Serializable> extends AbstractBean {

	private @Getter @Setter TEntidadeNegocio entidade;
	private @Getter @Setter TEntidadeNegocio entidadeSelecionada;
	private @Getter @Setter List<TEntidadeNegocio> listaEntidade;
	private @Getter @Setter EnumOperacao operacao;
	
	public  @Getter @Setter String redirecionaPagina;
	private String	paginaPesquisa;
	private String	paginaManter;
	
	public abstract void doInit() throws Exception;

	public abstract Class<TEntidadeNegocio> getClazzAlvo();

	
	@PostConstruct
	public void init() throws Exception {
		try {
			setEntidade(getClazzAlvo().newInstance());
			setEntidadeSelecionada(getClazzAlvo().newInstance());
			setListaEntidade(new ArrayList<TEntidadeNegocio>());
			doInit();
		} catch (NegocioException e) {
			adicionarMensagemAlerta(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			adicionarMensagemErroFatal("Ocorreu um erro inesperado.", e);
		}
	}
	
	protected AbstractGenericCRUDMBean(String paginaPesquisa, String paginaManter) {
		this.paginaPesquisa = paginaPesquisa;
		this.paginaManter = paginaManter;
	}
	
	public String confirmarOperacao() {
		if (getOperacao().isInclusao()) {
			return incluir();
		}
		if (getOperacao().isAlteracao()) {
			return alterar();
		}
		if (getOperacao().isExclusao()) {
			return excluir();
		}
		return null;
	}
	
	public String iniciarPesquisa() {
		try {
			instanciarEntidade();
			setOperacao(EnumOperacao.PESQUISAR);
		} catch (InstantiationException | IllegalAccessException e) {
			adicionarMensagemErro(e.getMessage());
		}
		return redirecionaTelaPesquisa();
	}
	
	public void pesquisar() {}
	
	public String iniciarAlteracao() {
		setOperacao(EnumOperacao.ALTERAR);
		return redirecionaTelaManter();
	}
	
	public String alterar() {
		try {
			instanciarEntidade();
		} catch (InstantiationException | IllegalAccessException e) {
			adicionarMensagemErro(e.getMessage());
		}
		return iniciarPesquisa();
	}
	
	public String iniciarInclusao() {
		try {
			instanciarEntidade();
			setOperacao(EnumOperacao.INCLUIR);
		} catch (InstantiationException | IllegalAccessException e) {
			adicionarMensagemErro(e.getMessage());
		}
		return redirecionaTelaManter();
		
	}
	
	public String incluir() {
		try {
			instanciarEntidade();
		} catch (InstantiationException | IllegalAccessException e) {
			adicionarMensagemErro(e.getMessage());
		}
		return iniciarPesquisa();
	}
	
	public String iniciarExclusao() {
		setOperacao(EnumOperacao.EXCLUIR);
		return redirecionaTelaPesquisa();
	}
	
	public String excluir() {
		return redirecionaTelaPesquisa();
	}
	
	public String iniciarVisualizacao() {
		setOperacao(EnumOperacao.VISUALIZAR);
		return redirecionaTelaManter();
		
	}
	
	protected String redirecionaTelaPesquisa() {
		return this.paginaPesquisa;
	}
	
	protected String redirecionaTelaManter() {
		return this.paginaManter;
	}
	
	public String voltar() {
		return redirecionaTelaPesquisa();
	}
	
	public String voltar(String caminho) {
		return caminho;
	}
	
	
	public void limparFormPesquisa() throws InstantiationException, IllegalAccessException {
		instanciarEntidade();
	}
	
	public void limparFormManter() throws InstantiationException, IllegalAccessException {
		instanciarEntidade();
	}
	
	public void instanciarEntidade() throws InstantiationException, IllegalAccessException {
		setEntidade(getClazzAlvo().newInstance());
		setEntidadeSelecionada(getClazzAlvo().newInstance());
		setListaEntidade(new ArrayList<TEntidadeNegocio>());
	}


	public void doReset() throws Exception {
		entidade = getClazzAlvo().newInstance();
		doInit();
	}	
	

}
