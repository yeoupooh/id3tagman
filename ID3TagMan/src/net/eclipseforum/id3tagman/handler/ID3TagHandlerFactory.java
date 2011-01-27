package net.eclipseforum.id3tagman.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Set;

import net.eclipseforum.id3tagman.handler.impl.JaudiotaggerID3TagHandler;
import net.eclipseforum.id3tagman.handler.impl.JaudiotaggerID3v24TagHandler;
import net.eclipseforum.id3tagman.handler.impl.Javamp3ID3TagHandler;

public class ID3TagHandlerFactory {
	private static ID3TagHandlerFactory instance = null;
	private HashMap<String, Type> handlers = new HashMap<String, Type>();

	public ID3TagHandlerFactory() {
		// handlers.put("Test", TestID3TagHandler.class);
		handlers.put("Javamp3", Javamp3ID3TagHandler.class);
		handlers.put("Jaudiotagger", JaudiotaggerID3TagHandler.class);
		handlers.put("Jaudiotagger ID3 v2.4",
				JaudiotaggerID3v24TagHandler.class);
	}

	public static ID3TagHandlerFactory getInstance() {
		if (instance == null) {
			instance = new ID3TagHandlerFactory();
		}

		return instance;
	}

	public IID3TagHandler getHandler(String handlerName)
			throws ID3TagHandlerException {
		if (handlers.containsKey(handlerName) == false) {
			throw new ID3TagHandlerNotFoundExpcetion(handlerName);
		}

		Object object = null;
		try {
			// Class<?> clazz = Class.forName(handlers.get(name).getClass()
			// .getName());
			Class<?> clazz = (Class<?>) handlers.get(handlerName);
			java.lang.reflect.Constructor<?> con = clazz.getConstructor();
			object = con.newInstance(new Object[] {});
			// object = clazz.newInstance();
			// } catch (ClassNotFoundException e) {
			// throw new ID3TagHandlerException(e);
		} catch (InstantiationException e) {
			throw new ID3TagHandlerException(e);
		} catch (IllegalAccessException e) {
			throw new ID3TagHandlerException(e);
		} catch (SecurityException e) {
			throw new ID3TagHandlerException(e);
		} catch (NoSuchMethodException e) {
			throw new ID3TagHandlerException(e);
		} catch (IllegalArgumentException e) {
			throw new ID3TagHandlerException(e);
		} catch (InvocationTargetException e) {
			throw new ID3TagHandlerException(e);
		}

		return (IID3TagHandler) object;
	}

	public Set<String> getHandlerNames() {
		return handlers.keySet();
	}

	public Object getHandlerDescription(String handlerName) {
		// TODO Auto-generated method stub
		return null;
	}

}
