package driver;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.sun.net.ssl.*;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.TestDataReader;
import utils.UserData;

import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.matchesText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.url;
import io.restassured.RestAssured;

public class MainMethods {

    /**
     * Useful methods for test scenarios
     */

    /**
     * Get status 200
     */
    public void getResponseCode(String urlString) throws MalformedURLException, IOException {

        URL url = new URL(urlString);
        HttpURLConnection huc = (HttpURLConnection)url.openConnection();
        huc.setRequestMethod("GET");
        huc.connect();
        System.out.println(huc.getResponseMessage());
        System.out.println(huc.getResponseCode());
    }

    public void checkPageLoaded() {
        String href = url();
        System.out.println(href);
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("bugbasters31");
        authScheme.setPassword("Azerty129");
        RestAssured.authentication = authScheme;
        Response res = RestAssured.get("https://tower.bet/finances");
        try {
            Assert.assertEquals(200,res.getStatusCode());
            System.out.println("Status code received is :: " + res.getStatusCode());
        } catch (AssertionError e){
            e.printStackTrace();
            System.out.println("Status code received is :: " + res.getStatusCode());
            System.out.println(res.getStatusLine());
            throw new AssertionError("Page not loaded successfully");
        }
    }

    public void checkPageLoadedAuth() {

    }

    /**
     * Verify page is loaded
     */
    public void verifyPageLoaded() {

    }


    /**
     * Get and verify page title
     */
    public void checkPageTitle(String titleText) {
        $("title").shouldHave(attribute("text", titleText));
    }

    /**
     * Get data from url
     **/
    public String splitText(String splitWord, int id) {
        String[] split = url().split(splitWord);
        return split[id];
    }


    /**
     * Useful methods with element interaction
     */

    /**
     * Collect drop-down list of elements, verify list size and verify actual list elements data with expected list data
     */
    public void getDropdownList(SelenideElement dropdown, ElementsCollection dropdownList, int size, String fileName, String key) {
        //open drop-down
        dropdown.click();

        //verify drop-down list size
        int listSize = dropdownList.texts().size();
        dropdownList.shouldHaveSize(size);

        //get each list element and verify it from expected list element
        for (int i = 0; i < listSize; i++) {
            dropdownList.get(i).shouldHave(matchesText(TestDataReader.getProperyData(fileName, key + i)));
        }
    }

    /**
     * Collect drop-down list of elements
     **/
    public void getListItemsText(ElementsCollection itemsList) {
        int listSize = itemsList.texts().size();

        for (int i = 0; i < listSize; i++) {
            System.out.println(itemsList.get(i).getText());
        }
    }

    /**
     * Collect last element from list of elements
     **/
    public String getLastListElementText(ElementsCollection itemsList) {
        int listSize = itemsList.texts().size();
        return itemsList.get(listSize - 1).getText();
    }

    /**
     * Run command from terminal
     **/
    public void runTerminal(String... args) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(args);

        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();

            System.out.println("Success!");
            System.out.println(output);
            // System.exit(0);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
