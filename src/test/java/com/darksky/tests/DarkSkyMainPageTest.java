package com.darksky.tests;

import com.darksky.base.BasePage;
import com.darksky.pages.DarkSkyMainPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Properties;

@Listeners
public class DarkSkyMainPageTest {
	WebDriver driver;
	Properties prop;
	BasePage basePage;
	DarkSkyMainPage darkSkyMainPage;

	@BeforeMethod
	public void setUp() throws Exception {
		basePage = new BasePage();
		prop = basePage.initialize_properties();
		driver = basePage.initialize_driver(prop);
		darkSkyMainPage = new DarkSkyMainPage(driver);
		Thread.sleep(2000);
	}

//	@Test(priority = 1, description = "DarkSky page title test... ", enabled = true)
//	public void EnterZipCode() throws InterruptedException {
//		String zipCode = "10001";
//		String title = darkSkyMainPage.getDarkSkyMainPageTitle();
//		System.out.println("zipCode : " + zipCode);
//		darkSkyMainPage.searchZipCode();
//		AssertJUnit.assertEquals(title, Constants.DARKSKY_PAGE_TITLE);
//	}

	@Test(priority = 1, description = "Verify forecast hours in timeline incremented by 2 hours for next 24 hours", enabled = true)
	public void verifyTempBarIncrement() throws InterruptedException {
		int incrementBy = 2;
		int forFutureHours = 11;
		darkSkyMainPage.searchZipCode();
		Thread.sleep(3000);
		Assert.assertTrue(darkSkyMainPage.verifyTemperatureBarValues(incrementBy, forFutureHours), "Hours in timeLine don't match");
	}

	@Test(priority = 2, description = "Verify present temperature is between lowest and highest", enabled =true )
	public void verifyCurrentTemperatureIsBetweenLowestAndHighest() throws InterruptedException {
		darkSkyMainPage.searchZipCode();
		darkSkyMainPage.verifyPresentTempBetweenLowHigh();
	}

	@AfterMethod
	public void tearDown() throws InterruptedException {
		basePage.quitBrowser();
	}

}
