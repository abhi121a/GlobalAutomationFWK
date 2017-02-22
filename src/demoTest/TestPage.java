package demoTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationUtils.ExcelDataReader;

public class TestPage {
	
	private String sTestCaseName;
	 
	private int iTestCaseRow;
	
	WebDriver driver;
	
	//@BeforeClass
	/*public void moblieSetup() throws MalformedURLException{
	
	DesiredCapabilities capabilities=new DesiredCapabilities();
	capabilities.setCapability("automationName","Appium"); //Which automation engine to use
	capabilities.setCapability("platformName","Android");  //Which mobile OS platform to use
	capabilities.setCapability("platformVersion", "6.0"); //Mobile os version  
	capabilities.setCapability("deviceName","Emulator");	//Device Name - The kind of mobile device or emulator to use
	capabilities.setCapability("browserName", "Chrome"); //Name of mobile web browser to automate. Should be an empty string if automating an app instead.
	

	
	//capabilities.setCapability("appPackage", "com.android.calculator2");
	//capabilities.setCapability("appActivity","com.android.calculator2.Calculator");
	
	The absolute local path or remote http URL to an .ipa or .apk file, or a
	.zip containing one of these. Appium will attempt to install this app binary
	on the appropriate device first. Note that this capability is not required for
	Android if you specify appPackage and appActivity capabilities (see below). Incompatible with browserName.
	
	//capabilities.setCapability("newCommandTimeout", "60");//How long (in seconds) Appium will wait for
														  //a new command from the client before assuming
														  //the client quit and ending the session
	//capabilities.setCapability("autoLaunch","true"); //Whether to have Appium install and launch the app automatically. Default true
	
	capabilities.setCapability("udid","3300d5b0574a6387");  //Unique device identifier of the connected physical device
	
	
	driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	
   driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
	}
	*/
	@BeforeClass
	public void websetup(){
		/*driver = new FirefoxDriver();
		driver.get("http://10.41.57.131:8080/PriceSimulator/login.action");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);*/
	}
	
	@DataProvider(name = "DirectAuthentication")
	 
	  public static Object[][] credentials() {
	 
	      return new Object[][] { { "abhishek.verma02@snapdeal.com", "Jan@2017" }, { "test@snapdeal.com", "Test@123" }, { "abhishek.verma07@snapdeal.com", "Jan@2017" }, { "abhishek.verma04@snapdeal.com", "Jan@2017" }, { "abhishek.verma03@snapdeal.com", "Jan@2017" }};
	 
	  }
	
	@DataProvider(name = "Authentication")
	 
	public Object[][] Authentication() throws Exception{
		 
        Object[][] testObjArray = ExcelDataReader	.getTableArray("D://ToolsQA//OnlineStore//src//testData//TestData.xlsx","Sheet1");

        return (testObjArray);

		}
	 
	
	
	
	@Test(enabled=true)
	public void testLogin()	{
		driver = new FirefoxDriver();
		driver.get("http://10.41.57.131:8080/PriceSimulator/login.action");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.name("email")).sendKeys("abhishek.verma02@snapdeal.com");
		driver.findElement(By.name("password")).sendKeys("Jan@2017");
		WebElement ele=driver.findElement(By.id("submitbutton"));
		ele.click();
		driver.close();
	}
	
	@Test(enabled=true, dataProvider="DirectAuthentication")
	public void testUsingDataProvider(String username,String password){
		driver = new FirefoxDriver();
		driver.get("http://10.41.57.131:8080/PriceSimulator/login.action");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.name("email")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		WebElement ele=driver.findElement(By.id("submitbutton"));
		ele.click();
		driver.close();
	}
	
	
	@Test(enabled=false, dataProvider="Authentication")
	public void testUsingDataProviderExcel(String username,String password){
		driver.findElement(By.name("email")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		WebElement ele=driver.findElement(By.id("submitbutton"));
		ele.click();
	}
	
	@Test(enabled=false)
	public void testCal() throws Exception {
		
		driver.get("http://www.google.com/");
		
	   //locate the Text on the calculator by using By.name()
	   WebElement two=driver.findElement(By.name("2"));
	   two.click();
	   WebElement plus=driver.findElement(By.name("+"));
	   plus.click();
	   WebElement four=driver.findElement(By.name("4"));
	   four.click();
	   WebElement equalTo=driver.findElement(By.name("="));
	   equalTo.click();
	   //locate the edit box of the calculator by using By.tagName()
	   WebElement results=driver.findElement(By.tagName("EditText"));
		//Check the calculated value on the edit box
	assert results.getText().equals("6"):"Actual value is : "+results.getText()+" did not match with expected value: 6";

	}
	
	
	@AfterClass
	public void teardown(){
		//close the app
		driver.quit();
	}
	
	
	
	
	
	
}
