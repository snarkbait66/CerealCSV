package cereal.annotation;

import java.lang.annotation.*;

/**
 * This annotation is used at the class level. All of the parameters are optional.
 *
 * <b>has_header</b> Set to true if the CSV file has a header info line, or if you want to
 * write data to a new CSV file using a header line. Defaults to false.
 *
 * <b>inferred_types</b> set to true if you want to get the file types of the
 * annotated fields automatically and not specify them in the @CSVField annotation.
 *
 * <b>header</b> A String representing the header info to <i>write</i> to
 * a new CSV file. This can be specified here by using a string with the column
 * header names, separated by commas, in the same order as the fields. If this is
 * left blank and <i>has_header</i> is set to true, the header will be generated
 * from the field names in the class.
 *
 * @author /u/Philboyd_Studge on 4/2/2017.
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVHeader {
    boolean has_header() default false;
    boolean inferred_types() default false;
    String header() default "";
}
