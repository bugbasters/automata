package utils;

import com.codeborne.selenide.WebDriverRunner;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;

public class BackendLogin {
    private static String loginToken = null;

    /**
     * This method sends POST request to get login token from json response on login
     */

    public void sendPost(String baseUrlApi, String tokenName, String username, String password) throws Exception {

        HttpPost post = new HttpPost(TestDataReader.getProperty(baseUrlApi));

        List<NameValuePair> urlParameters = new ArrayList();
        urlParameters.add(new BasicNameValuePair("username", username));
        urlParameters.add(new BasicNameValuePair("password", password));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);
            String line = EntityUtils.toString(response.getEntity());
            System.out.println("LINE: " + line);
            System.out.println(response.getStatusLine());

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(line);

            loginToken = (String) jsonObject.get(tokenName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendGet(String baseUrl, String baseUrlApi, String tokenName, String username, String password) throws Exception {
        BackendLogin obj = new BackendLogin();

        obj.sendPost(baseUrlApi, TestDataReader.getProperty(tokenName), TestDataReader.getProperty(username), TestDataReader.getProperty(password));
        System.out.println(loginToken);
        Response res = RestAssured.given().cookie("jwt_token", loginToken).when().get("http://moderator-stage.alluredate.com/");
        System.out.println(res.getStatusCode() + "   " + res.getStatusLine());
        System.out.println(res.getBody().asString());

    }

    /**
     * This method opens web app with login token that was got from json response on login.
     * So no UI login needed
     */

    public void loginAPI(String baseUrl, String baseUrlApi, String tokenName, String username, String password, String authCookie) throws Exception {

        BackendLogin obj = new BackendLogin();

        obj.sendPost(baseUrlApi, TestDataReader.getProperty(tokenName), TestDataReader.getProperty(username), TestDataReader.getProperty(password));

        open("/favicon.ico");
        System.out.println(loginToken);
        Cookie AUTH_COOKIE = new Cookie(TestDataReader.getProperty(authCookie), loginToken);
        WebDriverRunner.getWebDriver().manage().addCookie(AUTH_COOKIE);
        open(TestDataReader.getProperty(baseUrl));
    }
}
