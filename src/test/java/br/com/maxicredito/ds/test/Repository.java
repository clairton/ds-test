package br.com.maxicredito.ds.test;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

public class Repository implements Serializable {
	private static final long serialVersionUID = -3179335354498565442L;

	private EntityManager manager;

	@Inject
	public Repository(final EntityManager manager) {
		this.manager = manager;
	}

	@Transactional
	public void save(final Person person) {
		manager.persist(person);
		try {
			manager.joinTransaction();
		} catch (final TransactionRequiredException e) {
		}
		manager.flush();
	}
}
