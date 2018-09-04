package br.tottou.terminal.util.enums;

import lombok.Getter;
import lombok.ToString;

@ToString(of="descricao")
public enum EnumOperacao {

	INCLUIR("Incluir"),
	ALTERAR("Alterar"),
	PESQUISAR("Pesquisar"),
	VISUALIZAR("Visualizar"),
	EXCLUIR("Excluir"),
	SEM_ACAO("Sem Ação");

	private @Getter String descricao;

	private EnumOperacao(String descricao) {
		this.descricao = descricao;
	}

	public static EnumOperacao recuperarPorCodigo(String texto) {
		EnumOperacao resultado = null;
		for (EnumOperacao situacao : values()) {
			if (situacao.getDescricao().equals(texto)) {
				resultado = situacao;
			}
		}
		return resultado;
	}
	
	public boolean isInclusao() {
		return this == INCLUIR;
	}

	public boolean isAlteracao() {
		return this == ALTERAR;
	}

	public boolean isExclusao() {
		return this == PESQUISAR;
	}

	public boolean isPesquisar() {
		return this == PESQUISAR;
	}
	
	public boolean isVisualizar() {
		return this == VISUALIZAR;
	}
	
	public boolean isSemAcao() {
		return this == SEM_ACAO;
	}
	
	public boolean isInclusaoAlteracao() {
		return isInclusao() || isAlteracao();
	}
	
	public boolean isExclusaoVisualizacao() {
		return isVisualizar() || isExclusao();
	}
	
	public boolean isAlterarVisualizar() {
		return isVisualizar() || isAlteracao();
	}
}
