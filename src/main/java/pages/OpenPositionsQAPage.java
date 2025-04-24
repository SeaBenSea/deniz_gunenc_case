package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class OpenPositionsQAPage extends BasePage {
    @FindBy(id = "career-position-list")
    public WebElement sectionPositionList;

    @FindBy(xpath = "//div[contains(@class,'position-list-item')]")
    public List<WebElement> positionListItem;

    @FindBy(id = "select2-filter-by-location-container")
    public WebElement filterLocationBtn;

    @FindBy(id = "select2-filter-by-department-container")
    public WebElement filterDepartmentBtn;

    public OpenPositionsQAPage(WebDriver driver) {
        super(driver);
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
}
