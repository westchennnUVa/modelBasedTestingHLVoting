package test;

import org.junit.Before;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;       // for chrome

/**
 *
 * Note:
 *   Test environment: Chrome Version 68, selenium 3.14.0, Java 8, ChromeDriver 2.42
 */

public class TestHLVotingSelenium
{
    private WebDriver driver;
    private String url = "http://www.google.com";

    @Before
    public void setUp()
    {
        System.setProperty("webdriver.chrome.driver", "/Users/WestChen/IdeaProjects/HLVoting/src/tools/chromedriver");    // specify path the the driver
        driver = new ChromeDriver();    // create an instance of the web browser and open it
        driver.get(url);                // open the given url
    }

    @After
    public void teardown()
    {
        driver.quit();                  // close the browser
    }

    @Test
    public void test_openURL()
    {
        assertEquals(driver.getTitle(), "Measurement Conversion");	// check if we are on the right page
    }








}

