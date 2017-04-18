package cereal.test;

import cereal.classes.SamplePerson;
import cereal.csv.CSVReader;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;

/**
 * @author /u/Philboyd_Studge on 4/16/2017.
 */
public class CSVTest {
    @Test
    public void readTest() throws Exception {
        CSVReader<SamplePerson> reader = new CSVReader<>(SamplePerson.class);
        List<SamplePerson> list = reader.read("test.csv");
        assertNotNull(list);
        assertTrue(list.size() == 4);
        System.out.println(list.get(0));
        list = reader.readFromURL("http://www.blargffjskdskjhfdkjsvbikujhksjd.com");
        assertNull(list);
    }

    @Test
    public void mapTest() throws Exception {
        CSVReader<SamplePerson> reader = new CSVReader<>(SamplePerson.class);
        Map<String, SamplePerson> map = reader.readToMap("test.csv", 1);
        assertNotNull(map);
        System.out.println(map.get("Joe"));
        Map<Integer, List<SamplePerson>> map2 = reader.readToMapOfLists("test.csv", 0);
        System.out.println(map2.get(3));
    }
}
