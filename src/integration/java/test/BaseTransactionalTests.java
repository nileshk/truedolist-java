package test;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public class BaseTransactionalTests extends AbstractTransactionalDataSourceSpringContextTests {

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "applicationContext.xml" };
	}
	
}
