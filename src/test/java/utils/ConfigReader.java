package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    // static initializer

    static {
        try {
            // path to the file with properties (configurations of framework)
            String path = "src/test/resources/application.properties";

            // File IO
            FileInputStream inputStream = new FileInputStream(path);

            // laoding properties file into properties object
            properties = new Properties();
            properties.load(inputStream);

            // closing filestream
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key).trim();
    }

}
