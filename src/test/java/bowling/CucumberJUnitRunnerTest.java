package bowling;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Cucumber JUnit runner class
 * 
 * @author Jason Andersen (andersen.jason@gmail.com)
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = "pretty")
public class CucumberJUnitRunnerTest {

	@Test
	public void emptyTest() {
		//noop - just trying to get Maven to pick this up
	}
	
}
