package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentReportManager {
	public static ExtentReports report;
	public static ExtentTest test;

	public static void startExtentReport(String fileName) {
		System.out.println("Report started");
		report = new ExtentReports(System.getProperty("user.dir") + "\\ExtentReports\\" + fileName + ".html");
		test = report.startTest(fileName);

	}

	public static void endExtentReport() {

		report.flush();
		System.out.println("Report done");
	}
}
