package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class OpenPositionsQAPage extends BasePage {
    private static final String ENCODED_URL = "aHR0cHM6Ly91c2VpbnNpZGVyLmNvbS9jYXJlZXJzL29wZW4tcG9zaXRpb25z";
    private static final String URL = new String(Base64.getDecoder().decode(ENCODED_URL), StandardCharsets.UTF_8);
    private static final String LEVER_DOMAIN = "jobs.lever.co";

    private static final By TITLE = By.cssSelector(".position-title");
    private static final By DEPARTMENT = By.cssSelector(".position-department");
    private static final By LOCATION = By.cssSelector(".position-location");
    private static final By JOB_CARDS = By.cssSelector(".position-list-item-wrapper");

    @FindBy(id = "career-position-list")
    private WebElement sectionPositionList;

    @FindBy(xpath = "//div[contains(@class,'position-list-item-wrapper')]")
    private List<WebElement> positionListItem;

    @FindBy(id = "select2-filter-by-location-container")
    private WebElement filterLocationBtn;

    @FindBy(id = "select2-filter-by-department-container")
    private WebElement filterDepartmentBtn;

    @FindBy(xpath = "(//div[contains(@class,'position-list-item-wrapper')])[1]")
    private WebElement positionListItemFirst;

    @FindBy(xpath = "(//a[text()='View Role'])[1]")
    private WebElement linkViewRoleFirst;

    public OpenPositionsQAPage(WebDriver driver) {
        super(driver);
    }

    public OpenPositionsQAPage open() {
        driver.get(URL);
        return this;
    }

    public boolean isPositionsSectionDisplayed() {
        return isDisplayed(sectionPositionList) && !positionListItem.isEmpty();
    }

    private By optionLocator(String text) {
        return By.xpath(String.format("//option[contains(text(), '%s')]", text));
    }

    private void applyFilter(WebElement dropdownBtn, String value) {
        dropdownBtn.click();                          // open the <select2> dropdown
        By option = optionLocator(value);
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void filterByLocation(String location) {
        applyFilter(filterLocationBtn, location);
    }

    public void filterByDepartment(String dept) {
        applyFilter(filterDepartmentBtn, dept);
    }

    public boolean areFiltersAppliedCorrectly(String location, String department) {
        return location.equals(filterLocationBtn.getAttribute("title")) && department.equals(filterDepartmentBtn.getAttribute("title"));
    }

    public boolean jobListMatches(String location, String department) {
        wait.until(driver -> driver.findElements(JOB_CARDS).stream().allMatch(item -> {
            try {
                String loc = item.findElement(LOCATION).getText().trim();
                String dep = item.findElement(DEPARTMENT).getText().trim();
                return loc.equalsIgnoreCase(location) && dep.equalsIgnoreCase(department);
            } catch (StaleElementReferenceException e) {
                return false;
            }
        }));

        return driver.findElements(JOB_CARDS).stream().allMatch(card -> {
            String cardLoc = card.findElement(LOCATION).getText().trim();
            String cardDept = card.findElement(DEPARTMENT).getText().trim();
            String cardTitle = card.findElement(TITLE).getText().trim();
            return cardLoc.equalsIgnoreCase(location) && cardDept.equalsIgnoreCase(department) && cardTitle.toLowerCase().contains(department.toLowerCase());
        });
    }

    public boolean verifyViewRole() {
        hover(positionListItemFirst);
        jsClick(linkViewRoleFirst);

        switchLastTab();
        wait.until(ExpectedConditions.urlContains(LEVER_DOMAIN));

        String currentUrl = driver.getCurrentUrl();

        closeTab();
        switchToMainTab();

        return currentUrl != null && currentUrl.contains(LEVER_DOMAIN);
    }
}
