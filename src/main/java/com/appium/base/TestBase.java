package com.appium.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
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
    public static Logger logger;
    public static AppiumDriver driver;

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

    public static AppiumDriver initialization() throws MalformedURLException{
        if (driver == null) {
            if (prop.getProperty("PLATFORM").equals("android")) {
                logger.info("Running Tests On Android Platform.");
                androidSetup();
            } else if (prop.getProperty("PLATFORM").equals("ios")) {
                logger.info("Running Tests On IOS Platform.");
                iosSetup();
            }
        }
        return driver;
    }

    public static void androidSetup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("automationName", prop.getProperty("AUTOMATION_NAME"));
        caps.setCapability("deviceName", prop.getProperty("DEVICE_NAME"));
        caps.setCapability("udid", prop.getProperty("UDID"));
        caps.setCapability("platformName", prop.getProperty("PLATFORM"));
        caps.setCapability("platformVersion", prop.getProperty("PLATFORM_VERSION"));
        caps.setCapability("appPackage", prop.getProperty("APP_PACKAGE"));
        caps.setCapability("appActivity", prop.getProperty("APP_ACTIVITY"));
        caps.setCapability("app", System.getProperty("user.dir") + "/Apps/" + prop.getProperty("APP_NAME"));
        caps.setCapability("noReset", prop.getProperty("NO_RESET"));
        driver = new AndroidDriver<MobileElement>(new URL(prop.getProperty("APPIUMSERVER")), caps);
        logger.info("Starting Android Driver.");
    }

    public static void iosSetup() throws MalformedURLException{
        DesiredCapabilities caps = new DesiredCapabilities();
        // To be implemented
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        logger.info("Starting IOS Driver.");
    }

}
