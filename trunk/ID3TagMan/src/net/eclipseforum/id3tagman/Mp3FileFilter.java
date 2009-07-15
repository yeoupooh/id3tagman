package net.eclipseforum.id3tagman;
import java.io.File;
import java.io.FileFilter;

public class Mp3FileFilter implements FileFilter {

	public boolean accept(File pathname) {
		return pathname.getName().toLowerCase().endsWith(".mp3");
	}
}
