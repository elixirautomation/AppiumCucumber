package com.appium.testcases;

import com.appium.base.TestBase;
import com.appium.listeners.ExtentManager;
import com.appium.pages.MainPage;
import com.appium.testdata.DataReader;
import com.appium.util.ReusableFunctions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class MainPageTests extends TestBase {
    
    private static SoftAssert _softAssert;

    private static String testCaseName = null;
    private static ExtentTest extentLogger = null;
    private static ExtentReports extent = null;
    private static Logger logger = null;

    private static HashMap<String, HashMap<String, String>> tcData = DataReader.
            testDataMappedToTestName(prop.getProperty("TestDataExcelFileName"), prop.getProperty("TestDataSheetName"));

    @BeforeMethod
    public static void setUp(Method method) throws IOException {
        driverInitialization();
        driver.resetApp();
        _softAssert = new SoftAssert();
        testCaseName = method.getName();
        extent = ExtentManager.getReporter();
        extentLogger = ExtentManager.getLogger(testCaseName);
        logger = Logger.getLogger(MainPageTests.class.getName());
    }

    @Test(priority = 1)
    public void verifyMainScreenElements(){
        logger.info("Running Step: verify_all_main_page_elements_are_present()");
        if (!MainPage.verifyMainScreenElements()) {
            logger.error("Element not present on the page.");
            _softAssert.fail("Elements not present on the page");
        }
        _softAssert.assertAll();
    }

    @Test(priority = 2)
    public void verifySuperTackleAndResetFunctionality(){
        logger.info("Running Step: verify_Super_Tackle_and_Reset_Functionality()");
        String numA = tcData.get(testCaseName).get("MovePoint");
        if (!MainPage.verifySuperTacklePointAndReset(numA)) {
            _softAssert.fail("Super Tackle Functionality Failed.");
        }
        _softAssert.assertAll();
    }

    @AfterMethod
    public void getResult(ITestResult result) throws Exception{

        if(result.getStatus() == ITestResult.FAILURE){
            String screenShotPath = ReusableFunctions.takeScreenShot("DemoApp");
            extentLogger.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
            extentLogger.fail(result.getThrowable());
            extentLogger.fail("Snapshot below: " + extentLogger.addScreenCaptureFromPath(screenShotPath, testCaseName));

        }else if(result.getStatus() == ITestResult.SKIP){
            extentLogger.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test case SKIPPED due to below issues:", ExtentColor.GREY));
            extentLogger.skip(result.getThrowable());

        }else if(result.getStatus() == ITestResult.SUCCESS){
            extentLogger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test case PASSED.", ExtentColor.GREEN));
        }

        if (driver != null) {
            driver.resetApp();
        }
    }
    @AfterTest
    public void tearDown(){
        extent.flush();
    }
}
