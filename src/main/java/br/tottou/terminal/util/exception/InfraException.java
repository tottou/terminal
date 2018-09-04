package br.tottou.terminal.util.exception;

@SuppressWarnings("serial")
public class InfraException extends Exception {

	public InfraException() {
	    super();
    }


    public InfraException(String message) {
		super(message);
	}


    public InfraException(String message, Throwable cause) {
		super(message, cause);
	}


    public InfraException(Throwable cause) {
		super(cause);
	}
	
}
