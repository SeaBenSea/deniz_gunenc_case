package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.HomePage;

// TODO: Update assertions with fail messages
public class CaseStudyTest extends BaseTest {
    @Test(testName = "should load Home Page")
    public void firstTest() {
        HomePage homePage = new HomePage(driver).open();

        Assert.assertTrue(homePage.isPageLoaded());
    }

    @Test(testName = "should navigate to Careers Page from Home Page's Navbar")
    public void secondTest() {
        HomePage homePage = new HomePage(driver).open();
        CareersPage careersPage = homePage.goToCareersPage();

        Assert.assertTrue(careersPage.isPageLoaded());
        Assert.assertTrue(careersPage.sectionsAreVisible());
        Assert.assertTrue(careersPage.isTeamsButtonFunctional());
    }
}
