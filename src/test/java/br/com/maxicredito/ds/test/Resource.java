package br.com.maxicredito.ds.test;

import static javax.persistence.Persistence.createEntityManagerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@ApplicationScoped
public class Resource {
	@PersistenceUnit
	private EntityManagerFactory emf;

	@PostConstruct
	public void init() {
		if (emf == null) {
			emf = createEntityManagerFactory("people");
		}
	}

	@Produces
	@ApplicationScoped
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}

	@Produces
	@RequestScoped
	public EntityManager getEntityManager(final EntityManagerFactory factory) {
		return factory.createEntityManager();
	}

	public void dispose(@Disposes final EntityManager manager) {
		if (manager.isOpen()) {
			manager.close();
		}
	}

	public void dispose(@Disposes final EntityManagerFactory factory) {
		if (factory.isOpen()) {
			factory.close();
		}
	}
}
