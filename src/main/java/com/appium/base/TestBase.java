package com.appium.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.*;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class TestBase {

    public static Properties prop;
    public static AppiumDriver driver;
    private static Logger logger;

    static{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hhmmss");
        System.setProperty("current.date", dateFormat.format(new Date()));
    }

    public TestBase() {
        try {
            prop = new Properties();
            FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/appium/config/config.properties");
            prop.load(inputStream);

            PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/main/resource/log4j.properties");
            logger = Logger.getLogger(MethodHandles.lookup().lookupClass());

        } catch (FileNotFoundException Ex) {
            logger.info("File not found: " + Ex.getMessage());

        } catch (IOException Ex) {
            logger.info("Exception occurred: " + Ex.getMessage());
        }
    }

    /**
     * This is singleton driver initialization method
     * @throws MalformedURLException - In case of invalid appium server url
     */
    public static void driverInitialization() throws MalformedURLException{
        if (driver == null) {
            switch (prop.getProperty("Platform")){
                case "android":
                    logger.info("Running Tests On Android Platform.");
                    androidSetup();
                    break;
                case "browser_stack":
                    logger.info("Running Tests On Browser Stack.");
                    browserStack();
                    break;
                case "ios":
                    logger.info("Running Tests On IOS Platform.");
                    iosSetup();
                    break;
                default:
                    logger.info("Running Tests On Browser Stack.");
                    browserStack();
            }
        }
    }

    /**
     * This method is used for android driver setup
     * @throws MalformedURLException - In case of invalid appium server url
     */
    private static void androidSetup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("automationName", prop.getProperty("AutomationName"));
        caps.setCapability("deviceName", prop.getProperty("DeviceName"));
        caps.setCapability("udid", prop.getProperty("UDID"));
        caps.setCapability("platformName", prop.getProperty("Platform"));
        caps.setCapability("platformVersion", prop.getProperty("PlatformVersion"));
        caps.setCapability("appPackage", prop.getProperty("AppPackage"));
        caps.setCapability("appActivity", prop.getProperty("AppActivity"));
        caps.setCapability("app", System.getProperty("user.dir") + "/Apps/" + prop.getProperty("AppName"));
        caps.setCapability("noReset", prop.getProperty("NoReset"));
        caps.setCapability("autoGrantPermissions", prop.getProperty("AutoGrantPermissions"));
        driver = new AndroidDriver<MobileElement>(new URL(prop.getProperty("AppiumServer")), caps);
        logger.info("Starting Android Driver.");
    }

    /**
     * This method is used for browser stack driverInitialization
     * @throws MalformedURLException - In case of invalid browser stack server url
     */
    private static void browserStack() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        String bs_url = "https://"+prop.getProperty("bsUserName")+":"+prop.getProperty("bsAccessKey")+"@hub-cloud.browserstack.com/wd/hub";
        caps.setCapability("device", prop.getProperty("CloudDeviceName"));
        caps.setCapability("os_version", prop.getProperty("CloudPlatformVersion"));
        caps.setCapability("app", prop.getProperty("bsAppHash"));
        driver = new AndroidDriver(new URL(bs_url), caps);
        logger.info("Starting Android Driver on BrowserStack.\nBrowser Stack Server Details:\n"+bs_url);
    }

    /**
     * This method is used for ios driver setup
     * @throws MalformedURLException - In case of invalid appium server url
     */
    private static void iosSetup() throws MalformedURLException{
        DesiredCapabilities caps = new DesiredCapabilities();
        // To be implemented
        driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        logger.info("Starting IOS Driver.");
    }

}