package br.com.caelum.agiletickets.models;

import org.junit.Assert;
import org.junit.Test;

public class SessaoTest {

	@Test
	public void deveVenderIngressosSeQuantidadeDeVagasForSuperior() throws Exception {
		Sessao sessao = new Sessao();
        sessao.setTotalIngressos(2);

        Assert.assertTrue(sessao.podeReservar(1));
	}

	@Test
	public void deveVenderIngressosSeQuantidadeDeVagasForIgual() throws Exception {
		Sessao sessao = new Sessao();
        sessao.setTotalIngressos(2);

        Assert.assertTrue(sessao.podeReservar(2));
	}

	@Test
	public void naoDeveVenderIngressoSeQuantidadeDeVagasForInferior() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(2);

		Assert.assertFalse(sessao.podeReservar(3));
	}

	@Test
	public void reservarIngressosDeveDiminuirONumeroDeIngressosDisponiveis() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(5);

		sessao.reserva(3);
		Assert.assertEquals(2, sessao.getIngressosDisponiveis().intValue());
	}
	
}
