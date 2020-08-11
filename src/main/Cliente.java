package main;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;

import aeronaves.Aeronave;
import aeroporto.Aeroporto;
import gui.JanelaPrincipal;
import rede.ClienteGestorLigacoes;

public class Cliente{

	private static final int X = 500;
	private static final int Y = 400;
	
	private JFrame frame;
	private JanelaPrincipal janela;
	private ClienteGestorLigacoes gestorLigacoes;
	
	public Cliente(Aeroporto aeroporto, List<Aeronave> aeronavesCliente){
		frame = new JFrame("Cliente do Aeroporto");
		frame.setSize(X,Y);
		frame.setLocation(
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - X/2),
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2 - Y/2)
				);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLayout(new BorderLayout());
		janela = new JanelaPrincipal(aeroporto);
		janela.update(aeroporto);
		frame.add(janela, BorderLayout.CENTER);
		gestorLigacoes = new ClienteGestorLigacoes(this, aeronavesCliente);
	}

	public void updateAeroporto(Aeroporto aeroporto){
		janela.update(aeroporto);
		System.out.println("Aeroporto atualizado...");
	}
	
	public void start(){
		gestorLigacoes.start();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		Aeroporto aeroporto = new Aeroporto();
		List<Aeronave> aeronavesCliente;
		if(args.length > 0){		
			aeronavesCliente = Aeroporto.lerFicheiro(args[0]);
		}else{
			aeronavesCliente = Aeroporto.lerFicheiro("src/configClient.txt");
		}
		
		Cliente c = new Cliente(aeroporto,aeronavesCliente);
		c.start();
	}
	
}
