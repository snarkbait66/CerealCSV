package cereal.test;

import cereal.classes.SamplePerson;
import cereal.csv.CSVReader;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * @author /u/Philboyd_Studge on 4/16/2017.
 */
public class CSVTest {
    @Test
    public void readTest() throws Exception {
        CSVReader<SamplePerson> reader = new CSVReader<>(SamplePerson.class);
        List<SamplePerson> list = reader.read("test.csv");
        assertNotNull(list);
        assertTrue(list.size() == 3);
        System.out.println(list.get(0));
    }
}
