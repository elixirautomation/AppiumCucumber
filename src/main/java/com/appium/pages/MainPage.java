package com.appium.pages;

import com.appium.base.TestBase;
import com.appium.util.ReusableFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.util.ArrayList;

public class MainPage extends TestBase {

    private static Logger logger = Logger.getLogger(MainPage.class.getName());

    // Android App
    private static final By teamA_superTackle = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_STackle']");
    private static final By teamB_superTackle = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_STackle_b']");
    private static final By teamA_doOrDieRaid = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_DRaid']");
    private static final By teamB_doOrDieRaid = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_DRaid']");
    private static final By teamA_touch = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_Touch']");
    private static final By teamB_touch = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_Touch_b']");
    private static final By teamA_allOut = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_AllOut']");
    private static final By teamB_allOut = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_AllOut_b']");
    private static final By teamA_bonus = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_Bonus']");
    private static final By teamB_bonus = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_Bonus_b']");
    private static final By teamA_tackle = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_Tackle']");
    private static final By teamB_tackle = By.xpath("//android.widget.Button[@resource-id='com.example.android.prokabaddi:id/button_Tackle_b']");
    private static final By resetButton = By.xpath("//android.widget.Button[@text='RESET']");
    private static final By teamA_scoreView = By.xpath("//android.widget.TextView[@resource-id='com.example.android.prokabaddi:id/team_a_score']");
    private static final By teamB_scoreView = By.xpath("//android.widget.TextView[@resource-id='com.example.android.prokabaddi:id/team_b_score']");

    public static boolean verifyMainScreenElements() {
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

            if (ReusableFunctions.verifyElementsLocated(locatorsA)){
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


    public static boolean verifySuperTacklePointAndReset(String movePoint) {
        boolean flag = true;
        try{
            logger.info("Verifying Super Tackle Functionality.");

            ReusableFunctions.mouseClick(teamA_superTackle);

            if (ReusableFunctions.getTextByInnerText(teamA_scoreView).equals(movePoint)){
                logger.info("Super Tackle Point Displayed.");

                ReusableFunctions.mouseClick(resetButton);

                if (ReusableFunctions.getTextByInnerText(teamA_scoreView).equals("0")){
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


