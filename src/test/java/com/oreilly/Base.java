package com.oreilly;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * 
 * This class contains common aspects of all tests
 *
 */
public class Base {

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
