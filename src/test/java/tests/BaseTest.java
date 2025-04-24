package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {
    protected WebDriver driver;

    @Parameters("headless")
    @BeforeClass
    public void start(@Optional("false") String headless) {
        // TODO: Change hardcoded driver
        driver = new ChromeDriver(getChromeOptions(headless));
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void logFail(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // TODO: Add better logger
            System.out.println("Test failed: " + result.getName());
        }
    }

    private ChromeOptions getChromeOptions(String headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--disable-notifications",
                "--remote-allow-origins=*",
                "--disable-popup-blocking",
                "--no-sandbox",
                "--ignore-certificate-errors",
                "--ignore-certificate-errors-spki-list",
                "--suppress-message-center-popups"
        );
        options.setAcceptInsecureCerts(true);

        boolean isHeadless = Boolean.parseBoolean(headless);
        boolean isCI = Boolean.getBoolean("ci");

        if (isHeadless || isCI) {
            options.addArguments("--headless=new");
        }

        return options;
    }
}
