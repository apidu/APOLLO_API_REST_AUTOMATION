package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class is responsible for reading and managing configuration properties from the file `config.properties`.
 * It provides a utility method to fetch property values by key.
 */
public class ConfigManager {

    // A static Properties object to hold the configuration properties.
    private static Properties properties = new Properties();

    // Static block executes once when the class is loaded to initialize properties.
    static {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {

            // Check if the properties file is found in the classpath.
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            } else {
                // Load the properties from the file.
                properties.load(input);
            }
        } catch (IOException ex) {
            // Print stack trace if there's an error reading the file.
            ex.printStackTrace();
        }
    }

    /**
     * This method retrieves the value associated with a given key from the properties file.
     *
     * @param key The property key whose value needs to be fetched.
     * @return The value associated with the key, or null if the key is not found.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}





