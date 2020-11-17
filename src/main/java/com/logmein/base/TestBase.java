package com.logmein.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {
    protected WebDriver driver;
    public static Properties prop;
    static int PAGE_LOAD_TIMEOUT = 30;
    static int IMPLICIT_WAIT = 10;


    //Fetching properties from a file
    public TestBase() {

        try {
            prop = new Properties();
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("config.properties")).getFile());
            FileInputStream fin = new FileInputStream(file);
            prop.load(fin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //WebDriver Initialization
    public void initialization(String browser) {

        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/main/resources/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "/src/main/resources/msedgedriver.exe");
                driver = new EdgeDriver();
                break;
            case "opera":
                System.setProperty("webdriver.opera.driver", System.getProperty("user.dir") + "/src/main/resources/operadriver.exe");
                driver = new OperaDriver();
                break;
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.get(prop.getProperty("url"));
    }

    @Parameters("browser")
    @BeforeMethod
    public void test_Base_SetUp(String browser) {
        initialization(browser);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
