package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import utils.ExtentReportManager;

public class BaseUI extends ExtentReportManager {
	public static WebDriver driver;
	public static Properties prop;
	public static String userDir = System.getProperty("user.dir");
	String propFilePath = "\\src\\test\\java\\resources\\config.properties";
	Set<String> windowIds;
	static int ssNo = 0;
	String[] windowIdsArray;
	String parentWindow;
	public int i = 0;
	public WebElement userNameActual;
	public WebElement searchBox, searchIcon, truTimeLink;
	public List<WebElement> topDropDown, leftSideElements, displayedDates;
	public static String[] datesActual, datesExpected;
	public int flag = 0;
	public Date ExpTodayDate = null;
	public String ExpTodayDateInFormat;

	/*
	 * // This method is to load properties file
	 */ public void loadPropertiesFile() {
		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream file = new FileInputStream(userDir + propFilePath);
				prop.load(file);
				// System.out.println("dfdf" + test);
			} catch (Exception e) {

				Assert.fail("Exception loading properties file", e);

			}
		}
	}

	/*
	 * // This method is to launch browser
	 */ public void openBrowser() {
		try {
			if (getValue("browserName").equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver", userDir + prop.getProperty("ChromeDriverPath"));
				driver = new ChromeDriver();
			} else if (getValue("browserName").equalsIgnoreCase("gecko")) {
				System.setProperty("webdriver.gecko.driver", userDir + prop.getProperty("GeckoDriverPath"));
				driver = new FirefoxDriver();
			} else {

				Assert.fail("No browser opened, provided browser name invalid, enter chrome or gecko only");
			}
			reportPass(getValue("browserName") + " Browser is Opened Successfully");
			driver.manage().window().maximize();
		} catch (Exception e) {
			Assert.fail("No browser opened " + e.getMessage());

		}
	}

	/*
	 * // This Method is used to navigate to given URL
	 */ public void navigateToURL() {
		try {
			driver.get(prop.getProperty("BaseURL"));
			parentWindow = driver.getWindowHandle();
			reportPass(prop.getProperty("BaseURL") + " is accessible");
		} catch (Exception e) {
			reportFail(prop.getProperty("baseURL") + "is not accessible exception");
			// occured");
			Assert.fail("Not navigated to given URL", e);

		}

	}

	/*
	 * // This method is used to close tab/window
	 */ public void closeBrowser() {
		try {
			driver.close();
			test.log(LogStatus.PASS, "Tab close successfull");
		} catch (Exception e) {
			reportFail("Unable to close current tab/window exception occured");
			Assert.fail("Exception thrown for closing tab/window", e);
		}
	}

	public void tearDown() { // This method is used to Quit driver
		try {
			if (driver != null) {
				driver.quit();
				test.log(LogStatus.PASS, "Driver quit successfull");
			} else {
				test.log(LogStatus.INFO, "Driver null");
			}
			 reportPass("Driver quit successfull");
		} catch (Exception e) {
			reportFail("Driver quit not successfull exception occured");
			Assert.fail("Exception thrown for driver quit", e);

		}
	}

	/*
	 * This method is used to get key property from Properties file
	 */ public String getValue(String locatorKey) {
		return prop.getProperty(locatorKey);
	}

	/*
	 * This method is used to switch to new window/tab
	 */ public void switchToNewWindow() {
		try {
			windowIds = driver.getWindowHandles();
			windowIdsArray = windowIds.toArray(new String[windowIds.size()]);
			driver.switchTo().window(windowIdsArray[1]);
			reportPass("Driver switched to child window/tab successfully");
			System.out.println("Switched To New Winodw/tab");
		} catch (Exception e) {
			reportFail("Driver not switched to child window/tab");
			Assert.fail("Exception in switching to new window/tab", e);
		}
	}

	/*
	 * This method is used to switch to parent window
	 */ public void switchToParentWindow() {
		try {
			driver.switchTo().window(parentWindow);
			reportPass("Driver switched to parent window successfully");
		} catch (Exception e) {
			reportFail("Driver not switched to parent window");
			Assert.fail("Exception in switching to parent window", e);
		}
	}

	/*
	 * This method is used to navigate back
	 */ public void navigateBack() {
		try {
			driver.navigate().back();
		} catch (Exception e) {
			Assert.fail("Exception thrown for navigating back", e);
		}
	}

	/*
	 * This method is to log Pass status in Extent Report
	 */ public void reportPass(String message) {
		String ss = addScreenShot();
		test.log(LogStatus.PASS, message + ss);
	}

	/*
	 * This method is log Fail status in Extent Report
	 */ public void reportFail(String message) {
		String ss = addScreenShot();
		test.log(LogStatus.FAIL, message + ss);
	}

	/*
	 * This method is used to add screenshot in Extent Report
	 */ public String addScreenShot() {
		String imgPath = captureScreen();
		return ExtentReportManager.test.addScreenCapture(imgPath);

	}

	/*
	 * This method is used to capture screen
	 */ public String captureScreen() {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Date date = new Date();

		String d = date.toString();
		d = d.replaceAll(" ", "");
		d = d.replaceAll(":", "");
		String imgPath = userDir + "\\ExtentReports\\ExScreenshots\\capture" + ssNo + d + ".png";
		try {
			FileHandler.copy(screenshot, new File(imgPath));
			// System.out.println("Screen Captured");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ssNo++;
		return imgPath;

	}

	public void switchToFrame(String id) {
		try {
			driver.switchTo().frame(getValue(id));
			System.out.println("Driver switched to frame");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if(e.getCause()!=null) {
				System.out.println(e.getCause());
			}
			Assert.fail("Driver not switched to frame");
		}
	}

	public void waitForBeCognizant() {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 300);
			wait.until(ExpectedConditions.titleContains("Be.Cognizant"));
		}catch(Exception e) {
			System.out.println(e.getMessage());
			if(e.getCause()!=null) {
				System.out.println(e.getCause());
			}
			Assert.fail("\"Expecting for Page Title Be.Cognizant failed, waited for 5 minutes.\\n Login to Cognizant Account using credentials in 5 min\"");
			
		}
		}
	public void loginPage() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.titleContains("Sign in to your account"));
			//Assert.assertEquals(driver.getTitle(),"Sign in to your account");
			System.out.println("User Redirected To Login Page");
			reportPass("User Redirected to Login Page");
		}catch(Exception e) {
			
			reportFail("User Redirected to Login Page");
			Assert.fail("User Not Redirected To Login Page");
		}
	}

	/*
	 * This method is to find elements based on given locator key
	 */ public List<WebElement> waitAndGetVisibleElements(String locatorKey, String elementsName, int seconds) {
		List<WebElement> elements = null;
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		try {

			if (locatorKey.endsWith("_Id")) {
				elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(getValue(locatorKey))));
			} else if (locatorKey.endsWith("_Xpath")) {
				elements = wait
						.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(getValue(locatorKey))));
			} else if (locatorKey.endsWith("_TagName")) {
				elements = wait
						.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName(getValue(locatorKey))));
			} else {
				Assert.fail("Locator key does match with any provided conditions");
			}
			reportPass(elementsName+" are visible");
			System.out.println(elementsName + " are visible");
		} catch (Exception e) {
			reportFail("Unable to locate elements " + elementsName);
			Assert.fail("Unable to locate elements" + e);

		}
		return elements;
	}

	public WebElement waitAndGetVisiblElement(String locatorKey, String elementName, int seconds) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		try {

			if (locatorKey.endsWith("_Id")) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(getValue(locatorKey))));
			} else if (locatorKey.endsWith("_Xpath")) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getValue(locatorKey))));
			} else if (locatorKey.endsWith("_TagName")) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(getValue(locatorKey))));
			} else {
				Assert.fail("Locator key does match with any provided conditions");
			}
			reportPass(elementName+" is visible");
			System.out.println(elementName + " is visible");
		} catch (Exception e) {
			reportFail("Unable to locate element " + elementName);
			Assert.fail("Unable to locate element", e);
		}
		return element;

	}

	public void waitAndClickElement(WebElement element, String elementName, int seconds) {
		// WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		try {

			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			reportPass(elementName+" is clicked");
			System.out.println(elementName + " is clicked");
		} catch (Exception e) {
			reportFail("Unable to click element " + elementName);
			Assert.fail("Unable to click " + elementName, e);
		}
	}

	public String getTextFromElement(WebElement element, String elementName) {
		return element.getText();
	}

	public void assertValues(String actual, String expected, String elementName) {

		try {
			Assert.assertEquals(actual, expected);
			System.out.println(elementName + " is as Expected");
			System.out.println("User Name retrieved as: " + actual);

		} catch (AssertionError ae) {
			System.out.println(elementName + " is NOT as Expected");
			System.out.println(ae.getMessage());
		}

	}

	public void enterDataToElement(WebElement element, String data, String elementName) {
		element.sendKeys(data);
	}
}
