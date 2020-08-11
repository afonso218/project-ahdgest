package aeronaves;

public class AeronaveCarga extends Aeronave{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5271479458910768999L;
	private int tonelagem;

	public AeronaveCarga(String id, int deposito_capacidade, int deposito_atual, int consumo_hora, int tonelagem) {
		super(id, deposito_capacidade, deposito_atual, consumo_hora);
		this.tonelagem = tonelagem;
	}
	
	public boolean isMedica(){
		return false;
	}

	public int getTonelagem() {
		return tonelagem;
	}

	@Override
	public String toString() {
		return "C " + super.toString();
	}
}
