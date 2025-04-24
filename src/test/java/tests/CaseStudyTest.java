package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CareersPage;
import pages.CareersQAPage;
import pages.HomePage;
import pages.OpenPositionsQAPage;

// TODO: Update assertions with fail messages
// TODO: Remove hardcoded strings from tests
public class CaseStudyTest extends BaseTest {
    private static final String optionLocationValue = "Istanbul, Turkiye";
    private static final String optionDepartmentValue = "Quality Assurance";

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

    @Test(testName = "should navigate to Open Positions page with *department* parameter set")
    public void thirdTest() {
        CareersQAPage careersQAPage = new CareersQAPage(driver).open();

        Assert.assertTrue(careersQAPage.isPageLoaded());

        OpenPositionsQAPage openPositionsQAPage = careersQAPage.clickSeeAllQAJobs();

        Assert.assertTrue(openPositionsQAPage.isPageLoaded("open-positions/?department=qualityassurance"));
        Assert.assertTrue(openPositionsQAPage.isPositionsSectionDisplayed());

        openPositionsQAPage.filterByLocation(optionLocationValue);
        openPositionsQAPage.filterByDepartment(optionDepartmentValue);

        Assert.assertTrue(openPositionsQAPage.areFiltersAppliedCorrectly(optionLocationValue, optionDepartmentValue));
        Assert.assertTrue(openPositionsQAPage.isPositionsSectionDisplayed());
    }

    @Test(testName = "filtered job cards should relate to filters")
    public void fourthTest() {
        OpenPositionsQAPage openPositionsQAPage = new OpenPositionsQAPage(driver).open();

        Assert.assertTrue(openPositionsQAPage.isPageLoaded());
        Assert.assertTrue(openPositionsQAPage.isPositionsSectionDisplayed());

        openPositionsQAPage.filterByLocation(optionLocationValue);
        openPositionsQAPage.filterByDepartment(optionDepartmentValue);

        Assert.assertTrue(openPositionsQAPage.areFiltersAppliedCorrectly(optionLocationValue, optionDepartmentValue));
        Assert.assertTrue(openPositionsQAPage.isPositionsSectionDisplayed());
        Assert.assertTrue(openPositionsQAPage.jobListMatches(optionLocationValue, optionDepartmentValue));
    }

    @Test(testName = "redirects to Lever when View Role clicked on job card")
    public void fifthTest(){
        OpenPositionsQAPage openPositionsQAPage = new OpenPositionsQAPage(driver).open();

        Assert.assertTrue(openPositionsQAPage.isPageLoaded());
        Assert.assertTrue(openPositionsQAPage.isPositionsSectionDisplayed());
        Assert.assertTrue(openPositionsQAPage.verifyViewRole());
    }
}
