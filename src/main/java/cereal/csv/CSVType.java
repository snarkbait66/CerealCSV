package cereal.csv;

/**
 * @author /u/Philboyd_Studge on 4/16/2017.
 */
public enum CSVType {
    INTEGER(Integer::parseInt),
    LONG(Long::parseLong),
    BOOLEAN(Boolean::parseBoolean),
    BYTE(Byte::parseByte),
    SHORT(Short::parseShort),
    FLOAT(Float::parseFloat),
    DOUBLE(Double::parseDouble),
    STRING(null),
    DATE(null),
    OBJECT(null),
    LIST(null),
    ARRAY(null),
    MAP(null);

    CSVConvert convertFunction;

    CSVType(CSVConvert convertFunction) {
        this.convertFunction = convertFunction;
    }

    @SuppressWarnings("unchecked")
    public <T> T convert(String s) {
        return (T) this.convertFunction.convert(s);
    }

    public static CSVType inferType(String s) {
        if ("int".equals(s)) {
            return CSVType.INTEGER;
        }
        try {
            return CSVType.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CSVType.OBJECT;
        }
    }

}
