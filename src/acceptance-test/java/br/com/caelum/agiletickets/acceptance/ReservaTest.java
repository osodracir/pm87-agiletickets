package br.com.caelum.agiletickets.acceptance;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.caelum.agiletickets.acceptance.page.EstabelecimentosPage;
import br.com.caelum.agiletickets.acceptance.page.ReservaPage;

public class ReservaTest {
	public static String BASE_URL = "http://localhost:8080";
	private static WebDriver browser;
	private ReservaPage reservas;

	@BeforeClass
	public static void abreBrowser() {
		System.setProperty("webdriver.gecko.driver", "geckodriver");
		browser = new FirefoxDriver();
	}

	@Before
	public void setUp() throws Exception {
		reservas = new ReservaPage(browser);
	}

	@AfterClass
	public static void teardown() {
		browser.close();
	}

	@Test
	public void ingressoPrecoReajustadoQuandoNaReservaLimiarVaziosUltrapassado() {
		this.reservas.abreListagem();
		this.reservas.reservarIngressos("1");
		this.reservas.mensagemDeveSer("Sessão reservada com sucesso por R$ 55,00");
	}
}