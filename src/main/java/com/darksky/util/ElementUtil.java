package com.darksky.util;

import com.darksky.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ElementUtil extends BasePage {

	public static WebDriver driver;
	JavaScriptUtil javaScriptUtil;

	// Constructor
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getElement(By locator) {
		waitForElementPresentBy(locator);

		WebElement element = null;
		try {
			element = driver.findElement(locator);
			if (flash.equalsIgnoreCase("yes")) 
				javaScriptUtil.flash(element);
		} catch (Exception e) {
			System.out.println("some exception occured while creating webelement " + locator);
		}
		return element;
	}

	public List<WebElement> getAllElements(By locator) {
		List<WebElement> list = new ArrayList<WebElement>();
		waitForElementPresentBy(locator);

		try {
			list = driver.findElements(locator);
			if (flash.equalsIgnoreCase("yes"))
				javaScriptUtil.flash(locator);
		} catch (Exception e) {
			System.out.println("some exception occured while creating List of webelements " + locator);
		}
		return list;
	}

	public void waitForElementPresentBy(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void doClick(By locator) {
		try {
			waitForElementPresentBy(locator);
			getElement(locator).click();
		} catch (Exception e) {
			System.out.println("Some exception occured while click on  webelement " + locator);
		}
	}
	public void hoverAndClick(By locator) {
		WebElement element= driver.findElement(locator);
		Actions action = new Actions(driver);
		action.moveToElement(element).click(element).build().perform();
	}

	public void doSendKeys(By locator, String value) {
		try {
			getElement(locator).clear();
			getElement(locator).sendKeys(value);
		} catch (Exception e) {
			System.out.println("Some exception occured while sending to  webelement " + locator);
		}
	}

	public void clickEnter(By locator) {
		try {
			getElement(locator).sendKeys(Keys.ENTER);
		} catch (Exception e) {
			System.out.println("Some exception occured while clicking enter" + locator);
		}
	}

	public String doGetText(By locator) {
		String text = null;
		try {
			text = getElement(locator).getText();
		} catch (Exception e) {
			System.out.println("some exception occured while get text to webelement " + locator);
		}
		return text;
	}

	public String doGetValue(By locator){
		String text = null;
		try {
			text = getElement(locator).getAttribute("value");
		} catch (Exception e) {
			System.out.println("some exception occured while get attribute value to webelement "+ locator);
		}
		return text;
	}

	public String doGetAtributeValue(By locator, String atribute){
		String text = null;
		try {
			text = getElement(locator).getAttribute(atribute);
		} catch (Exception e) {
			System.out.println("some exception occured while get attribute value to webelement "+ locator);
		}
		return text;
	}

	public void doSelectDropDownValue(By locator, String value){
		String text = null;
		try {
			Select dropDown = new Select(getElement(locator));
			dropDown.selectByValue(value);
		} catch (Exception e) {
			System.out.println("some exception occured while select dropdown  "+ locator);
		}
	}

	public String waitForGetPageTitle(String title) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.titleContains(title));
		return driver.getTitle();
	}

	public boolean isElementDsiplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (Exception e) {
			System.out.println("some exception occured while checking webelement displayed " + locator);
			return false;
		}
	}


	public String getTextFromElement(By locator) {
		String text = null;
		try {
			text = getElement(locator).getText();
		} catch (NoSuchElementException e) {
			Assert.fail("Element is not found with this locator: " + locator.toString());
			e.printStackTrace();
		}
		return text;
	}

}
