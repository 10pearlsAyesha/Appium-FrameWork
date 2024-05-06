package com.qa.tests;

import com.qa.base.AppFactory;
import com.qa.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.pages.LoginPage;

import java.lang.reflect.Method;

public class LoginTest extends AppFactory {
    LoginPage loginPage;
    ProductPage productPage;

    @BeforeMethod
    // Mthod is used to print log in read able manners
    public void setup(Method method){
        loginPage = new LoginPage();
        System.out.println("\n" + "********** Staring Test: " + method.getName() + " **********" + "\n");
    }

    @Test
    public void verifyInvalidUserName(){
        System.out.println("This test is used to verify that User will get Error Message while entering Invalid User Name");
        loginPage.enterValidUserName("invalid_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = "Username and password do not match any user in this service.";
        System.out.println("Actual Error Message is - " + actualErrorMessage + "\n" + "Expected Error Message is - " + expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void verifyInvalidPassword(){
        System.out.println("This test is used to verify that User will get Error Message while entering Invalid Password");
        loginPage.enterValidUserName("standard_user");
        loginPage.enterPassword("invalid_password");
        loginPage.clickLoginButton();

        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = "Username and password do not match any user in this service.";
        System.out.println("Actual Error Message is - " + actualErrorMessage + "\n" + "Expected Error Message is - " + expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void verifyValidLogin(){
        System.out.println("This test us used to validate the successful login functionality with valid User Name and Password");
        loginPage.enterValidUserName("standard_user");
        loginPage.enterPassword("secret_sauce");
        productPage = loginPage.clickLoginButton();

        String actualProductTitle = productPage.getTitle();
        String expectedProductTitle = "PRODUCTS";
        System.out.println("Actual Product page title is - " + actualProductTitle + "\n" + "Expected Product page title is - " + expectedProductTitle);
        Assert.assertEquals(actualProductTitle, expectedProductTitle);
    }
}