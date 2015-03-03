package br.com.maxicredito.ds.test;

import javax.enterprise.event.Observes;

public abstract class Passo<T> {

	public void executar(final @Observes T observer) {
		executar();
	}

	abstract void executar();
}
