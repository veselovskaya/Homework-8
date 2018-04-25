package com.company;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jayway.restassured.RestAssured;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.auth.AuthType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Authorization {

    /// private BrowserMobProxyServer server;
    protected WebDriver driver;
    boolean haveResult = false;
    WebClient wc = new WebClient();

    @Before
    public void newDriver(){
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        ///   server = new BrowserMobProxyServer();
        ///   server.start(4444);
        ///   server.autoAuthorization("", "admin", "admin123", AuthType.BASIC);

        ///   Proxy seleniumProxy = ClientUtil.createSeleniumProxy(server);
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        ///   ChromeOptions options = new ChromeOptions().setProxy(seleniumProxy);
//        options.setCapability("capability_name", "capability_value");
        ///   driver = new ChromeDriver(options);
//        driver = new ChromeDriver(capabilities);
        driver = new ChromeDriver();

    }

    @Test
    public void negaiveLoginTest() {
        driver.get("https://diploma-courses.7bits.it/login");

        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("login"))
                .sendKeys("admin");

        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("admin1");

        driver.findElement(By.xpath("//button[@type='submit']")).click();


        //RestAssured.get("https://diploma-courses.7bits.it/login").statusCode();
        //int pageCode = RestAssured.get("https://diploma-courses.7bits.it/login").statusCode();
        try{
            Page pageHndl = wc.getPage("https://diploma-courses.7bits.it/login");
            int pageCode = pageHndl.getWebResponse().getStatusCode();
            System.out.println("Page status " + pageCode);
            if(pageCode == 401){
                haveResult = true;
            }
        }catch (IOException e){}


        //statusCode 403;
    }

    @Test
    public void positiveLoginTest() {
        driver.get("https://diploma-courses.7bits.it/login");

        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("login"))
                .sendKeys("admin");

        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("admin");

        driver.findElement(By.className("form-password-field__hide-show")).click();

        assertEquals(
                driver.findElement(By.name("password")).getAttribute("type"),
                "text"
        );

        driver.findElement(By.className("form-password-field__hide-show_opened")).click();

        assertEquals(
                driver.findElement(By.name("password")).getAttribute("type"),
                "password"
        );

        driver.findElement(By.xpath("//button[@type='submit']")).click();


        // boolean linkPersonal = isElementPresent(By.linkText("admin"));

/*
        assertEquals(
                driver.findElement(By.linkText("admin")).getText(),
                "admin"
        );
*/
        //driver.findElement(By.linkText("admin")).click();
        //driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/nav/div[1]/div/img")).click();
        //driver.findElement(By.className("left-menu__user-box-logout-icon")).click();
/*
        assertEquals(
                driver.findElement(By.xpath("//button[@type='submit']")).getText(),
                "Войти в систему"
        );
        */

    }


    @After
    public void quitDriver(){
        driver.quit();
    }
}
