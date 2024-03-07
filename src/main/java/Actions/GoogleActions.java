package Actions;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
        System.out.println(System.getProperty("env").toUpperCase());
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        System.out.println(System.getProperty("env").toUpperCase());
        options.addArguments("--headless=new");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("window-size=1920,1200");
        System.setProperty("webdriver.chrome.whitelistedIps", "");

        driver = new ChromeDriver(options);

        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public GoogleActions(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver,this);
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(20));
    }

    public void ImInTheHomePage() {
        Im_InTheHomePage();
    }

    public void SearchFor(String wwe) {
        Search_For(wwe);
    }

    public void ValidateResults() {
        //Validate_Results();
    }

    private void Im_InTheHomePage() {
        driver.get("https://www.google.com/");
        System.out.println("\n\n\n\n\nGoing to google");
    }

    private void Search_For(String wwe) {
        takeScreenshotAndSaveIt(searchBar, "image1");
        searchBar.sendKeys(wwe);
        takeScreenshotAndSaveIt(searchBar, "image2");
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        System.out.println("Searching for " + wwe);
    }

    private void Validate_Results() {
        //System.out.println(expectedResult.size());
        BufferedImage imagA = null;
        BufferedImage imagB = null;


        try {
            File fileA = getSavedImage("image1");
            File fileB = getSavedImage("image2");
            imagA = ImageIO.read(fileA);
            imagB = ImageIO.read(fileB);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int width1 = imagA.getWidth();
        int width2 = imagB.getWidth();
        int height1 = imagA.getHeight();
        int height2 = imagB.getHeight();

        System.out.println("Image 1: " + width1 + "x" + height1);
        System.out.println("Image 2: " + width2 + "x" + height2);


        wait.until(ExpectedConditions.visibilityOfAllElements(expectedResult));
        if(expectedResult.size() >0){
            System.out.println("it works");
        } else{
            System.out.println("Huston we have a problem");
        }
        System.out.println(expectedResult.size());
        //driver.close();
    }

    private void takeScreenshotAndSaveIt(WebElement element, String name) {
        File srcFile = element.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File("./Images/screenshots/" + name + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private File getSavedImage(String name) {
        return new File("./Images/screenshots/"+name+".png");
    }
}
