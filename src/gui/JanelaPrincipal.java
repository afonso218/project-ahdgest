package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import aeronaves.Aeronave;
import aeroporto.Aeroporto;
import aeroporto.Pista;

public class JanelaPrincipal extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2029786898641751900L;
	private JList<Aeronave> jList;
	private JTextField textPista1;
	private JTextField textPista2;
	private JCheckBox checkPista1;
	private JCheckBox checkPista2;
	
	private Aeroporto aeroporto;
	
	public JanelaPrincipal(Aeroporto aeroporto){
		this.aeroporto = aeroporto;
		setLayout(new GridLayout(1,2));
		addPainelAvioesEmEspera();
		addPainelPistas();
	}
	
	private void addPainelAvioesEmEspera() {
		
		JPanel painelDireita = new JPanel();
		JLabel avioesEmEsperaTexto = new JLabel("Avioes em espera:");
		jList = new JList<Aeronave>();
				
		painelDireita.setLayout(new BorderLayout());
		painelDireita.add(avioesEmEsperaTexto, BorderLayout.NORTH);
		painelDireita.add(jList, BorderLayout.CENTER);
		
		jList.setEnabled(false);
		add(painelDireita, BorderLayout.WEST);
	}

	private void addPainelPistas() {
		
		JPanel painelEsquerda = new JPanel();
		painelEsquerda.setLayout(new GridLayout(7,1));
		
		painelEsquerda.add(new JLabel("Pistas"));
		
		Pista pista1 = aeroporto.getPista1();
		painelEsquerda.add(new JLabel(pista1.toString()));
		if(pista1.estaLivre()){
			textPista1 = new JTextField(" - ");
		}else{
			textPista1 = new JTextField(pista1.getAeronave().toString());
		}
		textPista1.setEditable(false);
		painelEsquerda.add(textPista1);
		checkPista1 = new JCheckBox("aberta");
		checkPista1.setEnabled(false);
		checkPista1.setSelected(pista1.estaAberta());
		painelEsquerda.add(checkPista1);	
		
		Pista pista2 = aeroporto.getPista2();
		painelEsquerda.add(new JLabel(pista2.toString()));
		if(pista2.estaLivre()){
			textPista2 = new JTextField(" - ");
		}else{
			textPista2 = new JTextField(pista2.getAeronave().toString());
		}
		textPista2.setEditable(false);
		painelEsquerda.add(textPista2);
		checkPista2 = new JCheckBox("aberta");
		checkPista2.setEnabled(false);
		checkPista2.setSelected(pista2.estaAberta());
		painelEsquerda.add(checkPista2);
		
		add(painelEsquerda, BorderLayout.EAST);
	}
	
	public void update(Aeroporto aeroporto){
		this.aeroporto = aeroporto;
		updateAvioesEmEspera();
		updatePistasEmFuncionamento();
	}
	
	public void updatePistasEmFuncionamento() {
		Pista pista1 = aeroporto.getPista1();
		if(pista1.estaLivre()){
			textPista1.setText(" - ");
		}else{
			textPista1.setText(pista1.getAeronave().toString());
		}
		checkPista1.setSelected(pista1.estaAberta());
		
		Pista pista2 = aeroporto.getPista2();
		if(pista2.estaLivre()){
			textPista2.setText(" - ");
		}else{
			textPista2.setText(pista2.getAeronave().toString());
		}
		checkPista2.setSelected(pista2.estaAberta());
	}

	public void updateAvioesEmEspera(){
		DefaultListModel<Aeronave> model = new DefaultListModel<Aeronave>();
		for(Aeronave a : aeroporto.getAeronaves()){
			model.addElement(a);
		}
		jList.setModel(model);
	}

}
