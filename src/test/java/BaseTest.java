import aquality.selenium.browser.AqualityServices;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.TestDataManager;

public abstract class BaseTest {

    @BeforeMethod
    public void setUp() {
        TestDataManager data = new TestDataManager();
        RestAssured.baseURI = (data.getTestData("/baseApiUrl"));
        AqualityServices.getBrowser().goTo(data.getTestData("/url"));
    }

    @AfterMethod
    public void tearDown() {
        AqualityServices.getBrowser().quit();
    }
}
