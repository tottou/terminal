package br.tottou.terminal.util.exception;

public class ConexaoBDException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2481265486582628365L;

	public ConexaoBDException() {
		super("Erro de conexão com o banco de dados.");
	}

	public ConexaoBDException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ConexaoBDException(String mensagem, Throwable arg1) {
		super(mensagem, arg1);
	}

	public ConexaoBDException(String uf) {
		super("Erro de conexão com o banco de dados no tribunal : " + uf);
	}

	public ConexaoBDException(Throwable arg0) {
		super(arg0);
	}
	
	

}
