package br.com.caelum.agiletickets.acceptance.page;

import java.util.function.Function;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReservaPage {

	private static final String BASE_URL = "http://localhost:8080";
	private final WebDriver driver;

	public ReservaPage(WebDriver driver) {
		this.driver = driver;
	}

	public void abreListagem() {
		driver.get(BASE_URL + "/sessao/169");
	}

	public void reservarIngressos(String quantidade) {
		WebElement form = form();
		this.driver.findElement(By.id("qtde")).sendKeys(quantidade);
		form.submit();
	}
	
	public void mensagemDeveSer(String mensagemEsperada) {
		// https://stackoverflow.com/questions/36590274/selenium-how-to-wait-until-page-is-completely-loaded#36592173
		new WebDriverWait(this.driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
		
		WebElement message = driver.findElement(By.id("message"));
		Assert.assertEquals(mensagemEsperada, message.getText());
	}

	private WebElement form() {
		return driver.findElements(By.tagName("form")).get(0);
	}
}
