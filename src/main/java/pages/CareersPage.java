package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CareersPage extends BasePage {
    final By jobItemLocator = By.xpath("//div[contains(@class, 'job-item')]");
    final int initialJobCount = driver.findElements(jobItemLocator).size();

    @FindBy(id = "career-find-our-calling")
    private WebElement teamsSection;

    @FindBy(id = "career-our-location")
    private WebElement locationsSection;

    @FindBy(xpath = "//h2[starts-with(text(),'Life at')]/ancestor::section")
    private WebElement lifeAtSection;

    @FindBy(xpath = "//h3[contains(text(),'Find your calling')]")
    private WebElement teamsHeader;

    @FindBy(xpath = "//h3[contains(text(),'Our Locations')]")
    private WebElement locationsHeader;

    @FindBy(xpath = "//h2[contains(text(),'Life at')]")
    private WebElement lifeAtHeader;

    @FindBy(xpath = "//li[contains(@class, 'glide__slide')]")
    private List<WebElement> locations;

    @FindBy(xpath = "//div[contains(@class, 'swiper-slide')]")
    private List<WebElement> cards;

    @FindBy(xpath = "//a[contains(@class, 'loadmore')]")
    private WebElement teamsButton;

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    private boolean isTeamSectionDisplayed() {
        return isDisplayed(teamsSection) && isDisplayed(teamsHeader) && isDisplayed(teamsButton) && (initialJobCount > 0);
    }

    private boolean isLocationSectionDisplayed() {
        return isDisplayed(locationsSection) && isDisplayed(locationsHeader) && !locations.isEmpty();
    }

    private boolean isLifeAtSectionDisplayed() {
        return isDisplayed(lifeAtSection) && isDisplayed(lifeAtHeader) && !cards.isEmpty();
    }

    public boolean sectionsAreVisible() {
        return isTeamSectionDisplayed() && isLocationSectionDisplayed() && isLifeAtSectionDisplayed();
    }

    public boolean isTeamsButtonFunctional() {
        jsClick(teamsButton);

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(jobItemLocator, initialJobCount));

        return driver.findElements(jobItemLocator).size() > initialJobCount;
    }
}