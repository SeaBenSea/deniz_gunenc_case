package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void start() {
        // TODO: Change hardcoded driver
        driver = new ChromeDriver(getChromeOptions());
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

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications",
                "--remote-allow-origins=*",
                "--disable-popup-blocking",
                "--no-sandbox",
                "--ignore-certificate-errors",
                "--ignore-certificate-errors-spki-list",
                "--suppress-message-center-popups");
        options.setAcceptInsecureCerts(true);
        // TODO: Add condition or property to enable a headless option
        return options;
    }
}
