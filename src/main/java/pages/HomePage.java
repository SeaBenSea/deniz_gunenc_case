package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.Base64;

public class HomePage extends BasePage {
    private static final String ENCODED_URL = "aHR0cHM6Ly91c2VpbnNpZGVyLmNvbS8=";

    @FindBy(xpath = "//a[@id='navbarDropdownMenuLink' and contains(text(),'Company')]")
    private WebElement companyDropdown;

    @FindBy(xpath = "//*[text() = 'Careers']")
    private WebElement linkCareers;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        String decodedUrl = new String(Base64.getDecoder().decode(ENCODED_URL));
        driver.get(decodedUrl);

        return this;
    }

    private void clickLinkCareers() {
        click(linkCareers);
    }

    public CareersPage goToCareersPage() {
        Assert.assertTrue(isHidden(linkCareers));
        hover(companyDropdown);
        Assert.assertTrue(isDisplayed(linkCareers));
        clickLinkCareers();
        return new CareersPage(driver);
    }
}