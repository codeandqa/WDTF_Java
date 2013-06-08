package com.lst.TestBase;
import com.lst.TestEnvInfo.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.xml.sax.SAXException;
public abstract class Wdgl {
	protected static WebDriver driver;
    //private static TestEnvInfo testEInfo;
	private static TestEnvInfo testEnvInfo;
    public Wdgl() throws IOException, SAXException
    { 
    	testEnvInfo = new TestEnvInfo();
    	driver = testEnvInfo.getDriver();
        System.out.print(testEnvInfo.getBaseUrl());
    }
	//public static TestEnvInfo getTestEInfo() {return testEInfo; }
	//public static void setTestEInfo(TestEnvInfo testEInfo) { Wdgl.testEInfo = testEInfo; }
	public static TestEnvInfo getTestEnvInfo ()
	{
		return testEnvInfo;
	}

	public static boolean openUrl(String url)
	 {
		 driver.navigate().to(url);
		 return true;
	 }
	
	public static List<WebElement> FindElements (SearchBy by, String locator,String logMessage) throws Exception
	 {
		 return FindElements(by, locator, logMessage, null);
	 }
	public static List<WebElement> FindElements (SearchBy by, String locator, String logMessage, WebElement parentElement) throws Exception
	 {
		 List<WebElement> listOfElements = new ArrayList<WebElement>();
		 String[] splittedId = { };
		 String baseIdentifier = null;
         String innerText = null;
         List<String> containsItemList = new ArrayList<String>();
         
         String[] containtTextArray = {};
         org.openqa.selenium.By elementPointer = null;
		 if (locator.contains(":contains"))
         {
             splittedId = locator.split(":");
             baseIdentifier = splittedId[0];
			for (String item : splittedId)
             {
                 if (item.contains("contains("))
                 {
                     containsItemList.add(item.substring(item.indexOf('(') + 1, (item.indexOf(')') - item.indexOf('(') - 1)));
                 }
             }
             containtTextArray = (String[]) containsItemList.toArray(new String[containsItemList.size()]);  
         }
         else
         {
             baseIdentifier = locator;
         }
		 switch(by)
		 {
		 case ClassName:
			 elementPointer = org.openqa.selenium.By.className(baseIdentifier);
			 break;
		 case CssSelector:
			 elementPointer = org.openqa.selenium.By.cssSelector(baseIdentifier);
			 break;
		 case Id:
			 elementPointer = org.openqa.selenium.By.id(baseIdentifier);
			 break;
		 case LinkText:
			 elementPointer = org.openqa.selenium.By.linkText(baseIdentifier);
			 break;
		 case Name:
			 elementPointer = org.openqa.selenium.By.name(baseIdentifier);
			 break;
		 case PartialLinkText:
			 elementPointer = org.openqa.selenium.By.name(baseIdentifier);
			 break;
		 case TagName:
			 elementPointer = org.openqa.selenium.By.tagName(baseIdentifier);
			 break;
		 case XPath:
			 elementPointer = org.openqa.selenium.By.xpath(baseIdentifier);
			 break;
		 }
		 List<WebElement> elements = new ArrayList<WebElement>();
		 //DateTime currTime = new DateTime(`, month, day, hour, minute, second, timeZone)
		// elements = driver.FindElements(elementPointer);	
		 	
		try
        {
			long start = Calendar.getInstance().getTimeInMillis();
			long stop =  Calendar.getInstance().getTimeInMillis();;
			while (elements.size() == 0 && (stop-start) < 10000 )
			 {
				
	             if (parentElement == null)
	             {
	            	 elements = driver.findElements(elementPointer);
	             }
	             else
	             { 
	            	 elements = parentElement.findElements(elementPointer);
	             }
	             stop = Calendar.getInstance().getTimeInMillis();
			 }
        }
        catch (Exception e)
        {
       	 TakeScreenShot();
       	 return null;
        }	 
		
		
		 if (containtTextArray.length != 0)
         { 
             for (WebElement element: elements)
             {
            	 if(element.isDisplayed())
            	 {
            		 innerText = element.getText();
            		 for (String contText : containsItemList)
            		 {
            			 if (innerText.contains(contText))
            			 {
            				 listOfElements.add(element);
            			 }
                     
            		 }
            	 }
             }
             elements = listOfElements;
         }
         return elements;
	 }
	
