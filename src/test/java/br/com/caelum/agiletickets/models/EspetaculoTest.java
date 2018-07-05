package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}
	
	/* Testes. */
	@Test
	public void deveCriarApenasUmaSessaoQuandoOInicioForIgualAoFim() {
		// ARRANGE
		LocalDate inicio = new LocalDate(2018, 7, 5);
		LocalDate fim = inicio;
		LocalTime horario = new LocalTime(20, 0);
		Periodicidade periodicidade = Periodicidade.DIARIA;
		Espetaculo espetaculo = new Espetaculo();
		
		// ACT
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		// ASSERT
		Assert.assertNotNull("Sessões não devem ser nulas", sessoes);
		Assert.assertEquals(1, sessoes.size());
		
		Sessao sessao = sessoes.get(0);
		Assert.assertEquals("05/07/18", sessao.getDia());
		Assert.assertEquals("20:00", sessao.getHora());
		Assert.assertEquals(espetaculo, sessao.getEspetaculo());
	}
	
	@Test
	public void deveCriarTresEspetaculosComPeriodicidadeSemanal() {
		// ARRANGE
		int i;
		LocalDate inicio = new LocalDate(2018, 7, 9);
		LocalDate fim = new LocalDate(2018, 7, 23);
		LocalTime horario = new LocalTime(17, 0);
		Periodicidade periodicidade = Periodicidade.SEMANAL;
		Espetaculo espetaculo = new Espetaculo();
		
		// ACT
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		// ASSERT
		Assert.assertNotNull("Sessões não devem ser nulas", sessoes);
		Assert.assertEquals(3, sessoes.size());
		Assert.assertEquals("09/07/18", sessoes.get(0).getDia());
		Assert.assertEquals("16/07/18", sessoes.get(1).getDia());
		Assert.assertEquals("23/07/18", sessoes.get(2).getDia());
		for(i = 0; i < 3; ++i) {
			Assert.assertEquals("17:00", sessoes.get(i).getHora());
			Assert.assertEquals(espetaculo, sessoes.get(i).getEspetaculo());
		}
	}
	
	@Test(expected = Exception.class)
	public void deveReclamarQuandoInicioForPosteriorAoFim() {
		LocalDate inicio = new LocalDate(2018, 7, 23);
		LocalDate fim = new LocalDate(2018, 7, 9);
		LocalTime horario = new LocalTime(17, 0);
		Periodicidade periodicidade = Periodicidade.SEMANAL;
		Espetaculo espetaculo = new Espetaculo();	
		
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);

		Assert.assertNotNull("Sessões não devem ser nulas", sessoes);
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
}
