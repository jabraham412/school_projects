package com.bdd.air;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class signinSteps {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    private String temp =null;

    @Given("^I am on the log in page https://www.facebook.com/$")
    public void I_am_on_the_log_in_page_https_www_facebook_com() throws Throwable {
        driver = new FirefoxDriver();
        baseUrl = "https://www.facebook.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl + "/");
    }

    @Given("^I input an incorrect valid email address and incorrect password$")
    public void I_input_an_incorrect_valid_email_address_and_incorrect_password() throws Throwable {
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("111");
        driver.findElement(By.id("u_0_8")).clear();
        driver.findElement(By.id("u_0_8")).sendKeys("411368246@qq.com");
        driver.findElement(By.id("u_0_a")).clear();
        driver.findElement(By.id("u_0_a")).sendKeys("hyfcbq");
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("273923333335@qq.com");
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("3343");

        temp="Incorrect Email";
    }

    @When("^I click log in$")
    public void I_click_log_in() throws Throwable {
        driver.findElement(By.id("u_0_n")).click();
    }

    @Then("^I should see \"([^\"]*)\"$")
    public void I_should_see(String arg1) throws Throwable {
        assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*"+temp+"[\\s\\S]*$"));
    }

    @Given("^I input the correct username and password$")
    public void I_input_the_correct_username_and_password() throws Throwable {
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("111");
        driver.findElement(By.id("u_0_8")).clear();
        driver.findElement(By.id("u_0_8")).sendKeys("411368246@qq.com");
        driver.findElement(By.id("u_0_a")).clear();
        driver.findElement(By.id("u_0_a")).sendKeys("hyfcbq");
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("cs1699deliver3@gmail.com");
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("lola0944");

        temp="Welcome";
    }

    @Given("^I input a correct valid email address and an incorrect password$")
    public void I_input_a_correct_valid_email_address_and_an_incorrect_password() throws Throwable {
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("111");
        driver.findElement(By.id("u_0_8")).clear();
        driver.findElement(By.id("u_0_8")).sendKeys("411368246@qq.com");
        driver.findElement(By.id("u_0_a")).clear();
        driver.findElement(By.id("u_0_a")).sendKeys("hyfcbq");
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("cs1699deliver3@gmail.com");
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("lola0944");

        temp="Welcome";
    }

    @Given("^input an correct e-mail address and an incorrect password$")
    public void input_an_correct_e_mail_address_and_an_incorrect_password() throws Throwable {
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("111");
        driver.findElement(By.id("u_0_8")).clear();
        driver.findElement(By.id("u_0_8")).sendKeys("411368246@qq.com");
        driver.findElement(By.id("u_0_a")).clear();
        driver.findElement(By.id("u_0_a")).sendKeys("hyfcbq");
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("sscs1699deliver3@gmail.com");
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("11");

        temp="Incorrect Email";
    }

    @Given("^I input an invalid unregistered e-mail address$")
    public void I_input_an_invalid_unregistered_e_mail_address() throws Throwable {
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("yic59@pitt.edu");

        temp="Incorrect Email";
    }
}