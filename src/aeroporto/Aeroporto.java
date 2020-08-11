package aeroporto;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import aeronaves.Aeronave;
import aeronaves.AeronaveCarga;
import aeronaves.AeronaveMedica;
import aeronaves.AeronavePassageiros;
import aeronaves.ComparadorAeronaves;

public class Aeroporto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5876982398597606975L;
	private Pista pista1;
	private Pista pista2;
	private Queue<Aeronave> aeronaves;
		
	public Aeroporto(){
		pista1 = new Pista(1);
		pista2 = new Pista(2);
		aeronaves = new PriorityQueue<Aeronave>(new ComparadorAeronaves());
	}
	
	public Pista getPista1() {
		return pista1;
	}
	
	public Pista getPista2() {
		return pista2;
	}
	
	public Queue<Aeronave> getAeronaves() {
		return aeronaves;
	}
	
	public synchronized Aeronave obterProximoAeronave() {
		while(aeronaves.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("Thread " + Thread.currentThread().getId() + " Interrupted!");
			}
		}
		Aeronave aeronave = aeronaves.remove();
		notifyAll();
		return aeronave;
	}
	
	public synchronized void colocarNovoAeronave(Aeronave aeronave) {
		this.aeronaves.add(aeronave);
	}

	public synchronized void colocarNovoAeronaves(List<Aeronave> aeronaves) {
		this.aeronaves.addAll(aeronaves);
	}

	public static List<Aeronave> lerFicheiro(String filename){
		List<Aeronave> aeronaves = new ArrayList<Aeronave>();
		try {
			@SuppressWarnings("resource")
			Scanner s = new Scanner(new File(filename));
			while(s.hasNextLine()){
				String[] linha = s.nextLine().split(" ");
				String id = linha[0];
				int deposito_capacidade = Integer.parseInt(linha[1]);
				int deposito_atual = Integer.parseInt(linha[2]);
				int consumo_hora = Integer.parseInt(linha[3]);
				
				if(id.charAt(0) == 'U'){
					Aeronave novaAeronave = new AeronaveMedica(id, deposito_capacidade, deposito_atual, consumo_hora);
					aeronaves.add(novaAeronave);
					
				}else if (id.charAt(0) == 'C'){
					int tonelagem = Integer.parseInt(linha[4]);
					Aeronave novaAeronave = new AeronaveCarga(id, deposito_capacidade, deposito_atual, consumo_hora, tonelagem);
					aeronaves.add(novaAeronave);
					
				}else if (id.charAt(0) == 'P'){
					int lotacaoMaxima = Integer.parseInt(linha[4]);
					Aeronave novaAeronave = new AeronavePassageiros(id, deposito_capacidade, deposito_atual, consumo_hora, lotacaoMaxima);
					aeronaves.add(novaAeronave);
					
				}else{
					throw new IllegalArgumentException("O TIPO DE AERONAVE NAO EXISTE!");
				}
			}
			s.close();
		} catch (Exception e) {
			System.err.println("O ficheiro nao existe ou esta mal configurado!");
			e.printStackTrace();
		}
		return aeronaves;
	}

}
