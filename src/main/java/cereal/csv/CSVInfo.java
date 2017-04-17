package cereal.csv;

import cereal.annotation.CSVField;
import cereal.annotation.CSVHeader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author /u/Philboyd_Studge on 4/16/2017.
 */
public class CSVInfo {
    final int index;
    final CSVType type;
    final Class<?> innerClass;
    final Field field;
    final boolean quotes;
    final String format;
    final int listCount;

    private CSVInfo(CSVField annotation, Class<?> innerClass, Field field, boolean inferred) {
        this.index = annotation.index();
        if (inferred) {
            this.type = getInferredType(field);
        } else {
            this.type = annotation.type();
        }
        this.quotes = annotation.quotes();
        this.format = annotation.format();
        this.listCount = annotation.listCount();
        this.field = field;
        this.innerClass = innerClass;
    }

    static List<CSVInfo> getAnnotatedFieldInfo(Class<?> csvClass) {
        List<CSVInfo> fields = new ArrayList<>();
        boolean inferred = csvClass.getAnnotation(CSVHeader.class).inferred_types();
        Field[] fieldArray = csvClass.getDeclaredFields();
        for (Field each : fieldArray) {
            CSVField annotation = each.getAnnotation(CSVField.class);
            if (annotation != null) {
                each.setAccessible(true);
                if (annotation.type() == CSVType.OBJECT) {
                    try {
                        Class<?> custom = Class.forName(each.getType().getTypeName());
                        fields.add(new CSVInfo(annotation, custom, each, inferred));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    fields.add(new CSVInfo(annotation, null, each, inferred));
                }
            }
        }
        return fields;
    }

    private static CSVType getInferredType(Field field) {
        String type = field.getType().getTypeName();
        type = parseTypeString(type);
        return CSVType.inferType(type);
    }

    public static String parseTypeString(String s) {
        s = s.replaceAll("[<>]", "");
        int index = s.lastIndexOf('.');
        if (index > 0) {
            s = s.substring(index + 1);
        }
        return s;
    }
}
