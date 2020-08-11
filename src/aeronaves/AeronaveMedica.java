package aeronaves;

public class AeronaveMedica extends Aeronave{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3834433172966479756L;

	public AeronaveMedica(String id, int deposito_capacidade, int deposito_atual, int consumo_hora) {
		super(id, deposito_capacidade, deposito_atual, consumo_hora);
	}
	
	public boolean isMedica(){
		return true;
	}

	@Override
	public String toString() {
		return "U " + super.toString();
	}
	
}
