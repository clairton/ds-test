package br.com.maxicredito.ds.test;

import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.jboss.logging.Logger;
import org.jboss.weld.transaction.spi.TransactionServices;

// https://github.com/jbosstm/quickstart/blob/master/jta-1_2-standalone/src/test/java/org/jboss/narayana/quickstarts/jta/cdi/CDITransactionServices.java
public class WeldTransactionServices implements TransactionServices {

	private static final Logger LOG = Logger.getLogger(WeldTransactionServices.class);

	@Override
	public void registerSynchronization(Synchronization synchronizedObserver) {
		try {
			com.arjuna.ats.jta.TransactionManager.transactionManager().getTransaction()
					.registerSynchronization(synchronizedObserver);
		} catch (SystemException | IllegalStateException | RollbackException e) {
			throw new IllegalStateException("Cannot register synchronization observer " + synchronizedObserver
					+ " to the available transaction", e);
		}
	}

	@Override
	public boolean isTransactionActive() {
		try {
			int status = com.arjuna.ats.jta.TransactionManager.transactionManager().getStatus();
			switch (status) {
			case Status.STATUS_ACTIVE:
			case Status.STATUS_COMMITTING:
			case Status.STATUS_MARKED_ROLLBACK:
			case Status.STATUS_PREPARED:
			case Status.STATUS_PREPARING:
			case Status.STATUS_ROLLING_BACK:
				return true;
			default:
				return false;
			}
		} catch (SystemException se) {
			LOG.error("Cannot obtain the status of the transaction", se);
			return false;
		}
	}

	@Override
	public UserTransaction getUserTransaction() {
		return com.arjuna.ats.jta.UserTransaction.userTransaction();
	}

	@Override
	public void cleanup() {
		// nothing to clean
	}
}
