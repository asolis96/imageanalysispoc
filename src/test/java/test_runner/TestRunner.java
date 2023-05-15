package test_runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(

        features = {"src/test/resources/features"},
        glue = {"steps_definitions"},
        monochrome = true,
        plugin = {
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "json:Reports/cucumber.json",
                "html:docs/Cucumber.html",
                "rerun:target/rerun.txt",
                "junit:Reports/Cucumber-results.xml"
        }
)

public class TestRunner {
}
