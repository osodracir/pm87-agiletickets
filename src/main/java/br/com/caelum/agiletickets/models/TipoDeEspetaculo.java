package br.com.caelum.agiletickets.models;

public enum TipoDeEspetaculo {
	
	/* Conforme apresentado em bit.ly/pm87-refatoracao[-2] */
	CINEMA(0.05, 0.10, 0, 0),
	SHOW(0.05, 0.10, 0, 0),
	TEATRO(0, 0, 0, 0),
	BALLET(0.50, 0.20, 60, 0.10),
	ORQUESTRA(0.50, 0.20, 60, 0.10);
	
	private double percentagemVazios;
	
	private double acrescimoVazios;
	
	private int limiteDuracao;
	
	private double acrescimoDuracao;
	
	private TipoDeEspetaculo(double percentagemVazios, double acrescimoVazios, int limiteDuracao, double acrescimoDuracao) {
		this.percentagemVazios = percentagemVazios;
		this.acrescimoVazios = acrescimoVazios;
		this.limiteDuracao = limiteDuracao;
		this.acrescimoDuracao = acrescimoDuracao;
	}

	public double getPercentagemVazios() {
		return percentagemVazios;
	}

	public double getAcrescimoVazios() {
		return acrescimoVazios;
	}

	public int getLimiteDuracao() {
		return limiteDuracao;
	}

	public double getAcrescimoDuracao() {
		return acrescimoDuracao;
	}
}
