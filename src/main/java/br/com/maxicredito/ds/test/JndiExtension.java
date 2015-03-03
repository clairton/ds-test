package br.com.maxicredito.ds.test;

import static java.lang.System.setProperty;
import static javax.naming.Context.INITIAL_CONTEXT_FACTORY;

import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.as.naming.InMemoryNamingStore;
import org.jboss.as.naming.InitialContextFactory;
import org.jboss.as.naming.NamingContext;

public class JndiExtension extends Passo<BeforeBeanDiscovery> implements
		Extension {
	private final Logger logger = LogManager.getLogger(getClass().getName());

	@Override
	public void executar() {
		logger.debug("Iniciando JNDI server");
		NamingContext.setActiveNamingStore(new InMemoryNamingStore());
		final String factory = InitialContextFactory.class.getName();
		setProperty(INITIAL_CONTEXT_FACTORY, factory);
	}
}
