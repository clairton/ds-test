package br.com.maxicredito.ds.test;

import static java.lang.Boolean.FALSE;

import java.sql.Connection;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.junit.BeforeClass;
import org.junit.Test;

public class ExtensionTest {
	private final String dsName = "java:/jdbc/datasources/MyDS";
	private final String dsName2 = "jdbc/MyDS2";
	private final String dsNameApp = "java:/app/env/jdbc/datasources/MyDS";
	private final String tmName = "java:/comp/TransactionManager";

	private static Context context;

	@BeforeClass
	public static void setUp() throws NamingException {
		new Extension().run(null);
		context = new InitialContext();
	}

	@Test
	public void test() throws Exception {
		final DataSource ds = (DataSource) context.lookup(dsName);
		final Connection connection = ds.getConnection();
		connection.setAutoCommit(FALSE);
		final Statement statement = connection.createStatement();
		final String sql = "CREATE TABLE IF NOT EXISTs test(id integer)";
		statement.execute(sql);
		connection.commit();
	}

	@Test
	public void testApp() throws Exception {
		final DataSource ds = (DataSource) context.lookup(dsNameApp);
		final Connection connection = ds.getConnection();
		connection.setAutoCommit(FALSE);
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
		connection.setAutoCommit(FALSE);
		final Statement statement = connection.createStatement();
		final String sql = "CREATE TABLE IF NOT EXISTs test(id integer)";
		statement.execute(sql);
		connection.commit();
	}

}
