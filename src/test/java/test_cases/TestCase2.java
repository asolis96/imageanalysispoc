package test_cases;

import Actions.GoogleActions;
import Actions.PinterestActions;
import io.github.bonigarcia.wdm.WebDriverManager;
import nu.pattern.OpenCV;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestCase2 {
    WebDriver driver;
    PinterestActions pinterestActions;

    @Test
    public void test2() {
        pinterestActions.imInTheHomePage();
        pinterestActions.clickOnLoginButton();
        pinterestActions.fillForm();
        pinterestActions.validateForm();
    }

    @BeforeMethod
    public void beforeMethod() {
        OpenCV.loadShared();
        System.out.println(System.getProperty("env").toUpperCase());
        System.out.println("Starting Test On Chrome Browser");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true); //value true only to run on server
        options.addArguments("--remote-allow-origins=*"); //Added to avoid 'org.openqa.selenium.remote.http.ConnectionFailedException: Unable to establish websocket connection to' error
        options.addArguments("window-size=1920,1200");
        System.setProperty("webdriver.chrome.whitelistedIps", "");
        driver = new ChromeDriver(options);
        pinterestActions = new PinterestActions(driver);
    }

    @AfterMethod
    public void afterMethod() {
        driver.close();
        System.out.println("Finished Test on Chrome Browser");
    }
}
