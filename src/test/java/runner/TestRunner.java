package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefination",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {
	
}