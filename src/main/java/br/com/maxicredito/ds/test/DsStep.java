package br.com.maxicredito.ds.test;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.enterprise.inject.spi.Extension;
import javax.naming.InitialContext;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Cria datasource Baseados no arquivo datasources.properties.
 * 
 * @author Clairton Rodrigo Heinzen
 */
public class DsStep extends Step implements Extension {
	private final Logger logger = LogManager.getLogger(getClass());

	@Override
	public void run() {
		logger.debug("Criando do DataSources");
		try {
			final InitialContext context = new InitialContext();
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
					final String value = entry.getValue().toString();
					if (value.equalsIgnoreCase("datasource")) {
						names.add(entry.getKey().toString());
					}
				}
				for (final String name : names) {
					final BasicDataSource dataSource = new BasicDataSource();
					dataSource.setDriverClassName(file.get(name + ".driver").toString());
					dataSource.setUrl(file.get(name + ".url").toString());
					dataSource.setUsername(file.get(name + ".username").toString());
					dataSource.setPassword(file.get(name + ".password").toString());
					final String jndi = file.get(name + ".jndi").toString();
					context.bind(jndi, dataSource);
				}
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
