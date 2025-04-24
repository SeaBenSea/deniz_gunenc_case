package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.Base64;
import java.util.List;

public class OpenPositionsQAPage extends BasePage {
    private static final String ENCODED_URL = "aHR0cHM6Ly91c2VpbnNpZGVyLmNvbS9jYXJlZXJzL29wZW4tcG9zaXRpb25z";
    private static final By TITLE = By.cssSelector(".position-title");
    private static final By DEPARTMENT = By.cssSelector(".position-department");
    private static final By LOCATION = By.cssSelector(".position-location");
    private static final By JOB_CARDS = By.cssSelector(".position-list-item-wrapper");

    @FindBy(id = "career-position-list")
    public WebElement sectionPositionList;
    @FindBy(xpath = "//div[contains(@class,'position-list-item-wrapper')]")
    public List<WebElement> positionListItem;
    @FindBy(id = "select2-filter-by-location-container")
    public WebElement filterLocationBtn;
    @FindBy(id = "select2-filter-by-department-container")
    public WebElement filterDepartmentBtn;

    public OpenPositionsQAPage(WebDriver driver) {
        super(driver);
    }

    public OpenPositionsQAPage open() {
        String decodedUrl = new String(Base64.getDecoder().decode(ENCODED_URL));
        driver.get(decodedUrl);

        return this;
    }

    public boolean isPositionsSectionDisplayed() {
        return isDisplayed(sectionPositionList) && !positionListItem.isEmpty();
    }

    public By getFilterByXpath(String location) {
        return By.xpath("//option[contains(text(), '" + location + "')]");
    }

    public void filterByLocation(String location) {
        By locationFilterByXpath = getFilterByXpath(location);
        wait.until(ExpectedConditions.presenceOfElementLocated(locationFilterByXpath));
        click(filterLocationBtn);
        click(locationFilterByXpath);
    }

    public void filterByDepartment(String department) {
        By departmentFilterByXpath = getFilterByXpath(department);
        wait.until(ExpectedConditions.presenceOfElementLocated(departmentFilterByXpath));
        click(filterDepartmentBtn);
        click(departmentFilterByXpath);
    }

    public boolean areFiltersAppliedCorrectly(String location, String department) {
        String locationTitle = filterLocationBtn.getAttribute("title");
        String departmentTitle = filterDepartmentBtn.getAttribute("title");

        if (locationTitle == null || departmentTitle == null) {
            return false;
        }

        return locationTitle.equals(location) && departmentTitle.equals(department);
    }

    public void assertJobListIsFilteredCorrectly(String location, String department) {
        wait.until(driver -> driver.findElements(JOB_CARDS).stream().allMatch(item -> {
            try {
                String loc = item.findElement(LOCATION).getText().trim();
                String dep = item.findElement(DEPARTMENT).getText().trim();
                return loc.equalsIgnoreCase(location) && dep.equalsIgnoreCase(department);
            } catch (StaleElementReferenceException e) {
                return false;
            }
        }));

        List<WebElement> items = driver.findElements(JOB_CARDS);

        for (WebElement item : items) {
            String cardTitle = item.findElement(TITLE).getText().trim();
            String cardDepartment = item.findElement(DEPARTMENT).getText().trim();
            String cardLocation = item.findElement(LOCATION).getText().trim();

            Assert.assertTrue(cardTitle.toLowerCase().contains(department.toLowerCase()), "Job title does not contain the filter location");
            Assert.assertTrue(cardDepartment.equalsIgnoreCase(department), "Job department does not contain the filter department");
            Assert.assertTrue(cardLocation.equalsIgnoreCase(location), "Job location does not contain the filter location");
        }
    }
}
