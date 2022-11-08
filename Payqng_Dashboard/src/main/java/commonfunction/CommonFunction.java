package commonfunction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.xml.sax.SAXException;

import com.aventstack.extentreports.Status;

import extentreport.ExtentReport;
import screenshot.Screenshot_Taken;
import emailtrigger.Email_Trigger;
public class CommonFunction extends ExtentReport {
	public static Properties properties;
	public static WebDriver driver;
	Logger logger=Logger.getLogger(CommonFunction.class);
	public Properties loadproperties() throws IOException {
		properties =new Properties();
		FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"/resource/config.properties");
		properties.load(fileInputStream);
		return properties;
	}
	@BeforeMethod
	public void launchbrowser() throws IOException {
		loadproperties();
		PropertyConfigurator.configure(System.getProperty("user.dir")+"/resource/log4j.properties");
		String browser = properties.getProperty("browser");
		String driverlocation = properties.getProperty("Driverlocation");
		String url = properties.getProperty("url");
		String username = properties.getProperty("username");
		String password=properties.getProperty("password");
		if (browser.equals("chrome")) {
			logger.info("Browser Launching");
			System.setProperty("webdriver.chrome.driver", driverlocation);
			driver=new ChromeDriver();
			//Test

		}else if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", driverlocation);
			driver=new FirefoxDriver();		
		}
		logger.info("Browser maximise");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
		logger.info("Browser Navigated to the Payqng Admin Dashboard");
		driver.get(url);
	}
	@BeforeTest
	public void beforetest() {
		ExtentReport.extentreport_setupt();
	}
	@AfterTest
	public void afterextentreport() {
		extentreport.flush();
	}
	@AfterMethod
	public void teardown(ITestResult result) throws IOException, EmailException {
	if (result.getStatus()==ITestResult.SUCCESS) {
		testcase.log(Status.PASS,"Test case Passes "+result.getName());
	}
	else if(result.getStatus()==ITestResult.FAILURE) {
		testcase.log(Status.FAIL,"Test Case Failed "+result.getName());
		testcase.log(Status.FAIL,"Test Case Error Log "+result.getThrowable());
		String screenshotdestination=Screenshot_Taken.takescreenshot(driver, result.getName());
		testcase.addScreenCaptureFromPath(screenshotdestination);
	}
	else if(result.getStatus()==ITestResult.SKIP) {
		testcase.log(Status.SKIP,"Test Case Skipped "+result.getName());
	}
		driver.quit();;
	}
	@AfterSuite
	public void after_suite() throws ParserConfigurationException, SAXException, Exception {
		Email_Trigger.mailtrigger();
	}

}

