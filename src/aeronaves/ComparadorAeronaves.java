package aeronaves;

import java.io.Serializable;
import java.util.Comparator;

public class ComparadorAeronaves implements Serializable, Comparator<Aeronave>{

	@Override
	public int compare(Aeronave o1, Aeronave o2) {
		//  primeira prioridade 
		if(o1.getPercentagemDeposito() < 10 && o2.getPercentagemDeposito() < 10){
			return o1.getPercentagemDeposito() - o2.getPercentagemDeposito();
		}
		if(o1.getPercentagemDeposito() < 10 && o2.getPercentagemDeposito() >= 10){
			return -1;
		}
		if(o1.getPercentagemDeposito() >= 10 && o2.getPercentagemDeposito() < 10){
			return 1;
		}
		// segunda prioridade
		if(o1.isMedica() && !o2.isMedica()){
			return -1;
		}
		if(!o1.isMedica() && o2.isMedica()){
			return 1;
		}
		return 0;
	}	

	
	
}
