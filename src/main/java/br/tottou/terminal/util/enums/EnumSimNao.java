package br.tottou.terminal.util.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum EnumSimNao {

	NAO(0, "Não"),
	SIM(1, "Sim"),
	NAO_INFORMADO(2, "Não Informado");

	private Integer codigo;
	private String descricao;

	private EnumSimNao(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EnumSimNao recuperarPorCodigo(Integer codigo) {
		EnumSimNao resultado = null;
		for (EnumSimNao resposta : values()) {
			if (resposta.getCodigo().equals(codigo)) {
				resultado = resposta;
			}
		}
		return resultado;
	}
	
	public static List<EnumSimNao> getAsList() {
		return Arrays.asList(EnumSimNao.values());
	}
	
	public static List<EnumSimNao> getAsListParaFormulario(){
		List<EnumSimNao> lista = new ArrayList<EnumSimNao>();
		lista.add(SIM);
		lista.add(NAO);
		return lista;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
	
	public boolean isNao(){
		return this.equals(NAO);
	}
	public boolean isSim(){
		return this.equals(SIM);
	}
}