	public static WebElement FindElement(SearchBy by, String locator, String logMessage) throws Exception
	 {
		 
		 return FindElement(by, locator, logMessage, null);
	 }
	public static WebElement FindElement(SearchBy by, String locator, String logMessage, WebElement parentElement) throws Exception
	 {
		 List<WebElement> searchElements = FindElements(by, locator, logMessage, parentElement);
		 
		 if (searchElements != null)
         {
             if (searchElements.size() > 0)
             {
                 return searchElements.get(0);
             }
             else
             {
            	 TakeScreenShot();
                 return null;
             }
         }
         else
         {
        	 TakeScreenShot();
             return null;
         }
		 
	 }
	
	public static boolean Click(SearchBy by, String locator, String logMessage) throws Exception
	{
		return Click(by, locator, logMessage, null);
	}
	public static boolean Click(SearchBy by, String locator, String logMessage, WebElement parentElement) throws Exception
	{
		WebElement element = FindElement(by, locator, logMessage, parentElement);
		return Click(element, logMessage);
	}
	public static boolean Click(WebElement element, String logMessage) throws Exception
	{
		try
		{
			element.click();
		}
		catch(Exception e)
		{
			TakeScreenShot();
			return false;
		}
		return true;
	}
	
	public static boolean clear(WebElement element, String logMessage) throws Exception
	{
		try
		{
			element.clear();
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public static boolean clear(SearchBy by, String locator, String logMessage) throws Exception
	{
		WebElement element = FindElement(by, locator, logMessage, null);
		return clear(element, logMessage);
	}
	public static boolean clear(SearchBy by, String locator, String logMessage, WebElement parentElement) throws Exception
	{
		WebElement element = FindElement(by, locator, logMessage, parentElement);
		return clear(element, logMessage);
	}

	public static boolean Type(WebElement element, String inputText, String logMessage) throws Exception
	{
		try
		{
			element.sendKeys(inputText);
		}
		catch(Exception e)
		{
			TakeScreenShot();
			return false;
		}
		return true;
	}
	public static boolean Type(SearchBy by, String locator, String inputText, String logMessage) throws Exception
	{
		WebElement element = FindElement(by, locator, logMessage, null);
		return Type(element, inputText, logMessage);
		
	}
	public static boolean Type(SearchBy by, String locator, String inputText, String logMessage, WebElement parentElement) throws Exception
	{
		WebElement element = FindElement(by, locator, logMessage, parentElement);
		return Type(element, inputText, logMessage);
	}

	public static boolean fillInFields(String... locatorDollarSignDelimiterInput) throws Exception
	{
		for (String fieldSet : locatorDollarSignDelimiterInput)
        {
            if (fieldSet.contains("["))
            {
                Type(SearchBy.CssSelector, fieldSet.split("$")[0], fieldSet.split("$")[1], "Type " + fieldSet.split("$")[1] + " in input area " + fieldSet.split("$")[0]);
            }
            else if (fieldSet.contains("\\"))
            {
            	Type(SearchBy.XPath, fieldSet.split("$")[0], fieldSet.split("$")[1], "Type " + fieldSet.split("$")[1] + " in input area " + fieldSet.split("$")[0]);
            }
            else

            {
            	Type(SearchBy.Id, fieldSet.split("$")[0], fieldSet.split("$")[1], "Type " + fieldSet.split("$")[1] + " in input area " + fieldSet.split("$")[0]);
            }
        }
		return true;
	}
	
	public static String GetAttributeValue(WebElement element, String attributeName, String logMessage) throws Exception
	{
		String attributeValue = "";
		try
		{
			attributeValue = element.getAttribute(attributeName);
		}
		catch(Exception e)
		{
			TakeScreenShot();
			return null;
		}
		return attributeValue;
	}
	public static String GetAttributeValue(SearchBy by, String locator, String attributeName, String logMessage) throws Exception
	{
		WebElement element = FindElement(by, locator, logMessage, null);
		return GetAttributeValue(element, attributeName, logMessage);
		
	}
	public static String GetAttributeValue(SearchBy by, String locator, String attributeName, String logMessage, WebElement parentElement) throws Exception
	{
		WebElement element = FindElement(by, locator, logMessage, parentElement);
		return GetAttributeValue(element, attributeName, logMessage);
	}
    
	public static boolean MouseOverToElement(SearchBy by, String locator, String logMessage) throws Exception
	{
		WebElement toElement = FindElement(by, locator, logMessage);
		org.openqa.selenium.interactions.Actions action = new org.openqa.selenium.interactions.Actions(driver);
		action.moveToElement(toElement).build().perform();
		driver.wait(5);
		return true;
	}
	
	public static boolean WaitForElementPresent(SearchBy by, String locator, String logMessage,WebElement parentElement) throws Exception
	{
		List<WebElement> searchElements = FindElements(by, locator, logMessage, parentElement);
		 
            if (searchElements.size() > 0)
            {
                return searchElements.get(0).isDisplayed();
            }
            else
            {
           	 	TakeScreenShot();
                return false;
            }
        
		 
	}
	public static boolean waitForElementPresent(SearchBy by, String locator, String logMessage) throws Exception
	{
		return WaitForElementPresent(by, locator, logMessage, null);
	}
	
	public static boolean WaitForElementDisappear(SearchBy by, String locator, String logMessage,WebElement parentElement) throws Exception
	{
		WebElement searchElement = FindElement(by, locator, logMessage, parentElement);
		
		 while (searchElement.isDisplayed() == true)
		 {
			 //execute loop 
		 }
        return false;
	}
    public static boolean WaitForElementDisappear(SearchBy by, String locator, String logMessage) throws Exception
    {
    	return WaitForElementDisappear(by, locator, logMessage, null);
    }

    public static void TakeScreenShot() throws IOException
	{
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("c:\\tmp\\screenshot.png"));
	}

    public static boolean WaitForFlashingElemnt(SearchBy by, String locator, String logMessage) throws Exception
    {
    	WebElement searchElement = FindElement(by, locator, logMessage);
    	boolean ss = searchElement.isDisplayed();
    	
    	return ss;
    }
	public static boolean isElementExist(SearchBy by, String locator, String logMessage) throws Exception
	{
		return isElementExist(by, locator, logMessage, null);
	}
    public static boolean isElementExist(SearchBy by, String locator, String logMessage, WebElement parentElement) throws Exception
    {
    	List<WebElement> listOfElements = new ArrayList<WebElement>();
    	List<WebElement> elements = new ArrayList<WebElement>(); 
    	String[] splittedId = { };
		String baseIdentifier = null;
        String innerText = null;
        List<String> containsItemList = new ArrayList<String>();
        
        String[] containtTextArray = {};
        org.openqa.selenium.By elementPointer = null;
		if (locator.contains(":contains"))
        {
            splittedId = locator.split(":");
            baseIdentifier = splittedId[0];
			for (String item : splittedId)
            {
                if (item.contains("contains("))
                {
                    containsItemList.add(item.substring(item.indexOf('(') + 1, (item.indexOf(')') - item.indexOf('(') - 1)));
                }
            }
            containtTextArray = (String[]) containsItemList.toArray(new String[containsItemList.size()]);  
        }
        else
        {
            baseIdentifier = locator;
        }
		switch(by)
		{
			 case ClassName:
				 elementPointer = org.openqa.selenium.By.className(baseIdentifier);
				 break;
			 case CssSelector:
				 elementPointer = org.openqa.selenium.By.cssSelector(baseIdentifier);
				 break;
			 case Id:
				 elementPointer = org.openqa.selenium.By.id(baseIdentifier);
				 break;
			 case LinkText:
				 elementPointer = org.openqa.selenium.By.linkText(baseIdentifier);
				 break;
			 case Name:
				 elementPointer = org.openqa.selenium.By.name(baseIdentifier);
				 break;
			 case PartialLinkText:
				 elementPointer = org.openqa.selenium.By.name(baseIdentifier);
				 break;
			 case TagName:
				 elementPointer = org.openqa.selenium.By.tagName(baseIdentifier);
				 break;
			 case XPath:
				 elementPointer = org.openqa.selenium.By.xpath(baseIdentifier);
				 break;
		}
		if(parentElement == null)
		{
			elements = driver.findElements(elementPointer);
		}
		else
		{
			elements = parentElement.findElements(elementPointer);
		}
		if(elements.size()>0)
		{
			if (containtTextArray.length != 0)
	         { 
	             for (WebElement element: elements)
	             {
	            	innerText = element.getText();
	            	for (String contText : containsItemList)
	            	{
	            	 if (innerText.contains(contText))
	            	 {
	            		 listOfElements.add(element);
	            	 }
	            	}
	             }
	             elements = listOfElements;
	         }
			
		}
		else
		{
			return false;
		}
		
		if(elements.size()>0)
        {
       	 return true;
        }
       	 else 
       	 {   	
       		 return false;
       	 }
    }
    
    public static boolean SelectItemByIndex(SearchBy by, String locator, int index, String logMessage) throws Exception
    {
    	return SelectItemByIndex(by, locator, index, logMessage, null);
    }
    public static boolean SelectItemByIndex(SearchBy by, String locator, int index, String logMessage, WebElement parentElement) throws Exception
    {
    	WebElement element = FindElement(by, locator, logMessage, parentElement);
    	Select selectItem = new Select(element);
    	selectItem.selectByIndex(index);
    	return true;
    }
    
    public static boolean SelectItemByValue(SearchBy by, String locator, String value, String logMessage) throws Exception
    {
    	return SelectItemByValue(by, locator, value, logMessage, null);
    }
    public static boolean SelectItemByValue(SearchBy by, String locator, String value, String logMessage, WebElement parentElement) throws Exception
    {
    	WebElement element = FindElement(by, locator, logMessage, parentElement);
    	Select selectItem = new Select(element);
    	selectItem.selectByValue(value);
    	return true;
    }
    
    public static boolean SelectItemByText(SearchBy by, String locator, String text, String logMessage) throws Exception
    {
    	return SelectItemByText(by, locator, text, logMessage, null);
    }
    public static boolean SelectItemByText(SearchBy by, String locator, String text, String logMessage, WebElement parentElement) throws Exception
    {
    	WebElement element = FindElement(by, locator, logMessage, parentElement);
    	Select selectItem = new Select(element);
    	selectItem.selectByVisibleText(text);
    	return true;
    }
    
    public static boolean DeselectItem(SearchBy by, String locator, String logMessage) throws Exception
    {
    	return SelectItemByText(by, locator, logMessage, null);
    }
    public static boolean DeselectItem(SearchBy by, String locator, String logMessage, WebElement parentElement) throws Exception
    {
    	WebElement element = FindElement(by, locator, logMessage, parentElement);
    	Select selectItem = new Select(element);
    	selectItem.deselectAll();
    	return true;
    }
    
    public static boolean scrollPage(WebElement anyElementOnScrollingPage)
    {
    	anyElementOnScrollingPage.sendKeys(Keys.ARROW_DOWN);
    	return true;
    }
    
    //Android commands.
    
    public static boolean RotateScreen()
    {
    	return true;
    }
    
    
}
    


