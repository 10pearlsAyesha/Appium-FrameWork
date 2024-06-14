package com.qa.pages;

import com.qa.base.AppDriver;
import com.qa.base.AppFactory;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsPage extends AppFactory {
    public SettingsPage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppDriver.getDriver()), this);
    }

    @AndroidFindBy(accessibility = "test-LOGOUT")
    public WebElement logoutButton;

    public LoginPage clickLogoutButton() {
        clickElement(logoutButton, "Clicking on Logout Button");
        return new LoginPage();
    }
}