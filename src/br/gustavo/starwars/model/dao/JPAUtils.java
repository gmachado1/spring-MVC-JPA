package br.gustavo.starwars.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {

	private static EntityManagerFactory emf;

	public static EntityManager getEntityManager() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("planets");
		}
		return emf.createEntityManager();
	}
}
