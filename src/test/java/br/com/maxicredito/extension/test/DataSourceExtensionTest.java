package br.com.maxicredito.extension.test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiTestRunner.class)
public class DataSourceExtensionTest {
	private InitialContext context;
	private final String dsName = "java:/jdbc/datasources/MyDS";
	private final String dsName2 = "java:/jdbc/datasources/MyDS2";

	@Before
	public void setUp() throws NamingException {
		final Hashtable<String, String> p = new Hashtable<String, String>();
		context = new InitialContext(p);
	}

	@Test
	public void test() throws Exception {
		final DataSource ds = (DataSource) context.lookup(dsName);
		final Connection connection = ds.getConnection();
		connection.setAutoCommit(Boolean.FALSE);
		final Statement statement = connection.createStatement();
		final String sql = "CREATE TABLE test(id integer)";
		statement.execute(sql);
		connection.commit();
	}

	@Test
	public void test2() throws Exception {
		final DataSource ds = (DataSource) context.lookup(dsName2);
		final Connection connection = ds.getConnection();
		connection.setAutoCommit(Boolean.FALSE);
		final Statement statement = connection.createStatement();
		final String sql = "CREATE TABLE test(id integer)";
		statement.execute(sql);
		connection.commit();
	}

}
