package com.oreilly;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import java.util.Arrays;
import java.util.List;
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
	

	/**
	 * Find the index of the 3rd (target) search result which doesn't include video
	 * 
	 * @param driver
	 * @param target
	 * @return the index of the single search result
	 */
	private int findTheLinkIndexAndSkipVideo(WebDriver driver, int target) throws Exception {

		// Xpath for all listed regular search results. doesn't include ads
		String xpathOfFullList = "//*[@id=\"web\"]/ol/li";
		String skipWord = "Video Results";
		int count = 0;
		int index = 0;

		List<WebElement> allSearchResults = driver.findElements(By.xpath(xpathOfFullList));
		if (allSearchResults == null || allSearchResults.size() < target)
			throw new NotEnoughResultException();
		else {
			for (WebElement Element : allSearchResults) {
				index++;
				System.out.print(count + "=====" + Element.getText() + "\n");
				if (!Element.getText().contains(skipWord)) {
					if (++count >= target) {
						break;
					}
				}
				// else continue
				else {
					System.out.print("SKIP It \n");
				}
			}
		}
		return index;
	}

	private class NotEnoughResultException extends Exception {
		public NotEnoughResultException() {
			super();
		}
	}	
	
	private static List<String> givenFirstTermList = Arrays.asList("red", "green", "blue");
	private static List<String> givenLastTermList = Arrays.asList("grass", "the rainbow", "a turtle", "a unicorn");
	
	/**
	 * Generate a search query from terms
	 * 
	 * @return a search string
	 */
	private String generateSearchString() {
		String searchString = "";
		String searchString1 = getRandomElement(givenFirstTermList);
		String searchString2 = " is the color of ";
		String searchString3 = getRandomElement(givenLastTermList);

		searchString = searchString1 + searchString2 + searchString3;

		return searchString;
	}

	/**
	 * For debugging only
	 * 
	 * @return a hard code string for debugging
	 */
	private String getHardCodedSearchString() {
		return "red is the color of the rainbow";
	}
}