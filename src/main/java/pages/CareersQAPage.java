package pages;

import config.Urls;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CareersQAPage extends BasePage {
    @FindBy(xpath = "//*[text() = 'See all QA jobs']")
    public WebElement buttonSeeAllQAJobs;

    public CareersQAPage(WebDriver driver) {
        super(driver);
    }

    public CareersQAPage open() {
        driver.get(Urls.CAREERS_QA);

        return this;
    }

    public OpenPositionsQAPage clickSeeAllQAJobs() {
        click(buttonSeeAllQAJobs);
        return new OpenPositionsQAPage(driver);
    }
}