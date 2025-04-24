package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class CaseStudyTest extends BaseTest {

    @Test(testName = "should load Home Page")
    public void firstTest() {
        HomePage homePage = new HomePage(driver).open();

        Assert.assertTrue(homePage.isPageLoaded());
    }
}
