package selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBase {

    static final String CONFERENCE_ORGANIZER_EMAIL = "organizer@email.com";
    static final String CONFERENCE_ORGANIZER_PASSWORD = "organizer";

    static final String REVIEWER_EMAIL = "reviewer5@email.com";
    static final String REVIEWER_PASSWORD = "reviewer5";

    static final String SCIENTIST_EMAIL = "scientist5@email.com";
    static final String SCIENTIST_PASSWORD = "scientist5";

    static final String PATH_TO_CODE = "C:/Users/Ola/Desktop";

    static WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://conferencemanagementsystem.azurewebsites.net/root/");
    }

    @After
    public void quit() {
        driver.quit();
    }
}
