package io.railflow.demo;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.railflow.annotations.CustomField;
import io.railflow.annotations.Railflow;
import io.railflow.annotations.testng.CurrentTest;
import io.railflow.annotations.testng.RailflowReporter;

/**
 * Pizza tests.
 * 
 * @author Sergey Oplavin
 */
@Railflow(title = "Pizza tests", caseType = "Railflow", casePriority = "High", caseFields = {
		@CustomField(name = "Required text field", value = "Hello from Railflow"),
		@CustomField(name = "estimate", value = "42s") }, resultFields = {
				@CustomField(name = "Custom field", value = "Results from Railflow"),
				@CustomField(name = "version", value = "1.0") }, smartFailureAssignment = "user1@yourcompany.com")
@Listeners(RailflowReporter.class)
public class PizzaTest {
	private static final String GOOGLE = "https://google.com";
	private final RemoteWebDriver webDriver = new ChromeDriver(new ChromeOptions().addArguments("--start-fullscreen"));

	@BeforeClass
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver", new File("chromedriver.exe").getAbsolutePath());
	}

	@Railflow(title = "Find some pizzas")
	@Test
	public void find_some_pizzas() {
		this.webDriver.navigate().to(GOOGLE);
		final List<WebElement> results = getGoogleResults("pizza");
		assertTrue("Cannot find any pizzas", results.size() > 0);
	}

	@Railflow(testrailIds = 13390)
	@Test
	public void there_are_no_bad_pizzas() {
		this.webDriver.navigate().to(GOOGLE);
		final List<WebElement> results = getGoogleResults("bad pizza");
		assertEquals(results.size(), 0, "Liar! There are no bad pizzas!!!");
	}

	@AfterMethod
	public void afterMethod(final ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			final byte[] screenshot = this.webDriver.getScreenshotAs(OutputType.BYTES);
			CurrentTest.addAttachment(result.getName() + "_failure.png", screenshot, result);
		}
	}

	@AfterTest
	public void afterTest() {
		this.webDriver.quit();
	}

	private List<WebElement> getGoogleResults(final String query) {
		final WebElement element = this.webDriver.findElement(By.name("q"));
		element.sendKeys(query);
		element.submit();
		new WebDriverWait(this.webDriver, 1)
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("search")));
		return this.webDriver.findElements(By.xpath("//*[@id='rso']//*[@class='g']//a"));
	}
}
