package Modules;

import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import TestNGListener.ListenerTest;
import common.BaseUI;

@Listeners(ListenerTest.class)

public class HomePage extends BaseUI {

	@BeforeSuite(alwaysRun=true)
	void start() {
		loadPropertiesFile();
		startExtentReport(getValue("FileName"));
		openBrowser();

	}

	@Test(priority = 1, description = "Navigate To URL and Redirect to Login page",groups= {"Smoke","Functional"})
	void goToURL() {
		navigateToURL();
		loginPage();
	}

	@Test(priority = 2, description = "Check Visibility and Get Elements",groups= {"UI"},dependsOnMethods= {"goToURL"})
	void checkAndGetElements() {
		waitForBeCognizant();
		userNameActual = waitAndGetVisiblElement("UserName_Id", "User Name", 30);
		searchBox = waitAndGetVisiblElement("SearchBox_Id", "Search Box", 30);
		searchIcon = waitAndGetVisiblElement("SearchIcon_Xpath", "Search Icon", 30);
		topDropDown = waitAndGetVisibleElements("DropDowns_Xpath", "Top Drop Downs", 30);
	}

	@Test(priority = 3, description = "Verify User Name",groups= {"Functional"},dependsOnMethods= {"checkAndGetElements"})
	void verifyUserName() {
		assertValues(getTextFromElement(userNameActual, "User Name Actual"), getValue("UserNameExpected"), "User Name");
	}

	@Test(priority = 4, description = "Search for TruTime and validate functionality of search",groups= {"Functional"},dependsOnMethods= {"verifyUserName"})
	void searchForTruTime() {
		enterDataToElement(searchBox, getValue("SearchData"), "Search Box");
		waitAndClickElement(searchIcon, "Search Icon", 20);
		truTimeLink = waitAndGetVisiblElement("TruTimeLink_Xpath", "TruTime Link", 30);
		leftSideElements = waitAndGetVisibleElements("LeftSideElements_Xpath", "Left Side Elements", 20);

	}

	@Test(priority = 5, description = "Click on TruTime Link",groups= {"Functional"},dependsOnMethods= {"searchForTruTime"})
	void clickTruTime() {
		waitAndClickElement(truTimeLink, "TruTime Link", 20);
	}

	@Test(priority = 6, description = "Switch to new tab (TruTime Tab)",dependsOnMethods= {"clickTruTime"})
	void switchToTruTimePage() {
		switchToNewWindow();
	}
	// waitAndClickElement("TruTimeLink_Xpath", "TruTime Link", 30);

	/*
	 * try { Thread.sleep(6000); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } switchToNewWindow();
	 * waitAndGetVisiblElement("Frame_Id", "App Frame", 30);
	 * driver.switchTo().frame(getValue("Frame_Id")); List<WebElement>
	 * displayedDates = waitAndGetVisibleElements("Dates_Xpath", "Dates", 30);
	 * String[] datesActual = new String[7]; for(WebElement date : displayedDates) {
	 * System.out.println(getTextFromElement(date, (i+1)+" Date")); datesActual[i] =
	 * getTextFromElement(date, (i+1)+" Date"); i++; }
	 * System.out.println(datesActual[0]); System.out.println(driver.getTitle());
	 * 
	 * 
	 * }
	 */

	@AfterSuite(alwaysRun=true)
	public void tearDown() {
		driver.quit();
		endExtentReport();
	}
}
