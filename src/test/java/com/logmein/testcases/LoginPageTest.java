package com.logmein.testcases;

import com.logmein.base.TestBase;
import com.logmein.page.HomePage;
import com.logmein.page.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends TestBase {
     LoginPage loginPage;
     HomePage homePage;

   public LoginPageTest(){
        super();
    }

    @BeforeMethod
    public void login_Page_SetUp(){
        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    public void login_Out_From_Application(){
        loginPage.logout();
    }

    @Test
    public void verify_LoginPage_Title(){
       String title = loginPage.LoginPageTitle();
       Assert.assertEquals(title,"Secure Sign In");
    }

    @Test
    public void verify_GoToMeeting_Logo(){
        boolean flag = loginPage.validateLogo();
        Assert.assertTrue(flag);
    }

    @Test
    public void verify_Login(){
       loginPage.login();
        homePage = new HomePage(driver);
       Assert.assertEquals(homePage.homePageTitle(),"GoToMeeting Hub");
    }

}
