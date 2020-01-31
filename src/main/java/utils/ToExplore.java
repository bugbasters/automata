package utils;

import com.codeborne.selenide.WebDriverRunner;
import driver.MainMethods;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.Cookie;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;

public class ToExplore extends MainMethods {

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
}
