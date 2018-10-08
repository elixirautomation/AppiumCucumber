package com.appium.listeners;

import com.appium.base.TestBase;
import com.appium.util.ReusableFunctions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//OB: ExtentReports extent instance created here. That instance can be reachable by getReporter() method.

public class ExtentManager extends TestBase {

    private static ExtentReports extent;
    private static ExtentTest logger;

    public ExtentManager(ExtentReports reports){
        extent = reports;
    }

    /**
     * This method is used to get the extent reporter for framework reporting
     * @return - Instance of ExtentReport Class
     * @throws IOException - In case of unavailability of extent directory and file
     */
    public synchronized static ExtentReports getReporter() throws IOException {
        if(extent == null){
            String workingDir = System.getProperty("user.dir");
            String fileDir = workingDir+"/ExtentReports";
            String fileName ="ExtentReportResults"+System.getProperty("current.date")+".html";
            String reportFilePath = ReusableFunctions.createOrRetrieveFiles(fileDir, fileName);

            FileInputStream inputStream = new FileInputStream(new File(workingDir+"/src/test/resource/extent-config.xml"));

            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFilePath);
            htmlReporter.loadConfig(inputStream);

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Host Name", prop.getProperty("AppiumServer"));
            extent.setSystemInfo("Environment", prop.getProperty("Environment"));
            extent.setSystemInfo("User Name", prop.getProperty("TeamName"));

        }
        return extent;
    }

    /**
     * This method is used to get the extent logger for specific test case
     * @param testCaseName - currently executing test case name
     * @return - it returns instance of ExtentTest Class
     */
    public synchronized static ExtentTest getLogger(String testCaseName){
        try{
            logger = getReporter().createTest(testCaseName);
        }catch (Exception Ex){
            Ex.printStackTrace();
        }
        return logger;
    }
}
