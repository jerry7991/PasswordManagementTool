package com.epam.singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Loggers {
	private static Loggers logger;
	private Logger LOGGER;

	private Loggers() {

	}

	public static Loggers getLogger() {
		if (logger == null) {
			logger = new Loggers();
		}
		return logger;
	}

	public void printError(final Class<?> clazz, String msg) {
		LOGGER = LogManager.getLogger(clazz);
		LOGGER.error(msg);
	}

	public void printInfo(final Class<?> clazz, String msg) {
//		System.out.println(msg);
		LOGGER = LogManager.getLogger(clazz);
		LOGGER.info(msg);
	}

	public void printFatal(final Class<?> clazz, String msg) {
		LOGGER = LogManager.getLogger(clazz);
		LOGGER.fatal(msg);
	}

	public void printDebug(final Class<?> clazz, String msg) {
		LOGGER = LogManager.getLogger(clazz);
		LOGGER.debug(msg);
	}

	public void printWarn(final Class<?> clazz, String msg) {
		LOGGER = LogManager.getLogger(clazz);
		LOGGER.warn(msg);
	}
}
