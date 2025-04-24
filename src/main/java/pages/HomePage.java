package pages;

import org.openqa.selenium.WebDriver;

import java.util.Base64;

public class HomePage extends BasePage {
    private static final String ENCODED_URL = "aHR0cHM6Ly91c2VpbnNpZGVyLmNvbS8=";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        String decodedUrl = new String(Base64.getDecoder().decode(ENCODED_URL));
        driver.get(decodedUrl);

        return this;
    }
}