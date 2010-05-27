package net.eclipseforum.id3tagman.handler;

import net.eclipseforum.id3tagman.TagProperty;
import net.eclipseforum.id3tagman.TagProperty.IDs;

public interface IID3TagHandler {

	void load(String fileName) throws ID3TagHandlerException;

	String getProperty(TagProperty.IDs id) throws ID3TagHandlerException;

	void setProperty(IDs id, String value) throws ID3TagHandlerException;

	void save() throws ID3TagHandlerException;

}
