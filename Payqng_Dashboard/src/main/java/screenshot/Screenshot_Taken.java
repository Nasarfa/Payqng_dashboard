package screenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot_Taken {
	public static String takescreenshot(WebDriver driver,String screenshotname) throws IOException {
		String dateformat=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date());
		TakesScreenshot ts=(TakesScreenshot) driver;
		File Sourcefile=ts.getScreenshotAs(OutputType.FILE);
		String destination=System.getProperty("user.dir")+"/Extent Report/"+"/Screenshot Failed Test Case/" +screenshotname+" "+dateformat+ ".png";
		File destinationfile=new File(destination);
		FileUtils.copyFile(Sourcefile, destinationfile);
		return destination;
	}
}
