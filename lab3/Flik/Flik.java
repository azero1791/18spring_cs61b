/** An Integer tester created by Flik Enterprises. */
    import static org.junit.Assert.*;

public class Flik {

    public static boolean isSameNumber(Integer a, Integer b) {
        /** a == 721 and b == 722 which will result in a bug when a == 128 and b == 128 */
        assertTrue(a == b);
        return a == b;
    }
}
