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
		BASE_URL = "http://ec2-3-86-195-62.compute-1.amazonaws.com:8080";
		System.setProperty(driver, driverPath);

		webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		webDriver.get(BASE_URL+"/index.html");
	}

	@Test
	public void a_testWrongPwd() {
		webDriver.findElement(By.id("username")).sendKeys("admin1");
		webDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		webDriver.findElement(By.id("password")).sendKeys("admin1");
		delay();
		webDriver.findElement(By.className("login_btn")).click();
		Assert.assertEquals(BASE_URL+"/index.html", webDriver.getCurrentUrl());
	}

	@Test
	public void b_testLogin() {
		webDriver.findElement(By.id("username")).clear();
		webDriver.findElement(By.id("username")).sendKeys("admin");
		webDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		webDriver.findElement(By.id("password")).clear();
		webDriver.findElement(By.id("password")).sendKeys("admin");
		delay();
		webDriver.findElement(By.className("login_btn")).click();

		List<WebElement> webElements = webDriver.findElements(By.className("card-header"));
		List<String> headers = Arrays.asList("Savings", "Expenditure");
		for (WebElement we : webElements) {
			assert (headers.contains(we.getText()));
		}
	}
	
	@Test
	public void c_testSideMenu() {
		delay();
		webDriver.findElement(By.className("hamb-top")).click();
		Assert.assertEquals("toggled", webDriver.findElement(By.id("wrapper")).getAttribute("class"));
	}
	
	/*@Test
	public void onClickDashboard4() {
		webDriver.findElement(By.className("hamb-top")).click();
		Assert.assertEquals("", webDriver.findElement(By.id("wrapper")).getAttribute("class"));
	}*/
	
	@Test
	public void d_testSideMenuGoal() {
		delay();
		webDriver.findElement(By.linkText("Goals")).click();
		Assert.assertEquals(BASE_URL+"/main.html#goals", webDriver.getCurrentUrl());
	}
	
	@Test
	public void e_testSideMenuTransactions() {
		webDriver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
		webDriver.findElement(By.className("hamburger")).click();
		webDriver.findElement(By.linkText("Transactions")).click();
		Assert.assertEquals(BASE_URL+"/main.html#transactions", webDriver.getCurrentUrl());
	}
		
	@Test
	public void f_testSideMenuAnalysis() {
		delay();
		webDriver.findElement(By.className("hamburger")).click();
		webDriver.findElement(By.linkText("Analysis")).click();
		Assert.assertEquals(BASE_URL+"/main.html#analysis", webDriver.getCurrentUrl());
	}
	
	@Test
	public void g_testSideMenuMyProfile() {
		delay();
		webDriver.findElement(By.className("hamburger")).click();
		webDriver.findElement(By.linkText("My Profile")).click();
		Assert.assertEquals(BASE_URL+"/main.html#profile", webDriver.getCurrentUrl());
	}
	
	@Test
	public void h_testSideMenuLogout() {
		delay();
		webDriver.findElement(By.className("hamburger")).click();
		webDriver.findElement(By.linkText("Log Out")).click();
		Assert.assertEquals(BASE_URL+"/index.html", webDriver.getCurrentUrl());
	}

	private void delay() {
		try {
			Thread.sleep(1*1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
}