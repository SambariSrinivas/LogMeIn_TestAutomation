package com.logmein.page;

import com.logmein.base.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private boolean loggedInFlag;
    protected WebDriver driver;
    String email = TestBase.prop.getProperty("email");
    String pwd = TestBase.prop.getProperty("password");
    //Object Repository
    @FindBy(xpath = "//div[@id='productLogo']")
    WebElement GotomeetingLogo;

    @FindBy(xpath = "//input[@id='emailAddress']")
    WebElement EmailAddress;

    @FindBy(xpath = "//input[@id='next-button']")
    WebElement NextButton;

    @FindBy(xpath = "//input[@id='password']")
    WebElement Password;

    @FindBy(xpath = "//input[@id='submit']")
    WebElement SignInButton;

    @FindBy(xpath = "//span[contains(text(),'U2')]")
    WebElement UserImage;

    @FindBy(xpath = "//button[contains(text(),'Sign out')]")
    WebElement SignOut;

    //Initialize Login page objects
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    //PageActions
    public String LoginPageTitle() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.titleIs("Secure Sign In"));
        return driver.getTitle();
    }

    public boolean validateLogo() {
        return GotomeetingLogo.isDisplayed();
    }

    //login to GoTo meeting with credentials
    public void login() {
        EmailAddress.sendKeys(email);
        NextButton.click();
        Password.sendKeys(pwd);
        SignInButton.click();
        loggedInFlag = true;

    }

    public void logout() {
        if (loggedInFlag) {
            UserImage.click();
            SignOut.click();
            if (LoginPageTitle().contentEquals("Secure Sign In"))
                System.out.println("User has Successfully LoggedOff");
            else
                System.out.println("Logoff Failed");
        }
    }
}
