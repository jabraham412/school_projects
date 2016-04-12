import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	private Player player;
	private Field cream;
	private Field sugar;
	private Field coffee;

	/*
	 * Here, we set up a player that we'll use to test the methods to get
	 * cream, sugar, etc.  We do it this way because setting up reflection is annoying,
	 * so we will only do it once.
	 */
	@Before
	public void setUp() throws Exception {
		player = new Player(false, false, false);
		Class<? extends Player> playerClass = player.getClass();
		
		cream = playerClass.getDeclaredField("_hasCream");
		sugar = playerClass.getDeclaredField("_hasSugar");
		coffee = playerClass.getDeclaredField("_hasCoffee");
		
		cream.setAccessible(true);
		sugar.setAccessible(true);
		coffee.setAccessible(true);
	}

	/*
	 * If the player has no items, then they clearly don't have all the items.
	 * So, construct a player that has no items, and ensure hasAllItems returns false.
	 */
	@Test
	public void testHasNoItems() {
		Player p = new Player(false, false, false);
		
		assertFalse(p.hasAllItems());
	}
	
	/*
	 * If the player has some (but not all) items, then hasAllItems should still return
	 * false, so we should construct a player as such and test it.
	 */
	@Test
	public void testHasSomeItems() {
		Player p = new Player(false, true, false);
		
		assertFalse(p.hasAllItems());
	}
	
	/*
	 * If we construct a player with all the items, then hasAllItems should definitely
	 * return true.
	 */
	@Test
	public void testHasAllItems() {
		Player p = new Player(true, true, true);
		
		assertTrue(p.hasAllItems());
	}
	
	/*
	 * When the player has no items, the player should not be able to win the
	 * game.  So we construct a player object, specifying that they have no items,
	 * and ensure that .drink() returns false
	 */
	@Test
	public void testDrinkNoItems() {
		Player p = new Player(false, false, false);
		assertFalse(p.drink());
	}

	/*
	 * When the player has some items, but not all of them, .drink should still return
	 * false, since they shouldn't be able to win like that.
	 */
	@Test
	public void testDrinkSomeItems() {
		Player p = new Player(true, false, true);
		assertFalse(p.drink());
	}
	
	/*
	 * Finally, when the player has all of the items, they should be able to drink
	 * the coffee successfully, so .drink will return true.
	 */
	@Test
	public void testDrinkAllItems() {
		Player p = new Player(true, true, true);
		assertTrue(p.drink());
	}
	
	/*
	 * Using the generated player object at the top and the reflected fields created
	 * to monitor the private fields of said object, we use reflect to first assert
	 * that the player doesn't have the cream at the beginning of the game, and then 
	 * we call getCream() and ensure that they do have the cream.
	 */
	@Test
	public void testGetCream() {
		try {
			assertFalse(((Boolean) cream.get(player)).booleanValue());
			player.getCream();
			assertTrue(((Boolean) cream.get(player)).booleanValue());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// We don't expect to end up in this situation
			e.printStackTrace();
		}
	}
	
	/*
	 * Using the generated player object at the top and the reflected fields created
	 * to monitor the private fields of said object, we use reflect to first assert
	 * that the player doesn't have the sugar at the beginning of the game, and then 
	 * we call getSugar() and ensure that they do have the sugar.
	 */
	@Test
	public void testGetSugar() {
		try {
			assertFalse(((Boolean) sugar.get(player)).booleanValue());
			player.getSugar();
			assertTrue(((Boolean) sugar.get(player)).booleanValue());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// We don't expect to end up in this situation
			e.printStackTrace();
		}
	}
	
	/*
	 * Using the generated player object at the top and the reflected fields created
	 * to monitor the private fields of said object, we use reflect to first assert
	 * that the player doesn't have the coffee at the beginning of the game, and then 
	 * we call getCoffee() and ensure that they do have the coffee.
	 */
	@Test
	public void testGetCoffee() {
		try {
			assertFalse(((Boolean) coffee.get(player)).booleanValue());
			player.getCoffee();
			assertTrue(((Boolean) coffee.get(player)).booleanValue());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// We don't expect to end up in this situation
			e.printStackTrace();
		}
	}
}
