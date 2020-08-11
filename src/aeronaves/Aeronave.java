package aeronaves;

import java.io.Serializable;

public abstract class Aeronave implements Serializable, DetalhesAeronave{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3790966303676925452L;
	private String id;
	private int deposito_capacidade;
	private int deposito_atual;
	private int consumo_hora;
	
	public Aeronave(String id, int deposito_capacidade, int deposito_atual, int consumo_hora){
		this.id = id;
		this.deposito_capacidade = deposito_capacidade;
		this.deposito_atual = deposito_atual;
		this.consumo_hora = consumo_hora;
	}
	
	public int getConsumo_hora() {
		return consumo_hora;
	}
	
	public String getId() {
		return id;
	}
	
	public int getDeposito_atual() {
		return deposito_atual;
	}
	
	public int getDeposito_capacidade() {
		return deposito_capacidade;
	}

	public int getPercentagemDeposito() {
		return deposito_atual*100/deposito_capacidade;
	}
	
	@Override
	public String toString() {
		return "[" + id + ", " + getPercentagemDeposito() + "]";
	}

}
