package net.eclipseforum.id3tagman.handler;

import java.io.File;

import net.eclipseforum.id3tagman.TagProperty.IDs;

// http://www.javazoom.net/javalayer/javalayer.html
public class JLayerTagHandler implements ITagHandler {

	public String getName() {
		return "JLayer - MP3 library";
	}

	public String getProperty(IDs id) throws TagPropertyException {
		// TODO Auto-generated method stub
		return null;
	}

	public void load(File file) throws TagHandlerException {
		// TODO Auto-generated method stub

	}

	public void save() throws TagHandlerException {
		// TODO Auto-generated method stub

	}

	public void setProperty(IDs id, String value) throws TagPropertyException {
		// TODO Auto-generated method stub

	}

}
