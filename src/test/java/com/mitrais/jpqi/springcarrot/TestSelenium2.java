package com.mitrais.jpqi.springcarrot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class TestSelenium2 {
    WebDriver driver;
    @Test (priority = 2)
    public void loginWrong(){
        System.setProperty("webdriver.chrome.driver","D:\\Master\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        Reporter.log("browser is opened now");
        driver.get("http://localhost:4200");
        Reporter.log("browser is opening localhost:4200");
        WebElement elementUsername = driver.findElement(By.id("username"));
        WebElement elementPassword = driver.findElement(By.id("password"));
        WebElement buttonSubmit = driver.findElement(By.id("loginSubmit"));
        elementUsername.sendKeys("someones@rocketmail.com");
        elementPassword.sendKeys("passwordsomeones");
        buttonSubmit.click();
        Reporter.log("Incorrect credential submitted");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement header = driver.findElement(By.id("homeLogo"));
        assertTrue((header.isDisplayed()));
        driver.close();
//        WebElement socialFoundationTab = driver.findElement(By.xpath("//*[@id=\"ngb-tab-3\"]/text()"));
//        socialFoundationTab.click();
    }
}