import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class NotificationTests {
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

	/**Scenario: Receive a notification when a user sent me a friend request*/
	@Test
	public void testReceiveNotificationForFriendRequests() throws Exception {
		driver.get(baseUrl + "/");
		driver.findElement(By.name("requests")).click();
		driver.findElement(By.cssSelector("a.seeMore > span")).click();

		WebElement bodyTag = driver.findElement(By.tagName("body"));
		assertTrue(bodyTag.getText().contains("Respond to Your Friend Request"));
	}

	/**Scenario: Receive a notification when a message has been sent to my inbox*/
	@Test
	public void testReceiveNotificationForNewInboxMessage() throws Exception {
		driver.get(baseUrl + "/");
		driver.findElement(By.name("mercurymessages")).click();
		driver.findElement(By.cssSelector("#MercuryJewelFooter > div.jewelFooter > a.seeMore > span")).click();

		WebElement bodyTag = driver.findElement(By.tagName("body"));
		assertTrue(bodyTag.getText().contains("new message"));
	}

	/**Scenario: Receive a notification when a post has been shared on my wall*/
	@Test
	public void testReceiveNotificationForNewPostOnMyWall() throws Exception {
		driver.get(baseUrl + "/");
		driver.findElement(By.name("notifications")).click();
		driver.findElement(By.cssSelector("#fbNotificationsFlyout > div.jewelFooter > a.seeMore > span")).click();

		WebElement bodyTag = driver.findElement(By.tagName("body"));
		assertTrue(bodyTag.getText().contains("posted on your"));
	}

	/**Scenario: Receive a notification when one of my shared posts has been liked by a user*/
	@Test
	public void testReceiveNotificationWhenMyPostHasBeenLikedBySomeone() throws Exception {
		driver.get(baseUrl + "/");
		driver.findElement(By.name("notifications")).click();
		driver.findElement(By.cssSelector("#fbNotificationsFlyout > div.jewelFooter > a.seeMore > span")).click();

		WebElement bodyTag = driver.findElement(By.tagName("body"));
		assertTrue(bodyTag.getText().contains("this is my first post"));
	}


	/**Scenario: Receive a notification when one of my pictures has been liked by a user*/
	@Test
	public void testReceiveNotificationWhenMyPicturesHasBeenLikedBySomeone() throws Exception {
		driver.get(baseUrl + "/");
		driver.findElement(By.name("notifications")).click();
		driver.findElement(By.cssSelector("#fbNotificationsFlyout > div.jewelFooter > a.seeMore > span")).click();

		WebElement bodyTag = driver.findElement(By.tagName("body"));
		assertTrue(bodyTag.getText().contains("likes your"));
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