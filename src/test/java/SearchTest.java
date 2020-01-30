import com.codeborne.selenide.testng.TextReport;
import com.codeborne.selenide.testng.annotations.Report;
import io.qameta.allure.Description;
import org.apache.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import components.SearchPage;
import utils.Logs;
import utils.TestDataReader;

import static com.codeborne.selenide.Selenide.screenshot;

@Listeners(TextReport.class)
@Report

public class SearchTest extends BaseTest {

    String testCaseName = "Search in google";

    private SearchPage searchPage;

    @Parameters("searchQuery")
    @Test
    @Description("Search Test in Google")
    public void searchTest(String searchQuery) {
        Logs.startTestCase(testCaseName);
        TextReport.onSucceededTest = true;
        TextReport.onFailedTest = true;
        Logs.startTestCase(testCaseName);
        searchPage = new SearchPage();
        searchPage.searchPageOpen();
        Logs.info("Search page opened");
        screenshot("test");
        searchPage.search(TestDataReader.getProperyData("testData/search.properties", searchQuery));
        Logs.endTestCase(testCaseName);
    }
}
