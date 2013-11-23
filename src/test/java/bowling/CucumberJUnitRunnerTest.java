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
	//noop - just need the runner to execute the Cucumber classes
}
