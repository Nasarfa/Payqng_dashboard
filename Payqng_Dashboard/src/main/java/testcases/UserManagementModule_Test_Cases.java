package testcases;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import pageobjectmodel.LoginPage_PageObjectModel;
import pageobjectmodel.UserManagementPage_PageObjectModel;
public class UserManagementModule_Test_Cases extends commonfunction.CommonFunction {
@Test(priority = 0)
	public void verfiylogin() throws InterruptedException {
		testcase=extentreport.createTest("Login Page Verify test case");
		PageFactory.initElements(driver,LoginPage_PageObjectModel.class);
		LoginPage_PageObjectModel.username.sendKeys(properties.getProperty("username"));
		LoginPage_PageObjectModel.password.sendKeys(properties.getProperty("password"));
		LoginPage_PageObjectModel.clickonlogin.click();
		Thread.sleep(2000);
		String expectedurl="https://payqngapp.com/Dashboard/Admin/home.php";
		String actualurl=driver.getCurrentUrl();
		Assert.assertEquals(actualurl, expectedurl);
		testcase.info("Login Test case passed expected and Actual Result match");
			}
   @Test(priority = 1)
    public void verifyclickonusermanagement() throws InterruptedException {
    	testcase=extentreport.createTest("Verify Click on User Management function test case");
    	PageFactory.initElements(driver, LoginPage_PageObjectModel.class);
    	LoginPage_PageObjectModel.username.sendKeys(properties.getProperty("username"));
    	LoginPage_PageObjectModel.password.sendKeys(properties.getProperty("password"));
    	LoginPage_PageObjectModel.clickonlogin.click();
		PageFactory.initElements(driver, UserManagementPage_PageObjectModel.class);
		Thread.sleep(3000);
		UserManagementPage_PageObjectModel.clickonusermanagement.click();
		Thread.sleep(3000);
	    String actualurl=driver.getCurrentUrl();
	    String expectedurl="https://payqngapp.com/Dashboard/Admin/user.php";
	    Assert.assertEquals(actualurl, expectedurl);
	    testcase.info("Test Case Passed Actual and Expected Result Matched");
    }

}
