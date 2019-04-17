package br.com.maxicredito.ds.test;

import static java.util.logging.Level.FINE;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;

public class Extension implements javax.enterprise.inject.spi.Extension {
	private static final Logger logger = Logger.getLogger(Extension.class.getName());
	private final List<Step> steps = new LinkedList<Step>() {
		private static final long serialVersionUID = -519249438341601688L;

		{
			add(new JndiStep());
			add(new DsStep());
			add(new JtaStep());
		}
	};

	public void run(@Observes final BeforeBeanDiscovery event) {
		logger.log(FINE, "Iniciando criação de JNDI Server, DataSources e Contexto JTA");
		for (final Step step : steps) {
			step.run();
		}
	}
}
