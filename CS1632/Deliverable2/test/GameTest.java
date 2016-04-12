import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

import org.junit.Test;


public class GameTest {

	/**************************************************************
	 * Requirement #3:
	 * FUN-INPUT-CAPS - The system shall be case-insensitive
	 * in regards to input values; that is, it shall accept
	 * capital and lower-case letters and treat them as equivalent.
	 * /
	
	/**
	 * Tests if house moves North when "N" is pressed
	 */
	@Test
	public void testDoSomethingMoveNorthUpperCase() {
		House mockHouse = mock(House.class);
		Game g = new Game( mock(Player.class), mockHouse );
		g.doSomething("N");
		verify(mockHouse).moveNorth();
	}
	
	/**
	 * Tests if house moves North when "n" is pressed
	 */
	@Test
	public void testDoSomethingMoveNorthLowerCase() {
		House mockHouse = mock(House.class);
		Game g = new Game( mock(Player.class), mockHouse );
		g.doSomething("n");
		verify(mockHouse).moveNorth();		
	}

	/**
	 * Tests if house moves South when "S" is pressed
	 */
	@Test
	public void testDoSomethingMoveSouthUpperCase() {
		House mockHouse = mock(House.class);
		Game g = new Game( mock(Player.class), mockHouse );
		g.doSomething("S");
		verify(mockHouse).moveSouth();				
	}

	/**
	 * Tests if house South North when "s" is pressed
	 */
	@Test
	public void testDoSomethingMoveSouthLowerCase() {
		House mockHouse = mock(House.class);
		Game g = new Game( mock(Player.class), mockHouse );
		g.doSomething("s");
		verify(mockHouse).moveSouth();				
	}

	/**
	 * Tests if house looks when "L" is pressed
	 */
	@Test
	public void testDoSomethingLookUpperCase() {
		House mockHouse = mock(House.class);
		Player mockPlayer = mock(Player.class);
		Game g = new Game( mockPlayer, mockHouse );
		g.doSomething("L");
		verify(mockHouse).look(mockPlayer, null);
	}
	
	/**
	 * Tests if house looks when "l" is pressed
	 */
	@Test
	public void testDoSomethingLookLowerCase() {
		House mockHouse = mock(House.class);
		Player mockPlayer = mock(Player.class);
		Game g = new Game( mockPlayer, mockHouse );
		g.doSomething("l");
		verify(mockHouse).look(mockPlayer, null);		
	}

	
	/**
	 * Tests if Player shows inventory when "I" is pressed
	 */
	@Test
	public void testDoSomethingShowInventoryUpperCase() {
		House mockHouse = mock(House.class);
		Player mockPlayer = mock(Player.class);
		Game g = new Game( mockPlayer, mockHouse );
		g.doSomething("I");
		verify(mockPlayer).showInventory();
	}
	
	/**
	 * Tests if Player shows inventory when "i" is pressed
	 */
	@Test
	public void testDoSomethingShowInventoryLowerCase() {
		House mockHouse = mock(House.class);
		Player mockPlayer = mock(Player.class);
		Game g = new Game( mockPlayer, mockHouse );
		g.doSomething("i");
		verify(mockPlayer).showInventory();		
	}

	/**
	 * Tests if Player drinks when "D" is pressed
	 */
	@Test
	public void testDoSomethingDrinkUpperCase() {
		House mockHouse = mock(House.class);
		Player mockPlayer = mock(Player.class);
		Game g = new Game( mockPlayer, mockHouse );
		g.doSomething("D");
		verify(mockPlayer).drink();				
	}
	
	/**
	 * Tests if Player drinks when "d" is pressed
	 */
	@Test
	public void testDoSomethingDrinkLowerCase() {
		House mockHouse = mock(House.class);
		Player mockPlayer = mock(Player.class);
		Game g = new Game( mockPlayer, mockHouse );
		g.doSomething("d");
		verify(mockPlayer).drink();		
	}
	
	/**
	 * Tests an edge case. unexpected input. A 0 should be returned
	 */
	@Test
	public void testDoSomethingUnexpectedInputCommand() {
		House mockHouse = mock(House.class);
		Player mockPlayer = mock(Player.class);
		Game g = new Game( mockPlayer, mockHouse );
		assertEquals(g.doSomething("F"), 0);	
	}

	/**
	 * Requirement #9: FUN-HELP
	 * Upon entering "H" for Help, the player shall be shown a listing
	 * of possible commands and  what their effects are.
	 * Tests the Help command when "H" is entered.
	 * A -1 should be return and not a 0.
	 */
	@Test
	public void testDoSomethingHelpCommandUpperCase() {
		House mockHouse = mock(House.class);
		Player mockPlayer = mock(Player.class);
		Game g = new Game( mockPlayer, mockHouse );
		assertEquals(g.doSomething("H"), -1);
	}
	
	/**************************************************************
	 * Tests the run method in the Game class in case of a win.
	 * Code is copied over to this method and slightly modified
	 * because it waits on the program as it is in the Game class.
	 */
	@Test
	public void testRunWin() {
		Player p = new Player(true, true, true);
		House h = new House(6);
		Game g = new Game(p, h);

		boolean gameOver = false;
		boolean win = false;
		
		int status = g.doSomething("D");
		if (status == 1) {
			gameOver = true;
			win = true;
		} else if (status == -1) {
			gameOver = true;
			win = false;
		}
		
		assertTrue(win);
	}
	 
	/**
	* Tests the run method in the Game class in case of a loss.
	*/
	@Test
	public void testRunLose() {
		Player p = new Player(false, false, false);
		House h = new House(6);
		Game g = new Game(p, h);
		
		boolean gameOver = false;
		boolean win = false;
	
		int status = g.doSomething("D");
		if (status == 1) {
			gameOver = true;
			win = true;
		} else if (status == -1) {
			gameOver = true;
			win = false;
		}
		
		assertFalse(win);
	}
}