package com.qa.tests;
import com.qa.base.AppFactory;
import com.qa.pages.ProductPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.pages.LoginPage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;

public class LoginTest extends AppFactory {
    LoginPage loginPage;
    ProductPage productPage;
    InputStream inputStream;
    JSONObject loginUser;
    @BeforeClass
    public void setupDataStream() throws IOException {
        try {
            String dataFileName = "data/loginUsers.json";
            inputStream = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener jsonTokener = new JSONTokener(Objects.requireNonNull(inputStream));
            loginUser = new JSONObject(jsonTokener);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
    @BeforeMethod
    public void setup(Method method) {
        loginPage = new LoginPage();
        System.out.println("\n" + "********** Staring Test: " + method.getName() + " **********" + "\n");
    }
    @Test
    public void verifyInvalidUserName() {
        System.out.println("This test is used to verify that User will get Error Message while entering Invalid User Name");
        loginPage.enterValidUserName(loginUser.getJSONObject("invalidUser").getString("userName"));
        loginPage.enterPassword(loginUser.getJSONObject("invalidUser").getString("password"));
        loginPage.clickLoginButton();
        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = "Username and password do not match any user in this service.";
        System.out.println("Actual Error Message is - " + actualErrorMessage + "\n" + "Expected Error Message is - " + expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
    @Test
    public void verifyInvalidPassword() {
        System.out.println("This test is used to verify that User will get Error Message while entering Invalid Password");
        loginPage.enterValidUserName(loginUser.getJSONObject("invalidPassword").getString("userName"));
        loginPage.enterPassword(loginUser.getJSONObject("invalidPassword").getString("password"));
        loginPage.clickLoginButton();
        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = stringHashMap.get("error_invalid_userName_and_password");
        System.out.println("Actual Error Message is - " + actualErrorMessage + "\n" + "Expected Error Message is - " + expectedErrorMessage);
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
    @Test
    public void verifyValidLogin() {
        System.out.println("This test us used to validate the successful login functionality with valid User Name and Password");
        loginPage.enterValidUserName(loginUser.getJSONObject("validUserAndPassword").getString("userName"));
        loginPage.enterPassword(loginUser.getJSONObject("validUserAndPassword").getString("password"));
        productPage = loginPage.clickLoginButton();
        String actualProductTitle = productPage.getTitle();
        String expectedProductTitle = stringHashMap.get("product_title");;
        System.out.println("Actual Product page title is - " + actualProductTitle + "\n" + "Expected Product page title is - " + expectedProductTitle);
        Assert.assertEquals(actualProductTitle, expectedProductTitle);
    }
}