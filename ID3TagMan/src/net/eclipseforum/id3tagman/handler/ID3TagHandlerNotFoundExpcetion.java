package net.eclipseforum.id3tagman.handler;

public class ID3TagHandlerNotFoundExpcetion extends ID3TagHandlerException {

	public ID3TagHandlerNotFoundExpcetion(String handlerName) {
		super(handlerName + " is not found.");
	}

	public ID3TagHandlerNotFoundExpcetion(Exception e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8452106386997370669L;

}
