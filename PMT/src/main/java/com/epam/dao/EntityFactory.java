package com.epam.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityFactory {
	private static EntityManagerFactory factory;

	private EntityFactory() {

	}

	public static EntityManagerFactory getEntityManagerFactory() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("PMT_PROJECT");
		}
		return factory;
	}

	public static void closeEntityFactory() {
		if (factory != null) {
			factory.close();
		}
	}
}
