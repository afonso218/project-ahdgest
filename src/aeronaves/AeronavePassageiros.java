package aeronaves;

public class AeronavePassageiros extends Aeronave{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3588917828480727397L;
	private int lotacaoMaxima;

	public AeronavePassageiros(String id, int deposito_capacidade, int deposito_atual, int consumo_hora, int lotacaoMaxima) {
		super(id, deposito_capacidade, deposito_atual, consumo_hora);
		this.lotacaoMaxima = lotacaoMaxima;
	}

	public boolean isMedica(){
		return false;
	}
	
	public int getLotacaoMaxima() {
		return lotacaoMaxima;
	}

	@Override
	public String toString() {
		return "P " + super.toString();
	}
}
