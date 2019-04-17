package br.com.maxicredito.ds.test;

import static java.util.logging.Level.FINE;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;

public class Extension implements javax.enterprise.inject.spi.Extension {
	private static final Logger logger = Logger.getLogger(Extension.class.getName());
	private final List<Step> steps = new LinkedList<Step>();
	
	public Extension() {
		steps.add(new JndiStep());
		steps.add(new DsStep());
		steps.add(new JtaStep());
	}

	public void executar(final @Observes BeforeBeanDiscovery bdv) {
		logger.log(FINE, "Iniciando criação de JNDI Server, DataSources e Contexto JTA");
		for (final Step step : steps) {
			step.run();
		}
	}
}
