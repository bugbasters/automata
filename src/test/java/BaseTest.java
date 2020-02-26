import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.testng.TextReport;
import driver.MainMethods;
import com.codeborne.selenide.Configuration;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.TestNG;
import org.testng.annotations.*;
import com.codeborne.selenide.testng.ScreenShooter;
import org.testng.reporters.*;
import utils.UserData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static com.codeborne.selenide.FileDownloadMode.PROXY;
import static com.codeborne.selenide.WebDriverRunner.url;
import static utils.TestDataReader.getProperty;


@Listeners({ ScreenShooter.class, TextReport.class, TestHTMLReporter.class, SuiteHTMLReporter.class, FailedReporter.class, XMLReporter.class, EmailableReporter.class, JUnitReportReporter.class})

public class BaseTest extends MainMethods {

    @Parameters({"baseUrl", "browser"})
    @BeforeClass
    public void setup(String baseUrl, String browser) throws IOException {

        /**
         * Delete old allure results
         */
        /*runTerminal("rm", "-R", "allure-results");
        runTerminal("mkdir", "reports");
        runTerminal("mkdir", "results");*/

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
        Configuration.browser = browser;

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

        /**
         * Set base URL
         */
        Configuration.baseUrl = getProperty(baseUrl);
    }

    @Parameters("testName")
    @AfterClass
    public void generateopen(String testName){
        /**
         * Generate and open allure report.
         * Save allure report in separate folder
         */
        UserData userData = new UserData();
        String results = testName + "_" + userData.todayDateAndTime();
        String resultsFolder = "./results/" + results;
        String reportsFolder = "./reports/" + results;
        runTerminal("cp", "-R", "./allure-results", resultsFolder);
        runTerminal("allure", "generate", resultsFolder);
        runTerminal("cp", "-R", "./allure-report", reportsFolder);
        runTerminal("rm", "-R", "allure-results");
        runTerminal("rm", "-R", "allure-report");
        //runTerminal("allure", "open", reportsFolder);
    }
}
