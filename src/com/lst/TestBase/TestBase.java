package com.lst.TestBase;
import java.io.IOException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.xml.sax.SAXException;

public class TestBase extends Wdgl{
   public TestBase() throws IOException, SAXException {
		super();
	}

   @BeforeMethod
   public static void setup() throws IOException, SAXException
   {
	   Wdgl.openUrl("http://www.groupon.com/browse/indianapolis");
	   System.out.println("Opened URL: http://www.groupon.com/browse/indianapolis");
   }
   
   @AfterMethod
   public void tearDown() throws Exception {
     driver.quit();
     
   }


}
