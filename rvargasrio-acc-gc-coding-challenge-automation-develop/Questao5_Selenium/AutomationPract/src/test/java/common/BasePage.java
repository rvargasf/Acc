package common;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

	public static final String URL = "http://automationpractice.com/index.php?controller=contact";
	private static WebDriver driver;

	public void iniciarWebDriver() {
		if (driver == null) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
	}

	public void goToUrl() {
		driver.get(URL);
	}

	public void aguardarVisibilidadeElemento(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
	}

	public void escrever(By by, String value) {
		try {
			aguardarVisibilidadeElemento((driver.findElement(by)));
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(value);
		} catch (WebDriverException e) {
			Assert.fail("Elemento não localizado.");
		}
	}

	public void validarTitulo(String esperado) {
		String title = driver.getTitle();
		Assert.assertEquals(esperado, title);
	}

	public String obterTexto(By by) {
		if (!elementoVisivel(by)) {
			Assert.fail("Texto não localizado.");
		}
		return driver.findElement(by).getText();
	}

	public void click(By by) {
		try {
			aguardarVisibilidadeElemento((driver.findElement(by)));
			driver.findElement(by).click();
		} catch (Exception e) {
			Assert.fail("Elemento não localizado.");
		}
	}

	public boolean elementoVisivel(By by) {
		try {
			return driver.findElement(by).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void selecionarCombo(String id, String valor) {
		WebElement element = driver.findElement(By.id(id));
		Select combo = new Select(element);
		combo.selectByVisibleText(valor);
	}

	public void encerrarWebDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	public void aguardarCarregamentoPagina() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			// Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout durante o carregamento da página.");
		}
	}
}