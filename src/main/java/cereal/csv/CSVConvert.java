package cereal.csv;

/**
 * @author /u/Philboyd_Studge on 4/16/2017.
 */
@FunctionalInterface
public interface CSVConvert<T> {
    T convert(String s);
}
