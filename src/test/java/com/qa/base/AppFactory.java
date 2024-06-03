package com.qa.base;
import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import com.qa.utils.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import com.qa.utils.Utilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppFactory {

    public  static AppiumDriver driver;
    public static ConfigReader configReader;
    private static AppiumDriverLocalService server;
    protected static String dateTime;
    protected static HashMap<String, String> stringHashMap = new HashMap<>();
    InputStream stringIs;

    public Utilities utilities = new Utilities();
    static Logger log = LogManager.getLogger(AppFactory.class.getName());
    @BeforeSuite
    public void upAndRunningAppiumServer(){
        server = getAppiumServerDefault();
        if(!utilities.checkIfAppiumServerIsRunning(4723)){
            server.start();
            server.clearOutPutStreams();
            utilities.log().info("Starting Appium server...");
        }else {
            utilities.log().info("Appium Server is already up and running...");
        }
    }

    @AfterSuite
    public void shutDownServer(){
        server.stop();
        utilities.log().info("Appium Server shutdown...");
    }

    public AppiumDriverLocalService getAppiumServerDefault(){
        return AppiumDriverLocalService.buildDefaultService();
    }

    @BeforeTest
    @Parameters({"platformName", "platformVersion", "deviceName"})
    public void initializer(String platformName, String platformVersion, String deviceName) throws Exception {
        try {
            dateTime = utilities.getDateTime();
            configReader = new ConfigReader();
            String xmlFileName = "strings/strings.xml";
            AppDriver.setPlatformName(platformName);
            AppDriver.setDeviceName(deviceName);
            stringIs = getClass().getClassLoader().getResourceAsStream(xmlFileName);
            stringHashMap = utilities.parseStringXML(stringIs);
            configReader = new ConfigReader();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", platformName);
            capabilities.setCapability("platformVersion", platformVersion);
            capabilities.setCapability("deviceName", deviceName);
            capabilities.setCapability("appPackage",  configReader.getAppPackageName());
            capabilities.setCapability("appActivity", configReader.getAppActivity());
            capabilities.setCapability("newCommandTimeout", configReader.getCommandTimeoutValue());
            capabilities.setCapability("automationName", configReader.getAutomationName());
            capabilities.setCapability("noReset", configReader.getNoReset());
            capabilities.setCapability("app", System.getProperty("user.dir") + configReader.getAPKPath());
            driver = new AndroidDriver(new URL(configReader.getAppiumServerEndPoint()), capabilities);
            AppDriver.setDriver(driver);
            System.out.println("Android Driver is Set");
            utilities.log().info("appURL is {}", configReader.getAppiumServerEndPoint());
            utilities.log().info("Android Driver is Set");
        }catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }finally {
            if (stringIs != null) {
                stringIs.close();
            }
        }
    }
    public void waitForVisibility(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Utilities.WAIT));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void clickElement(WebElement element, String message){
        waitForVisibility(element);
        utilities.log().info(message);
        ExtentReport.getTest().log(Status.INFO, message);
        element.click();
    }

    public void sendKeys(WebElement element, String text,String message){
        waitForVisibility(element);
        utilities.log().info(message);
        ExtentReport.getTest().log(Status.INFO, message);
        element.sendKeys(text);
    }

    public String getAttribute(WebElement element, String attribute){
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }
    public String getText(WebElement element, String message){
        String elementText = null;
        elementText = getAttribute(element, "text");
        utilities.log().info("{}{}", message, elementText);
        ExtentReport.getTest().log(Status.INFO, message + elementText);
        return elementText;
    }
    public static String getDateAndTime() {
        return dateTime;
    }
    @AfterTest
    public static void quiteDriver(){
        if(null != driver){
            driver.quit();
        }

    }
}