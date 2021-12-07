package com.epam.singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reader {
	private static BufferedReader reader;

	private Reader() {

	}

	public static BufferedReader getReader() {
		if (reader == null) {
			reader = new BufferedReader(new InputStreamReader(System.in));
		}
		return reader;
	}

	public static boolean closeReader() {
		boolean closed = true;
		try {
			reader.close();
		} catch (IOException e) {
			closed = false;
			e.printStackTrace();
		}
		return closed;
	}
}
