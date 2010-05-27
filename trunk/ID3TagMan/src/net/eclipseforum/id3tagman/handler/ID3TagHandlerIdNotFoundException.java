package net.eclipseforum.id3tagman.handler;

import net.eclipseforum.id3tagman.TagProperty.IDs;

public class ID3TagHandlerIdNotFoundException extends ID3TagHandlerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1205413332259647050L;

	public ID3TagHandlerIdNotFoundException(IDs id) {
		super(id + " is not found");
	}

}
