package com.example.runners;

//import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/AppFeature",
        glue = "VinGroup.Mavenroject",
        plugin = {
        		 "pretty", // Provides readable output
                 "html:target/cucumber-reports.html", // Generates HTML report
                 "json:target/cucumber-reports/cucumber.json", // Generates JSON report
                 "junit:target/cucumber-reports/cucumber.xml" // Generates JUnit XML report
         },
         monochrome = true )// Makes console output more readable
public class TestRunner extends AbstractTestNGCucumberTests {

}
