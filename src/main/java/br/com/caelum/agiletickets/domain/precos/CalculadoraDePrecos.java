package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;
import java.util.HashMap;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		
		TipoDeEspetaculo tipo = sessao.getEspetaculo().getTipo();
		BigDecimal preco = CalculoPreco.getInstancia(tipo).calculo(sessao);

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static abstract class CalculoPreco {
		
		private static HashMap<TipoDeEspetaculo, CalculoPreco> mapa = new HashMap<TipoDeEspetaculo, CalculoPreco>();
		
		protected double percentagemVazios;
		
		protected double acrescimoVazios;
		
		protected int duracao;
		
		protected double acrescimoDuracao;
		
		static {
			CalculoPreco.mapa.put(TipoDeEspetaculo.CINEMA, new CalculoPrecoCinema());
			CalculoPreco.mapa.put(TipoDeEspetaculo.SHOW, new CalculoPrecoCinema());
			CalculoPreco.mapa.put(TipoDeEspetaculo.BALLET, new CalculoPrecoBallet());
			CalculoPreco.mapa.put(TipoDeEspetaculo.ORQUESTRA, new CalculoPrecoOrquestra());
			CalculoPreco.mapa.put(TipoDeEspetaculo.TEATRO, new CalculoPrecoOutros());
		}
		
		public static CalculoPreco getInstancia(TipoDeEspetaculo tipo) {
			return CalculoPreco.mapa.get(tipo);
		}
				
		public BigDecimal calculo(Sessao sessao) {
			BigDecimal preco = this.reajusteOcupacao(sessao);
			return this.reajusteDuracao(sessao, preco);
		}
		
		public BigDecimal reajusteDuracao(Sessao sessao, BigDecimal preco) {
			boolean reajuste = (sessao.getDuracaoEmMinutos() != null) && (sessao.getDuracaoEmMinutos() > this.duracao);
			BigDecimal valorReajuste = reajuste ? sessao.getPreco().multiply(BigDecimal.valueOf(this.acrescimoDuracao)) : BigDecimal.ZERO;
			return preco.add(valorReajuste);
		}

		public BigDecimal reajusteOcupacao(Sessao sessao) {
			BigDecimal valorReajuste;
			boolean reajuste = ((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue()) <= this.percentagemVazios;
			
			valorReajuste = reajuste ? sessao.getPreco().multiply(BigDecimal.valueOf(this.acrescimoVazios)) : BigDecimal.ZERO;
			return sessao.getPreco().add(valorReajuste);
		}
		
		private static class CalculoPrecoCinema extends CalculoPreco {
			
			public CalculoPrecoCinema() {
				super();
				this.percentagemVazios = 0.05;
				this.acrescimoVazios = 0.10;
				this.duracao = 0;
				this.acrescimoDuracao = 0;
			}
		}

		private static class CalculoPrecoBallet extends CalculoPreco {

			public CalculoPrecoBallet() {
				super();
				this.percentagemVazios = 0.50;
				this.acrescimoVazios = 0.20;
				this.duracao = 60;
				this.acrescimoDuracao = 0.10;
			}
		}

		private static class CalculoPrecoOrquestra extends CalculoPreco {

			public CalculoPrecoOrquestra() {
				super();
				this.percentagemVazios = 0.50;
				this.acrescimoVazios = 0.20;
				this.duracao = 60;
				this.acrescimoDuracao = 0.10;
			}
		}
		
		private static class CalculoPrecoOutros extends CalculoPreco {

			public CalculoPrecoOutros() {
				super();
				this.percentagemVazios = 0;
				this.acrescimoVazios = 0;
				this.duracao = 0;
				this.acrescimoDuracao = 0;
			}
		}
	}
	
}