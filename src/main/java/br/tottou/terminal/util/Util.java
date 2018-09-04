package br.tottou.terminal.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.Normalizer;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;


public class Util {

	public static final Integer				ORDEM_CRESCENTE		= 1;
	public static final Integer				ORDEM_DECRESCENTE	= -1;
	
	/**
	 * Retorna o nome do host.
	 * 
	 * @return
	 */
	public static String getComputerName() {
		String serverName = "";
		try {
			serverName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return serverName;
	}

	// ------------------------------------- VALICAES -------------------------------------------------------

	/**
	 * Verifica se o valor est branco.
	 * 
	 *
	 * @param <T>
	 * @param valor
	 * @return
	 */
	public static <T> boolean isValorEmBranco(T valor) {
		try {
			return valor.toString().trim().equals("");
		} catch (NullPointerException e) {
			return false;
		}
	}

	/**
	 * Verifica se o valor est nulo ou branco
	 * 
	 *
	 * @param <T>
	 * @param valor
	 * @return
	 */
	public static <T> boolean isNullOuBranco(T valor) {
		return valor == null || isValorEmBranco(valor);
	}
	
	
	/**
	 * Verifica se o valor est nulo ou branco
	 * 
	 *
	 * @param <T>
	 * @param valor
	 * @return
	 */
	public static <T> boolean isNullOuBrancoString(T valor) {
		return valor == null || valor.toString().trim().equals("") || valor.toString().trim().equals("null");
	}

	/**
	 * Verifica se o valor NO est nulo ou branco
	 * 
	 *
	 * @param <T>
	 * @param valor
	 * @return
	 */
	public static <T> boolean isNotNullOuBranco(T valor) {
		return valor != null || isValorEmBranco(valor);
	}

	/**
	 * Verifica se o valor e nulo
	 * 
	 *
	 * @param valor
	 * @return TRUE se nulo.
	 */
	public static <T> boolean isNull(T valor) {
		return valor == null;
	}

	/**
	 * Verifica se o valor no  nulo
	 * 
	 *
	 *
	 * @param valor
	 * @return TRUE se nulo.
	 */
	public static <T> boolean isNotNull(T valor) {
		return valor != null; 
	}

	/**
	 * Mtodo utilizado para ordenar uma coleo de objetos de acordo com o
	 * atributo passado como parametro.
	 * 
	 * @param classe
	 *            - Classe a qual o objeto inserido na coleo pertence.
	 * @param colecao
	 *            - Coleo a qual ser ordenada.
	 * @param atributo
	 *            - Nome do atributo o qual se quer ordenar.
	 * @param Ordem
	 * @see Util.ORDEM_CRESCENTE, Util.ORDEM_DECRESCENTE
	 * 
	 * @return a coleo ordenada.
	 */
	public static Collection<?> ordenar(Class<?> classe, Collection<?> colecao, String atributo, final int ordem) {

		if (colecao == null) {
			return null;
		}
		final Field field = getField(classe, atributo);
		field.setAccessible(true);

	Collections.sort((List<?>) colecao, new Comparator<Object>() {

			public int compare(Object obj1, Object obj2) {

				try {
					Object o1 = field.get(obj1);
					Object o2 = field.get(obj2);
					String s1 = o1.toString().toLowerCase();
					String s2 = o2.toString().toLowerCase();
					return (ordem) * s1.compareTo(s2);

				} catch (IllegalAccessException ex) {
					throw new RuntimeException(ex);
				}
			}
		});
		

		return colecao;
	}

	public static String getExtensaoArquivo(String nomeArquivo) {
		String retorno = "";
		String ext[] = nomeArquivo.split("\\.");
		int i = ext.length;
		if (i > 1) {
			retorno = ext[i - 1];
		}
		return retorno.toUpperCase();
	}
	
	
	/**
	 * Retorna o atributo da classe.
	 * 
	 * @param classe
	 * @param campo
	 *
	 * @return
	 */
	private static Field getField(Class<?> classe, String campo) {
		Field f = null;
		try {
			f = classe.getDeclaredField(campo);
			f.setAccessible(true);
		} catch (NullPointerException e) {
			throw new RuntimeException("Campo " + campo + " Inexistente!");
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			f = getField(classe.getSuperclass(), campo);
		}
		return f;
	}


	/**
	 * Abre o arquivo associado.
	 *
	 * @param arquivo
	 */
	public static void abrirArquivo(String arquivo) {
		try {
			Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+arquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static int valueOfInteger(String numero) {
    	try {
	    	if (isNullOuBranco(numero) || numero.equals("null")) { 
	    		return 0;
	    	}
    	} catch (NumberFormatException e) {
    		return 0;
    	}
    	return new Integer(numero).intValue();
    }
    
    
    public static boolean valueOfBoolean(String valor) {
    	try {
	    	if (isNullOuBranco(valor) || valor.equals("null")) { 
	    		return new Boolean(false).booleanValue();
	    	} else if (valor.equalsIgnoreCase(Constantes.FALSE)){
	    		return new Boolean(false).booleanValue();
	    	}
    	} catch (NumberFormatException e) {
    		return new Boolean(false).booleanValue();
    	}
    	return new Boolean(true).booleanValue();
    }
    
	public static boolean isNumero(String numero) {
		try {
			Integer.parseInt(numero);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	

	public static boolean isServidorJboss() {
		return !isNullOuBranco(System.getProperty("jboss.home.dir"));
	}
	
    public static boolean isValidaBoolean(String valor) {
    	if (!isNullOuBranco(valor) && valor.equals("1")) {
    		return true;
    	} 
    	return false;
    }

	
	public static boolean isString(String texto) {
		if(texto.contains("^[a-Z]")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Converte
	 *
	 * @param texto
	 * @return
	 */
	public static String convertUTF8(String texto) {
		StringBuffer ostr = new StringBuffer();
		for (int i = 0; i < texto.length(); i++) {
			char ch = texto.charAt(i);
			if ((ch >= 0x0020) && (ch <= 0x007e)) {
				ostr.append(ch);
			} else {
				ostr.append("\\u");
				String hex = Integer.toHexString(texto.charAt(i) & 0xFFFF);
				for (int j = 0; j < 4 - hex.length(); j++) {
					ostr.append("0");
				}
				ostr.append(hex.toLowerCase());
			}
		}
		return (new String(ostr));
	}
	
	public static String convertToISO(String string) {
        Charset charsetUtf8 = Charset.forName("UTF-8");
        CharsetEncoder encoder = charsetUtf8.newEncoder();

        Charset charsetIso88591 = Charset.forName("ISO-8859-1");
        CharsetDecoder decoder = charsetIso88591.newDecoder();
        String s = "";
        try {
                ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(string));

                CharBuffer cbuf = decoder.decode(bbuf);
                s = cbuf.toString();
        } catch (CharacterCodingException e) {
                e.printStackTrace();
        }
        return s;
	} 
	
	
	/**
	 * Remove acentos
	 *
	 */
	public static String removeAcentos(final String texto) {
		String strSemAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD);
		return strSemAcentos.replaceAll("[^\\p{ASCII}]", "");
	}
	 
	
	/**
	 * Copia propriedades
	 * Obs: Converter quando a data vim nulo
	 *
	 */
	public static void copiarPropriedades(Object destino, Object origem) throws Exception {
		try {
			Date valorPadrao = null;
			DateConverter conversor = new DateConverter(valorPadrao);
			ConvertUtils.register(conversor, java.util.Date.class);
			BeanUtils.copyProperties(destino, origem);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public static byte[] converterInputStreamParaByte(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];

		for (int dataSize = 0; dataSize != -1; dataSize = input.read(buf)) {
			output.write(buf, 0, dataSize);
		}
		return output.toByteArray();
	}
	
	
	/**
	 * Salva arquivo 
	 * @param caminhoArquivo
	 * @param arquivo
	 * @throws IOException
	 */
	public static void setArquivoPasta(String caminhoArquivo, byte[] arquivo) throws IOException{
		FileOutputStream out = new FileOutputStream(caminhoArquivo);
		out.write(arquivo);
		out.close();
	}
	
	public static String getExceptionString(final Throwable throwable) {
	     final StringWriter sw = new StringWriter();
	     final PrintWriter pw = new PrintWriter(sw, true);
	     throwable.printStackTrace(pw);
	     return sw.getBuffer().toString();
	}
	
}