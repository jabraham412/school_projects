import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class PersonalInformationTests {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "https://www.facebook.com";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		//Begin each test by signing into facebook
		driver.get(baseUrl + "/");
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("someuser123123@gmail.com");
		driver.findElement(By.id("pass")).clear();
		driver.findElement(By.id("pass")).sendKeys("helloworld123");
		driver.findElement(By.id("u_0_n")).click();
	}


	/**Scenario: Add an address*/
	@Test
	public void testAddAnAddress() throws Exception {
		driver.get(baseUrl + "/profile.php?id=100009171314243&sk=about&section=contact-info");
		driver.findElement(By.linkText("Contact and Basic Info")).click();
		driver.findElement(By.xpath("//div[@id='pagelet_contact']/div/ul/li[3]/a/span")).click();
		driver.findElement(By.name("current_address")).clear();
		driver.findElement(By.name("current_address")).sendKeys("123 semple st");
		driver.findElement(By.name("__submit__")).click();


		WebElement bodyTag = driver.findElement(By.tagName("body"));
		assertTrue(bodyTag.getText().contains("123 semple st"));
	}


	/**Scenario: Add a favorite quote*/
	@Test
	public void testAddAFavoriteQuote() throws Exception {
		driver.get(baseUrl + "/profile.php?id=100009171314243&sk=about");
		driver.findElement(By.xpath("//div[@id='u_0_2o']/ul/li[6]/a/span")).click();
		driver.findElement(By.xpath("//div[@id='pagelet_quotes']/div/ul/li/a/span")).click();
		driver.findElement(By.name("textarea")).clear();
		driver.findElement(By.name("textarea")).sendKeys("somequote");
		driver.findElement(By.name("__submit__")).click();

		WebElement bodyTag = driver.findElement(By.tagName("body"));
		assertTrue(bodyTag.getText().contains("somequote"));
	}

	/**Scenario: Add a bio*/
	@Test
	public void testAddABio() throws Exception {
		driver.get(baseUrl + "/profile.php?id=100009171314243&sk=about");
		driver.findElement(By.linkText("Details About You")).click();
		driver.findElement(By.cssSelector("span._1f-_._50f4")).click();
		driver.findElement(By.name("textarea")).clear();
		driver.findElement(By.name("textarea")).sendKeys("Hi there");
		driver.findElement(By.name("__submit__")).click();

		WebElement bodyTag = driver.findElement(By.tagName("body"));
		assertTrue(bodyTag.getText().contains("Hi there"));
	}

	/**Scenario: Add a relationship status*/
	@Test
	public void testAddARelationshipStatus() throws Exception {
		driver.get(baseUrl + "/profile.php?id=100009171314243&sk=about&section=relationship");
		driver.findElement(By.xpath("//div[@id='pagelet_relationships']/div/ul/li/a/div/div/div")).click();
		new Select(driver.findElement(By.name("status"))).selectByVisibleText("Single");
		driver.findElement(By.name("__submit__")).click();

		WebElement bodyTag = driver.findElement(By.tagName("body"));
		assertTrue(bodyTag.getText().contains("Single"));
	}

	/**Scenario: Add a mobile phone number*/
	@Test
	public void testAddAMobilePhoneNumber() throws Exception {
		driver.get(baseUrl + "/profile.php?id=100009171314243&sk=about&section=contact-info");
		driver.findElement(By.cssSelector("span._1f-_._50f4")).click();
		driver.findElement(By.name("phone_number[705456762826020]")).clear();
		driver.findElement(By.name("phone_number[705456762826020]")).sendKeys("4124252339");
		driver.findElement(By.name("__submit__")).click();
		driver.findElement(By.linkText("About")).click();


		WebElement bodyTag = driver.findElement(By.tagName("body"));
		assertTrue(bodyTag.getText().contains("2339"));
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
		fail(verificationErrorString);
		}
	}
}