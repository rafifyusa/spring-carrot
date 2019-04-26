package com.mitrais.jpqi.springcarrot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class LoginAsStaff {
    WebDriver driver;

//    @BeforeClass
//    public void init(){
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//        driver.navigate().to("http://localhost:4200");
//        Reporter.log("browser is going to localhost:4200");
//    }

    @Test(priority = 1)
    public void loginCorrect(){
        System.setProperty("webdriver.chrome.driver","D:\\Master\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        Reporter.log("browser is opened now \n");

        driver.get("http://localhost:4200");
        Reporter.log("browser is opening login page \n");

        WebElement elementUsername = driver.findElement(By.id("username"));
        WebElement elementPassword = driver.findElement(By.id("password"));
        WebElement buttonSubmit = driver.findElement(By.id("loginSubmit"));
        Reporter.log("login page opened \n");

        elementUsername.sendKeys("lukas@getnada.com");
        elementPassword.sendKeys("password");
        buttonSubmit.click();
        Reporter.log("correct credential submitted\n");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement header = driver.findElement(By.id("homeLogo"));
        assertTrue((header.isDisplayed())); //validation: after logged in, there is header displayed.
        Assert.assertEquals(driver.getCurrentUrl(), "localhost:4200/employee");
        Reporter.log("reach the employee screen \n");
    }

    @Test (priority = 2)
    public void openBarnForm() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //delay to access db
        driver.findElement(By.id("linkReward")).click();
        Reporter.log("modal for new barn clicked");

        assertTrue(driver.findElement(By.xpath("/html/body/ngb-modal-window/div/div/form/div[1]/div[1]/input")).isEnabled());
        Reporter.log("modal for new barn opened");
    }

    @Test (priority = 3)
    public void createNewBarn(){
        driver.findElement(By.xpath("/html/body/ngb-modal-window/div/div/form/div[1]/div[1]/input")).sendKeys("barn selenium");
        WebElement drpOwner = driver.findElement(By.xpath("/html/body/ngb-modal-window/div/div/form/div[1]/div[2]/select"));
        drpOwner.click();
        Select owner = new Select(drpOwner);
        owner.selectByVisibleText("Anofial Lightning");
        WebElement dateStartBox = driver.findElement(By.xpath("/html/body/ngb-modal-window/div/div/form/div[1]/div[3]/th[1]/div/input"));
        dateStartBox.sendKeys("12122009");
        driver.findElement(By.xpath("//*[@id=\"input4\"]")).sendKeys("12122020");
        driver.findElement(By.xpath("//*[@id=\"input5\"]")).sendKeys("1000");
        driver.findElement(By.xpath("/html/body/ngb-modal-window/div/div/form/div[1]/div[5]/div/label[2]")).click();
        driver.findElement(By.xpath("/html/body/ngb-modal-window/div/div/form/div[1]/div[6]/div/label[2]")).click();
        Reporter.log("all forms are filled");

        driver.findElement(By.xpath("/html/body/ngb-modal-window/div/div/form/div[2]/button")).click();
        Reporter.log("click submit new barn");

        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.navigate().refresh();
        try {
            WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'barn selenium')]"));
            Assert.assertEquals(element.getText(), "barn selenium");
            Reporter.log("new barn found");
        } catch (Exception e){
            System.out.println(e);
        }
        driver.close();
    }

}
