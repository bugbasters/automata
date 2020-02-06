import components.LoginPage;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.BackendLogin;

public class LoginTest extends BaseTest {

    @Parameters
    @Test
    public void login() throws Exception {
        BackendLogin loginPage = new BackendLogin();
        //loginPage.loginAPI();
    }
}
