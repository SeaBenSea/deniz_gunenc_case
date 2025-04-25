package pages;

import config.Urls;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HomePage extends BasePage {
    @FindBy(xpath = "//a[@id='navbarDropdownMenuLink' and contains(text(),'Company')]")
    private WebElement companyDropdown;

    @FindBy(xpath = "//*[text() = 'Careers']")
    private WebElement linkCareers;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(Urls.HOME);

        return this;
    }

    private void clickLinkCareers() {
        click(linkCareers);
    }

    public CareersPage goToCareersPage() {
        Assert.assertTrue(isHidden(linkCareers), "Link Careers is not hidden");
        hover(companyDropdown);
        Assert.assertTrue(isDisplayed(linkCareers), "Link Careers is not displayed");
        clickLinkCareers();
        return new CareersPage(driver);
    }
}