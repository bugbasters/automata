import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.testng.ScreenShooter;
import com.codeborne.selenide.testng.TextReport;
import com.codeborne.selenide.testng.annotations.Report;
import components.SearchPage;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.Logs;
import utils.TestDataReader;

import static com.codeborne.selenide.FileDownloadMode.PROXY;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;
import static utils.TestDataReader.getProperty;

@Listeners(TextReport.class)
@Report

public class ClassTest {

    @BeforeClass
    public void setup() {

        /**
         * Start with maximized browser window adjusted to users screen resolution
         */
        Configuration.startMaximized = true;

        /**
         * Set basic timeout to wait for element
         */
        Configuration.timeout = 15000;

        /**
         * Set browser to run
         */
        Configuration.browser = "chrome";

        /**
         * Enable ability to download files without direct URL
         */
        Configuration.fileDownload = PROXY;
        Configuration.proxyEnabled = true;


        /**
         * Capabilities to ignore Your connection is not private NET::ERR_CERT_AUTHORITY_INVALID
         */
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setAcceptInsecureCerts(true);
        Configuration.browserCapabilities = capabilities;
        /**
         * Enable screenshots of successful tests (call on step where it's needed
         */
        ScreenShooter.captureSuccessfulTests = true;

        TextReport.onSucceededTest = true;
        TextReport.onFailedTest = true;

        /**
         * Set base URL
         */
        //Configuration.baseUrl = getProperty("google");


    }

    @Test
    public void searchTest() {
        Selenide.open("https://www.google.com/");
        $(By.name("q")).val("selenide").pressEnter();
    }
}
