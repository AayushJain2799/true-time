package Modules;

import java.util.Calendar;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import TestNGListener.ListenerTest;
import common.BaseUI;
@Listeners(ListenerTest.class)

public class TruTimePage extends BaseUI {
	@Test(priority = 1, description = "Wait for App Frame and switch to it")
	void switchToAppFrame() {
		waitAndGetVisiblElement("Frame_Id", "App Frame", 30);
		switchToFrame("Frame_Id");
	}

	@Test(priority = 2, description = "Check Visibility of Dates and Print Dates Displayed",dependsOnMethods= {"switchToAppFrame"})
	void printDates() {
		displayedDates = waitAndGetVisibleElements("Dates_Xpath", "Dates", 30);
		datesActual = new String[7];
		test.log(LogStatus.INFO,"Dates Displayed In TruTime");
		for (WebElement date : displayedDates) {
			System.out.println(getTextFromElement(date, (i + 1) + " Date"));
			datesActual[i] = getTextFromElement(date, (i + 1) + " Date");
			test.log(LogStatus.INFO, datesActual[i]);
			i++;
		}
	}

	@Test(priority = 3, description = "Verify Dates Displayed",dependsOnMethods= {"printDates"})
	void verifyDates() {
		//datesActual[5] = "Mon, 21 March";
		for (int i = 0; i < 7; i++) {
			try {
				Assert.assertEquals(datesActual[i], datesExpected[i], "Date displayed not correct");
			} catch (AssertionError ae) {
				flag++;
				test.log(LogStatus.FAIL, ae.getMessage());
				System.out.println(ae.getMessage());
			}
		}
		if (flag == 0) {
			System.out.println("All dates are correct");
			} else {
			System.out.println("Total no. of wrong dates displayed " + flag);
			reportFail("Dates displayed not correct");
			Assert.fail("Dates displayed not correct");
			}
	}
}
