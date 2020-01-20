import components.LoginPage;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void login() throws Exception {
        LoginPage loginPage = new LoginPage();
        loginPage.loginAPI();
    }

    @Test
    public void logintwo() throws Exception {
        LoginPage loginPage = new LoginPage();
        loginPage.loginApitwoo();
    }

    @Test
    public void loginthree() throws Exception {
        LoginPage loginPage = new LoginPage();
        loginPage.loginAPItwoo();
    }
}
