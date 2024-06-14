package com.qa.base;

import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.qa.utils.Utilities;
import com.qa.utils.ConfigReader;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class AppFactory {

    protected static AppiumDriver driver;
    protected static ConfigReader configReader;
    protected static HashMap<String, String> stringHashMap = new HashMap<String, String>();
    protected static String dateTime;
    private static AppiumDriverLocalService server;
    InputStream stringsIs;
    public Utilities utilities = new Utilities();

    @BeforeSuite
    public void upAndRunningAppiumServer() {
        server = getAppiumServerDefault();
        if (!utilities.checkIfAppiumServerIsRunning(4723)) {
            server.start();
            server.clearOutPutStreams(); // This command is used to stop print Appium server's logs into console.
            utilities.log().info("Starting Appium server...");
        } else {
            utilities.log().info("Appium Server is already up and running...");
        }
    }

    @AfterSuite
    public void shutDownServer() {
        server.stop();
        utilities.log().info("Shutting down Appium server...");
    }

    public AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }

    @BeforeTest
    @Parameters({"platformName", "platformVersion", "deviceName"})
    public void initializer(String platformName, String platformVersion, String deviceName) throws Exception {
        try {
            dateTime = utilities.getDateTime();
            configReader = new ConfigReader();
            String xmlFileName = "strings/strings.xml";
            AppDriver.setPlatform(platformName);
            AppDriver.setDeviceName(deviceName);

            stringsIs = getClass().getClassLoader().getResourceAsStream(xmlFileName);
            stringHashMap = utilities.parseStringXML(stringsIs);

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", platformName);
            capabilities.setCapability("platformVersion", platformVersion);
            capabilities.setCapability("deviceName", deviceName);
            capabilities.setCapability("appPackage", configReader.getAppPackage());
            capabilities.setCapability("appActivity", configReader.getAppActivity());
            capabilities.setCapability("newCommandTimeout", configReader.getCommandTimeoutValue());
            capabilities.setCapability("automationName", configReader.getAutomationName());
            capabilities.setCapability("noReset", configReader.getNoReset());
            capabilities.setCapability("app", System.getProperty("user.dir") + configReader.getApkPath());
            driver = new AndroidDriver(new URL(configReader.appiumServerEndpointURL()), capabilities);
            AppDriver.setDriver(driver);
            utilities.log().info("appURL is {}", configReader.appiumServerEndpointURL());
            utilities.log().info("Android driver is set");
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        } finally {
            if (stringsIs != null) {
                stringsIs.close();
            }
        }
    }

    public void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utilities.WAIT));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utilities.WAITFORCLICK));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void clickElement(WebElement element, String message) {
        waitForVisibility(element);
        utilities.log().info(message);
        ExtentReport.getTest().log(Status.INFO, message);
        element.click();
    }

    public void sendKeys(WebElement element, String text, String message) {
        waitForVisibility(element);
        utilities.log().info(message);
        ExtentReport.getTest().log(Status.INFO, message);
        element.sendKeys(text);
    }

    public String getAttribute(WebElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }

    public String getText(WebElement element, String message) {
        String elementText = null;
        elementText = getAttribute(element, "text");
        utilities.log().info("{}{}", message, elementText);
        ExtentReport.getTest().log(Status.INFO, message + elementText);
        return elementText;
    }

    public void closeApp() {
        ((InteractsWithApps) AppDriver.getDriver()).terminateApp(configReader.getAppPackage());
    }

    public void launchApp() {
        ((InteractsWithApps) AppDriver.getDriver()).activateApp(configReader.getAppPackage());
    }

    public void scrollToElement(String parentDescription, String targetDescription) {
        String uiAutomatorString = String.format(
                "new UiScrollable(new UiSelector().description(\"%s\")).scrollIntoView("
                        + "new UiSelector().description(\"%s\"));",
                parentDescription, targetDescription
        );

        AppDriver.getDriver().findElement(AppiumBy.androidUIAutomator(uiAutomatorString));
    }

    public static String getDateAndTime() {
        return dateTime;
    }

    @AfterTest
    public static void quitDriver() {
        if (null != driver) {
            driver.quit();
        }
    }
}