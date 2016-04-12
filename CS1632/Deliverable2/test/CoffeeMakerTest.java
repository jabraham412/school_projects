import java.io.ByteArrayOutputStream;

import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

public class CoffeeMakerTest {
	private final ByteArrayOutputStream systemOut = new ByteArrayOutputStream();

	/*
	 * Set up System.out to use our output buffer
	 */
	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(systemOut));
	}

	/*
	 * Go back to the normal output buffers
	 */
	@After
	public void tearDown() throws Exception {
		System.setOut(null);
	}

	/*
	 * Here, we have a method called runArgs that should output the instructions
	 * to play the game.  Though the instructions may change, it seems reasonable to expect
	 * that the output should always contain the title "Instructions for Coffee Maker Quest".
	 * Since we reassign System.out to our output buffer, we can check the printed output.
	 * 
	 * Additionally, we make sure that runArgs returns with the right return value (of 0)
	 */
	@Test
	public void testRunArgs() {
		CoffeeMaker c = new CoffeeMaker();
		String arg = "";
		
		int result = c.runArgs(arg);
		assertThat(systemOut.toString(), containsString("Instructions for Coffee Maker Quest"));
		
		assertEquals(result, 0);
	}
}
