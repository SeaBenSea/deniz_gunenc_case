package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Base64;

public class CareersQAPage extends BasePage {
    private static final String ENCODED_URL = "aHR0cHM6Ly91c2VpbnNpZGVyLmNvbS9jYXJlZXJzL3F1YWxpdHktYXNzdXJhbmNlLw==";

    @FindBy(xpath = "//*[text() = 'See all QA jobs']")
    public WebElement buttonSeeAllQAJobs;

    public CareersQAPage(WebDriver driver) {
        super(driver);
    }

    public CareersQAPage open() {
        String decodedUrl = new String(Base64.getDecoder().decode(ENCODED_URL));
        driver.get(decodedUrl);

        return this;
    }

    public OpenPositionsQAPage clickSeeAllQAJobs() {
        click(buttonSeeAllQAJobs);
        return new OpenPositionsQAPage(driver);
    }
}