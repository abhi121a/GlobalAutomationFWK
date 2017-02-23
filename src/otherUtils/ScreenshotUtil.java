package otherUtils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ScreenshotUtil {
	
	String screenshotPath=null;
	
	Logger log=Logger.getLogger(ScreenshotUtil.class);

	public void takeScreenshot(WebDriver driver){
		log.info("Started");
		String SEP=System.getProperty("file.separator");
		log.info("file seperator::"+SEP);
		log.info("TAKING SCREENSHOT");
		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {System.out.println("copying file");
		log.info("COPYING FILE TO BELOW PATH");
			 // now copy the  screenshot to desired location using copyFile //method
		System.out.println(System.getProperty("user.dir")+SEP+"target"+SEP+"selenium"+SEP+System.currentTimeMillis()+"REPORT.png");
		log.info("PATH::"+System.getProperty("user.dir")+SEP+"target"+SEP+"selenium"+SEP+System.currentTimeMillis()+"REPORT.png");
		FileUtils.copyFile(src, new File(System.getProperty("user.dir")+SEP+"target"+SEP+"selenium"+SEP+System.currentTimeMillis()+"REPORT.png"));
			}
			 
			catch (IOException e)
			 {
				log.info("Exception found "+e.getMessage());
				System.out.println(e.getMessage());
			 
			 }
			 }

	
	
	
}
