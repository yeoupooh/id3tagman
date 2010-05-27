package net.eclipseforum.id3tagman.handler.impl;

import net.eclipseforum.id3tagman.TagProperty.IDs;
import net.eclipseforum.id3tagman.handler.IID3TagHandler;

public class TestID3TagHandler implements IID3TagHandler {

	public void load(String fileName) {
		// TODO Auto-generated method stub

	}

	public String getProperty(IDs id) {
		switch (id) {
		case Title:
			return "test title";
		}

		return null;
	}

	public void save() {
		// TODO Auto-generated method stub
		
	}

	public void setProperty(IDs id, String value) {
		// TODO Auto-generated method stub
		
	}

}
