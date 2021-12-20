package com.epam.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Loggers {
	private Logger logger;

	private Loggers() {

	}

	public void printError(final Class<?> clazz, String msg) {
		logger = LogManager.getLogger(clazz);
		logger.error(msg);
	}

	public void printInfo(final Class<?> clazz, String msg) {
		logger = LogManager.getLogger(clazz);
		logger.info(msg);
	}

	public void printFatal(final Class<?> clazz, String msg) {
		logger = LogManager.getLogger(clazz);
		logger.fatal(msg);
	}

	public void printDebug(final Class<?> clazz, String msg) {
		logger = LogManager.getLogger(clazz);
		logger.debug(msg);
	}

	public void printWarn(final Class<?> clazz, String msg) {
		logger = LogManager.getLogger(clazz);
		logger.warn(msg);
	}
}
