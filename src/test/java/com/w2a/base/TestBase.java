package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {
	
	/*
	 * 
	 * WebDriver
	 * Properties
	 * Logs -log4j.jar, .logs,log4j.properties , Logger
	 * ExtentReports
	 * DB
	 * Excel
	 * Mail
	 * ReportNG,Extentreports
	 * Jenkins
	 * 
	 */

	
	public static WebDriver driver=null;
	public static Properties config=new Properties();
	public static Properties OR=new Properties();
	public static FileInputStream fis=null;
	public static Logger log=Logger.getLogger("devpinoyLogger");
	
	@BeforeSuite
	public void setup() {
		
		if(driver==null) {
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config File Loaded..!!!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 try {
				fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR File Loaded..!!!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			if(config.getProperty("browser").equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executable\\chromedriver.exe");
				driver=new ChromeDriver();
				log.debug("Chrome Launched..!!!!!");
			}else if(config.getProperty("browser").equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executable\\geckodriver.exe");
				driver=new FirefoxDriver();
			}else if(config.getProperty("browser").equalsIgnoreCase("edge")) {
				System.setProperty("webdriver.edge.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executable\\msedgedriver.exe");
				driver=new EdgeDriver();
			}
			
			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated To----"+config.getProperty("testsiteurl"));
			
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.Wait")), TimeUnit.SECONDS);
			
		}
		
		
	}
	
	
	@AfterSuite
	public void tearDown() {
		
		if(driver!=null) {
		driver.quit();
		}
		
		log.debug("Test Execution Completed..");
	}
}
