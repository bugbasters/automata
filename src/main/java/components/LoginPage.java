package components;

import com.codeborne.selenide.*;
import driver.MainMethods;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static com.codeborne.selenide.Selenide.*;

public class LoginPage extends MainMethods {

    static String linetht = null;
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    /** Declare page elements */
    private SelenideElement usernameInput = $(By.name(""));
    private SelenideElement passwordInput = $(By.name(""));
    private SelenideElement logInButton = $x("");

    /**
     * Login as full method
     */
    public void signIn(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        logInButton.click();
    }

    /**
     * Login as separate step method
     */
    public void inputUsername(String username) {
        usernameInput.sendKeys(username);
    }

    public void inputPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickLogInButton() {
        logInButton.click();
    }

    private void close() throws IOException {
        httpClient.close();
    }

    private void sendPost() throws Exception {

        HttpPost post = new HttpPost("https://dev.tower.bet/api/login");

        //HttpPost post = new HttpPost("https://10.200.2.237/login");

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList();
        urlParameters.add(new BasicNameValuePair("username", "bugbasters31"));
        urlParameters.add(new BasicNameValuePair("password", "Azerty12"));
        //urlParameters.add(new BasicNameValuePair("data[User][username]", "ikovtunov@kapitus.com"));
        //urlParameters.add(new BasicNameValuePair("data[User][password]", "Welcome123"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);
            String line = EntityUtils.toString(response.getEntity());
            System.out.println(line);
            String[] split = line.split("\"apiToken\":\"");
            String linetwoo = split[1];
            String[] splittwoo = linetwoo.split("\",\"");
            linetht = splittwoo[0];
            System.out.println(linetht);
            System.out.println("hhhhh" + response.getStatusLine());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loginAPI() throws Exception {

        String coo = null;
        String cookie = null;
        try {
            System.out.println("Testing 2 - Send Http POST request");

            Connection.Response response;
            response = Jsoup.connect("https://10.200.2.237/login")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36")
                    //.referrer("http://www.google.com")
                    .ignoreHttpErrors(true)
                    .validateTLSCertificates(false)
                    .method(Connection.Method.GET)
                    .execute();
            System.out.println(response.statusCode());
            System.out.println(response.statusMessage());
            coo = response.cookies().get("sfs-core-services");
            System.out.println("JSESSIONID=" + response.cookies().get("sfs-core-services"));


            response = Jsoup.connect("https://10.200.2.237/login")
                    .ignoreHttpErrors(true)
                    .validateTLSCertificates(false)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36")
                    .referrer("http://www.google.com")
                    .data("data[User][username]", "ikovtunov@kapitus.com")
                    .data("data[User][password]", "Welcome123")
                    .timeout(30000)
                    .cookies(response.cookies())
                    .method(Connection.Method.POST)
                    .execute();

            System.out.println(response.statusCode());
            System.out.println(response.statusMessage());
            System.out.println("JSESSIONID=" + response.cookies().get("sfs-core-services"));


            Map<String, String> cookies = response.cookies();

            System.out.println(cookies.size());

            for (String key: cookies.values()){
                System.out.println("j    " + key + "    l    ");
            }

            response = Jsoup.connect("https://10.200.2.237/")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36")
                    //.referrer("http://www.google.com")
                    .ignoreHttpErrors(true)
                    .validateTLSCertificates(false)
                    .method(Connection.Method.GET)
                    .execute();
            System.out.println(response.statusCode());
            System.out.println(response.statusMessage());
            coo = response.cookies().get("sfs-core-services");
            System.out.println("JSESSIONID=" + response.cookies().get("sfs-core-services"));


//And this is the easieste way I've found to remain in session
            Document doc = Jsoup.connect("https://10.200.2.237/merchants")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36")
                    .ignoreHttpErrors(true)
                    .validateTLSCertificates(false)
                    //.cookies(cookies.get(""))
                    .get();
            System.out.println(doc);
            cookie = response.cookies().get("sfs-core-services");
            System.out.println(cookie);



        } catch (Exception e){
            e.printStackTrace();
        }

        open("https://10.200.2.237/");
        System.out.println("authhh    " + cookie);
        Cookie AUTH_COOKIE = new Cookie("sfs-core-services", cookie);
        WebDriverRunner.getWebDriver().manage().addCookie(AUTH_COOKIE);
        System.out.println("authh sent");
        open("https://10.200.2.237/merchants");
        refresh();
        System.out.println("refreshed");
        Thread.sleep(60000);
    }

    public void loginApitwoo() throws Exception {

        LoginPage obj = new LoginPage();

        try {
            System.out.println("Testing 2 - Send Http POST request");
            obj.sendPost();
        } finally {
            obj.close();
        }

    open("https://dev.tower.bet");
        System.out.println(linetht);
        Cookie AUTH_COOKIE = new Cookie("__tt", linetht);
        WebDriverRunner.getWebDriver().manage().addCookie(AUTH_COOKIE);
        open("https://dev.tower.bet");
        refresh();
        Thread.sleep(60000);
    }


    public void loginAPItwoo() throws Exception {

        String coo = null;
        String cookie = null;
        try {
            System.out.println("Testing 2 - Send Http POST request");

            Connection.Response response;
            response = Jsoup.connect("https://dev.tower.bet")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36")
                    //.referrer("http://www.google.com")
                    .ignoreHttpErrors(true)
                    .validateTLSCertificates(false)
                    .method(Connection.Method.GET)
                    .execute();
            System.out.println(response.statusCode());
            System.out.println(response.statusMessage());
            coo = response.cookies().get("__tt");
            System.out.println("JSESSIONID=" + response.cookies().get("__tt"));


            response = Jsoup.connect("https://dev.tower.bet")
                    .ignoreHttpErrors(true)
                    .validateTLSCertificates(false)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36")
                    .referrer("http://www.google.com")
                    .data("username", "bugbasters31")
                    .data("password", "Azerty12")
                    .timeout(30000)
                    .cookies(response.cookies())
                    .method(Connection.Method.POST)
                    .execute();

            System.out.println(response.statusCode());
            System.out.println(response.statusMessage());
            System.out.println("JSESSIONID=" + response.cookies().get("__tt"));


            Map<String, String> cookies = response.cookies();

            System.out.println(cookies.size());

            for (String key: cookies.values()){
                System.out.println("j    " + key + "    l    ");
            }

            response = Jsoup.connect("https://dev.tower.bet")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36")
                    //.referrer("http://www.google.com")
                    .ignoreHttpErrors(true)
                    .validateTLSCertificates(false)
                    .method(Connection.Method.GET)
                    .execute();
            System.out.println(response.statusCode());
            System.out.println(response.statusMessage());
            coo = response.cookies().get("sfs-core-services");
            System.out.println("JSESSIONID=" + response.cookies().get("sfs-core-services"));


//And this is the easieste way I've found to remain in session
            Document doc = Jsoup.connect("https://dev.tower.bet")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36")
                    .ignoreHttpErrors(true)
                    .validateTLSCertificates(false)
                    //.cookies(cookies.get(""))
                    .get();
            System.out.println(doc);
            cookie = response.cookies().get("__tt");
            System.out.println(cookie);



        } catch (Exception e){
            e.printStackTrace();
        }

        open("https://10.200.2.237/");
        System.out.println("authhh    " + cookie);
        Cookie AUTH_COOKIE = new Cookie("sfs-core-services", cookie);
        WebDriverRunner.getWebDriver().manage().addCookie(AUTH_COOKIE);
        System.out.println("authh sent");
        open("https://10.200.2.237/merchants");
        refresh();
        System.out.println("refreshed");
        Thread.sleep(60000);
    }
}


