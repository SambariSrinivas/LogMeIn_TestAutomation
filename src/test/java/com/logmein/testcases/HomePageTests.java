package com.logmein.testcases;

import com.logmein.base.TestBase;
import com.logmein.page.HomePage;
import com.logmein.page.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTests extends TestBase {
    LoginPage loginPage;
    HomePage homePage;

    public HomePageTests() {
        super();
    }

    @BeforeMethod
    public void home_Page_SetUp() {
        loginPage = new LoginPage(driver);
        loginPage.login();
        homePage = new HomePage(driver);
    }

    @AfterMethod
    public void login_Out_From_Application(){
        loginPage.logout();
    }

    //verify  Home page elements
    @Test
    public void verify_HomePage_Elements() {
        String[] actualElementsTexts = homePage.verifyAllChatPageElements();
        String[] expectedElementText = {"MEETINGS", "CHATS", "SETTINGS", "DISCOVER", "MORE"};
        Assert.assertEquals(actualElementsTexts, expectedElementText);
    }

}
