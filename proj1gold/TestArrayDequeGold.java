import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testStudentArrayDeque(){
        StudentArrayDeque<Integer> sAD = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> aDS = new ArrayDequeSolution<>();

        int testTimes = StdRandom.uniform(1000, 5000);
        Integer testWay = 0;
        Integer testInteger = 0;
        Integer expected = 0;
        Integer actual = 0;
        String message = "";
        for (int i = 0; i < testTimes; i++) {

            if (aDS.isEmpty()) {
                testWay = StdRandom.uniform(0, 2);
                testInteger = StdRandom.uniform(-1000, 1000);
                switch (testWay) {
                    case 0 :
                        sAD.addFirst(testInteger);
                        aDS.addFirst(testInteger);
                        expected = aDS.get(0);
                        actual = sAD.get(0);
                        message += "addFirst(" + testInteger + ")\n";
                        break;
                    case 1 :
                        sAD.addLast(testInteger);
                        aDS.addLast(testInteger);
                        expected = aDS.get(aDS.size() - 1);
                        actual = sAD.get(aDS.size() - 1);
                        message += "addLast(" + testInteger + ")\n";
                        break;
                }
            }   else {
                testWay = StdRandom.uniform(0, 4);
                switch (testWay) {
                    case 0 :
                        testInteger = StdRandom.uniform(-1000, 1000);
                        sAD.addFirst(testInteger);
                        aDS.addFirst(testInteger);
                        message += "addFirst(" + testInteger + ")\n";
                        break;
                    case 1 :
                        sAD.addLast(testInteger);
                        aDS.addLast(testInteger);
                        message += "addLast(" + testInteger + ")\n";
                        break;
                    case 2 :
                        actual = sAD.removeFirst();
                        expected = aDS.removeFirst();
                        message += "removeFirst()\n";
                        break;
                    case 3 :
                        actual = sAD.removeLast();
                        expected = aDS.removeLast();
                        message += "removeLast()\n";
                        break;
                }
            }
            assertEquals(message, expected, actual);
        }
    }
}
