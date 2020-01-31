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

}


