package com.oreilly;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 * This class contains common aspects of all tests
 *
 */
public class Base {

	List<String> givenFirstTermList = Arrays.asList("red", "green", "blue");
	List<String> givenLastTermList = Arrays.asList("grass", "the rainbow", "a turtle", "a unicorn");

	/**
	 * Find the index of the 3rd (target) search result which doesn't include video
	 * 
	 * @param webdriver
	 * @param           int
	 * @return the index of the single search result
	 */
	protected int findTheLinkIndexAndSkipVideo(WebDriver driver, int target) throws Exception {

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

	protected class NotEnoughResultException extends Exception {
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
	protected void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {

		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);
	}

	/**
	 * Generate a search query from terms
	 * 
	 * @return a search string
	 */
	protected String generateSearchString() {
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
	protected String getHardCodedSearchString() {
		return "red is the color of the rainbow";
	}

	/**
	 * Return a random chosen element from given list
	 * 
	 * @param givenList
	 * @return random element
	 */
	protected String getRandomElement(List<String> givenList) {
		Random rand = new Random();
		String randomElement = givenList.get(rand.nextInt(givenList.size()));
		return randomElement;
	}
}
