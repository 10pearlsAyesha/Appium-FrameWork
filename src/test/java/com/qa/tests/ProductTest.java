package com.qa.tests;
import com.qa.base.AppFactory;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;

public class ProductTest extends AppFactory {

    LoginPage loginPage;
    ProductsPage productsPage;
    ProductDetailsPage productDetailsPage;
    InputStream inputStream;
    JSONObject loginUsers;
    SettingsPage settingsPage;

    @BeforeClass
    public void setupDataStream() throws IOException {
        try {
            String dataFileName = "data/loginUsers.json";
            inputStream = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener jsonTokener = new JSONTokener(Objects.requireNonNull(inputStream));
            loginUsers = new JSONObject(jsonTokener);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        closeApp();
        launchApp();
    }

    @BeforeMethod
    public void setup(Method method) {
        loginPage = new LoginPage();
        utilities.log().info("\n********** Starting Test: {} **********\n", method.getName());

        productsPage = loginPage.login(loginUsers.getJSONObject("validUserAndPassword").getString("userName"),
                loginUsers.getJSONObject("validUserAndPassword").getString("password"));
    }

    @Test
    public void validateProductOnProductsPage() {
        SoftAssert softAssert = new SoftAssert();

        String SLBTitle = productsPage.getSLBackpackTitle();
        softAssert.assertEquals(SLBTitle, stringHashMap.get("products_page_slb_title"));

        String SLBPrice = productsPage.getSLBackpackPrice();
        softAssert.assertEquals(SLBPrice, stringHashMap.get("products_page_slb_price"));
        softAssert.assertAll();
    }

    @Test
    public void validateProductOnProductDetailPage() {
        SoftAssert softAssert = new SoftAssert();

        productDetailsPage = productsPage.clickSLBackpackTitle();
        String SLBTitle = productDetailsPage.getSLBackpackTitle();
        softAssert.assertEquals(SLBTitle, stringHashMap.get("product_details_page_slb_title"));

        String SLBDescription = productDetailsPage.getSLBackpackDescription();
        softAssert.assertEquals(SLBDescription, stringHashMap.get("product_details_page_slb_description"));

        // Instead of using a scrollable view for <parent_container>, if there is only one container on the view, you can use .scrollable(true).
        scrollToElement("test-Inventory item page", "test-Price");

        String SLBPrice = productDetailsPage.getSLBPrice();
        softAssert.assertEquals(SLBPrice, stringHashMap.get("product_details_page_slb_price"));

        productsPage = productDetailsPage.clickBackToProduct();

        String actualProductTitle = productsPage.getTitle();
        String expectedProductTitle = stringHashMap.get("product_title");
        utilities.log().info("Actual Product page title is - {}\nExpected Product page title is - {}", actualProductTitle, expectedProductTitle);
        Assert.assertEquals(actualProductTitle, expectedProductTitle);
        softAssert.assertAll();
    }

    @AfterMethod
    public void tearDown() {
        settingsPage = productsPage.clickSettingsButton();
        loginPage = settingsPage.clickLogoutButton();
    }
}