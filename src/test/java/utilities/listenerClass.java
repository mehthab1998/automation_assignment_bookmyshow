package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import demo_testng.automation_assignment;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class listenerClass implements ITestListener {
    ExtentReports reports;
    ExtentSparkReporter extentSparkReporter;
    ExtentTest extentTest;
    automation_assignment assignment;

    public void onStart(ITestContext context)
    {
        String path= System.getProperty("user.dir");
        reports=new ExtentReports();
        extentSparkReporter=new ExtentSparkReporter(path+"\\reports\\report.html");
        try{
            reports.setSystemInfo("Machine Name", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        reports.attachReporter(extentSparkReporter);
    }

    public void onFinish(ITestContext context) {

        reports.flush();
    }

    public void onTestStart(ITestResult result) {
        System.out.println("New Test Started" +result.getName());
    }

    public void onTestSuccess(ITestResult result) {
        extentTest=reports.createTest(result.getName());
        extentTest.log(Status.PASS, result.getName());
        try{
            extentTest.addScreenCaptureFromPath(assignment.takeScreenshot());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onTestFailure(ITestResult result) {
        extentTest=reports.createTest(result.getName());
        extentTest.log(Status.FAIL, result.getName());
        try{
            extentTest.addScreenCaptureFromPath(assignment.takeScreenshot());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onTestSkipped(ITestResult result) {
        extentTest=reports.createTest(result.getName());
        extentTest.log(Status.SKIP, result.getName());

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("onTestFailedButWithinSuccessPercentage" +result.getName());
    }

}
