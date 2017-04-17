package cereal.test;

import cereal.csv.CSVInfo;
import cereal.csv.CSVType;
import cereal.parse.CSVParser;
import org.junit.Test;
import static junit.framework.TestCase.*;

/**
 * @author /u/Philboyd_Studge on 4/16/2017.
 */
public class ParserTest {
    @Test
    public void commaTest() throws Exception {
        String[] out = CSVParser.CSVSplit("bob, 123, \"joe,bill\", 34.55");
        assertEquals( "bob", out[0]);
        assertEquals("123", out[1]);
        assertEquals("joe,bill", out[2]);
        assertEquals("34.55", out[3]);
    }

    @Test
    public void delimiterTest() throws Exception {
        String[] out = CSVParser.CSVSplit("bob| 123| \"joe|bill\"| 34.55", '|');
        assertEquals("bob", out[0]);
        assertEquals("123", out[1]);
        assertEquals("joe|bill", out[2]);
        assertEquals("34.55", out[3]);
    }

    @Test
    public void tabTest() throws Exception {
        String[] out = CSVParser.CSVSplit("bob\t mary\t 123", '\t');
        assertEquals("mary", out[1]);
    }

    @Test
    public void parseTypeTest() throws Exception {
        String s = "this.is.a.test.<Integer>";
        assertEquals("Integer", CSVInfo.parseTypeString(s));
        assertEquals(CSVType.INTEGER, CSVType.inferType(CSVInfo.parseTypeString(s)));
    }
}
