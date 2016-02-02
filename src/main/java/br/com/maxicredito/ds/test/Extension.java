package br.com.maxicredito.ds.test;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Extension implements javax.enterprise.inject.spi.Extension {
	private static final Logger logger = LogManager.getLogger(Extension.class);
	private final List<Step> steps = new LinkedList<Step>();
	
	public Extension() {
		steps.add(new JndiStep());
		steps.add(new DsStep());
		steps.add(new JtaStep());
	}

	public void executar(final @Observes ProcessProducer<?, EntityManager> observer) {
		logger.debug("Iniciando criação de JNDI Server, DataSources e Contexto JTA");
		for (final Step step : steps) {
			step.run();
		}
	}
}
