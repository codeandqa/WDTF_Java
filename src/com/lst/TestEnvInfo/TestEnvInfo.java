package com.lst.TestEnvInfo;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestEnvInfo
{
    private WebDriver driver;
    
    private String baseUrl;
    private String parentBrowser ;
    
    private String testClassName;
    private String guid;
    private String implicitTimeout;
    
    private String userName;
    private String userLogin;
    private String userPassword;
    
    private boolean IsReportSend;
    private String reportReceiverEmail;
    private String resultLocation;
    //private String isResultDeletAfterTest;
    public TestEnvInfo() throws IOException, SAXException
    {
    	try {
			File xmlDoc = new File("/Users/Anwi/workspace/common/src/com/lst/config/NewFile.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			Document doc = dbBuilder.parse(xmlDoc);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("settings");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					 guid = eElement.getElementsByTagName("GUID").item(0).getTextContent();
					 implicitTimeout = eElement.getElementsByTagName("ImplicitWaits").item(0).getTextContent();
					 parentBrowser = eElement.getElementsByTagName("ParentBrowser").item(0).getTextContent();
					 userLogin = eElement.getElementsByTagName("userLogin").item(0).getTextContent();
					 userPassword = eElement.getElementsByTagName("userPassword").item(0).getTextContent();
					 userName = eElement.getElementsByTagName("UserName").item(0).getTextContent();
					 reportReceiverEmail = eElement.getElementsByTagName("ReportReceiverEmail").item(0).getTextContent();
					 resultLocation = eElement.getElementsByTagName("NetworkLocation").item(0).getTextContent();
					 baseUrl = eElement.getElementsByTagName("baseURL").item(0).getTextContent();
					 IsReportSend = eElement.getElementsByTagName("sendlog").item(0).getTextContent() == "true"?true:false;
					// guid = eElement.getElementsByTagName("DeleteOldLogfiles").item(0).getTextContent();
					 //guid = eElement.getElementsByTagName("RecordingWhileFailure").item(0).getTextContent();
					 //guid = eElement.getElementsByTagName("EndToEndTesting").item(0).getTextContent();
					 
					 //retrun as boolean and then start driver.
				}
		}
		}
    	catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		}
    	driver = StartDriver();
    }
    public TestEnvInfo(WebDriver driver, String baseurl, String testClassName, String guid, String implicitTimeout, String UserName, String userLogin, String userPassword, boolean IsReportSend, String reportReceiverEmail, String resultLocation) throws IOException
    {
    	
    }
    public String getBrowser()
    {
        return parentBrowser;
    }
    public void setBrowser(String newBrowser)
    {
        parentBrowser = newBrowser;
    }
    public String getGuidName()
    {
    	return guid;
    }
    public void setGuidName(String guid)
    {
    	this.guid = guid;
    }
    public String getBaseUrl()
    {
    	return baseUrl;
    }
    public void setBaseUrl(String newUrl)
    {
    	baseUrl = newUrl;
    }
    public String getReportReceiverEmail()
    {
    	return reportReceiverEmail;
    }
    public void setEmail(String newEmailOfEmailReceiver)
    {
    	reportReceiverEmail = newEmailOfEmailReceiver;
    }
    public String getNetworkLocatioctionForFile()
    {
    	return resultLocation;
    }
    public void setNetworkLocatioctionForFile(String newNetworkLocation)
    {
    	resultLocation = newNetworkLocation;
    }
    public String getTestClassName()
    {
    	return testClassName;
    }
    public String getUserLogin()
    {
        return userLogin;
    }
    public void setUserLogin(String newUserLogin)
    {
    	userLogin = newUserLogin;
    }
    public void setUserPassword(String newUserPassword)
    {
    	userPassword = newUserPassword;
    }
    public String getUserPassword()
    {
        return userPassword;
    }
   
    public String getUserName()
    {
        return userName;
    }
    public void setUserName(String newUserName)
    {
    	userName = newUserName;
    }
    
    public boolean getResultSendCondition()
    {
        return IsReportSend;
    }
    public void setResultSendCondition(boolean newReportSendCondition)
    {
    	IsReportSend = newReportSendCondition;
    }
    /*
    public String getBrowser()
    {
        return parentBrowser;
    }
    public void setBrowser(String newBrowser)
    {
        parentBrowser = newBrowser;
    }
  */  
    
    public WebDriver getDriver()
    {
        return driver;
    }
    public WebDriver StartDriver() throws IOException
    {
        if (parentBrowser.toLowerCase().equals("Firefox".toLowerCase()))
        {
        	File fBug = new File("C:\\WDTF\\ThirdPartyTools\\firebug-1.9.2.xpi");
            FirefoxProfile firefoxProfile = new FirefoxProfile();
            firefoxProfile.addExtension(fBug);
            firefoxProfile.setPreference("extensions.firebug.currentVersion", "1.9.2"); 
            firefoxProfile.setEnableNativeEvents(true);
            this.driver = new FirefoxDriver();
            
        }
        else if (parentBrowser.toLowerCase().equals("googlechrome".toLowerCase()))
        {
            this.driver = new ChromeDriver();
        }
        else if (parentBrowser.toLowerCase().equals("Iexplore".toLowerCase()))
        {
            this.driver = new InternetExplorerDriver();
        }
        else if (parentBrowser.toLowerCase().equals("Android".toLowerCase()))
        {
        	
        	//Runtime.getRuntime().exec("cmd /c 'adb -s 0123456789ABCDEF -e install -r android-server-2.21.0.apk'");
        	//Runtime.getRuntime().exec("cmd /c adb -s 0123456789ABCDEF shell am start -a android.intent.action.MAIN -n org.openqa.selenium.android.app/.MainActivity");
        	//Runtime.getRuntime().exec("cmd /c start \"cd /d C:\\Users\\Anwi\\AppData\\Local\\Android\\android-sdk\\platform-tools && adb -s 0123456789ABCDEF forward tcp:8080 tcp:8080\"");
            this.driver =new  AndroidDriver();
          
            
        }
        else
        {
            this.driver = null;
        }
        //driver.manage().timeouts().implicitlyWait(Long.parseLong(implicitTimeout), TimeUnit.SECONDS);
        return driver;
    }
    public int setImplicitTimeout()
    {
        return Integer.parseInt(implicitTimeout);
    }
    
}