package com.qa.base;

import com.qa.utils.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
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
    protected static HashMap<String, String> stringHashMap = new HashMap<>();
    InputStream stringIs;
    Utilities utilities;
    static Logger log = LogManager.getLogger(AppFactory.class.getName());
    @BeforeTest
    @Parameters({"platformName", "platformVersion", "deviceName"})
    public void initializer(String platformName, String platformVersion, String deviceName) throws Exception {
        try {
            log.debug("This is debug message");
            log.info("This is Info message");
            log.warn("This is Warring message");
            log.error("This is Error message");
            log.fatal("This is Fatal Error Message");
            utilities = new Utilities();
            configReader = new ConfigReader();
            String xmlFileName = "strings/strings.xml";
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

    public void clickElement(WebElement element){
        waitForVisibility(element);
        element.click();
    }

    public void sendKeys(WebElement element, String text){
        waitForVisibility(element);
        element.sendKeys(text);
    }

    public String getAttribute(WebElement element, String attribute){
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }

    @AfterTest
    public static void quiteDriver(){
        if(null != driver){
            driver.quit();
        }
    }
}