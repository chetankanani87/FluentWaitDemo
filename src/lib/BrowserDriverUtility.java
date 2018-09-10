package lib;

import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

public class BrowserDriverUtility {
	static WebDriver driver;

	public static WebDriver InvokeBrowser(String key, String path, String url) {
		try {
			System.setProperty(key, path);
			switch (key) {
			case "webdriver.chrome.driver":
				driver = new ChromeDriver();
				break;
			case "webdriver.gecko.driver":
				driver = new FirefoxDriver();
				break;
			case "webdriver.opera.driver":
				OperaOptions options = new OperaOptions();
				options.setBinary(new File("C:\\Users\\hck\\AppData\\Local\\Programs\\Opera\\launcher.exe"));
				driver = new OperaDriver(options);
				break;
			case "webdriver.edge.driver":
				driver = new EdgeDriver();
				break;
			case "webdriver.ie.driver":
				driver = new InternetExplorerDriver();
				break;
			}

			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			//driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
			driver.get(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}
}
