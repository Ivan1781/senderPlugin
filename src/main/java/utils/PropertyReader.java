package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static final String PROP_FILES = "app.properties";
    public static String getProperty(String prop) {
        Properties properties = new Properties();
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream(PROP_FILES)) {
            if (input == null) {
                return null;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties.getProperty(prop);
    }
}
