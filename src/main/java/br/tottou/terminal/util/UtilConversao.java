package br.tottou.terminal.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class UtilConversao {

	private UtilConversao() {
	}

	/**
	 * Converte uma data no formato String (dd/mm/aaaa) para java.sql.Date
	 * @param data
	 * @return Data no formato java.sql.Date
	 */
	public static Date stringToSqlDate(String data) {

		if (isVazio(data)) {
			return null;
		}
		String dia = null;
		String mes = null;
		String ano = null;
		String date = null;

		dia = data.substring(0, 2);
		mes = data.substring(3, 5);
		ano = data.substring(6, 10);

		date = ano.concat("-").concat(mes).concat("-").concat(dia);

		return Date.valueOf(date);
	}

	/**
	 * Recebe uma hora como string no formato HH:mm e transforma em um Time
	 * @param horas
	 */
	public static Time stringToTime(String horas) {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Time time = null;

		try {
			if (horas != null && !horas.trim().equals("")) {
				java.util.Date novaData = sdf.parse(horas);
				time = new Time(novaData.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return time;

	}

	/**
	 * Metodo para mudar o formato da data informada de dd/mm/aaaa para o
	 * formato mm/dd/aaaa.
	 * 
	 * @param nome
	 * @return String
	 */
	public static String formatarToDate(String nome) {

		String data = null;
		data = nome.substring(3, 5);
		data = data.concat("/");
		data = data.concat(nome.substring(0, 2));
		data = data.concat("/");
		data = data.concat(nome.substring(6, nome.length()));

		return data;

	}

	/**
	 * Converte uma data no formato String (dd/mm/aaaa) para java.util.Date
	 * @return Data no formata java.util.Date
	 */
	public static java.util.Date stringToUtilDate(String data) {
		String dia = null;
		String mes = null;
		String ano = null;
		String date = null;

		dia = data.substring(0, 2);
		mes = data.substring(3, 5);
		ano = data.substring(6, 10);

		date = ano.concat("-").concat(mes).concat("-").concat(dia);

		return Date.valueOf(date);
	}

	/**
	 * Converte uma data no formato java.sql.Date (yyyy-mm-dd) para String (dd/mm/aaaa)
	 * @return Data no formato String (dd/mm/aaaa)
	 */
	public static String sqlDateToString(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}

	/**
	 * Converte uma data no formato java.sql.Util (yyyy-mm-dd) para String
	 * (dd/mm/aaaa)
	 * 
	 * @param data
	 * @return Data no formato String (dd/mm/aaaa)
	 */
	public static String utilDateToString(java.util.Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}

	/**
	 * Converte uma data no formato java.sql.Util (yyyy-mm-dd) para String
	 * (MM/dd/aaaa)
	 * 
	 * @param data
	 * @return Data no formato String (MM/dd/aaaa)
	 */
	public static String dateToStrinMesDiaAno(java.util.Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(data);
	}

	/**
	 * Mtodo que recebe duas datas no formato dd/mm/aaaa, uma data inicial e
	 * outra data final, e verifica se a data inicial  menor que a data final.
	 * 
	 * @param dataInicio
	 * @param dataFinal
	 * @return true, caso a data inicial seja menor que a data final.
	 */
	public static boolean isStartDateBefore(String dataInicio, String dataFinal) {

		boolean retorno = false;

		if (dataInicio != null && dataFinal != null) {

			Calendar dtI = getInstanceDate(dataInicio);
			Calendar dtF = getInstanceDate(dataFinal);

			if (dtI.before(dtF)) {
				retorno = true;
			}
		} else {
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Mtodo que recebe duas datas no formato dd/mm/aaaa, uma data inicial e
	 * outra data final, e retorna o intervalo de dias entre elas.
	 * 
	 * @param dataInicio
	 * @param dataFinal
	 * @return long, intervalo de dias entre as datas.
	 */
	public static long getIntervalEntersDates(String dataInicio,
			String dataFinal) {
		long intervalo = 0L;

		if (dataInicio != null && dataFinal != null) {

			Calendar dtI = getInstanceDate(dataInicio);
			Calendar dtF = getInstanceDate(dataFinal);

			long diffDtMillis = dtF.getTime().getTime()
					- dtI.getTime().getTime();

			intervalo = diffDtMillis / (24 * 60 * 60 * 1000);
		}
		return intervalo;
	}

	/**
	 * Dado uma data no formato (dd/mm/aaaa) retorna uma instncia da classe
	 * java.util.GregorianCalendar. Esse mtodo foi criado para ser utilizado
	 * com um apoio aos mtodos publico isStartDateBefore e
	 * getIntervalEntersDates.
	 * 
	 * @param data
	 * @return Instncia de GregorianCalendar.
	 */
	private static Calendar getInstanceDate(String data) {

		int dia = 0;
		int mes = 0;
		int ano = 0;

		dia = Integer.parseInt(data.substring(0, 2));
		mes = Integer.parseInt(data.substring(3, 5));
		ano = Integer.parseInt(data.substring(6, 10));

		switch (mes) {
		case 1:
			return new GregorianCalendar(ano, Calendar.JANUARY, dia);
		case 2:
			return new GregorianCalendar(ano, Calendar.FEBRUARY, dia);
		case 3:
			return new GregorianCalendar(ano, Calendar.MARCH, dia);
		case 4:
			return new GregorianCalendar(ano, Calendar.APRIL, dia);
		case 5:
			return new GregorianCalendar(ano, Calendar.MAY, dia);
		case 6:
			return new GregorianCalendar(ano, Calendar.JUNE, dia);
		case 7:
			return new GregorianCalendar(ano, Calendar.JULY, dia);
		case 8:
			return new GregorianCalendar(ano, Calendar.AUGUST, dia);
		case 9:
			return new GregorianCalendar(ano, Calendar.SEPTEMBER, dia);
		case 10:
			return new GregorianCalendar(ano, Calendar.OCTOBER, dia);
		case 11:
			return new GregorianCalendar(ano, Calendar.NOVEMBER, dia);
		default:
			return new GregorianCalendar(ano, Calendar.DECEMBER, dia);
		}
	}

	/**
	 * Realiza validao de valor numrico.
	 * 
	 * @param numero
	 * @return boolean
	 */
	public static boolean isNumero(String numero) {
		ArrayList<String> listaNumeros = (ArrayList<String>) getListaNumeros();
		for (int i = 0; i < numero.length(); i++) {
			if (!listaNumeros.contains(String.valueOf(numero.charAt(i)))) {
				return false;
			}
		}
		return true;
	}

	private static List<String> getListaNumeros() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(0, "0");
		list.add(1, "1");
		list.add(2, "2");
		list.add(3, "3");
		list.add(4, "4");
		list.add(5, "5");
		list.add(6, "6");
		list.add(7, "7");
		list.add(8, "8");
		list.add(9, "9");
		return list;
	}

	/**
	 * @param texto
	 * @return String
	 */
	public static String acento(String texto) {
		StringBuffer a = new StringBuffer(texto.toUpperCase());
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) > 122 && a.charAt(i) != 237 && a.charAt(i) != 205) {
				a.setCharAt(i, Character.toLowerCase(a.charAt(i)));
			}
		}
		return a.toString();
	}

	/**
	 * Recebe um numero, identifica se o mesmo  inteiro ou fracionrio (se tem
	 * vrgula ou no), e formata no padro 999.999.999 caso inteiro e
	 * 999.999,99 caso fracionrio.
	 * 
	 * @param numero
	 * @return String
	 */
	public static String formatar(String numero) {

		NumberFormat nFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
		String valorFormatado = "";
		boolean temVirgula = false;
		Double valor = null;

		try {

			if (numero != null && !numero.equals("")) {

				for (int x = 0; x < numero.length(); x++) {

					if (numero.charAt(x) == ',') {
						temVirgula = true;
						break;
					}
				}

				if (temVirgula) {
					String numeroFormatadoParaDouble = numero.replace(',', '.');
					nFormat.setMinimumFractionDigits(2);
					nFormat.setMaximumFractionDigits(2);
					valor = new Double(numeroFormatadoParaDouble);
				} else {
					nFormat.setMinimumFractionDigits(0);
					nFormat.setMaximumFractionDigits(0);
					valor = new Double(numero);
				}

				valorFormatado = nFormat.format(valor.doubleValue());
			}
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace(System.out);
		}

		return valorFormatado;
	}

	/**
	 * @param texto
	 * @return String
	 */
	public static String removerChar10(String texto) {
		StringBuffer a = new StringBuffer();

		for (int i = 0; i < texto.length(); i++) {
			if (texto.charAt(i) != (char) 10) {
				a.append(texto.charAt(i));
			}
		}
		return a.toString();

	}

	/**
	 * @param texto
	 * @return String
	 */
	public static String incluirTagBR(String texto) {
		StringBuffer a = new StringBuffer();

		for (int i = 0; i < texto.length(); i++) {
			a.append(texto.charAt(i));
			if (texto.charAt(i) == (char) 13) {
				a.append("<br>");
			}
		}
		return a.toString();
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
	 * Varifica se o valor passado como parmetro  vazio
	 * 
	 * @param valor
	 * @return boolean
	 */
	public static boolean isVazio(String valor) {
		return valor == null || valor.trim().equals("")
				|| valor.trim().equals("null");
	}

	/**
	 * Converte uma string em um int
	 * 
	 * @param string
	 * @return int
	 */
	public static int getInt(String string) {
		if (isVazio(string)) {
			return 0;
		}
		return Integer.parseInt(string);
	}

	/**
	 * Converte uma string em um Integer
	 * 
	 * @param string
	 * @return Integer
	 */
	public static Integer getInteger(String string) {
		if (isVazio(string)) {
			return null;
		}
		return new Integer(string);
	}

	/**
	 * Retorna o estilo da linha de acordo com o ndice da mesma na tabela.
	 * 
	 * @param indice
	 * @return String
	 */
	public static String getEstiloLinha(int indice) {
		String retorno = "itemListaBranca";
		if (indice % 2 == 1) {
			retorno = "itemListaAzul";
		}
		return retorno;
	}

	/**
	 * Retorna o estilo do radio de acordo com o ndice da linha atual da tabela
	 * 
	 * @param indice
	 * @return String
	 */
	public static String getEstiloRadio(int indice) {
		String retorno = "radioBranco";
		if (indice % 2 == 1) {
			retorno = "radioAzul";
		}
		return retorno;
	}

	/**
	 * Converte um java.util.Date para um java.sql.Date
	 * 
	 * @param date
	 * @return java.sql.Date
	 */
	public static Date utilDateToSqlDate(java.util.Date date) {

		if (date != null) {
			return new Date(date.getTime());
		}
		return null;
	}

	/**
	 *
	 * @Date 25/05/2006 - 19:21:22
	 * @return Integer
	 */
	public static int getAnoAtual() {
		String sDataAtual = getDataAtual();
		String sAnoAtual = sDataAtual.substring(0, 4);
		return Integer.parseInt(sAnoAtual);
	}

	/**
	 *
	 * @Date 25/05/2006 - 19:21:17
	 * @return static
	 */
	public static String getDataAtual() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"));
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");

		sdf.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
		return sdf.format(cal.getTime());
	}

	/**
	 *
	 * @Date 02/10/2006 - 09:51:42
	 * @param data
	 * @return static
	 */
	public static String timestampToString(Timestamp data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}

	/**
	 * Zera hora, minuto, segundo e milisegundo.
	 * 
	 * @param data
	 */
	protected static void zeraHoraMinutoSegundo(Calendar data) {
		data.set(Calendar.HOUR_OF_DAY, 0);
		data.set(Calendar.MINUTE, 0);
		data.set(Calendar.SECOND, 0);
		data.set(Calendar.MILLISECOND, 0);
	}
	
	/**
	 * Usada no retorno do nome aps validao da Receita
	 * 
	 * @param nome
	 * @return
	 */
	public static boolean isNomeEhValido(String nome){
		String prefixoCPF = "CPF";
		String prefixoCNPJ = "CNPJ";
		boolean retorno = false;		
		if(!UtilConversao.isVazio(nome)){
			if(!(nome.contains(prefixoCPF) || nome.contains(prefixoCNPJ))){
				retorno = true;
			}
		}
		return retorno;
	}
	
	
}
