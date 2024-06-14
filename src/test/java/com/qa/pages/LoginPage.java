package com.qa.pages;

import com.qa.base.AppDriver;
import com.qa.base.AppFactory;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AppFactory {

    public LoginPage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppDriver.getDriver()), this);
    }

    @AndroidFindBy(accessibility = "test-Username")
    private WebElement userNameTextbox;

    @AndroidFindBy(accessibility = "test-Password")
    private WebElement passwordTextbox;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]")
    private WebElement errorTextMessage;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"LOGIN\"]")
    private WebElement loginButton;

    public LoginPage enterUserName(String userName) {
        sendKeys(userNameTextbox, userName, "Login with: " + userName);
        return this;
    }

    public LoginPage enterPassword(String password) {
        sendKeys(passwordTextbox, password, "Password is: " + password);
        return this;
    }

    public ProductsPage clickLoginButton() {
        clickElement(loginButton, "Clicking on Login Button");
        return new ProductsPage();
    }

    public ProductsPage login(String userName, String password) {
        enterUserName(userName);
        enterPassword(password);
        return clickLoginButton();
    }

    public String getErrorMessage() {
        return getText(errorTextMessage, "Error Text is: ");
    }
}