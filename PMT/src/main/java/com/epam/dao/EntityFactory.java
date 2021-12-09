package com.epam.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

@Component
public class EntityFactory {
	private EntityManagerFactory factory;

	public EntityManagerFactory getEntityManagerFactory() {
		if (factory == null) {
			System.out.println("runned factory");
			factory = Persistence.createEntityManagerFactory("PMT_PROJECT");
		}
		return factory;
	}

	public void closeEntityFactory() {
		if (factory != null) {
			factory.close();
		}
	}
}
