package com.qa.pages;

import com.qa.base.AppDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductDetailsPage extends MenuPage {

    public ProductDetailsPage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppDriver.getDriver()), this);
    }

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
    private WebElement SLBackpackTitle;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
    private WebElement SLBackpackDescription;

    @AndroidFindBy(accessibility = "test-BACK TO PRODUCTS")
    private WebElement backToProductButton;

    @AndroidFindBy(accessibility = "test-Price")
    private WebElement SLBProductPrice;

    public String getSLBackpackTitle() {
        return getText(SLBackpackTitle, "Title is: ");
    }

    public String getSLBackpackDescription() {
        return getText(SLBackpackDescription, "Product Description is: ");
    }

    public String getSLBPrice() {
        return getText(SLBProductPrice, "Product Price is: ");
    }

    public ProductsPage clickBackToProduct() {
        clickElement(backToProductButton, "Navigate to Product Page");
        return new ProductsPage();
    }
}