package utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static utils.TestDataReader.getProperty;

public class UserData {

    /**
     * Get today date in US format
     */

    public String todayDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String todayDate = dateFormat.format(date);
        return todayDate;
    }

    /**
     * Get today date and time
     */

    public String todayDateAndTime() {
        DateFormat dateFormat = new SimpleDateFormat("MMddyy-HHmm");
        Date date = new Date();
        String todayDateTime = dateFormat.format(date);
        return todayDateTime;
    }

    /**
     * Get current time
     */

    public String currentTime() {
        DateFormat dateFormat = new SimpleDateFormat("HHmmssSSS");
        Date date = new Date();
        String currentTime = dateFormat.format(date);
        return currentTime;
    }

    /**
     * Generate unique username based on date time of creation
     */

    public String uniqueUsername(String userName) {
        DateFormat dateFormat = new SimpleDateFormat("MMddHHmm");
        Date date = new Date();
        String uniqueUsername = userName + "_" + dateFormat.format(date);
        return uniqueUsername;
    }

    /**
     * Generate random email
     */

    public String randomEmailAddress(String domain) {
        String email = RandomStringUtils.random(20, true, true)+"@"+domain;
        return email;
    }

    /**
     * Generate random phone number
     */

    public String randomPhoneNumber(int countryCode) {
        String phone = "+"+countryCode+RandomStringUtils.random(9, false, true);
        return phone;
    }

    /**
     * Generate random number
     */

    public String randomNumber(int digits) {
        String num = RandomStringUtils.random(digits, false, true);
        return num;
    }

    /**
     * Generate random SSN
     */

    public String randomSSNNumber() {
        String ssn = RandomStringUtils.random(7, false, true);
        return ssn;
    }

    /**
     * Generate random name
     */
    public String randomname(int letters) {
        String name = RandomStringUtils.randomAlphabetic(letters);
        return name;
    }

}
