package test_cases;

import Actions.GoogleActions;
import io.github.bonigarcia.wdm.WebDriverManager;
import nu.pattern.OpenCV;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestCase1 {

    WebDriver driver;
    GoogleActions googleActions;

    @Test
    public void test1() {
        googleActions.ImInTheHomePage();
        googleActions.SearchFor("WWE");
        //googleActions.ValidateResults();
    }

    @BeforeMethod
    public void beforeMethod() {
        OpenCV.loadShared();
        System.out.println("Starting Test On Chrome Browser");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("window-size=1920,1200");
        System.setProperty("webdriver.chrome.whitelistedIps", "");
        driver = new ChromeDriver(options);
        googleActions = new GoogleActions(driver);
    }

    @AfterMethod
    public void afterMethod() {
        driver.close();
        System.out.println("Finished Test on Chrome Browser");
    }
}
