package br.com.maxicredito.ds.test;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiTestRunner.class)
public class JtaIntegrationTest {
	@Inject
	private Repository repository;

	@Test
	public void test() throws Exception {
		repository.save(new Person("Pedro"));
	}
}
