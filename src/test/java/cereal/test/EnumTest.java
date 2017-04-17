package cereal.test;

import cereal.csv.CSVType;
import org.junit.Test;

import static junit.framework.TestCase.*;

/**
 * @author /u/Philboyd_Studge on 4/16/2017.
 */
public class EnumTest {
    @Test
    public void intTest() throws Exception {
        String s = "12345";
        Integer n = 12345;
        int n2 = n;
        int n3 = CSVType.INTEGER.convert(s);
        assertEquals("Not converted properly.", n, CSVType.INTEGER.convert(s));
        assertEquals("Not converted properly.", n2, (int) CSVType.INTEGER.convert(s));
        assertEquals(n2, n3);

    }

    @Test
    public void longTest() throws Exception {
        String s = "9876543210";
        long l = 9876543210L;
        assertEquals("not converted properly.", l, (long) CSVType.LONG.convert(s));
    }

    @Test
    public void inferTest() throws Exception {
        assertEquals(CSVType.inferType("int"), CSVType.INTEGER);
        assertEquals("Invalid CSV type.", CSVType.inferType("Poop"), CSVType.OBJECT);
        assertEquals("Invalid CSV type", CSVType.inferType("double"), CSVType.DOUBLE);
    }
}
