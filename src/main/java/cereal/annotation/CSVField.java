package cereal.annotation;

import cereal.csv.CSVType;

import java.lang.annotation.*;

/**
 * This annotation is to mark and inform the fields of the class you want to
 * deserialize from the CSV file.
 * <b>index</b> refers to the column index (zero-based) that you want applied to the field.
 * This property is mandatory. Indexes out of bounds will not cause fatal error
 * but will cause that particular field to be ignored.
 *
 * <b>type</b> is used to denote the data type related to the actual java class.
 * Defaults to String if not specified. This step can be inferred by the actual
 * Java type by specifying the <i>inferred_type</i> property in the @CSVHeader annotation to true.
 * The type must match, although any CSV data can be interpreted as a String.
 *
 * <b>quotes</b> is used only for the Writer to encase this value inside double quotes
 * in the final CSV file. This is necessary if the String will contain commas, quotes or other
 * escape characters. Defaults to false.
 *
 * <b>format</b> can be used by type such as Date to specify the formatting (uses SimpleDateFormatter)
 * and can be used to format the number types, however this can cause the CSV data to be
 * un-parseable back to the original data type in some cases (adding commas or dollar signs, for example
 *
 *
 * Any fields not marked with this annotation will be ignored.
 * @author /u/Philboyd_Studge on 4/1/2017.
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVField {
    int index();
    CSVType type() default CSVType.STRING;
    boolean quotes() default false;
    String format() default "";
    int listCount() default 1;
}
