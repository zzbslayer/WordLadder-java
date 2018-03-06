package WordLadder;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.*;

import static WordLadder.WordLadder.IsAdjacent;
import static WordLadder.WordLadder.IsExist;
import static WordLadder.WordLadder.IsValid;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testIsAdjacent() {
        assertTrue( IsAdjacent("bag","bug"));
        assertTrue( IsAdjacent("cat", "cab"));
        assertFalse( IsAdjacent( "bag", "bags"));
        assertFalse( IsAdjacent("money", "elephant"));
        assertFalse( IsAdjacent("money", ""));
        assertFalse( IsAdjacent("zoo", "zoo"));
    }

    public void testIsExist(){
        Set<String> test = new HashSet<String>();
        test.add("monkey");
        test.add("tiger");

        assertTrue( IsExist(test,"monkey"));
        assertFalse( IsExist(test,"dog"));
    }

    public void testIsValid(){
        assertTrue( IsValid("faggawe"));
        assertFalse( IsValid("fasdf1"));
        assertFalse( IsValid("awgee;"));
        assertFalse( IsValid("awgee`0;"));
        assertFalse( IsValid("awgee"+'\t'));
        assertFalse( IsValid(""));
    }
}
