package br.com.maxicredito.ds.test;

import java.sql.Connection;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiTestRunner.class)
public class TestExtensionTest {
	private InitialContext context;
	private final String dsName = "java:/jdbc/datasources/MyDS";
	private final String dsName2 = "jdbc/MyDS2";
	private final String tmName = "java:/comp/TransactionManager";

	@Before
	public void setUp() throws NamingException {
		context = new InitialContext();
	}

	@Test
	public void test() throws Exception {
		final DataSource ds = (DataSource) context.lookup(dsName);
		final Connection connection = ds.getConnection();
		connection.setAutoCommit(Boolean.FALSE);
		final Statement statement = connection.createStatement();
		final String sql = "CREATE TABLE IF NOT EXISTs test(id integer)";
		statement.execute(sql);
		connection.commit();
	}

	@Test
	public void testJta() throws Exception {
		final TransactionManager tm = (TransactionManager) context.lookup(tmName);
		tm.begin();
		tm.commit();
	}

	@Test
	public void test2() throws Exception {
		final DataSource ds = (DataSource) context.lookup(dsName2);
		final Connection connection = ds.getConnection();
		connection.setAutoCommit(Boolean.FALSE);
		final Statement statement = connection.createStatement();
		final String sql = "CREATE TABLE IF NOT EXISTs test(id integer)";
		statement.execute(sql);
		connection.commit();
	}

}
