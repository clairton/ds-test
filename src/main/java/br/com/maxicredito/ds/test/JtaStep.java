package br.com.maxicredito.ds.test;

import static com.arjuna.ats.jta.common.jtaPropertyManager.getJTAEnvironmentBean;
import static java.lang.System.setProperty;
import static java.util.logging.Level.FINE;

import java.util.logging.Logger;

import javax.enterprise.inject.spi.Extension;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;

import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionSynchronizationRegistryImple;
import com.arjuna.ats.jta.common.JTAEnvironmentBean;
import com.arjuna.ats.jta.utils.JNDIManager;

public class JtaStep extends Step implements Extension {
	private static final Logger logger = Logger.getLogger(JtaStep.class.getName());

	private final String[] jndis = new String[] { "java:/TransactionManager", "java:/comp/TransactionManager" };
	private final String jndi = "java:/comp/TransactionSynchronizationRegistry";

	@Override
	public void run() {
		logger.log(FINE, "Iniciando Contexto para Transação");
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
					e.printStackTrace();
				}
			}
			try {
				context.bind(jndi, new TransactionSynchronizationRegistryImple());
			} catch (final NameAlreadyBoundException e) {
				e.printStackTrace();
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
