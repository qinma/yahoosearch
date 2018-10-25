package com.oreilly;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.TakesScreenshot;

public class SearchTest01 extends helper {

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

		String searchString = generateSearchString();
		// String searchString = getHardCodedSearchString();
		System.out.println("generated search string is: " + searchString);

		// Submit search string
		element.sendKeys(searchString);
		element.submit();

		// find the 3rd link in the list
		String oneSearchResultXpath = "//*[@id=\"web\"]/ol/li[" + findTheLinkIndexAndSkipVideo(driver, 3)
				+ "]/div/div[1]/h3/a";
		WebElement oneSearchResult = driver.findElement(By.xpath(oneSearchResultXpath));
		// WebElement oneSearchResult =
		// driver.findElement(By.xpath("//*[@id=\"web\"]/ol/li[4]/div/div[1]/h3/a"));

		System.out.print("oneSearchResult +++++++ " + oneSearchResult.getText());

		oneSearchResult.click();

		// get parent window
		String parentWindow = driver.getWindowHandle();

		// find the child window
		Set<String> handles = driver.getWindowHandles();
		for (String windowHandle : handles) {
			if (!windowHandle.equals(parentWindow)) {
				driver.switchTo().window(windowHandle);

				// take screenshot and save
				this.takeSnapShot(driver, outputFileLocation);

				// close child window
				driver.close();

				driver.switchTo().window(parentWindow);
			}
		}
	}

	/**
	 * Find the index of the 3rd (target) search result which doesn't include video
	 * 
	 * @param webdriver
	 * @param           int
	 * @return the index of the single search result
	 */
	public int findTheLinkIndexAndSkipVideo(WebDriver driver, int target) throws Exception {

		// xpath for all listed regular search results. doesn't include ads
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

	public class NotEnoughResultException extends Exception {
		public NotEnoughResultException() {
			super();
		}
	}

	/**
	 * Take a snapshot on given page and save at destination file location
	 * 
	 * @param webdriver
	 * @param fileWithPath
	 * @throws Exception
	 */
	public void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {

		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);
	}

	List<String> givenFirstTermList = Arrays.asList("red", "green", "blue");
	List<String> givenLastTermList = Arrays.asList("grass", "the rainbow", "a turtle", "a unicorn");

	/**
	 * Generate a search query from terms
	 * 
	 * @return a search string
	 */
	public String generateSearchString() {
		String searchString = "";
		String searchString1 = getRandomElement(givenFirstTermList);
		String searchString2 = " is the color of ";
		String searchString3 = getRandomElement(givenLastTermList);

		searchString = searchString1 + searchString2 + searchString3;

		return searchString;
	}

	/**
	 * Return a random chosen element from given list
	 * 
	 * @param givenList
	 * @return random element
	 */
	public String getRandomElement(List<String> givenList) {
		Random rand = new Random();
		String randomElement = givenList.get(rand.nextInt(givenList.size()));
		return randomElement;
	}
}