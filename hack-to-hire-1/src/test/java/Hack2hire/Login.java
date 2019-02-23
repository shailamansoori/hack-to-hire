package Hack2hire;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Login {

	static String BASE_URL = null;
	static String driverPath = "C:\\Users\\Shaila\\git\\hack-to-hire\\hack-to-hire-1\\src\\main\\resources\\drivers\\chrome\\chromedriver.exe";
	static String driver = "webdriver.chrome.driver";

	static WebDriver webDriver = null;

	@BeforeClass
	public static void beforeClass() {
		BASE_URL = "http://ec2-3-86-195-62.compute-1.amazonaws.com:8080/index.html";
		System.setProperty(driver, driverPath);

		webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		webDriver.get(BASE_URL);
	}

	@Test
	public void a_testWrongPwd1() {
		webDriver.findElement(By.id("username")).sendKeys("admin1");
		webDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		webDriver.findElement(By.id("password")).sendKeys("admin1");
		webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		webDriver.findElement(By.className("login_btn")).click();
		Assert.assertEquals(BASE_URL, webDriver.getCurrentUrl());
	}

	@Test
	public void testLogin2() {
		webDriver.findElement(By.id("username")).clear();
		webDriver.findElement(By.id("username")).sendKeys("admin");
		webDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		webDriver.findElement(By.id("password")).clear();
		webDriver.findElement(By.id("password")).sendKeys("admin");
		webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		webDriver.findElement(By.className("login_btn")).click();

		List<WebElement> webElements = webDriver.findElements(By.className("card-header"));
		List<String> headers = Arrays.asList("Savings", "Expenditure");
		for (WebElement we : webElements) {
			assert (headers.contains(we.getText()));
		}
	}
	
	@Test
	public void testSideMenu3() {
		webDriver.findElement(By.className("hamb-top")).click();
		Assert.assertEquals("toggled", webDriver.findElement(By.id("wrapper")).getAttribute("class"));
	}
	
	@Test
	public void onClickDashboard4() {
		webDriver.findElement(By.className("hamb-top")).click();
		Assert.assertEquals("", webDriver.findElement(By.id("wrapper")).getAttribute("class"));
	}

}