package com.lst.VerifyLibs;
import org.junit.*;

public class Verify implements IVerify {
	public Verify()
	{
		
	}

	public boolean VerifyText(String actualText, String expectedText, boolean IsCaseIngnore)
	{
		Assert.assertEquals(actualText, expectedText);
		return true;
	}
}
