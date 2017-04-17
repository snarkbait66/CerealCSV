package cereal.classes;

import cereal.annotation.CSVField;
import cereal.annotation.CSVHeader;
import cereal.csv.CSVType;

/**
 * @author /u/Philboyd_Studge on 4/16/2017.
 */
@CSVHeader(has_header = true)
public class SamplePerson {

    @CSVField(index = 0, type = CSVType.INTEGER)
    private int id;

    @CSVField(index = 1)
    private String firstName;

    @CSVField(index = 2)
    private String lastName;

    @CSVField(index = 3, type = CSVType.INTEGER)
    private int age;

    @CSVField(index = 4, type = CSVType.DOUBLE)
    private double wage;

    public SamplePerson() {}

    @Override
    public String toString() {
        return "SamplePerson{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", wage=" + wage +
                '}';
    }
}
