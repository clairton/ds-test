package br.com.maxicredito.extension.test;

import static java.lang.System.setProperty;
import static javax.naming.Context.INITIAL_CONTEXT_FACTORY;
import static org.hsqldb.jdbc.JDBCDataSourceFactory.createDataSource;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.as.naming.InMemoryNamingStore;
import org.jboss.as.naming.InitialContextFactory;
import org.jboss.as.naming.NamingContext;

public class DataSourceExtension implements
		javax.enterprise.inject.spi.Extension {
	private final Logger logger = LogManager.getLogger(getClass().getName());
	private InitialContext context;

	public void jndi(final @Observes BeforeBeanDiscovery bd) throws Exception {
		logger.debug("Iniciando JNDI server");
		NamingContext.setActiveNamingStore(new InMemoryNamingStore());
		final String factory = InitialContextFactory.class.getName();
		setProperty(INITIAL_CONTEXT_FACTORY, factory);
	}

	public void dataSource(final @Observes AfterDeploymentValidation adv)
			throws Exception {
		logger.debug("Criando do DataSources");
		context = new InitialContext();
		final String fileName = "datasources.properties";
		final ClassLoader loader = getClass().getClassLoader();
		final Enumeration<URL> resources = loader.getResources(fileName);
		while (resources.hasMoreElements()) {
			final URL url = resources.nextElement();
			final InputStream stream = url.openStream();
			final Properties file = new Properties();
			file.load(stream);
			final Set<String> names = new HashSet<String>();
			for (final Entry<Object, Object> entry : file.entrySet()) {
				if (entry.getValue().toString().equalsIgnoreCase("datasource")) {
					names.add(entry.getKey().toString());
				}
			}
			for (final String name : names) {
				final Properties p = new Properties();
				p.put("url", file.get(name + ".url"));
				p.put("username", file.get(name + ".username"));
				p.put("password", file.get(name + ".password"));
				p.put("jndi", file.get(name + ".jndi"));
				bind(p);
			}

		}
	}

	private void bind(final Properties properties) throws Exception {
		final DataSource dataSource = createDataSource(properties);
		final String name = properties.getProperty("jndi");
		context.bind(name, dataSource);
	}
}
