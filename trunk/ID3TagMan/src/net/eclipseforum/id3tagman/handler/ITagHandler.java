package net.eclipseforum.id3tagman.handler;

import java.io.File;

import net.eclipseforum.id3tagman.TagProperty;

public interface ITagHandler {
	public void load(File file) throws TagHandlerException;

	public String getProperty(TagProperty.IDs id) throws TagPropertyException;

	public void setProperty(TagProperty.IDs id, String value) throws TagPropertyException;

	public void save() throws TagHandlerException;
}
