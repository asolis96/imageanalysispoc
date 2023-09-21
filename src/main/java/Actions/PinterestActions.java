package Actions;

import org.apache.commons.io.FileUtils;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FastFeatureDetector;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.time.Duration;

public class PinterestActions {

    private WebDriver driver;
    protected WebDriverWait wait;

    @FindBy(xpath = "//div[@data-test-id='simple-login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//form[@data-test-id='registerForm']")
    private WebElement registerForm;

    @FindBy(xpath = "//input[@id='email']")
    private WebElement emailInputField;

    public PinterestActions(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver,this);
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(20));
    }

    public void imInTheHomePage() {
        im_InTheHomePage();
    }

    public void clickOnLoginButton() {
        click_OnTheLoginButton();
    }

    public void fillForm() {
        fill_Form();
    }

    public void validateForm() {
        validate_Form();
        //try {
        //    validate_Image();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    private void im_InTheHomePage() {
        driver.get("https://www.pinterest.com/");
        System.out.println("\n\n\n\n\nGoing to Pinterest");
    }

    private void click_OnTheLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    private void fill_Form() {
        wait.until(ExpectedConditions.visibilityOf(registerForm));
        takeScreenshotAndSaveIt(registerForm,"image1");
        //emailInputField.sendKeys("TEST");
        takeScreenshotAndSaveIt(registerForm, "image2");
    }

    private void validate_Form() {
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

        long difference = 0;

        for (int y = 0; y < height1; y++) {

            for (int x = 0; x < width1; x++) {

                int rgbA = imagA.getRGB(x,y);
                int rgbB = imagB.getRGB(x,y);
                int redA = (rgbA >> 16) & 0xff;
                int greenA = (rgbA >> 8) & 0xff;
                int blueA = (rgbA) & 0xff;
                int redB = (rgbB >> 16) & 0xff;
                int greenB = (rgbB >> 8) & 0xff;
                int blueB = (rgbB) &0xff;

                difference += Math.abs(redA - redB);
                difference += Math.abs(greenA - greenB);
                difference += Math.abs(blueA - blueB);
            }
        }
        double total_pixels = width1 * width2 * 3;

        double avg_different_pixels = difference / total_pixels;

        double percentage = (avg_different_pixels / 255) * 100;

        System.out.println("Difference Percentage -->" + percentage);
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

    private void validate_Image() throws IOException {
        int ret;
        ret = sendImageCompare("","");
        System.out.println(ret);
        if (ret > 0) {
            System.out.println("Two Images are the same.");
        } else {
            System.out.println("Two Images are different.");
        }
    }

    private int sendImageCompare(String fileSource, String fileDestination) throws IOException {
        /**
        URL url = new URL(fileSource);
        InputStream in = new BufferedInputStream(url.openStream());
        OutputStream out = new BufferedOutputStream(new FileOutputStream("./Images/screenshots/image3.jpg"));

        for (int i; (i = in.read()) != -1;) {
            out.write(i);
        }
        in.close();
        out.close();
         */

        int retVal = 0;
        long startTime = System.currentTimeMillis();

        String fileName1 = "./Images/screenshots/image1.png";
        String fileName2 = "./Images/screenshots/image2.png";

        Mat img1 = Imgcodecs.imread(fileName1, Imgcodecs.IMREAD_COLOR);
        Mat img2 = Imgcodecs.imread(fileName2, Imgcodecs.IMREAD_COLOR);

        MatOfKeyPoint keyPoints1 = new MatOfKeyPoint();
        MatOfKeyPoint keyPoints2 = new MatOfKeyPoint();
        Mat descriptors1 = new Mat();
        Mat descriptors2 = new Mat();

        ORB detector = ORB.create();


        detector.detect(img1,keyPoints1, descriptors1);
        detector.detect(img2, keyPoints2, descriptors2);
        detector.detectAndCompute(img1, new Mat(), keyPoints1, descriptors1);
        detector.detectAndCompute(img2, new Mat(), keyPoints2, descriptors2);

        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

        MatOfDMatch matches = new MatOfDMatch();

        if (descriptors2.cols() == descriptors1.cols()) {
            matcher.match(descriptors1,descriptors2,matches);

            DMatch[] match = matches.toArray();
            double max_dist = 0;
            double min_dist = 100;

            for(int i=0;i<descriptors1.rows();i++) {
                double dist = match[i].distance;
                if( dist < min_dist) min_dist = dist;
                if(dist > max_dist) max_dist = dist;
            }
            System.out.println("max_dist=" + max_dist + ", min_dist=" + min_dist);

            for (int i=0; i < descriptors1.rows();i++) {
                if(match[i].distance <= 10) {
                    retVal++;
                }
            }
            System.out.println("matching count=" + retVal);
        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("estimatedTime=" + estimatedTime + "ms");

        return retVal;

    }
}
