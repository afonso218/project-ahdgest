package aeroporto;

import java.io.Serializable;

import aeronaves.Aeronave;

public class Pista implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3897920323081574184L;
	private final int id;
	private boolean aberta;
	private Aeronave aeronave;
	
	public Pista(int id){
		this.id = id;
		aberta = false;
	}
	
	public boolean estaAberta(){
		return aberta;
	}
	
	public boolean estaLivre(){
		return aeronave == null;
	}
	
	public Aeronave getAeronave() {
		return aeronave;
	}
	
	public void setAeronave(Aeronave aeronave) {
		this.aeronave = aeronave;
	}

	public void removeAeronave() {
		aeronave = null;
	}
	
	public void alterar(){
		aberta = !aberta;
	}
	
	@Override
	public String toString() {
		return "Pista " + id;
	}

	
}
