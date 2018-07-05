package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		
		BigDecimal preco = CalculadoraDePrecos.calculo(sessao);

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	public static BigDecimal calculo(Sessao sessao) {
		BigDecimal preco = CalculadoraDePrecos.reajusteOcupacao(sessao);
		return CalculadoraDePrecos.reajusteDuracao(sessao, preco);
	}
	
	public static BigDecimal reajusteDuracao(Sessao sessao, BigDecimal preco) {
		TipoDeEspetaculo tipo = sessao.getEspetaculo().getTipo();
		boolean reajuste = (sessao.getDuracaoEmMinutos() != null) && (sessao.getDuracaoEmMinutos() > tipo.getLimiteDuracao());
		BigDecimal valorReajuste = reajuste ? sessao.getPreco().multiply(BigDecimal.valueOf(tipo.getAcrescimoDuracao())) : BigDecimal.ZERO;
		return preco.add(valorReajuste);
	}

	public static BigDecimal reajusteOcupacao(Sessao sessao) {
		TipoDeEspetaculo tipo = sessao.getEspetaculo().getTipo();
		boolean reajuste = ((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue()) <= tipo.getPercentagemVazios();
		BigDecimal valorReajuste = reajuste ? sessao.getPreco().multiply(BigDecimal.valueOf(tipo.getAcrescimoVazios())) : BigDecimal.ZERO;
		return sessao.getPreco().add(valorReajuste);
	}
}