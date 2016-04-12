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


public class signupSteps {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private String temp=null;


	@Given("^I am on the website https://m.facebook.com/r.php$")
	public void I_am_on_the_website_https_m_facebook_com_r_php() throws Throwable {
		driver = new FirefoxDriver();
		baseUrl = "https://m.facebook.com/r.php";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "/r.php");
	}

	@Given("^I try to sign up without filling any field$")
	public void I_try_to_sign_up_without_filling_any_field() throws Throwable {
		temp="You must fill in all of the fields.";
	}

	@When("^I click sign up$")
	public void I_click_sign_up() throws Throwable {
		driver.findElement(By.id("signup_button")).click();
	}

	@Then("^i should see \"([^\"]*)\"$")
	public void i_should_see(String arg1) throws Throwable {
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*"+temp+"[\\s\\S]*$"));
	}

	@Given("^I input all the information correctly but with existing account.$")
	public void I_input_all_the_information_correctly_but_with_existing_account() throws Throwable {
		driver.findElement(By.name("firstname")).clear();
		driver.findElement(By.name("firstname")).sendKeys("allen");
		driver.findElement(By.name("lastname")).clear();
		driver.findElement(By.name("lastname")).sendKeys("allen");
		driver.findElement(By.name("email")).sendKeys("cs1699deliver3@gmail.com");
		new Select(driver.findElement(By.id("gender"))).selectByVisibleText("Female");
		new Select(driver.findElement(By.id("gender"))).selectByVisibleText("Male");
		driver.findElement(By.id("month")).clear();
		driver.findElement(By.id("month")).sendKeys("1");
		driver.findElement(By.id("day")).clear();
		driver.findElement(By.id("day")).sendKeys("1");
		driver.findElement(By.id("year")).clear();
		driver.findElement(By.id("year")).sendKeys("1990");
		driver.findElement(By.name("pass")).clear();
		driver.findElement(By.name("pass")).sendKeys("lola0944");

		temp="It looks like you already have an account.";
	}

	@Given("^I input all the information correctly but with (\\d+) as password.$")
	public void I_input_all_the_information_correctly_but_with_as_password(int arg1) throws Throwable {
		driver.findElement(By.name("firstname")).clear();
		driver.findElement(By.name("firstname")).sendKeys("allen");
		driver.findElement(By.name("lastname")).clear();
		driver.findElement(By.name("lastname")).sendKeys("allen");
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("yic59@pitt.edu");
		new Select(driver.findElement(By.id("gender"))).selectByVisibleText("Male");
		driver.findElement(By.id("month")).clear();
		driver.findElement(By.id("month")).sendKeys("01");
		driver.findElement(By.id("day")).clear();
		driver.findElement(By.id("day")).sendKeys("01");
		driver.findElement(By.id("year")).clear();
		driver.findElement(By.id("year")).sendKeys("1990");
		driver.findElement(By.name("pass")).clear();
		driver.findElement(By.name("pass")).sendKeys("0000");
		temp="Your password must be at least 6 characters long. Please try another.";
	}


	@Given("^I input all the information correctly but with abc as email or phone.$")
	public void I_input_all_the_information_correctly_but_with_abc_as_email_or_phone() throws Throwable {
		driver.findElement(By.name("firstname")).clear();
		driver.findElement(By.name("firstname")).sendKeys("allen");
		driver.findElement(By.name("lastname")).clear();
		driver.findElement(By.name("lastname")).sendKeys("allen");
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("abc");
		new Select(driver.findElement(By.id("gender"))).selectByVisibleText("Male");
		driver.findElement(By.id("month")).clear();
		driver.findElement(By.id("month")).sendKeys("01");
		driver.findElement(By.id("day")).clear();
		driver.findElement(By.id("day")).sendKeys("01");
		driver.findElement(By.id("year")).clear();
		driver.findElement(By.id("year")).sendKeys("1990");
		driver.findElement(By.name("pass")).clear();
		driver.findElement(By.name("pass")).sendKeys("lola0944");
		temp="Please enter a valid email address or mobile number.";
	}

	@Given("^I input all the information correctly but firstname$")
	public void I_input_all_the_information_correctly_but_firstname() throws Throwable {
		driver.findElement(By.name("lastname")).clear();
		driver.findElement(By.name("lastname")).sendKeys("allen");
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("yic59@pitt.edu");
		new Select(driver.findElement(By.id("gender"))).selectByVisibleText("Male");
		driver.findElement(By.id("month")).clear();
		driver.findElement(By.id("month")).sendKeys("1");
		driver.findElement(By.id("day")).clear();
		driver.findElement(By.id("day")).sendKeys("1");
		driver.findElement(By.id("year")).clear();
		driver.findElement(By.id("year")).sendKeys("1990");
		driver.findElement(By.name("pass")).clear();
		driver.findElement(By.name("pass")).sendKeys("lola0944");
		temp="You must fill in all of the fields.";
	}
}