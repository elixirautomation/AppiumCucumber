package com.appium.steps;

import com.appium.base.TestBase;
import com.appium.pages.MainPage;
import com.appium.util.ReusableFunctions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.asserts.SoftAssert;

public class CucumberStepDefs extends TestBase {

    public static AppiumDriver driver;
    public static MainPage _mainPage;

    public static SoftAssert _softAssert;
    public static ReusableFunctions _reusableFunc;
    Scenario _scenario;

    @Before
    public void initializeScenario(Scenario scenarioInstance){
        this._scenario = scenarioInstance;
    }

    @Before
    public void setUp() {
        _mainPage = new MainPage();
        _softAssert = new SoftAssert();
        _reusableFunc = new ReusableFunctions();
    }


    @Given("^Clean install and launch demo app$")
    public void clean_install_and_launch_demo_app() throws Throwable {
        logger.info("Running Step: clean_install_and_launch_demo_app()");
        driver = initialization();
    }

    @Then("^Verify all main page elements are present$")
    public void verify_all_main_page_elements_are_present() throws Throwable {
        logger.info("Running Step: verify_all_main_page_elements_are_present()");
        if (!_mainPage.verifyMainScreenElements()) {
            _softAssert.fail("Elements not present on the page");
        }
        _softAssert.assertAll();
    }

    @Then("^Verify Super Tackle \"([^\"]*)\" and Reset Functionality$")
    public void verify_Super_Tackle_and_Reset_Functionality(String arg1) throws Throwable {
        logger.info("Running Step: verify_Super_Tackle_and_Reset_Functionality()");
        if (!_mainPage.verifySuperTacklePointAndReset(arg1)) {
            _softAssert.fail("Super Tackle Functionality Failed.");
        }
        _softAssert.assertAll();
    }

    @After
    public void closeAllAppInstances() {
        if (_scenario.isFailed()) {
            _scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
            _scenario.write("Scenario Failed: "+ _scenario.getClass().getName());
            logger.info("Scenario Failed: "+ _scenario.getClass().getName());

        } else {
            _scenario.write("Scenario Passed: "+ _scenario.getClass().getName());
            logger.info("Scenario Passed: "+ _scenario.getClass().getName());
        }
        if (driver != null) {
            driver.resetApp();
//            driver.closeApp();
        }
    }
}
