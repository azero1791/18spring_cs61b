import org.junit.Test;
import static org.junit.Assert.*;
public class TestOffByN {

    public static OffByN offByN5 = new OffByN(5);
    public static OffByN offByN8 = new OffByN(8);

    @Test
    public void TestEqualChars() {
        assertTrue(offByN5.equalChars('a', 'f'));
        assertFalse(offByN8.equalChars('a', 'f'));
    }

}
