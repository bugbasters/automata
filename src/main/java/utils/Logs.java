package utils;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Logs {

    /**
     * Initialize Log4j logs
     */
    final static Logger logger = Logger.getLogger(Logs.class);


    /**
     * This method is used to print log for some message
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * This method is used to print log for the start of the test case
     */

    public static void startTestCase(String testCaseName){
        logger.info(testCaseName + " started.");
    }

    /**
     * This method is used to print log for the ending of the test case
     */

    public static void endTestCase(String testCaseName){
        logger.info(testCaseName + " finished.");
    }


}
