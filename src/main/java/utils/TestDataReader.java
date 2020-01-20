package utils;

import java.util.Properties;

public class TestDataReader {

    /**
     * This method allows to read data from config.properties data file from resourses
     * This file keeps the most common used data, such as URLs, credentials
     */

    private static Properties properties;

    static {
        properties = new Properties();

        try {
            properties.load(ClassLoader.getSystemResource("testData/config.properties").openStream());
        } catch (Exception e) {
            System.out.println("config.properties file ERROR");
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }



    /**
     * This method is used for various test usages, where data for separate test is kept.
     * The variables are: property file name, the key of needed element.
     */


    public static String getProperyData(String file, String key) {
        Properties prop = new Properties();
        try {
            prop.load(ClassLoader.getSystemResource(file).openStream());
        } catch (Exception e) {
            System.out.println("config.properties file ERROR");
            e.printStackTrace();
        }

        return prop.getProperty(key.replace("â\u0080\u0099", "’"));

    }
}
