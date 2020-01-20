package components;

import driver.MainMethods;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class SearchPage extends MainMethods {

    /** Declare page elements */

    private SelenideElement searchInput = $(By.name("q"));


    /**
     * Open Search Page
     */

    public void searchPageOpen() {
        Selenide.open("/");
    }


    /**
     * Example of using full step method scenario
     */

    public void search(String searchQuerry) {
        searchInput.sendKeys(searchQuerry);
        searchInput.pressEnter();
    }


    /**
     * Example of using step by step method scenario, interacting with each element separately
     */

    public void inputSearch(String searchQuerry){
        searchInput.sendKeys(searchQuerry);
    }

    public void pressEnterSearch() {
        searchInput.pressEnter();
    }

}
