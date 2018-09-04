/*
 * UtilitarioParaData.java
 *
 * Data de criao: 29/07/2004
 *
 * Desenvolvido por Politec Ltda.
 * Fbrica de Software - Braslia
 */
package br.tottou.terminal.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

import br.tottou.terminal.util.exception.AplicacaoGenericaException;

/**
 * Classe que possui mtodos para a formatacao em java do sistema.
 * 
 */
public final class UtilData {
	
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat SIMPLE_DATE_HOUR_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	private static final Locale BRASIL = new Locale("pt", "BR");

	private UtilData() {
		//
	}
	
	/**
	 * Formata a Data com ano de 4 posies.
	 * 
	 * @param data	
	 * @return data Formatada
	 */
	public static Date getData(String data) {
		Date retorno = null;
		if (data != null && !data.trim().equals("")) {
			try {
				retorno = UtilFormatacao.getSimpleDateFormat("dd/MM/yyyy").parse(data);
			} catch (ParseException e) {
				retorno = null;
			}
		}
		return retorno;
	}
	
	public static Date toDate(String parameterValue) throws AplicacaoGenericaException {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(parameterValue);
		} catch (ParseException e) {
			throw new AplicacaoGenericaException("Data inválida!");
		}
	}
	
	
	public static String getDataHoraFormatada(Date data) {
		String dataFormatada = null;

		if (data != null) {
			dataFormatada = SIMPLE_DATE_HOUR_FORMAT.format(data);
		}

		return dataFormatada;
	}

	public static Date getDataHora(String dataHora) {
		Date retorno = null;
		if (dataHora != null && !dataHora.trim().equals("")) {
			try {
				retorno = UtilFormatacao.getSimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dataHora);
			} catch (ParseException e) {
				retorno = null;
			}
		}
		return retorno;
	}
	
	
	public static Date converteDataHoraBanco(String dataHora) {
		Date retorno = null;
		if (dataHora != null && !dataHora.trim().equals("")) {
			try {
				retorno = UtilFormatacao.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataHora);
			} catch (ParseException e) {
				retorno = null;
			}
		}
		return retorno;
	}
	
	
	/**
	 * Retorna a data formatada
	*
	 * @param data
	 * @param formato
	 * @return
	 */
	public static String formatarData(Date data, String formato) {
		SimpleDateFormat formatter = new SimpleDateFormat(formato);
		return formatter.format(data);
	}
	
	/**
	 * Retorna a data no formato dd/MM/aaaa
	 * @param data
	 * @return
	 */
	public static String formatarDatePadrao(Date data) {		
		return formatarData(data, "dd/MM/yyyy"); 
	}
	
	/**
	 * Retorna a data e hora no padro : dd/MM/yyyy HH:mm:ss
	*
	 * @param data
	 * @return
	 */
	public static String formatarDataTime(Date data) {		
		return formatarData(data, "dd/MM/yyyy HH:mm:ss"); 
	}

	/**
	 * Retorna a hora, minuto e segundo.
	 * A hora e no  formato de 12h
	*
	 * @param data
	 * @return
	 */
	public static String formatarTime12h(Date data) {		
		return formatarData(data, "hh:mm:ss"); 
	}
	
	/**
	 * Retorna a hora, minuto e segundo.
	 * A hora e no  formato de 24h
	*
	 * @param data
	 * @return
	 */
	public static String formatTime24h (Date data){
		return formatarData(data, "HH:mm:ss");
	}
	
	/**
	 * Transforma a string passada como parmetro em java.sql.Date.
	 * 
	 * @param string
	 * @return java.sql.Date
	 */
	public static java.sql.Date getSqlDate(String string) {
		Date data = getData(string);
		return converteDateParaSqlDate(data);
	}

	/**
	 * Verifica se a data  vlida.
	 * 
	 * @param data
	 * @return boolean
	 */
	public static boolean validarData(String data) {
		boolean retorno = false;
		try {
			if (data != null && !data.equals("")) {
				StringTokenizer tokenizer = new StringTokenizer(data, "/");
				int dia = Integer.parseInt(tokenizer.nextToken());
				int mes = Integer.parseInt(tokenizer.nextToken());
				int ano = Integer.parseInt(tokenizer.nextToken());

				TimeZone timeZone = TimeZone.getTimeZone("GMT-3");
				Calendar calendario = Calendar.getInstance(timeZone, BRASIL);
				Date resposta = null;
				resposta = UtilFormatacao.getSimpleDateFormat("dd/MM/yyyy").parse(
						data);
				calendario.setTime(resposta);
				if (ano == calendario.get(Calendar.YEAR)
						&& mes == calendario.get(Calendar.MONTH) + 1
						&& dia == calendario.get(Calendar.DATE)) {

					retorno = true;
				}
			}
		} catch (Exception e) {
			// Se ocorrer uma exceo o mtodo deve retornar false.
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Faz a converso de java.util.Date para java.sql.Date
	 * 
	 * @param data
	 * @return java.sql.Date
	 */
	public static java.sql.Date converteDateParaSqlDate(Date data) {
		java.sql.Date dataSQL = null;
		if (data != null) {
			dataSQL = new java.sql.Date(data.getTime());
		}
		return dataSQL;
	}

	/**
	 * Verifica se a data passada como parmetro  anterior  data atual,
	 * comparando apenas dia, ms e ano.
	 * 
	 * @param data
	 * @return boolean
	 */
	public static boolean isDataMenorQueDataAtual(Date data) {
		GregorianCalendar data1 = new GregorianCalendar(BRASIL);
		GregorianCalendar dataAtual = new GregorianCalendar(BRASIL);
		boolean retorno = false;

		data1.setTime(data);
		dataAtual.setTime(new Date());

		if (data1.get(Calendar.YEAR) > dataAtual.get(Calendar.YEAR)) {
			retorno = false;
		} else if (data1.get(Calendar.YEAR) < dataAtual.get(Calendar.YEAR)) {
			retorno = true;
		} else if (data1.get(Calendar.MONTH) > dataAtual.get(Calendar.MONTH)) {
			retorno = false;
		} else if (data1.get(Calendar.MONTH) < dataAtual.get(Calendar.MONTH)) {
			retorno = true;
		} else {
			retorno = data1.get(Calendar.DAY_OF_MONTH) < dataAtual
					.get(Calendar.DAY_OF_MONTH);
		}
		return retorno;
	}

	/**
	 * Verifica se a data passada como parmetro  anterior  data atual,
	 * comparando apenas minuto, hora, dia, ms e ano.
	 * 
	 * @param data
	 * @return boolean
	 */
	public static boolean isDataHoraMenorQueDataAtual(Date data) {
		GregorianCalendar data1 = new GregorianCalendar(BRASIL);
		GregorianCalendar dataAtual = new GregorianCalendar(BRASIL);
		boolean retorno = false;

		if (data == null) {
			return true;
		}

		data1.setTime(data);
		dataAtual.setTime(new Date());

		if (data1.get(Calendar.YEAR) > dataAtual.get(Calendar.YEAR)) {
			retorno = false;
		} else if (data1.get(Calendar.YEAR) < dataAtual.get(Calendar.YEAR)) {
			retorno = true;
		} else if (data1.get(Calendar.MONTH) > dataAtual.get(Calendar.MONTH)) {
			retorno = false;
		} else if (data1.get(Calendar.MONTH) < dataAtual.get(Calendar.MONTH)) {
			retorno = true;
		} else if (data1.get(Calendar.DAY_OF_MONTH) > dataAtual
				.get(Calendar.DAY_OF_MONTH)) {
			retorno = false;
		} else if (data1.get(Calendar.DAY_OF_MONTH) < dataAtual
				.get(Calendar.DAY_OF_MONTH)) {
			retorno = true;
		} else if (data1.get(Calendar.HOUR_OF_DAY) > dataAtual
				.get(Calendar.HOUR_OF_DAY)) {
			retorno = false;
		} else if (data1.get(Calendar.HOUR_OF_DAY) < dataAtual
				.get(Calendar.HOUR_OF_DAY)) {
			retorno = true;
		} else {
			retorno = data1.get(Calendar.MINUTE) < dataAtual
					.get(Calendar.MINUTE);
		}
		return retorno;
	}
	
	
	/**
	 * Verifica se a data passada como parmetro  anterior  data atual,
	 * comparando apenas minuto, hora, dia, ms e ano.
	 * 
	 * @param data
	 * @return boolean
	 */
	public static boolean isDataHoraMenorQueDataAtual(Date data, Date dataBd) {
		GregorianCalendar data1 = new GregorianCalendar(BRASIL);
		GregorianCalendar dataAtual = new GregorianCalendar(BRASIL);
		boolean retorno = false;

		if (data == null) {
			return true;
		}

		data1.setTime(data);
		dataAtual.setTime(dataBd);

		if (data1.get(Calendar.YEAR) > dataAtual.get(Calendar.YEAR)) {
			retorno = false;
		} else if (data1.get(Calendar.YEAR) < dataAtual.get(Calendar.YEAR)) {
			retorno = true;
		} else if (data1.get(Calendar.MONTH) > dataAtual.get(Calendar.MONTH)) {
			retorno = false;
		} else if (data1.get(Calendar.MONTH) < dataAtual.get(Calendar.MONTH)) {
			retorno = true;
		} else if (data1.get(Calendar.DAY_OF_MONTH) > dataAtual
				.get(Calendar.DAY_OF_MONTH)) {
			retorno = false;
		} else if (data1.get(Calendar.DAY_OF_MONTH) < dataAtual
				.get(Calendar.DAY_OF_MONTH)) {
			retorno = true;
		} else if (data1.get(Calendar.HOUR_OF_DAY) > dataAtual
				.get(Calendar.HOUR_OF_DAY)) {
			retorno = false;
		} else if (data1.get(Calendar.HOUR_OF_DAY) < dataAtual
				.get(Calendar.HOUR_OF_DAY)) {
			retorno = true;
		} else {
			retorno = data1.get(Calendar.MINUTE) < dataAtual
					.get(Calendar.MINUTE);
		}
		return retorno;
	}

	/**
	 * Verifica se a data passada como parmetro  posterior  data atual,
	 * comparando apenas minuto, hora, dia, ms e ano.
	 * 
	 * @param data
	 * @return boolean
	 */
	public static boolean isDataHoraMaiorQueDataAtual(Date data) {
		GregorianCalendar data1 = new GregorianCalendar(BRASIL);
		GregorianCalendar dataAtual = new GregorianCalendar(BRASIL);
		boolean retorno = false;

		if (data == null) {
			return false;
		}

		data1.setTime(data);
		dataAtual.setTime(new Date());

		if (data1.get(Calendar.YEAR) < dataAtual.get(Calendar.YEAR)) {
			retorno = false;
		} else if (data1.get(Calendar.YEAR) > dataAtual.get(Calendar.YEAR)) {
			retorno = true;
		} else if (data1.get(Calendar.MONTH) < dataAtual.get(Calendar.MONTH)) {
			retorno = false;
		} else if (data1.get(Calendar.MONTH) > dataAtual.get(Calendar.MONTH)) {
			retorno = true;
		} else if (data1.get(Calendar.DAY_OF_MONTH) < dataAtual
				.get(Calendar.DAY_OF_MONTH)) {
			retorno = false;
		} else if (data1.get(Calendar.DAY_OF_MONTH) > dataAtual
				.get(Calendar.DAY_OF_MONTH)) {
			retorno = true;
		} else if (data1.get(Calendar.HOUR_OF_DAY) < dataAtual
				.get(Calendar.HOUR_OF_DAY)) {
			retorno = false;
		} else if (data1.get(Calendar.HOUR_OF_DAY) > dataAtual
				.get(Calendar.HOUR_OF_DAY)) {
			retorno = true;
		} else {
			retorno = data1.get(Calendar.MINUTE) > dataAtual
					.get(Calendar.MINUTE);
		}
		return retorno;
	}
	
	
	
	/**
	 * Verifica se a data passada como parmetro  posterior  data atual,
	 * comparando apenas minuto, hora, dia, ms e ano.
	 * 
	 * @param data
	 * @return boolean
	 */
	public static boolean isDataHoraMaiorQueDataAtual(Date dataFinal, Date dataBD) {
		GregorianCalendar data1 = new GregorianCalendar(BRASIL);
		GregorianCalendar dataAtual = new GregorianCalendar(BRASIL);
		boolean retorno = false;

		if (dataFinal == null) {
			return false;
		}

		data1.setTime(dataFinal);
		dataAtual.setTime(dataBD);

		if (data1.get(Calendar.YEAR) < dataAtual.get(Calendar.YEAR)) {
			retorno = false;
		} else if (data1.get(Calendar.YEAR) > dataAtual.get(Calendar.YEAR)) {
			retorno = true;
		} else if (data1.get(Calendar.MONTH) < dataAtual.get(Calendar.MONTH)) {
			retorno = false;
		} else if (data1.get(Calendar.MONTH) > dataAtual.get(Calendar.MONTH)) {
			retorno = true;
		} else if (data1.get(Calendar.DAY_OF_MONTH) < dataAtual
				.get(Calendar.DAY_OF_MONTH)) {
			retorno = false;
		} else if (data1.get(Calendar.DAY_OF_MONTH) > dataAtual
				.get(Calendar.DAY_OF_MONTH)) {
			retorno = true;
		} else if (data1.get(Calendar.HOUR_OF_DAY) < dataAtual
				.get(Calendar.HOUR_OF_DAY)) {
			retorno = false;
		} else if (data1.get(Calendar.HOUR_OF_DAY) > dataAtual
				.get(Calendar.HOUR_OF_DAY)) {
			retorno = true;
		} else {
			retorno = data1.get(Calendar.MINUTE) > dataAtual
					.get(Calendar.MINUTE);
		}
		return retorno;
	}

	/**
	 * Retorna a data atual formatada por extenso
	 * 
	 * @return String
	 */
	public static String getDataAtualFormatadaPorExtenso() {
		SimpleDateFormat formatDia = UtilFormatacao.getSimpleDateFormat("dd");
		SimpleDateFormat formatMes = UtilFormatacao
				.getSimpleDateFormat("MMMMMMMMMM");
		SimpleDateFormat formatAno = UtilFormatacao.getSimpleDateFormat("yyyy");

		String retorno = formatDia.format(new Date()) + " de "
				+ formatMes.format(new Date()) + " de "
				+ formatAno.format(new Date());
		return retorno;
	}

	/**
	 * Retorna o ano atual
	 * 
	 * @return String
	 */
	public static String getAnoAtual() {
		SimpleDateFormat formatAno = UtilFormatacao.getSimpleDateFormat("yyyy");
		return formatAno.format(new Date());
	}

	/**
	 * Retorna o ms atual por extenso
	 * 
	 * @return String
	 */
	public static String getMesAtualPorExtenso() {
		SimpleDateFormat formatMes = UtilFormatacao
				.getSimpleDateFormat("MMMMMMMMMM");
		return formatMes.format(new Date());
	}

	/**
	 * Retorna o dia atual no formato DD
	 * 
	 * @return String
	 */
	public static String getDiaAtual() {
		SimpleDateFormat formatDia = UtilFormatacao.getSimpleDateFormat("dd");
		return formatDia.format(new Date());
	}

	/**
	 * Converte a data para o tipo Timestamp
	 * 
	 * @param data
	 * @return Date
	 */
	public static Timestamp converterDateParaTimestamp(Date data) {
		if (data == null) {
			return null;
		}
		return new Timestamp(data.getTime());
	}

	/**
	 * Adiciona x dias  data informada.
	 * 
	 * @param data
	 * @param dias
	 * @return Date
	 */
	public static Date adicionaDias(Date data, int dias) {
		GregorianCalendar date = new GregorianCalendar();
		date.setTime(data);
		date.add(Calendar.DATE, dias);
		Date novaData = date.getTime();
		return novaData;
	}

	  /**
	 * Converte para String para Date.
	 * @param data
	 * @param mascara
	 * @return Date
	 */
	public static Date converteStringParaDate(String data, String mascara) 
			throws ParseException {
	    
		return new SimpleDateFormat(mascara).parse(data);
	}
	
	/**
	 * Converte uma data de um determinado formato para um outro formato.
	 * @param mascaraAtual A mscara em que se encontra a data atual. Exemplos: 
	 * dd/MM/yyyy, yyyy-MM-dd HH:mm:SS
	 * @param mascaraDesejada A mscara para a qual quer converter a data.
	 * Exemplos: dd/MM/yyyy HH:mm:SS, yyyy-MM-dd
	 * @param data A data que se deseja mudar o formato
	 * @return A data no formato desejado
	 */
    public static String conveterData(String mascaraAtual, 
            String mascaraDesejada, String data) {
        
        String strDataRetorno = "";
        try {
            Date dataConvertida = new SimpleDateFormat(mascaraAtual).parse(
                    data);
            strDataRetorno = new SimpleDateFormat(mascaraDesejada).format(
                    dataConvertida);
        } catch (ParseException parseEx) {
        	parseEx.getMessage();
        }
        return strDataRetorno;
    }


}
