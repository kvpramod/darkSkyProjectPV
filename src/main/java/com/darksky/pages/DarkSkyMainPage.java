package com.darksky.pages;

import com.darksky.base.BasePage;
import com.darksky.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.ArrayList;

public class DarkSkyMainPage extends BasePage {

	WebDriver driver;
	ElementUtil elementUtil;
	JavaScriptUtil javaScriptUtil;

	By location=By.cssSelector("#searchForm input");
	By searchButton=By.className("searchButton");
	private String tempBarValuesStart = "//div[@class='hours']//span[";
	private String tempBarValuesEnd = "]/span";
	private By presentTemperature = By.xpath("//span[@class='currently']//span[@class='summary swap']");
	private By temperatureLow = By.xpath("//span[@class='summary-high-low']/span[2]");
	private By temperatureHigh = By.xpath("//span[@class='summary-high-low']/span[3]");

	public DarkSkyMainPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
		javaScriptUtil=new JavaScriptUtil(driver);
	}

	public void searchZipCode() throws InterruptedException {
		System.out.println(" *********ZipCode Search*********");
		elementUtil.doSendKeys(location, Constants.ZIP_CODE);
		Thread.sleep(3000);
		elementUtil.doClick(searchButton);
	}

	public static int incrementTempBy;
	public static int forFutureHours;

	public boolean verifyTemperatureBarValues(int incrementBy, int futureHours) {
		System.out.println(" *********Verify Temp timeline*********");
		incrementTempBy = incrementBy;
		forFutureHours = futureHours;
		return isTwoArrayListEquals(getExpectedTemperatureTimeline(), getActualTemperatureTimeline());
	}

	public static boolean isTwoArrayListEquals(ArrayList expectedArrayList, ArrayList actualArrayList) {
		boolean result = false;
		int expectedArrayLength = expectedArrayList.size();
		int actualArrayLength = actualArrayList.size();

		if (expectedArrayLength == actualArrayLength) {
			for (int i = 0 ; i < actualArrayList.size() ; i++) {
				Assert.assertEquals(expectedArrayList.get(i), actualArrayList.get(i), "Value is not same. Actual: " + actualArrayList.get(i) + ", and expected: " + expectedArrayList.get(i));
				result = true;
			}
		} else {
			Assert.assertFalse(result, "Length of given arrayList didn't match");
			result = false;
		}
		return result;
	}

	private ArrayList<String> getActualTemperatureTimeline() {
		ArrayList<String> actualTemperatureBar = new ArrayList<String>();
		for (int i = 1; i <= 23 ; i+=2) {
			String completeLocator = tempBarValuesStart + i + tempBarValuesEnd;
			actualTemperatureBar.add(elementUtil.getTextFromElement(By.xpath(completeLocator)));
		}
		return actualTemperatureBar;
	}

	private ArrayList<String> getExpectedTemperatureTimeline() {
		ArrayList<String> expectedTemperatureBar = new ArrayList<String>();
		String[] hour_DayHalf = DateUtils.getCurrentHourIn12Hours_hhaaFormat();
		String hour = hour_DayHalf[0];
		int hourInt = Integer.parseInt(hour);
		String dayTime = hour_DayHalf[1].toLowerCase();
		String tempValue = Integer.toString(hourInt) + dayTime;
		expectedTemperatureBar.add(tempValue);
		for (int i= 0 ; i < forFutureHours ; i++) {
			int prevValue = Integer.parseInt(GetValuesUsingRegex.getHourUsingRegex(expectedTemperatureBar.get(i)));
			int addThis = prevValue + incrementTempBy;
			if (prevValue == 12) {
				addThis = addThis - 12;
			} else if (addThis >= 12) {
				dayTime = DateUtils.switchDayHalf(dayTime);
				if(addThis != 12) {
					addThis = addThis - 12;
				}
			}
			hour = Integer.toString(addThis);
			hour = hour + dayTime;
			expectedTemperatureBar.add(hour);
		}
		expectedTemperatureBar.set(0, "Now");
		return expectedTemperatureBar;
	}

	public void verifyPresentTempBetweenLowHigh() {
		try {
			Thread.sleep(5000);
			int present = getPresentTemperature();
			int low = getLowTemperature();
			int high = getHighTemperature();
			Assert.assertTrue(low <= present && present <= high, "Present temp (" + present + "˚) is not in between low (" + low + "˚) and high (" + high + "˚)");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private int getPresentTemperature() {
		return GetValuesUsingRegex.getCurrentTempValueUsingRegEx(elementUtil.getTextFromElement(presentTemperature));
	}

	private int getLowTemperature() {
		return GetValuesUsingRegex.getLowHighTempValueUsingRegEx(elementUtil.getTextFromElement(temperatureLow));
	}

	private int getHighTemperature() {
		return GetValuesUsingRegex.getLowHighTempValueUsingRegEx(elementUtil.getTextFromElement(temperatureHigh));
	}
}
