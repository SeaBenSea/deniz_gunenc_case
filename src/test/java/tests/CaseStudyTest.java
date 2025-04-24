package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.CareersQAPage;
import pages.HomePage;
import pages.OpenPositionsQAPage;

// TODO: Remove hardcoded strings from tests
public class CaseStudyTest extends BaseTest {
    private static final String optionLocationValue = "Istanbul, Turkiye";
    private static final String optionDepartmentValue = "Quality Assurance";

    @Test(testName = "should load Home Page")
    public void firstTest() {
        HomePage homePage = new HomePage(driver).open();

        Assert.assertTrue(homePage.isPageLoaded(), "Home Page is not loaded");
    }

    @Test(testName = "should navigate to Careers Page from Home Page's Navbar")
    public void secondTest() {
        HomePage homePage = new HomePage(driver).open();
        CareersPage careersPage = homePage.goToCareersPage();

        Assert.assertTrue(careersPage.isPageLoaded(), "Careers Page is not loaded");
        Assert.assertTrue(careersPage.sectionsAreVisible(), "Careers Page sections are not visible");
        Assert.assertTrue(careersPage.isTeamsButtonFunctional(), "Teams button is not functional");
    }

    @Test(testName = "should navigate to Open Positions page with *department* parameter set")
    public void thirdTest() {
        CareersQAPage careersQAPage = new CareersQAPage(driver).open();

        Assert.assertTrue(careersQAPage.isPageLoaded(), "Open Positions Page is not loaded");

        OpenPositionsQAPage openPositionsQAPage = careersQAPage.clickSeeAllQAJobs();

        Assert.assertTrue(openPositionsQAPage.isPageLoaded("open-positions/?department=qualityassurance"), "Open Positions Page is not loaded");
        Assert.assertTrue(openPositionsQAPage.isPositionsSectionDisplayed(), "Positions section is not displayed");

        openPositionsQAPage.filterByLocation(optionLocationValue);
        openPositionsQAPage.filterByDepartment(optionDepartmentValue);

        Assert.assertTrue(openPositionsQAPage.areFiltersAppliedCorrectly(optionLocationValue, optionDepartmentValue), "Filters are not applied correctly");
        Assert.assertTrue(openPositionsQAPage.isPositionsSectionDisplayed(), "Positions section is not displayed");
    }

    @Test(testName = "filtered job cards should relate to filters")
    public void fourthTest() {
        OpenPositionsQAPage openPositionsQAPage = new OpenPositionsQAPage(driver).open();

        Assert.assertTrue(openPositionsQAPage.isPageLoaded(), "Open Positions Page is not loaded");
        Assert.assertTrue(openPositionsQAPage.isPositionsSectionDisplayed(), "Positions section is not displayed");

        openPositionsQAPage.filterByLocation(optionLocationValue);
        openPositionsQAPage.filterByDepartment(optionDepartmentValue);

        Assert.assertTrue(openPositionsQAPage.areFiltersAppliedCorrectly(optionLocationValue, optionDepartmentValue), "Filters are not applied correctly");
        Assert.assertTrue(openPositionsQAPage.isPositionsSectionDisplayed(), "Positions section is not displayed");
        Assert.assertTrue(openPositionsQAPage.jobListMatches(optionLocationValue, optionDepartmentValue), "Job list does not match filters");
    }

    @Test(testName = "redirects to Lever when View Role clicked on job card")
    public void fifthTest() {
        OpenPositionsQAPage openPositionsQAPage = new OpenPositionsQAPage(driver).open();
        openPositionsQAPage.acceptCookieConsentBanner();

        Assert.assertTrue(openPositionsQAPage.isPageLoaded(), "Open Positions Page is not loaded");
        Assert.assertTrue(openPositionsQAPage.isPositionsSectionDisplayed(), "Positions section is not displayed");
        Assert.assertTrue(openPositionsQAPage.verifyViewRole(), "View Role button is not functional");
    }
}
