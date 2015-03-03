package br.com.maxicredito.ds.test;

import static com.arjuna.ats.jta.common.jtaPropertyManager.getJTAEnvironmentBean;
import static java.lang.System.setProperty;

import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.arjuna.ats.jta.common.JTAEnvironmentBean;
import com.arjuna.ats.jta.utils.JNDIManager;

public class JtaExtension extends Passo<BeforeBeanDiscovery> implements
		Extension {
	private final Logger logger = LogManager.getLogger(getClass().getName());

	private final String[] jndis = new String[] { "java:/TransactionManager",
			"java:/comp/TransactionManager" };

	@Override
	public void executar() {
		logger.debug("Iniciando COntexto para Transação");
		setProperty("com.arjuna.ats.arjuna.objectstore.objectStoreDir", "target/PutObjectStoreDirHere");
		setProperty("ObjectStoreEnvironmentBean.objectStoreDir", "target/objectStoreDir");
		try {
			final InitialContext context = new InitialContext();
			try {
				context.createSubcontext("jboss");
			} catch (final NameAlreadyBoundException e) {
			}
			final JTAEnvironmentBean bean = getJTAEnvironmentBean();
			bean.setTransactionManagerJNDIContext("java:/jboss/TransactionManager");
			JNDIManager.bindJTAImplementations(context);
			for (final String jndi : jndis) {
				try {
					context.bind(jndi, bean.getTransactionManager());
				} catch (final NameAlreadyBoundException e) {
				}
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

	}
}
