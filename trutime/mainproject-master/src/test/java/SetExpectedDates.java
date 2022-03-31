import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import common.BaseUI;

public class SetExpectedDates extends BaseUI {
	@BeforeTest
	void start() {

		driver.get("https://www.google.com/");
	}

	@Test(description = "Get date from Google Search and Set Expected Dates")
	void setDates() {

		driver.findElement(By.xpath("//input[@name='q']")).sendKeys("Date");
		driver.findElement(By.xpath("//input[@name='q']")).sendKeys(Keys.RETURN);

		String dateFromGoogle = // "Wed, 23 March 2022";
				driver.findElement(By.xpath("//span[contains(text(),'Date')]//..//div")).getText();
		test.log(LogStatus.INFO, "Date from Google Search Result " + dateFromGoogle);
		LocalDate date = LocalDate.now();
		DateTimeFormatter ft = DateTimeFormatter.ofPattern("EE, dd MMMM yyyy");

		test.log(LogStatus.INFO, "Date from Local System" + ft.format(date));
		SimpleDateFormat formatter1 = new SimpleDateFormat("EE, dd MMMM yyyy");

		try {
			ExpTodayDate = formatter1.parse(dateFromGoogle);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SimpleDateFormat formatter2 = new SimpleDateFormat("E, dd MMM");
		ExpTodayDateInFormat = formatter2.format(ExpTodayDate);
		System.out.println(ExpTodayDateInFormat);

		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(formatter1.parse(dateFromGoogle));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int dayOfWeekValue = cal.get(Calendar.DAY_OF_WEEK);
		

		datesExpected = new String[7];
		cal.add(Calendar.DAY_OF_MONTH, -(dayOfWeekValue - 1));
		datesExpected[0] = formatter2.format(cal.getTime());
		// System.out.println("Expected dates");
		// System.out.println(datesExpected[0]);
		for (int i = 1; i < 7; i++) {
			cal.add(Calendar.DAY_OF_MONTH, +1);
			datesExpected[i] = formatter2.format(cal.getTime());

			// System.out.println(datesExpected[i]);
		}

	}
}
