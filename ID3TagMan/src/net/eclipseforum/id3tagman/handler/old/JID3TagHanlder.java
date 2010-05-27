package net.eclipseforum.id3tagman.handler.old;

import java.io.File;

import net.eclipseforum.id3tagman.TagProperty.IDs;

import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.ID3Tag;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.MediaFile;
import org.blinkenlights.jid3.v1.ID3V1_0Tag;
import org.blinkenlights.jid3.v2.ID3V2_3_0Tag;

//
// http://jid3.blinkenlights.org/
// NOT WORKING
// 2010-05-25: Failed to read 01 아름다운 이별.mp3 file.
//
public class JID3TagHanlder implements ITagHandler {
	private ID3Tag[] aoID3Tag = null;

	public String getName() {
		return "JID3 - A JavaTM ID3 Class Library Implementation (http://jid3.blinkenlights.org/)";
	}

	public String getProperty(IDs id) throws TagPropertyException {
		// let's loop through and see what we've got
		// (NOTE: we could also use getID3V1Tag() or getID3V2Tag() methods,
		// if
		// we specifically want one or the other)
		for (int i = 0; i < aoID3Tag.length; i++) {
			// check to see if we read a v1.0 tag, or a v2.3.0 tag (just for
			// example..)
			if (aoID3Tag[i] instanceof ID3V1_0Tag) {
				ID3V1_0Tag oID3V1_0Tag = (ID3V1_0Tag) aoID3Tag[i];

				switch (id) {
				case Title:
					return oID3V1_0Tag.getTitle();
				}

			} else if (aoID3Tag[i] instanceof ID3V2_3_0Tag) {
				ID3V2_3_0Tag oID3V2_3_0Tag = (ID3V2_3_0Tag) aoID3Tag[i];

				// check if this v2.3.0 frame contains a title, using the actual
				// frame name
				if (oID3V2_3_0Tag.getTIT2TextInformationFrame() != null) {
					switch (id) {
					case Title:
						return oID3V2_3_0Tag.getTIT2TextInformationFrame()
								.getTitle();
					case Year:
						// but check using the convenience method if it has a
						// year set (either way works)
						try {
							return new Integer(oID3V2_3_0Tag.getYear())
									.toString(); // reads TYER frame
						} catch (ID3Exception e) {
							// error getting year.. if one wasn't set
							System.out.println("Could get read year from tag: "
									+ e.toString());
						}
					}
				}
			}
		} // for

		return null;
	}

	public void load(File file) throws TagHandlerException {

		// create an MP3File object representing our chosen file
		MediaFile oMediaFile = new MP3File(file);

		// any tags read from the file are returned, in an array, in an order
		// which you should not assume
		try {
			aoID3Tag = oMediaFile.getTags();
		} catch (ID3Exception e1) {
			throw new TagHandlerException(e1);
		}
	}

	public void save() throws TagHandlerException {
		// TODO Auto-generated method stub

	}

	public void setProperty(IDs id, String value) throws TagPropertyException {
		// TODO Auto-generated method stub

	}

}
