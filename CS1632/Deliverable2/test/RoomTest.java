import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Test;


public class RoomTest {

	/* room.hasItem() should always return false if the user has no
	 * items.  Create a room object with no items in it, hasItem() should
	 * be false.
	 */
	@Test
	public void testHasItemAllFalse() {
		Room room = new Room(false, false, false, false, false);
		
		assertFalse(room.hasItem());
	}
	
	/*
	 * If the room has more than zero items, hasItems should return true.
	 * Construct a room with an item in at and check this property.
	 */
	@Test
	public void testHasItemOneTrue() {
		Room room = new Room(false, true, false, false, false);
		
		assertTrue(room.hasItem());
	}
	
	/*
	 * If the room has all of the items, should hasItem() should definitely return true.
	 * Create a room with all of the items, make sure hasItem has the proper value.
	 */
	@Test
	public void testHasItemAllTrue() {
		Room room = new Room(true, true, true, false, false);
		
		assertTrue(room.hasItem());
	}
	
	/*
	 * If there is cream in the room, hasCream() should return true.
	 */
	@Test
	public void testHasCream() {
		Room room = new Room(false, true, false, false, false);
		
		assertTrue(room.hasCream());
	}
	
	/*
	 * If there is no cream in the room, has cream should return false 
	 */
	@Test
	public void testHasNoCream() {
		Room room = new Room(false, false, false, false, false);
		
		assertFalse(room.hasCream());
	}
	
	/*
	 * If there is coffee in the room, hasCoffee should return true
	 */
	@Test
	public void testHasCoffee() {
		Room room = new Room(true, false, false, false, false);
		
		assertTrue(room.hasCoffee());
	}
	
	/*
	 * If there is no coffee in the room, hasCoffee() should return false
	 */
	@Test
	public void testHasNoCoffee() {
		Room room = new Room(false, false, false, false, false);
		
		assertFalse(room.hasCoffee());
	}
	
	/*
	 * If there is sugar in the room, hasSugar() should return room.
	 */
	@Test
	public void testHasSugar() {
		Room room = new Room(false, false, true, false, false);
		
		assertTrue(room.hasSugar());
	}
	
	/*
	 * If there is no sugar, hasSugar() should return false.
	 */
	@Test
	public void testHasNoSugar() {
		Room room = new Room(false, false, false, false, false);
		
		assertFalse(room.hasSugar());
	}
	
	/*
	 * If room has a north exit, northExit() should return true
	 */
	@Test
	public void testHasNorthExit() {
		Room room = new Room(false, false, false, true, false);
		
		assertTrue(room.northExit());
	}
	
	/*
	 * If room has no north exit, northExit() should return false
	 */
	@Test
	public void testNoNorthExit() {
		Room room = new Room(false, false, false, false, false);
		
		assertFalse(room.northExit());
	}

	/*
	 * If room has a south exit, southExit() should return true
	 */
	@Test
	public void testHasSouthExit() {
		Room room = new Room(false, false, false, false, true);
		
		assertTrue(room.southExit());
	}
	
	/*
	 * If room has no south exit, southExit() should return false
	 */
	@Test
	public void testNoSouthExit() {
		Room room = new Room(false, false, false, false, false);
		
		assertFalse(room.southExit());
	}
	
		/*
	 * If a room has a north exit, its description should indicate this.
	 * Create a room with northExit = true, and check to make sure the description
	 * describes a door to the north (contains "door leads North").
	 */
	@Test
	public void testNorthDescriptionPresent() {
		Room room = new Room(false, false, false, true, false);
		
		String desc = room.getDescription();
		
		assertThat(desc, containsString("door leads North"));
	}
	
	/*
	 * If a room has no north exit (initialized with northExit set to false), then
	 * the description string should not contain "door leads North".
	 */
	@Test
	public void testNoNorthDescriptionPresent() {
		Room room = new Room(false, false, false, false, false);
		String desc = room.getDescription();
		
		assertThat(desc, not(containsString("door leads North")));
	}
	
	/*
	 * As above, if a room has a south exit, we should see "Door leads
	 * South" in the description.
	 */
	@Test
	public void testSouthDescription() {
		Room room = new Room(false, false, false, false, true);
		String desc = room.getDescription();
		
		assertThat(desc, containsString("door leads South"));
	}
	
	/*
	 * As above, if a room has no south exit, we should not see "Door leads
	 * South" in the description.
	 */
	@Test
	public void testNoSouthDescription() {
		Room room = new Room(false, false, false, false, false);
		String desc = room.getDescription();
		
		assertThat(desc, not(containsString("door leads South")));
	}
	
	/*
	 * Get description should return the description.  Since we can't set this with a
	 * constructor and it's a private field, we use reflection to ensure that the method
	 * return value is the same as the _description field.
	 */
	@Test
	public void testDescription() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Room room = new Room(false, false, false, false, false);
		Class<? extends Room> roomClass = room.getClass();
		
		Field desc = roomClass.getDeclaredField("_description");
		desc.setAccessible(true);
		assertEquals(desc.get(room), room.getDescription());
	}
}
