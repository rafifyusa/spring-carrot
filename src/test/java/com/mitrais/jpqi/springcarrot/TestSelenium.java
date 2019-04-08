package com.mitrais.jpqi.springcarrot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class TestSelenium {
    WebDriver driver;

    @Test (priority = 1)
    public void loginCorrect(){
        System.setProperty("webdriver.chrome.driver","D:\\Master\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        Reporter.log("browser is opened now");
        driver.get("http://localhost:4200");
        Reporter.log("browser is going to localhost:4200");
        WebElement elementUsername = driver.findElement(By.id("username"));
        WebElement elementPassword = driver.findElement(By.id("password"));
        WebElement buttonSubmit = driver.findElement(By.id("loginSubmit"));
        elementUsername.sendKeys("someone@rocketmail.com");
        elementPassword.sendKeys("passwordsomeone");
//        elementPassword.submit();
        buttonSubmit.click();
        Reporter.log("correct credential submitted");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement header = driver.findElement(By.id("homeLogo"));
        assertTrue((header.isDisplayed()));
        driver.close();

//        WebElement homeButton = driver.findElement(By.id("homeLogo"));
//        homeButton.click();
//        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
//        WebElement tabAdminButton = driver.findElement(By.xpath("/html/body/app-root/section/app-home/section/div/div[2]/a[5]"));
//        tabAdminButton.click();
        }

}