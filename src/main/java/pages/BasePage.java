package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

public abstract class BasePage {
    private static final int TIMEOUT = 15;

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final JavascriptExecutor jsExecutor;
    protected final Actions actions;
    @FindBy(xpath = "//*[text() = 'Accept All']")
    public WebElement buttonCookieConsentAcceptAll;
    @FindBy(css = "img[alt$='_logo']")
    private WebElement logo;
    @FindBy(id = "navigation")
    private WebElement navbar;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        this.jsExecutor = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        PageFactory.initElements((new AjaxElementLocatorFactory(driver, TIMEOUT)), this);
    }

    /**
     * Return a *visible* WebElement from either a By locator or an existing element.
     */
    private <T> WebElement waitVisible(T ref) {
        if (ref instanceof By by) {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }
        if (ref instanceof WebElement el) {
            return wait.until(ExpectedConditions.visibilityOf(el));
        }
        throw new IllegalArgumentException("Unsupported reference type: " + ref.getClass().getName());
    }

    /**
     * Return a *clickable* WebElement from either a By locator or an existing element.
     */
    private <T> WebElement waitClickable(T ref) {
        if (ref instanceof By by) {
            return wait.until(ExpectedConditions.elementToBeClickable(by));
        }
        if (ref instanceof WebElement el) {
            return wait.until(ExpectedConditions.elementToBeClickable(el));
        }
        throw new IllegalArgumentException("Unsupported reference type: " + ref.getClass().getName());
    }

    protected <T> void click(T ref) {
        waitClickable(ref).click();
    }

    protected <T> void jsClick(T ref) {
        WebElement elem = waitClickable(ref);
        jsExecutor.executeScript("arguments[0].scrollIntoView({block:'center',inline:'center'});", elem);
        jsExecutor.executeScript("arguments[0].click();", elem);
    }

    protected <T> void hover(T ref) {
        actions.moveToElement(waitVisible(ref)).perform();
    }

    protected <T> boolean isDisplayed(T ref) {
        try {
            return waitVisible(ref).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected <T> boolean isHidden(T ref) {
        if (ref instanceof By by) {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        }
        if (ref instanceof WebElement el) {
            return wait.until(ExpectedConditions.invisibilityOf(el));
        }
        throw new IllegalArgumentException("Unsupported reference type: " + ref.getClass().getName());
    }

    public boolean isPageLoaded(String... url) {
        boolean isUrlValid = url.length == 0 || (driver.getCurrentUrl() != null && driver.getCurrentUrl().contains(url[0]));
        return isUrlValid && isLogoLoaded() && isNavbarLoaded();
    }

    private boolean isLogoLoaded() {
        return isDisplayed(logo);
    }

    private boolean isNavbarLoaded() {
        return isDisplayed(navbar);
    }

    public void acceptCookieConsentBanner() {
        if (isDisplayed(buttonCookieConsentAcceptAll)) {
            click(buttonCookieConsentAcceptAll);
        }
    }

    protected void switchLastTab() {
        ArrayList<String> newTb = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(newTb.get(1));
    }

    protected void switchToMainTab() {
        ArrayList<String> newTb = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(newTb.getFirst());
    }

    protected void closeTab() {
        driver.close();
    }
}
