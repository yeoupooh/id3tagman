package net.eclipseforum.id3tagman.test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import junit.framework.TestCase;
import net.eclipseforum.id3tagman.TagProperty.IDs;
import net.eclipseforum.id3tagman.handler.ID3TagHandlerException;
import net.eclipseforum.id3tagman.handler.ID3TagHandlerFactory;
import net.eclipseforum.id3tagman.handler.ID3TagHandlerNotFoundExpcetion;
import net.eclipseforum.id3tagman.handler.IID3TagHandler;
import net.eclipseforum.id3tagman.util.FileCopy;

public class TagHandlerTestCase extends TestCase {
	// public enum IDs {
	// FileName(0), Title(1), TrackNumber(2), Artist(3), Album(4), AlbumTitle(
	// 5), Genre(6), Authors(7), Comment(8), Year(9);
	//
	// private int index;
	//
	// IDs(int index) {
	// this.index = index;
	// }
	//
	// public int index() {
	// return this.index;
	// }
	// };

	// - get file list from dir
	// - read mp3 file
	// - read tag info and parse
	// - get properties
	// - convert if needed
	// - set properties
	// - save tag info
	// - save file
	public void test() throws IOException {
		// - get file list from dir
		File dir = new File("/Users/yeoupooh/");
		String[] fileNames = dir.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.toLowerCase().endsWith(".mp3"))
					return true;
				return false;
			}
		});

		// - read mp3 file
		if (fileNames == null) {
			return;
		}

		// String handlerName = "Javamp3";
		String handlerName = "Jaudiotagger";

		int i = 0;

		// for (int i = 0; i < fileNames.length; i++) {
		String fileName = dir + "/" + fileNames[i];
		System.out.println("src file=[" + fileName + "]");

		String testFileName = fileName + ".test.mp3";
		System.out.println("test file=[" + testFileName + "]");

		File testFile = new File(testFileName);
		if (testFile.exists()) {
			testFile.delete();
		}
		FileCopy.copy(fileName, testFileName);

		ID3TagHandlerFactory factory = ID3TagHandlerFactory.getInstance();
		IID3TagHandler handler;
		try {
			handler = factory.getHandler(handlerName);

			// - read tag info and parse
			handler.load(testFileName);

			// - get properties
			System.out
					.println("title=[" + handler.getProperty(IDs.Title) + "]");
			System.out
					.println("album=[" + handler.getProperty(IDs.Album) + "]");
			System.out.println("genr=[" + handler.getProperty(IDs.Genre) + "]");
			System.out.println("artist=[" + handler.getProperty(IDs.Artist)
					+ "]");

			// - convert if needed

			// - set properties
			setProperty(handler, IDs.Title, "new title");
			setProperty(handler, IDs.Artist, "new artist");
			setProperty(handler, IDs.Album, "new album");
			setProperty(handler, IDs.Genre, "new genre");

			// - save tag info
			// - save file
			handler.save();
			System.out.println("saved.");
		} catch (ID3TagHandlerNotFoundExpcetion e1) {
			e1.printStackTrace();
		} catch (ID3TagHandlerException e) {
			System.err.println("error in loading mp3");
			e.printStackTrace();
		}

		// } // for

		System.out.println("done.");
	}

	private void setProperty(IID3TagHandler handler, IDs id, String value)
			throws ID3TagHandlerException {
		handler.setProperty(id, value);
		System.out.println("set " + id + "=[" + value + "]");
	}
}
