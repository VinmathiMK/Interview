package com.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private Properties properties;

    public ConfigReader() {
        properties = new Properties();
        try (InputStream input = new FileInputStream("src/test/resources/config.properties")) {
            // Load properties file
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to load configuration properties");
           
        }
    }

    /**
     * Retrieves the property value for the given key.
     *
     * @param key the key for the property
     * @return the property value
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property key '" + key + "' not found in the configuration file");
        }
        return value;
    }
}