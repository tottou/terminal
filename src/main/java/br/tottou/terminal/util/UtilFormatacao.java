/*
 * UtilitarioParaFormatacao.java
 *
 * Data de criao: 29/07/2004
 *
 * Desenvolvido por Politec Ltda.
 * Fbrica de Software - Braslia
 */
package br.tottou.terminal.util;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Classe que possui mtodos para a formatacao em java do sistema.
 */
public final class UtilFormatacao {

	private static final Locale						BRASIL	= new Locale("pt","BR");
	private static Map<String, SimpleDateFormat>	mapaDeSimpleDateFormat;

	private UtilFormatacao() {}

	/**
	 * @return Map
	 */
	protected static Map<String, SimpleDateFormat>  getMapaDeSimpleDateFormat() {
		if (mapaDeSimpleDateFormat == null) {
			mapaDeSimpleDateFormat = novoMapaDeSimpleDateFormat();
		}
		return mapaDeSimpleDateFormat;
	}

	/**
	 * Mtodo esttico que recebe uma data como string no formata dd/MM/yyyy E
	 * retorna verdadeiro se essa data for vlida, caso contrario retorna falso.
	 * 
	 * @param data
	 * @return boolean
	 */
	public static boolean verificarDataValida(String data) {
		boolean retorno = true;
		String padrao = "dd/MM/yyyy";
		try {
			SimpleDateFormat formatador = new SimpleDateFormat(padrao);
			formatador.setLenient(false);

			if (data != null && !data.equals("")) {
				if (data.length() != padrao.length()) {
					throw new ParseException("data invlida", 0);
				}
			} else {
				retorno = false;
			}
		} catch (ParseException e) {
			retorno = false;
		}

		return retorno;
	}

	/**
	 * @return Map
	 */
	protected static Map<String, SimpleDateFormat> novoMapaDeSimpleDateFormat() {
		String[] patterns = new String[] { "yyyy/MM/dd HH:mm", "dd/MM/yyyy",
				"dd/MM/yyyy HH:mm", "dd/MM/yyyy HH:mm:ss",
				"dd/MM/yyyy HH:mm:ss.SSS", "yyyyMMddHHmmss", "yyyyMMddHHmm",
				"yyyyMMdd", "ddMMyyyy", "HH:mm", "HH:mm:ss", "EEE", "dd", "MM",
				"yyyy", "MM/yyyy", "yyyy-MM-dd", "HH.mm" };

		String[] patternsBrasil = new String[] { "MMMMMMMMMM" };

		Map<String, SimpleDateFormat> mapa = new HashMap<String, SimpleDateFormat>();

		for (int i = 0; i < patterns.length; i++) {
			mapa.put(patterns[i], novoSimpleDateFormat(patterns[i]));
		}
		for (int i = 0; i < patternsBrasil.length; i++) {
			mapa.put(patternsBrasil[i], novoSimpleDateFormatBrasil(patternsBrasil[i]));
		}

		return mapa;
	}

	/**
	 * @param pattern
	 * @return SimpleDateFormat
	 */
	protected static SimpleDateFormat novoSimpleDateFormat(String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		dateFormat.setLenient(false);
		return dateFormat;
	}

