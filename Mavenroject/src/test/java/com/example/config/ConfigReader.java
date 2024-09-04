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
        	if (input == null) {
            throw new RuntimeException("Sorry, unable to find config.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to load configuration properties");
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property key '" + key + "' not found in the configuration file");
            
        }
        return value;
    }
}