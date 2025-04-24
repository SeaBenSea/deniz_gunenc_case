package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    private static final int TIMEOUT = 15;

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final JavascriptExecutor jsExecutor;
    protected final Actions actions;

    @FindBy(css = "img[alt$='_logo']")
    private WebElement logo;

    @FindBy(id = "navigation")
    private WebElement navbar;

    @FindBy(css = "[aria-label = 'cookieconsent']")
    private WebElement cookieConsentBanner;

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

    public boolean isPageLoaded() {
        return isLogoLoaded() && isNavbarLoaded() && isCookieConsentDisplayed();
    }

    private boolean isLogoLoaded() {
        return isDisplayed(logo);
    }

    private boolean isNavbarLoaded() {
        return isDisplayed(navbar);
    }

    private boolean isCookieConsentDisplayed() {
        return isDisplayed(cookieConsentBanner);
    }
}
