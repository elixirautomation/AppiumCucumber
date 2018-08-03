package com.appium.pages;

import com.appium.base.TestBase;
import com.appium.util.ReusableFunctions;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.util.ArrayList;

public class MainPage extends TestBase {
    ReusableFunctions _reusableFunc = new ReusableFunctions();

    // Android App
    public final By teamA_superTackle = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_STackle']");
    public final By teamB_superTackle = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_STackle_b']");
    public final By teamA_doOrDieRaid = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_DRaid']");
    public final By teamB_doOrDieRaid = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_DRaid']");
    public final By teamA_touch = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_Touch']");
    public final By teamB_touch = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_Touch_b']");
    public final By teamA_allOut = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_AllOut']");
    public final By teamB_allOut = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_AllOut_b']");
    public final By teamA_bonus = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_Bonus']");
    public final By teamB_bonus = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_Bonus_b']");
    public final By teamA_tackle = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_Tackle']");
    public final By teamB_tackle = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_Tackle_b']");
    public final By resetButton = By.xpath("//android.widget.Button[@text='RESET']");
    public final By teamA_scoreView = By.xpath("//android.widget.TextView[@resource-id='com.example.android.prokabaddi:id/team_a_score']");
    public final By teamB_scoreView = By.xpath("//android.widget.TextView[@resource-id='com.example.android.prokabaddi:id/team_b_score']");

    public boolean verifyMainScreenElements() {
        boolean flag = true;
        try{
            logger.info("Verifying Main Page Elements.");

            ArrayList<By> locatorsA = new ArrayList<By>();

            locatorsA.add(teamA_superTackle);
            locatorsA.add(teamB_superTackle);
            locatorsA.add(teamA_doOrDieRaid);
            locatorsA.add(teamB_doOrDieRaid);
            locatorsA.add(teamA_touch);
            locatorsA.add(teamB_touch);
            locatorsA.add(teamA_allOut);
            locatorsA.add(teamB_allOut);
            locatorsA.add(teamA_bonus);
            locatorsA.add(teamB_bonus);
            locatorsA.add(teamA_tackle);
            locatorsA.add(teamB_tackle);
            locatorsA.add(resetButton);
            locatorsA.add(teamA_scoreView);
            locatorsA.add(teamB_scoreView);

            if (_reusableFunc.verifyElementsLocated(driver, locatorsA)){
                logger.info("All Main Page Elements Displayed.");
            }else {
                flag = false;
                logger.error("Main Page Elements Not Displayed.");
            }

        }catch(Exception Ex){
            flag = false;
            logger.error("Exception Occurred While Verifying Main Page Elements: "+Ex.getMessage());
        }
        return flag;
    }


    public boolean verifySuperTacklePointAndReset(String movePoint) {
        boolean flag = true;
        try{
            logger.info("Verifying Super Tackle Functionality.");

            _reusableFunc.click(driver, teamA_superTackle);

            if (_reusableFunc.getTextByInnerText(driver, teamA_scoreView).equals(movePoint)){
                logger.info("Super Tackle Point Displayed.");

                _reusableFunc.click(driver, resetButton);

                if (_reusableFunc.getTextByInnerText(driver, teamA_scoreView).equals("0")){
                    logger.info("Reset functionality working fine.");

                }else {
                    flag = false;
                    logger.error("Reset functionality is not working fine.");
                }

            }else {
                flag = false;
                logger.error("Super Tackle Point Not Displayed.");
            }

        }catch(Exception Ex){
            flag = false;
            logger.error("Exception Occurred While Verifying Main Page Elements: "+Ex.getMessage());
        }
        return flag;
    }
}


