package runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/Features",

    glue = {
        "stepdefinitions"
    },

            //tags = "@LeadCreation",

    plugin = {
        "pretty",
        "html:target/cucumber-report.html",
        "json:target/cucumber-report.json"
    },

    monochrome = true,
    publish = false,
    snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class TestRunner {
}