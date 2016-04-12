import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;


public class HouseTest {
	
	/**********************************************************************************
	 * Tests the getCurrentRoomInfo method in the House Class.
	 * The return value of this method depends on the Room class.
	 * We're creating a House with only one Room and checking if
	 * the getCurrentRoomInfo method matches the description of
	 * the only room we created.
	 */
	@Test
	public void testgetCurrentRoomInfo() {
		Room[] r = { new Room(false, false, false, false, false) };
		House h = new House(r);
		assertEquals(r[0].getDescription(), h.getCurrentRoomInfo());
	}
	
	/**********************************************************************************
	 * The next 4 Tests test requirement #4:
	 * The system shall allow a player to move North only if a door exists going North.
	 */
	
	/**
	 * Tests the moveNorth method starting from the first room in the house and there
	 * exists a door going North. This method returns void and it has a side effect 
	 * that changes the value of the private variable _currentRoom. To test it we
	 * need to use reflection to gain access to the private variable _currentRoom.
	 */
	@Test
	public void testMoveNorthAndDoorExists() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		House h = new House(2);
		h.moveNorth();

		Field privateCurrentRoomField = House.class.getDeclaredField("_currentRoom");
		privateCurrentRoomField.setAccessible(true);
		int privateCurrRoom = (Integer)privateCurrentRoomField.get(h);
		assertEquals(privateCurrRoom, 1);
	}
	
	/**
	 * Tests the moveNorth method when the player is in the North most room in the house
	 * and there does not exist a door going North. This method returns void but it has
	 * a side effect that changes the value of the private variable _currentRoom. To test
	 * it we need to use reflection to gain access to the private variable _currentRoom.
	 */
	@Test
	public void testMoveNorthAndDoorDoesNotExist() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		House h = new House(2);
		h.moveNorth();
		h.moveNorth();
		
		Field privateCurrentRoomField = House.class.getDeclaredField("_currentRoom");
		privateCurrentRoomField.setAccessible(true);
		int privateCurrRoom = (Integer)privateCurrentRoomField.get(h);
		assertEquals(privateCurrRoom, 1);
	}
	
	/**
	 * Tests the moveSouth method starting from the first room in the house and there
	 * DOES NOT exist a door going South. This method returns void but it has a side
	 * effect that changes the value of the private variable _currentRoom. To test it
	 * we need to use reflection to gain access to the private variable _currentRoom.
	 */
	@Test
	public void testMoveSouthAndDoorDoesNotExist() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		House h = new House(2);
		h.moveSouth();

		Field privateCurrentRoomField = House.class.getDeclaredField("_currentRoom");
		privateCurrentRoomField.setAccessible(true);
		int privateCurrRoom = (Integer)privateCurrentRoomField.get(h);
		assertEquals(privateCurrRoom, 0);
	}

	/**
	 * Tests the moveSouth method when the player is in a room in the house where there
	 * exists a door going South. This method returns void but it has a side effect that
	 * changes the value of the private variable _currentRoom. To test it we need to use
	 * reflection to gain access to the private variable _currentRoom.
	 */
	@Test
	public void testMoveSouthAndDoorExists() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		House h = new House(2);
		h.moveNorth();
		h.moveSouth();
		
		Field privateCurrentRoomField = House.class.getDeclaredField("_currentRoom");
		privateCurrentRoomField.setAccessible(true);
		int privateCurrRoom = (Integer)privateCurrentRoomField.get(h);
		assertEquals(privateCurrRoom, 0);
	}
	
	/**********************************************************************************
	 * Tests look method if there's coffee in the room. Mock objects have been used in
	 * this test because there's more than one dependency. The look method depends on
	 * Room and Player. Mock objects eliminate these dependencies.
	 */
	@Test
	public void testLookGetCoffee() {
		House h = new House(6);
		Player mockPlayer = mock(Player.class);
		Room mockRoom = mock(Room.class);
		
		when(mockRoom.hasItem()).thenReturn(true);
		when(mockRoom.hasCoffee()).thenReturn(true);

		h.look(mockPlayer, mockRoom);
		verify(mockPlayer).getCoffee();
	}

	/**
	 * Tests look method if there's cream in the room. Mock objects have been used in
	 * this test because there's more than one dependency. The look method depends on
	 * Room and Player. Mock objects eliminate these dependencies.
	 */
	@Test
	public void testLookGetCream() {
		House h = new House(6);
		Player mockPlayer = mock(Player.class);
		Room mockRoom = mock(Room.class);
		
		when(mockRoom.hasItem()).thenReturn(true);
		when(mockRoom.hasCream()).thenReturn(true);

		h.look(mockPlayer, mockRoom);
		verify(mockPlayer).getCream();		
	}

	/**
	 * Tests look method if there's sugar in the room. Mock objects have been used in
	 * this test because there's more than one dependency. The look method depends on
	 * Room and Player. Mock objects eliminate these dependencies.
	 */
	@Test
	public void testLookGetSugar() {
		House h = new House(6);
		Player mockPlayer = mock(Player.class);
		Room mockRoom = mock(Room.class);
		
		when(mockRoom.hasItem()).thenReturn(true);
		when(mockRoom.hasSugar()).thenReturn(true);

		h.look(mockPlayer, mockRoom);
		verify(mockPlayer).getSugar();
	}
		
	/**********************************************************************************
	 * Tests the generateRooms method in the House class which returns an array of Room
	 * objects based on the number of room specified. Here we're only testing to see
	 * if the method generates something and doesn't just return null.
	 */
	@Test
	public void testGenerateRooms() {
		Room[] rooms = null;
		House h = new House(0);
		rooms = h.generateRooms(6);
		assertNotNull(rooms);
	}
}