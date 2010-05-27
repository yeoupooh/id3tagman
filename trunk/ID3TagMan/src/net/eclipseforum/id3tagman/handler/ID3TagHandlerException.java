package net.eclipseforum.id3tagman.handler;

public class ID3TagHandlerException extends Exception {

	public ID3TagHandlerException(Exception e) {
		super(e);
	}

	public ID3TagHandlerException(String msg) {
		super(msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4550398876091969376L;

}
