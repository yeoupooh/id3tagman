package net.eclipseforum.id3tagman.test;

import java.util.Set;

import junit.framework.TestCase;
import net.eclipseforum.id3tagman.handler.ID3TagHandlerException;
import net.eclipseforum.id3tagman.handler.ID3TagHandlerFactory;
import net.eclipseforum.id3tagman.handler.IID3TagHandler;
import net.eclipseforum.id3tagman.handler.impl.TestID3TagHandler;

public class ID3TagHandlerFactoryTest extends TestCase {
	private ID3TagHandlerFactory factory = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		factory = ID3TagHandlerFactory.getInstance();
	}

	public void testGetInstance() {
		assertNotNull(factory);
	}

	public void testGetHandler() throws ID3TagHandlerException {
		assertNotNull(factory.getHandler("Test"));

		TestID3TagHandler testHandler = new TestID3TagHandler();
		IID3TagHandler testHandlerFromFactory = factory.getHandler("Test");

		assertEquals(testHandler.getClass(), testHandlerFromFactory.getClass());
	}

	public void testGetHandlerNames() throws ID3TagHandlerException {
		assertNotNull(factory.getHandlerNames());

		Set<String> keys = factory.getHandlerNames();
		for (String key : keys) {
			System.out.println("key=" + key);
			assertNotNull(factory.getHandler(key));
		}
	}

	public void testGetHandlerDescription() {
		assertNotNull(factory.getHandlerDescription("Test"));
	}

}
