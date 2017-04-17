package cereal.csv;

import cereal.annotation.CSVHeader;
import cereal.parse.CSVParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author /u/Philboyd_Studge on 4/16/2017.
 */
public class CSVReader<T> {
    private final CSVClass csvClass;
    private final Logger logger = Logger.getLogger(CSVReader.class.getName());

    /**
     * Constructor
     * Classname must be sent as a parameter in the form <tt>ClassName.class</tt>
     * @param parentClass name of class containing CSVField/CSVHeader annotations
     */
    public CSVReader(Class<?> parentClass) {
        //this.csvClass = csvClass;
        csvClass = new CSVClass(parentClass);
        logger.log(Level.INFO, "Reader created successfully...");
    }

    @SuppressWarnings("unchecked")
    private <U> U getObjectFromCSV(String line, CSVClass csvClass, int baseIndex) {
        try {
            String[] split = CSVParser.CSVSplit(line);
            Object instance;
            try {
                instance = csvClass.pClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            }
            for (CSVInfo each : csvClass.fields) {
                int index = each.index + baseIndex;
                if (index < 0 || index >= split.length) {
                    logger.log(Level.WARNING, "Incorrect CSV entry for line:");
                    logger.log(Level.WARNING, line);
                    logger.log(Level.WARNING, "Ignoring line");
                    return null;
                }
                String temp = split[index];
                if (!temp.isEmpty()) {
                    try {
                        switch (each.type) {
                            case INTEGER:
                            case LONG:
                            case BYTE:
                            case SHORT:
                            case FLOAT:
                            case DOUBLE:
                            case BOOLEAN:
                                each.field.set(instance, each.type.convert(temp));
                                break;
                            case STRING:
                                each.field.set(instance, temp);
                                break;
                            case DATE:
                                SimpleDateFormat format = new SimpleDateFormat(each.format);
                                Date date = format.parse(temp);
                                each.field.set(instance, date);
                                break;

                            case OBJECT:
                                CSVClass innerClass = new CSVClass(each.innerClass);
                                each.field.set(instance, getObjectFromCSV(line, innerClass, index));
                                break;
                            case LIST:
                                List list = (List) each.field.get(instance);
                                String genericTypeName = CSVInfo.parseTypeString(each.field.getGenericType().getTypeName());
                                CSVType genericType = CSVType.inferType(genericTypeName);
                                for (int i = 0; i < each.listCount; i++) {
                                    if (genericType != CSVType.OBJECT) {
                                        list.add(genericType.convert(split[index + i]));
                                    } else {
                                        list.add(split[index + i]);
                                    }

                                }
                                each.field.set(instance, list);
                                break;
                            case ARRAY:
                            case MAP:
                                // todo: implement
                                break;
                        }
                    }
                    catch (NumberFormatException nfe) {
                        logger.log(Level.WARNING,"Incorrect CSV entry for line: Number Format exception");
                        logger.log(Level.WARNING, line);
                        logger.log(Level.WARNING, "Ignoring line");
                        return null;
                    }
                    catch (ParseException pe) {
                        logger.log(Level.WARNING, "Incorrect CSV entry for line: Problem parsing Date");
                        logger.log(Level.WARNING, line);
                        logger.log(Level.WARNING, "Ignoring line");
                        return null;
                    }
                }
            }
            return (U) instance;
        } catch ( IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<T> read(String filename) {
        List<T> results = null;
        try (BufferedReader br = Files.newBufferedReader(new File(filename).toPath())) {
            results = readListFromCSV(br);
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, "Unable to open file.");
            logger.log(Level.SEVERE, ioe.getMessage());
        }
        return results;
    }

    private List<T> readListFromCSV(BufferedReader br) throws IOException{
        int itemCount = 0;
        int ignoredItems = 0;
        List<T> results = new ArrayList<>();

        String input;
        if (csvClass.pClass.isAnnotationPresent(CSVHeader.class)) {
            CSVHeader header = csvClass.pClass.getAnnotation(CSVHeader.class);
            if (header.has_header()) {
                // ignore header line
                br.readLine();
            }
        }
        while ((input = br.readLine()) != null) {
            T t = getObjectFromCSV(input, csvClass, 0);
            if (t != null) {
                results.add(t);
                itemCount++;
            } else {
                ignoredItems++;
            }
        }
        logger.log(Level.INFO, String.format("List created. %s items added. %s items ignored.", itemCount, ignoredItems));
        return results;
    }
}
