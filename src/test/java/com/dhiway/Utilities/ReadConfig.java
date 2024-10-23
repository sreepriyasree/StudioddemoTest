package com.dhiway.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
    private Properties pro;

    public ReadConfig() {
        File file = new File("Config/config.properties");
        try {
            FileInputStream fis = new FileInputStream(file);
            pro = new Properties();
            pro.load(fis);
        } catch (Exception e) {
            System.out.println("Exception is: " + e.getMessage());
        }
    }

    // Method to get a property value by key
    public String getProperty(String key) {
        return pro.getProperty(key);
    }
}