package com.appium.tests;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * @author abhilash.sharma
 *
 * Cucumber Tags:
 *	1. Run the test with single tag:
 *		tags = {"@sanity"}
 *	2. Run the test with multiple tag -OR- condition (keep the tag name in single string):
 *		tags = {"@sanity,@regression"}
 *	3. Run the test with multiple tag -AND- condition (keep the tag name in different string):
 *		tags = {"@sanity","@regression"}
 */

@CucumberOptions(features = {"src/test/resource/features"}, glue = {"com/appium/steps"}, tags = {"@sanity,@regression"}, plugin = { "pretty",
        "html:target/cucumber-reports/cucumber-pretty", "json:target/cucumber-reports/CucumberTestReport.json",
        "rerun:target/cucumber-reports/rerun.txt" })

public class TestRunner extends AbstractTestNGCucumberTests {

}
