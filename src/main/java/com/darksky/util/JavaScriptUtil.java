package com.darksky.util;

import com.darksky.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil extends BasePage {
	public static  WebDriver driver;
	
	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
	}

	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 5; i++) {
			changeColor(element,"rgb(0,200,0)");// 1
			changeColor(element, bgcolor);// 
		}
	}
	
	public void flash(By locator) {
		WebElement element= driver.findElement(locator);
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 4; i++) {
			changeColor(element,"rgb(0,200,0)");// 1
			changeColor(element, bgcolor);// 
		}
	}

	public void changeColor(By locator, String color) {
		WebElement element= driver.findElement(locator);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}
	public void changeColor(WebElement element, String color) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}


	public  void drawBorder(WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.border='2px solid red'", element);
	}

	public  void drawBorder(By locator) {
		WebElement element= driver.findElement(locator);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.border='2px solid red'", element);
	}

	public  void generateAlert(String message) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("alert('" + message + "')");
	}

	public void clickElementByJS(WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].click();", element);
	}

	public  void clickElementByJS(By locator) {
		WebElement element= driver.findElement(locator);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].click();", element);
	}

	public   void refreshBrowserByJS() {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("history.go(0)");
	}

	public String getTitleByJS() {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String title = js.executeScript("return document.title;").toString();
		return title;
	}

	public  String getPageInnerText() {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String pageText = js.executeScript("return document.documentElement.innerText;").toString();
		return pageText;
	}

	public  void scrollPageDown() {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public  void scrollIntoView(WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public  void scrollIntoView(By locator) {
		WebElement element= driver.findElement(locator);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public String getBrowserInfo(){
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String uAgent = js.executeScript("return navigator.userAgent;").toString();     
		return uAgent;
	}
	
	public  void sendKeysUsingJSWithID(String id, String value){
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("document.getElementById('" + id + "').value='"+value+"'");
	}
	
	public  void sendKeysUsingJSWithClassName(String className, String value){
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("document.getElementByClassName('" + className + "').value='"+value+"'");
	}
	
	public  void sendKeysUsingJSWithName(String name, String value){
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("document.getElementByName('" + name + "').value='"+value+"'");
	}

	public void doSetAtributeValue(By locator, String atribute, String value){
		WebElement element= driver.findElement(locator);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String script="arguments[0].setAttribute('"+atribute+"', '"+value+"')";
		try {
			js.executeScript(script,element);
		} catch (Exception e) {
			System.out.println("some exception occured while set attribute value to webelement "+ locator);
		}
	}

}
