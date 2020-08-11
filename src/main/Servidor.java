package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import aeronaves.Aeronave;
import aeroporto.Aeroporto;
import aeroporto.GestorPista;
import aeroporto.Pista;
import gui.JanelaPrincipal;
import rede.ServidorGestorLigacoes;

public class Servidor{
	
	private static final int X = 500;
	private static final int Y = 500;
	
	private JFrame frame;
	private JanelaPrincipal janela;
	private Aeroporto aeroporto;
	private GestorPista gestorPista1;	
	private GestorPista gestorPista2;	
	private ServidorGestorLigacoes gestorLigacoes;
	
	public Servidor(Aeroporto aeroporto){
		this.aeroporto = aeroporto;
		frame = new JFrame("Servidor do Aeroporto");
		frame.setSize(X,Y);
		frame.setLocation(
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - X/2),
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2 - Y/2)
				);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		janela = new JanelaPrincipal(aeroporto);
		frame.add(janela, BorderLayout.CENTER);
		frame.add(configurarPainelPistasEmFuncionamento(), BorderLayout.SOUTH);
		janela.update(aeroporto);
		gestorLigacoes = new ServidorGestorLigacoes(this);
	}

	public Aeroporto getAeroporto() {
		return aeroporto;
	}
	
	public void update(List<Aeronave> lista){
		aeroporto.colocarNovoAeronaves(lista);
		janela.update(aeroporto);
	}
	
	private JPanel configurarPainelPistasEmFuncionamento() {
		JPanel painelBaixo = new JPanel();
		JLabel pistasEmFuncionamento = new JLabel("Pistas em funcionamento:");
		JButton button_pista1 = new JButton(aeroporto.getPista1().toString());
		button_pista1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Pista pista = aeroporto.getPista1();
				pista.alterar();
				janela.updatePistasEmFuncionamento();
				if(pista.estaAberta()){
					gestorPista1 = new GestorPista(aeroporto, pista, janela, gestorLigacoes);
					gestorPista1.start();
				}
			}
		});
		JButton button_pista2 = new JButton(aeroporto.getPista2().toString());
		button_pista2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Pista pista = aeroporto.getPista2();
				pista.alterar();
				janela.updatePistasEmFuncionamento();
				if(pista.estaAberta()){
					gestorPista2 = new GestorPista(aeroporto, pista, janela, gestorLigacoes);
					gestorPista2.start();
				}
			}
		});
		painelBaixo.setLayout(new GridLayout(3, 1));
		painelBaixo.add(pistasEmFuncionamento);
		
		painelBaixo.add(button_pista1);
		painelBaixo.add(button_pista2);
		return painelBaixo;
	}

	public void start(){
		gestorLigacoes.start();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
	
		Aeroporto aeroporto = new Aeroporto();
		if(args.length > 0){
			List<Aeronave> lista = Aeroporto.lerFicheiro(args[0]);
			aeroporto.colocarNovoAeronaves(lista);
		}else{
			List<Aeronave> lista = Aeroporto.lerFicheiro("src/config.txt");
			aeroporto.colocarNovoAeronaves(lista);
		}

		Servidor servidor = new Servidor(aeroporto);
		servidor.start();
		
	}
	
}
