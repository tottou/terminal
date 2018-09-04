package br.tottou.terminal.util;

import java.io.File;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.Locale;

public class UtilString {

	private UtilString() {
	}

	/**
	 * Se a String fornecida como parmetro for maior que o tamanho solicitado,
	 * esta string ser recortado, caso contrrio nada ser feito.
	 * 
	 * @param texto
	 * @param tam
	 * @return String
	 */
	public static final String fitString(String texto, int tam) {
		if (texto != null && !"".equals(texto) && tam > 0) {
			if (texto.length() > tam) {
				return texto.substring(0, tam);
			}
			return new String(texto);
		}

		return new String("");
	}

	private static final String[][]	PATTERNS				= {
			{ "[]", "A", }, { "", "C" }, { "[]", "E" },
			{ "[]", "I" }, { "", "N" }, { "[]", "O" },
			{ "[]", "U" }, { "[]", "a", }, { "", "c" },
			{ "[]", "e" }, { "[]", "i" }, { "", "n" },
			{ "[]", "o" }, { "[]", "u" }			};
	public static String			MASCARA_DOUBLE_BRASIL	= "0.00";

	public static String getNomeArquivoSemExtensao(File file) {
		return file.getName().substring(0, file.getName().indexOf("."));
	}

	public static String transform(String string) {
		if (string == null) {
			return null;
		}

		for (int i = 0; i < PATTERNS.length; i++) {
			string = string.replaceAll(PATTERNS[i][0], PATTERNS[i][1]);
		}
		return string;
	}

	public static String removeCaracteresEspeciais(String s) {
		String retorno = transform(s).replaceAll(" |/", "_").replace(".", "_")
				.replace("\\", "_");

		return retorno;
	}

	public static int compare(String s1, String s2) {
		Collator myFrenchCollator = Collator.getInstance(Locale.FRANCE);
		return myFrenchCollator.compare(s1, s2);
	}

	public static boolean isPreenchida(String s) {
		return (s != null) && (s.length() > 0);
	}

	public static boolean isPreenchida(int s) {
		return (s > 0);
	}

	public static boolean isPreenchida(double s) {
		return (s > 0);
	}

	public static long getVersao(String s) {
		return getVersao(s, "\\.");
	}

	public static long getVersao(String s, String regExp) {
		String[] aux = s.split(regExp);
		long resultado = 0;
		for (int i = 0; i < aux.length && i < 3; i++) {
			resultado += new Integer(aux[i]) * Math.pow(256, 2 - i);
		}
		return resultado;
	}

	public static String getVersao(long versao) {
		StringBuilder resultado = new StringBuilder("");
		long aux = versao;
		for (int i = 0; i < 3; i++) {
			if (i > 0) {
				resultado.append(".");
			}
			resultado.append(aux % 256);
			aux /= 256;
		}
		return resultado.toString();
	}

	public static String aspas(String valor) {
		return (valor != null && valor.length() > 0) ? valor = "'" + valor
				+ "'" : null;
	}

	public static String valorOuNulo(String valor) {
		return (valor == null || valor.length() < 1 || valor.equals("''")) ? null
				: valor;
	}

	public static String aspas(char valor) {
		return "'" + valor + "'";
	}

	/**
	 * Substitui os caracteres terminadores de linha por um espao.
	 * 
	 * @param inputStr
	 * @return String sem caracteres terminadores
	 */
	public static String removeLineTerminators(String inputStr) {
		return inputStr.replace('\n', ' ').replace('\r', ' ');
	}

	public static String toAspasSimples(String valor) {

		return "''" + valor.replace(",", "'',''") + "''";

	}

	public static String formatNumber(double number) {
		DecimalFormat formatador = new DecimalFormat(MASCARA_DOUBLE_BRASIL);
		return formatador.format(number);
	}

	public static String formatNumber(double number, String mask) {
		DecimalFormat formatador = new DecimalFormat(mask);
		return formatador.format(number);
	}

	public static String completaZeros(String s, int size) {
		String retorno = s;

		while (retorno.length() < size) {
			retorno = "0" + retorno;
		}

		return retorno;
	}

	public static String removeAcentos(String value) {
		value = value.replaceAll("", "A");
		value = value.replaceAll("", "a");
		value = value.replaceAll("", "A");
		value = value.replaceAll("", "a");
		value = value.replaceAll("", "A");
		value = value.replaceAll("", "a");
		value = value.replaceAll("", "A");
		value = value.replaceAll("", "a");

		value = value.replaceAll("", "E");
		value = value.replaceAll("", "e");
		value = value.replaceAll("", "E");
		value = value.replaceAll("", "e");

		value = value.replaceAll("", "I");
		value = value.replaceAll("", "i");

		value = value.replaceAll("", "O");
		value = value.replaceAll("", "o");
		value = value.replaceAll("", "O");
		value = value.replaceAll("", "o");
		value = value.replaceAll("", "O");
		value = value.replaceAll("", "o");

		value = value.replaceAll("", "U");
		value = value.replaceAll("", "u");
		value = value.replaceAll("", "U");
		value = value.replaceAll("", "u");

		value = value.replaceAll("", "C");
		value = value.replaceAll("", "c");

		return value;
	}

	public static String fillWith(String linha_a_preencher, String letra,
			int tamanho, String direcao) {
		// Checa se Linha a preencher  nula ou branco
		if (linha_a_preencher == null || linha_a_preencher.trim() == "") {
			linha_a_preencher = "";
		}

		// Enquanto Linha a preencher possuir 2 espaos em branco seguidos,
		// substitui por 1 espao apenas
		while (linha_a_preencher.contains("  ")) {
			linha_a_preencher = linha_a_preencher.replaceAll("  ", " ").trim();
		}

		// Retira caracteres estranhos
		linha_a_preencher = linha_a_preencher.replaceAll("[./-]", "");
		StringBuffer sb = new StringBuffer(linha_a_preencher);
		if (direcao.equals("<")) { // a Esquerda
			for (int i = sb.length(); i < tamanho; i++) {
				sb.insert(0, letra);
			}
		} else if (direcao.equals(">")) {// a Direita
			for (int i = sb.length(); i < tamanho; i++) {
				sb.append(letra);
			}
		}
		return sb.toString();
	}

	public static double parseDouble(String s) {
		return Double.parseDouble(s.replace(",", "."));
	}

	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String getStringLimite(String texto, int limite) {
		if (texto.length() > limite) {
			return texto.substring(0, limite - 1);
		} 
		return texto;
	}
}