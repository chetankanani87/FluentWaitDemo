package lib;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportUtility {
	static ExtentHtmlReporter reporter;
	static ExtentReports report;
	static ExtentTest logger;

	
	public static  ExtentReports InvokeExtentReport() {
		reporter = new ExtentHtmlReporter("./ExtentReport/report.html");
		reporter.config().setDocumentTitle("Automation Testing Demo Report");
		reporter.config().setReportName("CK's Report");
		reporter.config().setTestViewChartLocation(ChartLocation.TOP);
		reporter.config().setTheme(Theme.STANDARD);
		reporter.loadXMLConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
		
		report = new ExtentReports();
		report.attachReporter(reporter);
		
		report.setSystemInfo("Host Name", "Chetan Kanani");
		report.setSystemInfo("Username", "xyz");
		report.setSystemInfo("OS", "Windows10");
		report.setSystemInfo("Tools", "Selenium, TestNG, ExtentReports v3.1.5, Eclipse");

		return report;
	}
}
