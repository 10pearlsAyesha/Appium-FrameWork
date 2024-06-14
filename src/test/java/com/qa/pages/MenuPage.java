package com.qa.pages;

import com.qa.base.AppDriver;
import com.qa.base.AppFactory;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MenuPage extends AppFactory {
    public MenuPage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppDriver.getDriver()), this);
    }

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView")
    private WebElement settingsButton;

    public SettingsPage clickSettingsButton() {
        waitForElementToBeClickable(settingsButton);
        clickElement(settingsButton, "Clicking on Settings Button");
        return new SettingsPage();
    }
}