package aeroporto;

import aeronaves.Aeronave;
import gui.JanelaPrincipal;
import rede.ServidorGestorLigacoes;

public class GestorPista extends Thread{

	private JanelaPrincipal janela;
	private Aeroporto aeroporto;
	private Pista pista;
	private ServidorGestorLigacoes gestorLigacoes;
	
	public GestorPista(Aeroporto aeroporto, Pista pista, JanelaPrincipal janela, ServidorGestorLigacoes gestorLigacoes) {
		this.aeroporto = aeroporto;
		this.pista = pista;
		this.janela = janela;
		this.gestorLigacoes = gestorLigacoes;
	}
	
	@Override
	public void run() {

		System.out.println("A " + pista + " foi aberta!");
		while(pista.estaAberta()){
			Aeronave aeronave = aeroporto.obterProximoAeronave();
			pista.setAeronave(aeronave);
			janela.update(aeroporto);
			gestorLigacoes.atualizarClientes();
			System.out.println(pista + " > " + aeronave);
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			pista.removeAeronave();
			janela.update(aeroporto);
			gestorLigacoes.atualizarClientes();
		}
		System.out.println("A " + pista + " foi fechada!");
	}
	
}
