package br.tottou.terminal.util;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import lombok.extern.log4j.Log4j;

@Log4j
public class Msg {

	private static Configuration defaults;
	private static CompositeConfiguration cc = new CompositeConfiguration();
	
	static {
		try {
			defaults = new PropertiesConfiguration(Msg.class.getResource("/messages_pt_BR.properties"));
			cc.addConfiguration(defaults);
		} catch (ConfigurationException e) {
			log.error("Erro ao carregar arquivos de configuração", e);
		}

	}

	public static String getMensagem(String chave) {
		log.debug("chave da mensagem: [" + chave + "]");
		String msg = cc.getString(chave);
		if(msg != null){
			return msg;			
		}else{
			return chave;
		}	
	}

	public static String getMensagem(String chave, String parametro) {
		log.debug("chave da mensagem: [" + chave + "]");
		String mensagem = cc.getString(chave);
		return mensagem.replace("{0}", parametro);
	}

	public static String getMensagem(String chave, String[] parametros) {
		log.debug("chave da mensagem: [" + chave + "]");
		String mensagem = cc.getString(chave);
		for (int i = 0; i < parametros.length; i++)
			mensagem = mensagem.replace("{" + i + "}", parametros[i]);

		return mensagem;
	}
	
}
