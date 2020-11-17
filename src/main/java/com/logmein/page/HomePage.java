package com.logmein.page;

import com.logmein.utils.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {
    protected WebDriver driver;
    public Utilities util;

    @FindBy(xpath = "//a[@title='Meetings']")
    WebElement Meetings;

    @FindBy(xpath = "//a[@title='Chats']")
    WebElement Chats;

    @FindBy(xpath = "//a[@title='Settings']")
    WebElement Settings;

    @FindBy(xpath = "//a[@title='Discover']")
    WebElement Discover;

    @FindBy(xpath = "//span[normalize-space()='More']")
    WebElement MoreSection;

    //Initialize Home Page Objects
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        util = new Utilities(driver);
    }

    //Page Actions

    public String homePageTitle() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.titleIs("GoToMeeting Hub"));
        return driver.getTitle();
    }

    public String[] verifyAllChatPageElements() {
        return new String[]{util.getInnerText(Meetings), util.getInnerText(Chats),
                util.getInnerText(Settings), util.getInnerText(Discover), util.getInnerText(MoreSection)};
    }
}