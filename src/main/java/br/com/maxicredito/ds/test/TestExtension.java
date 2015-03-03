package br.com.maxicredito.ds.test;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Extension;

public class TestExtension implements Extension {
	private final List<Passo<?>> passos = new LinkedList<Passo<?>>() {
		private static final long serialVersionUID = 1L;
		{
			add(new JndiExtension());
			add(new DsExtension());
			add(new JtaExtension());
		}
	};

	public void executar(final @Observes AfterDeploymentValidation observer) {
		for (final Passo<?> passo : passos) {
			passo.executar();
		}
	}
}
