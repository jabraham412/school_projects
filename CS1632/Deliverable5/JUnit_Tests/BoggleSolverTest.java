/**
* BoggleSolverTest.java
*
* @author John Abraham
*/

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class BoggleSolverTest {
	
	ArrayList<String> b;
	
	@Before
	public void setUp() throws Exception {
		b = new ArrayList<String>() {{
			add("fear"); add("fears"); add("fen"); add("fee");
			add("feet"); add("fees"); add("fer"); add("fere");
			add("feres"); add("fet"); add("few"); add("ear");
			add("ears"); add("enter"); add("entera"); add("enters");
			add("era"); add("ere"); add("ers"); add("are");
			add("ares"); add("ars"); add("arse"); add("near");
			add("nears"); add("nee"); add("net"); add("new");
			add("newt"); add("news"); add("ref"); add("renew");
			add("renews"); add("rent"); add("rente"); add("rentes");
			add("ree"); add("reef"); add("rees"); add("ret");
			add("res"); add("tee"); add("teen"); add("rea");
			add("tear"); add("tears"); add("ten"); add("tew");
			add("tews"); add("twee"); add("tween"); add("wee");
			add("ween"); add("weer"); add("wear"); add("wears");
			add("wen"); add("went"); add("were"); add("wet");
			add("wren"); add("see"); add("seen"); add("seer");
			add("sea"); add("sear"); add("sen"); add("sene");
			add("sent"); add("ser"); add("sere"); add("set");
			add("sew"); add("sewn"); add("sweer"); add("swear");
			add("swear");
			}};
	}
	
	@Test
	public void testNumberOfWordsFound() throws Exception {
		BoggleSolver a = new BoggleSolver();
		assertEquals(a.wordsFound().size(), b.size());
	}

	@Test
	public void testWordsFound() throws Exception {
		BoggleSolver a = new BoggleSolver();
		assertEquals(a, b);
	}

}