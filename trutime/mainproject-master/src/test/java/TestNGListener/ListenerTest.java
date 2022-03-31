package TestNGListener;

import common.BaseUI;
import utils.ExtentReportManager;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.LogStatus;

public class ListenerTest extends ExtentReportManager implements ITestListener {

	public void onFinish(ITestContext Result) {
		// TODO Auto-generated method stub


	}

	public void onStart(ITestContext Result) {
		// TODO Auto-generated method stub


	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult Result) {
		// TODO Auto-generated method stub
		test.log(LogStatus.UNKNOWN, Result.getMethod().getDescription()+Result.getThrowable());

	}

	public void onTestFailure(ITestResult Result) { 
		// TODO Auto-generated method stub
		test.log(LogStatus.FAIL, Result.getMethod().getDescription()+Result.getThrowable());

	}

	public void onTestSkipped(ITestResult Result) {
		// TODO Auto-generated method stub
		System.out.println("Skipped" + Result.getMethod().getDescription());
		//System.out.println(test);
		//test= report.startTest(Result.getName());
		test.log(LogStatus.SKIP, Result.getMethod().getDescription()+Result.getThrowable());

	}

	public synchronized void onTestStart(ITestResult Result) {
		// TODO Auto-generated method stub
		test.log(LogStatus.INFO,"Started test :"+Result.getMethod().getDescription());

	}

	public void onTestSuccess(ITestResult Result) {
		// TODO Auto-generated method stub
		test.log(LogStatus.PASS,"Completed :"+Result.getMethod().getDescription());


	}

}
