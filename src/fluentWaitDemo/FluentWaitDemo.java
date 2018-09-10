package fluentWaitDemo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.common.base.Function;
import lib.BrowserDriverUtility;
import lib.ExtentReportUtility;
import lib.ScreenshotUtility;

public class FluentWaitDemo {

	WebDriver dr;
	ExtentReports report;
	ExtentTest logger;

	@BeforeMethod
	public void startReport() {
		report = ExtentReportUtility.InvokeExtentReport();
	}

	@Test
	public void FluentWait_Demo() throws InterruptedException {
		
		logger = report.createTest("\"WebDriver\" word verification test");

		dr = BrowserDriverUtility.InvokeBrowser("webdriver.chrome.driver",
				"C:\\Chetan\\Softs\\SeleniumSuite\\WebDrivers\\chromedriver.exe",
				"http://seleniumpractise.blogspot.com/2016/08/how-to-use-explicit-wait-in-selenium.html");

		dr.findElement(By.xpath("//button[@onclick='timedText()']")).click();

		// Waiting 30 seconds for an element to be present on the page, checking for its
		// presence once every 1 seconds.
		@SuppressWarnings("deprecation")
		Wait<WebDriver> wait = new FluentWait<WebDriver>(dr).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement ele = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement ele = driver.findElement(By.xpath("//*[@id='demo']"));
				String val = ele.getAttribute("innerHTML");
				
				if (val.equalsIgnoreCase("WebDriver")) {
					String path = ScreenshotUtility.CaptureScreenshot(driver, val);
					try {
						logger.pass("Congratulations... \"WebDriver\" word found Successfully...!.",
								MediaEntityBuilder.createScreenCaptureFromPath(path).build());
					} catch (IOException e) {
						e.printStackTrace();
					}
					return ele;
				} else if (val.startsWith("Click")) {
					System.out.println("Text which is coming on screen is: " + val);

					String click = val.substring(0, 5);
					String path = ScreenshotUtility.CaptureScreenshot(driver, click);
					try {
						logger.fail("Page still not have word \"WebDriver\"",
								MediaEntityBuilder.createScreenCaptureFromPath(path).build());
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				} else {
					String path = ScreenshotUtility.CaptureScreenshot(driver, val);
					try {
						logger.fail("Page still not have word \"WebDriver\"",
								MediaEntityBuilder.createScreenCaptureFromPath(path).build());
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				}
			}
		});
		System.out.println("Element is Displayed?: " + ele.isDisplayed());
	}

	@AfterMethod
	public void AddStatus(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			logger.pass(MarkupHelper.createLabel("The Test Method name as: " + result.getName() + " is passed.", ExtentColor.GREEN));
		} else if (result.getStatus() == ITestResult.FAILURE) {
			logger.fail(MarkupHelper.createLabel("The Test Method name as: " + result.getName() + " is failed.", ExtentColor.RED));
			logger.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.skip(MarkupHelper.createLabel("The Test Method name as: " + result.getName() + " is skipped.", ExtentColor.YELLOW));
			logger.skip(result.getThrowable());
		}
	}

	@AfterTest
	public void tearDown() {
		report.flush();
		dr.close();
	}

}
