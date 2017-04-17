package cereal.csv;

import java.util.List;

/**
 * @author /u/Philboyd_Studge on 4/16/2017.
 */
class CSVClass {
    final Class<?> pClass;
    final List<CSVInfo> fields;

    CSVClass(Class<?> pClass) {
        this.pClass = pClass;
        this.fields = CSVInfo.getAnnotatedFieldInfo(pClass);
    }
}
