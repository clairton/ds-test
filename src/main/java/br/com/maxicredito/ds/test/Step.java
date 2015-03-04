package br.com.maxicredito.ds.test;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.persistence.EntityManager;

public abstract class Step {

	public void run(@Observes ProcessProducer<?, EntityManager> observer) {
		run();
	}

	abstract void run();
}
