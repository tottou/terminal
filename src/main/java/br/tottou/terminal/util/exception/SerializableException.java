package br.tottou.terminal.util.exception;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class SerializableException {

	private String className;

	private String message;

	private SerializableException cause;

	public SerializableException() {
	}

	private static String getHostName() {
		try {
			return Inet4Address.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			throw new Error("Erro ao localizar o localhost", e);
		}
	}

	public SerializableException(Throwable toSerialize) {
		this(toSerialize, getHostName());
	}

	public SerializableException(Throwable toSerialize, String host) {
		try {
			this.message = toSerialize.getMessage();
		} catch (Throwable e) {
			this.message = toSerialize.getClass().getName() + ".getMessage() threw " + e.getClass().getName();
		}
		this.className = toSerialize.getClass().getName();
		if (toSerialize.getCause() != null) {
			this.cause = new SerializableException(toSerialize.getCause(), host);
		}
	}

	SerializableException(String className, String message, SerializableException cause) {
		super();
		this.className = className;
		this.message = message;
		this.cause = cause;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCause(SerializableException cause) {
		this.cause = cause;
	}

	public String getMessage() {
		return this.message;
	}

	public synchronized SerializableException getCause() {
		return this.cause;
	}

	@Override
	public String toString() {
		String message = getMessage();
		return this.className + (message == null ? "" : ": " + message);
	}
}