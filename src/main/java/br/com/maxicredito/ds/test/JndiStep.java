package br.com.maxicredito.ds.test;

import static java.lang.System.setProperty;
import static java.util.logging.Level.FINE;
import static javax.naming.Context.INITIAL_CONTEXT_FACTORY;

import java.util.logging.Logger;

import javax.enterprise.inject.spi.Extension;

import org.jboss.as.naming.InMemoryNamingStore;
import org.jboss.as.naming.InitialContextFactory;
import org.jboss.as.naming.NamingContext;

public class JndiStep extends Step implements Extension {
	private static final Logger logger = Logger.getLogger(JndiStep.class.getName());

	@Override
	public void run() {
		logger.log(FINE, "Iniciando JNDI server");
		NamingContext.setActiveNamingStore(new InMemoryNamingStore());
		final String factory = InitialContextFactory.class.getName();
		setProperty(INITIAL_CONTEXT_FACTORY, factory);
	}
}
