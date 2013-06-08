package com.lst.TestBase;
import java.util.List;
import com.lst.TestEnvInfo.*;

import org.openqa.selenium.*;
public interface Commandable {
	
	boolean openUrl(String url);
	
	WebElement findElement(SearchBy by, String locator, String logMessage);
	WebElement findElement(SearchBy by, String locator, String logMessage, WebElement parentElement);
	
	List<WebElement> findElements(SearchBy by, String locator,String logMessage);
	List<WebElement> findElements(SearchBy by, String locator, String logMessage, WebElement parentElement);
	
	boolean click(SearchBy by, String locator, String logMessage);
	boolean click(SearchBy by, String locator, String logMessage, WebElement parentElement);
	boolean click(WebElement element, String logMessage);
		
	boolean clear(SearchBy by, String locator, String logMessage);
	boolean clear(WebElement element, String logMessage);
	boolean clear(SearchBy by, String locator, String logMessage, WebElement parentElement);
	
	boolean type(WebElement element, String inputText, String logMessage);
	boolean type(SearchBy by, String locator, String inputText, String logMessage);
	boolean type(SearchBy by, String locator, String inputText, String logMessage, WebElement parentElement);

	
	boolean fillInFields(String... locatorDollarSignDelimiterInput);
	
	String getAttributeValue(WebElement element, String attributeName, String logMessage);
	String getAttributeValue(SearchBy by, String locator, String attributeName, String logMessage);
	String getAttributeValue(SearchBy by, String locator, String attributeName, String logMessage, WebElement parentElement);

	boolean isElementExist(SearchBy by, String locator, String logMessage);
	boolean isElementExist(SearchBy by, String locator, String logMessage, WebElement parentElement);

	
	
	
	
	
	
	boolean selectItem(WebElement webElement, String value);
	
	boolean isElementVisible(SearchBy by, String locator, String logMessage);
	boolean isElementPresent(SearchBy by, String locator, String logMessage);
	boolean isElementEnabled(SearchBy by, String locator, String logMessage);
	
	boolean elementMouseOver(SearchBy by, String locator, String logMessage);
	boolean elementMouseOver(WebElement targetElement);
	boolean performDragAndDrop(SearchBy sourceBy, String sourceLocator, SearchBy targetBy, String targetLocator, String logMessage);
	boolean performDragAndDrop(SearchBy sourceBy, String sourceLocator, SearchBy targetBy, String targetLocator);
	
	boolean waitForElementPresent(SearchBy by, String locator, String logMessage);
	boolean waitForElementGone(SearchBy by, String locator, String logMessage);
	
	void emergencyTeardown(TestEnvInfo t);
	void executeJavaScript(WebElement targetElement);
	void takeScreenShot(TestEnvInfo testInfo);
	
	void switchFrame(int frameIndex);
	void switchFrame(String frameName);
	void switchToContent();
	void switchFrame(WebElement webElement);
	void switchWindow(String windowName);
	void alertAccept();
	void alertDismiss();
	void maximizeWindow();
	
	}
