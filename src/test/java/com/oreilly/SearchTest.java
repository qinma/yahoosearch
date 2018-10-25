package com.oreilly;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import java.util.Set;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SearchTest extends Base {

	private WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	@Test
	public void verifySearchResult() throws Exception {

		String outputFileLocation = "/Users/qin/eclipse-workspace/yahoosearch/temp/screenshot.png";
		String url = "http://www.yahoo.com";

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
		
		WebElement element = driver.findElement(By.name("p"));

		// Generate string
		String searchString = generateSearchString();
		// String searchString = getHardCodedSearchString();
		System.out.println("generated search string is: " + searchString);

		// Submit search string
		element.sendKeys(searchString);
		element.submit();

		// Find the 3rd link in the list
		String oneSearchResultXpath = "//*[@id=\"web\"]/ol/li[" + findTheLinkIndexAndSkipVideo(driver, 3)
				+ "]/div/div[1]/h3/a";
		WebElement oneSearchResult = driver.findElement(By.xpath(oneSearchResultXpath));
		// WebElement oneSearchResult = driver.findElement(By.xpath("//*[@id=\"web\"]/ol/li[4]/div/div[1]/h3/a"));
		System.out.print("Found the link: ++++++" + oneSearchResult.getText());

		// Open link
		oneSearchResult.click();

		// Get parent window
		String parentWindow = driver.getWindowHandle();

		// Find the child window
		Set<String> handles = driver.getWindowHandles();
		for (String windowHandle : handles) {
			if (!windowHandle.equals(parentWindow)) {
				driver.switchTo().window(windowHandle);

				// Take screenshot and save
				this.takeSnapShot(driver, outputFileLocation);

				// Close child window
				driver.close();

				driver.switchTo().window(parentWindow);
			}
		}
	}
}