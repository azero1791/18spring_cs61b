import org.junit.Test;
import static org.junit.Assert.*;
public class TestOffByN {

    static OffByN offByN5 = new OffByN(5);
    static OffByN offByN8 = new OffByN(8);

    @Test
    public void testEqualChars() {
        assertTrue(offByN5.equalChars('a', 'f'));
        assertFalse(offByN8.equalChars('a', 'f'));
    }

}