	/**
	 * @param pattern
	 * @return SimpleDateFormat
	 */
	protected static SimpleDateFormat novoSimpleDateFormatBrasil(String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, BRASIL);
		dateFormat.setLenient(false);
		return dateFormat;
	}

	/**
	 * Retorna um SimpleDateFormat de acordo com o pattern passado como
	 * parmetro
	 * 
	 * @param pattern
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getSimpleDateFormat(String pattern) {
		return (SimpleDateFormat) getMapaDeSimpleDateFormat().get(pattern);
	}

	/**
	 * Retorna a data formatada no padrao informado
	 * 
	 * @param data
	 * @param pattern
	 * @return String
	 */
	public static String formatarData(Date data, String pattern) {
		if (data == null || getSimpleDateFormat(pattern) == null) {
			return "";
		}
		return getSimpleDateFormat(pattern).format(data);
	}

	/**
	 * Retorna a hora formatada no padrao informado
	 * 
	 * @param hora
	 * @param pattern
	 * @return String
	 */
	public static String formatarHora(Time hora, String pattern) {
		if (hora == null || getSimpleDateFormat(pattern) == null) {
			return "";
		}
		return getSimpleDateFormat(pattern).format(hora);
	}

	/**
	 * Retorna a data formatada no padrao informado
	 * 
	 * @param data
	 * @param pattern
	 * @return Date
	 */
	public static Date converterData(String data, String pattern) {
		Date retorno = null;
		if (data != null && pattern != null && getSimpleDateFormat(pattern) != null) {
			try {
				retorno = getSimpleDateFormat(pattern).parse(data);
			} catch (ParseException e) {
				retorno = null;
			}
		}
		return retorno;
	}

	/**
	 * Retorna a hora formatada no padrao informado
	 * 
	 * @param hora
	 * @param pattern
	 * @return Date
	 */
	public static Time converterHora(String hora, String pattern) {
		Time retorno = null;
		Date date = null;
		if (hora != null && pattern != null && getSimpleDateFormat(pattern) != null) {
			try {
				date = getSimpleDateFormat(pattern).parse(hora);
				retorno = new Time(date.getTime());
			} catch (ParseException e) {
				retorno = null;
			}
		}
		return retorno;
	}

	/**
	 * Remove qualquer tipo de mscara.
	 * 
	 * @param numero
	 * @return String
	 */
	public static String removerMascara(String numero) {
		if (numero == null) {
			return numero;
		}
		char[] numeros = numero.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < numeros.length; i++) {
			if (numeros[i] != '.' && numeros[i] != '-' && numeros[i] != '/' && numeros[i] != ',') {
				sb.append(numeros[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * Coloca mscara no CPF.
	 * 
	 * @param numero
	 * @return String
	 */
	public static String mascararCpf(String numero) {
		StringBuffer cpf = new StringBuffer();
		if (!UtilConversao.isVazio(numero)) {
			if (numero.length() < 11) {
				for (int i = 0; i < 11 - numero.length(); i++) {
					cpf.append("0");
				}
			}
			cpf.append(numero);
			cpf.insert(3, '.');
			cpf.insert(7, '.');
			cpf.insert(11, '-');
		}
		return cpf.toString();
	}

	/**
	 * Coloca mscara no Nmero do Protocolo do Processo.
	 * 
*
	 * @param numero
	 * @return String
	 */
	public static String mascararProtocolo(String numero) {
		StringBuffer protocolo = new StringBuffer();
		if (!UtilConversao.isVazio(numero)) {
			if (numero.length() > 4) {
				protocolo.append(numero);
				protocolo.insert(numero.length() - 4, '/');
			}
		}
		return protocolo.toString();
	}

	/**
	 * Coloca mscara no CNPJ.
	 * 
*
	 * @param numero
	 * @return String
	 */
	public static String mascararCnpj(String numero) {
		StringBuffer cnpj = new StringBuffer();
		if (!UtilConversao.isVazio(numero)) {
			if (numero.length() < 14) {
				for (int i = 0; i < 14 - numero.length(); i++) {
					cnpj.append("0");
				}
			}
			cnpj.append(numero);
			cnpj.insert(2, '.');
			cnpj.insert(6, '.');
			cnpj.insert(10, '/');
			cnpj.insert(15, '-');
		}
		return cnpj.toString();
	}

	public static String formataStringTokenizada(String conteudo,
			String separador, String novoSeparador) {
		StringBuffer novoConteudo = new StringBuffer();
		if (!UtilConversao.isVazio(conteudo)) {
			StringTokenizer tokens = new StringTokenizer(conteudo, separador);
			if (tokens != null) {
				int i = 0;
				while (tokens.hasMoreElements()) {
					String token = tokens.nextToken().trim();
					if (i == 0) {
						novoConteudo.append(token);
					} else {
						novoConteudo.append(novoSeparador + " ");
						novoConteudo.append(token);
					}
					i++;
				}
			}
		}
		return novoConteudo.toString();
	}

	/**
	 * Compara se duas datas so iguais
	 * 
	 * @param a
	 * @param b
	 * @return boolean
	 */
	public static boolean compararDatas(Date a, Date b) {
		Calendar calA = null;
		Calendar calB = null;
		boolean retorno = false;

		calA = Calendar.getInstance();
		calB = Calendar.getInstance();
		calA.setTime(a);
		calB.setTime(b);
		
		if (calA.get(Calendar.DATE) == calB.get(Calendar.DATE) && calA.get(Calendar.MONTH) == calA.get(Calendar.MONTH) && calA.get(Calendar.YEAR) == calA.get(Calendar.YEAR)) {
			retorno = true;
		} else {
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Compara se a diferena entre a data passada e a data atual  igual a
	 * quantidade de dias passados
	 * 
	 * @param data
	 * @param dias
	 * @return boolean
	 */
	public static boolean compara(Date data, int dias) {
		Calendar calData = null;
		Calendar calAtual = null;
		boolean retorno = false;

		calData = Calendar.getInstance();
		calAtual = Calendar.getInstance();
		calData.setTime(data);
		calAtual.setTime(new Date());
		calAtual.add(Calendar.DATE, dias);
		
		if (calData.get(Calendar.DATE) == calAtual.get(Calendar.DATE) && calData.get(Calendar.MONTH) == calAtual.get(Calendar.MONTH) 
				&& calData.get(Calendar.YEAR) == calAtual.get(Calendar.YEAR)) {
			retorno = true;
		} else {
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Adiciona (ou subtrai) dias a uma Data
	 * 
	 * @param dataInicial
	 * @param dias
	 * @return Date
	 */
	public static Date adicionaDias(Date dataInicial, int dias) {

		Calendar calendar = null;
		Date dataFinal = null;
		if (dataInicial != null) {
			calendar = Calendar.getInstance();
			calendar.setTime(dataInicial);
			calendar.add(Calendar.DATE, dias);
			dataFinal = calendar.getTime();
		}
		dataInicial = null;
		calendar = null;
		return dataFinal;
	}

	/**
	 * Ajusta as horas, minutos e segundos de uma data
	 * @param data
	 * @param horas
	 * @param minutos
	 * @param segundos
	 * @return Date
	 */
	public static Date ajustaData(Date data, int horas, int minutos, int segundos) {
		Calendar calendar = null;
		if (data != null) {
			calendar = Calendar.getInstance();
			calendar.setTime(data);
			calendar.set(Calendar.HOUR, horas);
			calendar.set(Calendar.MINUTE, minutos);
			calendar.set(Calendar.SECOND, segundos);
			data = calendar.getTime();
		}
		return data;
	}

	/**
	 * Retorna nome do dia.
	 * 
	 * @param data
	 * @return String
	 */
	public static String getDiaSemana(Date data) {
		String dataFormatada = null;
		if (data != null) {
			dataFormatada = getSimpleDateFormat("EEE").format(data);
		}
		return dataFormatada;
	}

	/**
	 * Mtodo que formata o valor para gravar no banco de dados Transforma o
	 * valor 1.009,99 para 1009.99
	 * 
	 * @param valor
	 * @return Double
	 */
	public static Double formatDouble(String valor) {
		Double retorno = null;
		try {
			DecimalFormat f = new DecimalFormat("#,##0.00");
			f.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale("pt","BR")));
			retorno = new Double(f.parse(valor).doubleValue());
		} catch (Exception e) {
			retorno = null;
		}
		return retorno;
	}

	/**
	 * Formata a a String com zeros a esquerda, com o tamanho informado.
	 * 
	 * @param valor
	 * @param tamanho
	 * @return String
	 */
	public static String completaComZeroEsquerda(String valor, int tamanho) {
		if (valor.length() < tamanho) {
			do {
				valor = "0" + valor;
			} while (valor.length() < tamanho);
		}
		return valor;
	}

	/**
	 * Formata a a String com zeros a esquerda, com o tamanho informado.
	 * 
	 * @param valor
	 * @param tamanho
	 * @return String
	 */
	public static String completaComZeroEsquerda(long valor, int tamanho) {
		String retorno = String.valueOf(valor);
		if (retorno.length() < tamanho) {
			do {
				retorno = "0" + retorno;
			} while (retorno.length() < tamanho);
		}
		return retorno;
	}

	/**
	 * Mascara o CEP
	 * 
	 * @param numeroCompleto
	 * @return String
	 */
	public static String mascaraCEP(String numeroCompleto) {
		StringBuffer cep = new StringBuffer();
		if (!UtilConversao.isVazio(numeroCompleto)) {
			if (numeroCompleto.length() < 8) {
				for (int i = 0; i < 8 - numeroCompleto.length(); i++) {
					cep.append("0");
				}
			}
			cep.append(numeroCompleto);
			cep.insert(2, '.');
			cep.insert(6, '-');
		}
		return cep.toString();
	}

	/**
	 * Mascara o CEP
	 * 
	 * @param nuCep
	 * @return String
	 */
	public static String mascaraCEP(int nuCep) {
		return mascaraCEP(String.valueOf(nuCep));
	}

	/**
	 * Formata percentual
	 * 
	 * @param numero
	 * @return String
	 */
	public static String formatarPercentual(float numero) {
		DecimalFormat formatador = new DecimalFormat();
		formatador.applyPattern("##0.00");
		return formatador.format(numero);
	}

}
