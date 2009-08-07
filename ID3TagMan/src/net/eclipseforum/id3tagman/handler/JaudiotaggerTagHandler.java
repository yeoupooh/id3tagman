package net.eclipseforum.id3tagman.handler;

import java.io.File;
import java.io.IOException;

import net.eclipseforum.id3tagman.TagProperty.IDs;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;

// http://www.jthink.net/jaudiotagger/devenv.jsp
public class JaudiotaggerTagHandler implements ITagHandler {

	private MP3File mp3File = null;
	private MP3AudioHeader header = null;

	public String getName() {
		return "Jaudiotagger";
	}

	public String getProperty(IDs id) throws TagPropertyException {
		// TODO Auto-generated method stub
		return null;
	}

	public void load(File file) throws TagHandlerException {
		try {
			mp3File = (MP3File) AudioFileIO.read(file);
			header = (MP3AudioHeader) mp3File.getAudioHeader();
		} catch (CannotReadException e) {
			e.printStackTrace();
			throw new TagHandlerException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new TagHandlerException(e);
		} catch (TagException e) {
			e.printStackTrace();
			throw new TagHandlerException(e);
		} catch (ReadOnlyFileException e) {
			e.printStackTrace();
			throw new TagHandlerException(e);
		} catch (InvalidAudioFrameException e) {
			e.printStackTrace();
			throw new TagHandlerException(e);
		}
	}

	public void save() throws TagHandlerException {
		// TODO Auto-generated method stub

	}

	public void setProperty(IDs id, String value) throws TagPropertyException {
		// TODO Auto-generated method stub

	}

}
