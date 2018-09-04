package br.tottou.terminal.util.exception;

public class RegraException extends Exception {

	private static final long serialVersionUID = 2481265486582628365L;


	public RegraException(String mensagem, Throwable throwable) {
		super(mensagem, throwable);
	}

	
	public RegraException(String mensagem) {
		super(mensagem);
	}
	

}
