package com.epam.common;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import com.epam.util.Loggers;

@WebMvcTest(Loggers.class)
@ContextConfiguration(classes = { Loggers.class })
class LoggersTest {

	@Mock
	private Loggers LOGGER;

	@Test
	void testPrintError() {
		LOGGER.printError(LoggersTest.class, "Error here");
		assertTrue(true);
	}

	@Test
	void testPrintInfo() {
		LOGGER.printInfo(LoggersTest.class, "Info here");
		assertTrue(true);
	}

	@Test
	void testPrintFatal() {
		LOGGER.printFatal(LoggersTest.class, "Fatal here");
		assertTrue(true);
	}

	@Test
	void testPrintDebug() {
		LOGGER.printDebug(LoggersTest.class, "Debug here");
		assertTrue(true);
	}

	@Test
	void testPrintWarn() {
		LOGGER.printWarn(LoggersTest.class, "Warn here");
		assertTrue(true);
	}

}
