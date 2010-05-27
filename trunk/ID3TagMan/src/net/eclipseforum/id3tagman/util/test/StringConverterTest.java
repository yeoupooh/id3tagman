package net.eclipseforum.id3tagman.util.test;

import net.eclipseforum.id3tagman.util.StringConverter;
import junit.framework.TestCase;

public class StringConverterTest extends TestCase {

	public void testConvert() {
		String src = "한글";
		String srcEncoding = "UTF-8";
		String dstEncoding = "MacRoman";
		StringConverter converter1 = new StringConverter(srcEncoding,
				dstEncoding);
		String converted1 = converter1.convert(src);
		StringConverter converter2 = new StringConverter(dstEncoding,
				srcEncoding);
		String converted2 = converter2.convert(converted1);
		assertEquals(src, converted2);
	}

}
