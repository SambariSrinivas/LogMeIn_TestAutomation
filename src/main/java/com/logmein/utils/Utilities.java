package com.logmein.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import java.io.File;
import java.io.IOException;

public class Utilities {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public Utilities(WebDriver driver) {
        this.driver = driver;
    }

    /****
     * click on the element.
     ****/
    public void click(WebElement webElement) {
        click(webElement, false);
    }

    /****
     * click on the element.
     * @param webElement web element.
     ****/
    public void click(WebElement webElement, boolean scroll) {
        if (scroll) {
            scrollDown();
        }
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.click();
    }

    /***
     * wait until element to be visible.
     * @param webElement web element.
     */
    protected void waitUntilElementVisible(WebElement webElement) {
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }


    /***
     * enter the text into text box.
     * @param webElement locator of web element.
     * @param text text to be fill in web element like textbox or text area.
     *
     */
    public void inputText(WebElement webElement, String text) {
        waitUntilElementVisible(webElement);
        webElement.sendKeys(text);
    }

    /***
     * Check whether element is present or not.
     *  @param webElement web element.
     */
    private boolean isElementPresent(WebElement webElement) {
        try {
            waitUntilElementVisible(webElement);
            webElement.isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /***
     * Get the inner text from the element.
     * @param webElement .
     * @return {String} returns text if element is present otherwise null.
     */
    public String getInnerText(WebElement webElement) {
        return isElementPresent(webElement) ? webElement.getText() : null;
    }

    /***
     * Scroll down to window.
     */
    private void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1000)");
    }

    //This method will move and click on an element
    public void actionMoveAndClick(WebElement element) {
        Actions action = new Actions(driver);
        try {
            this.wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
            action.moveToElement(element).click().build().perform();
            System.out.println("Successfully Action Moved and Clicked on the WebElement, using locator: " + "<" + element.toString() + ">");
        } catch (StaleElementReferenceException elementUpdated) {
            boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
            if (elementPresent) {
                action.moveToElement(element).click().build().perform();
                System.out.println("(Stale Exception) - Successfully Action Moved and Clicked on the WebElement, using locator: " + "<" + element.toString() + ">");
            }
        } catch (Exception e) {
            System.out.println("Unable to Action Move and Click on the WebElement, using locator: " + "<" + element.toString() + ">");
            Assert.fail("Unable to Action Move and Click on the WebElement, Exception: " + e.getMessage());
        }
    }

    //This method will move and click on element and selects the group or user
    public void actionMoveAndClick(WebElement element, String groupOrUserName) {
        Actions action = new Actions(driver);
        try {
            this.wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
            action.moveToElement(element).click().build().perform();
            element.sendKeys(groupOrUserName);
            Thread.sleep(300);
            element.sendKeys(Keys.ARROW_DOWN);
            element.sendKeys(Keys.ENTER);
            System.out.println("Successfully Moved and Clicked on the WebElement, selected Group or User using locator: " + "<" + element.toString() + ">");
        } catch (StaleElementReferenceException elementUpdated) {
            boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
            if (elementPresent) {
                element.sendKeys(groupOrUserName);
                element.sendKeys(Keys.ARROW_DOWN);
                element.sendKeys(Keys.ENTER);
                System.out.println("Successfully Moved and Clicked on the WebElement, selected Group or User using locator: " + "<" + element.toString() + ">");
            }
        } catch (Exception e) {
            System.out.println("Unable to Move and Click on the WebElement, using locator: " + "<" + element.toString() + ">");
            Assert.fail("Unable to Move and Click on the WebElement, Exception: " + e.getMessage());
        }
    }

    //Overriding the above method to send two users
    public void actionMoveAndClick(WebElement element, String firstUser, String secondUser) {
        Actions action = new Actions(driver);
        try {
            this.wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
            action.moveToElement(element).click().sendKeys(firstUser).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
            action.moveToElement(element).click().sendKeys(secondUser).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
            System.out.println("Successfully Action Moved and Clicked on the WebElement, selected users using locator: " + "<" + element.toString() + ">");
        } catch (StaleElementReferenceException elementUpdated) {
            boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
            if (elementPresent) {
                action.moveToElement(element).click().sendKeys(firstUser).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
                action.moveToElement(element).click().sendKeys(secondUser).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
                System.out.println("Successfully Action Moved and Clicked on the WebElement, selected users using locator: " + "<" + element.toString() + ">");
            }
        } catch (Exception e) {
            System.out.println("Unable to Action Move and Click on the WebElement, using locator: " + "<" + element.toString() + ">");
            Assert.fail("Unable to Action Move and Click on the WebElement, Exception: " + e.getMessage());
        }
    }

    protected void takeScreenshotAtEndOfTest() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        File destFile = new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png");
        FileUtils.copyFile(scrFile, destFile);

    }
}
