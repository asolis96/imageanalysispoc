package Actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GoogleActions {
    private WebDriver driver;
    protected WebDriverWait wait;

    @FindBy(name = "q")
    private WebElement searchBar;

    @FindBy(name = "btnK")
    private WebElement searchButton;

    @FindBy(xpath = "//cite")
    private List<WebElement> expectedResult;

    public GoogleActions() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("window-size=1920,1200");
        System.setProperty("webdriver.chrome.whitelistedIps", "");

        driver = new ChromeDriver(options);

        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void ImInTheHomePage() {
        Im_InTheHomePage();
    }

    public void SearchFor(String wwe) {
        Search_For(wwe);
    }

    public void ValidateResults() {
        Validate_Results();
    }

    private void Im_InTheHomePage() {
        driver.get("https://www.google.com/");
        System.out.println("\n\n\n\n\nGoing to google");
    }

    private void Search_For(String wwe) {
        searchBar.sendKeys(wwe);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        System.out.println("Searching for WWE");
    }

    private void Validate_Results() {
        if(expectedResult.size() >0){
            System.out.println("it works");
        } else{
            System.out.println("Huston we have a problem");
        }
        driver.close();
    }
}
