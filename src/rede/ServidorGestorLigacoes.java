package rede;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import aeronaves.Aeronave;
import main.Servidor;

public class ServidorGestorLigacoes extends Thread{

	public static final int PORTO = 8080;
	
	private Servidor servidor;
	private ServerSocket serverSocket;
	private List<LigacaoCliente> clientes;
	
	public ServidorGestorLigacoes(Servidor servidor) {
		this.servidor = servidor;
		try {
			serverSocket = new ServerSocket(PORTO);
		} catch (IOException e) {
			System.out.println("Não conseguiu inicializar o server socket!");
		}
		System.out.println("Lançou ServerSocket: " + serverSocket); 
		clientes = new ArrayList<LigacaoCliente>();
	}
	
	@Override
	public void run() {
		
		while(!interrupted()){
			Socket socket = null;
			try{
				socket = serverSocket.accept();
				System.out.println("Conexão aceite: " + socket);

				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				
				List<Aeronave> aeronaves = (List<Aeronave>) in.readObject();
				System.out.println("Recebeu pedido para... " + aeronaves);
				servidor.update(aeronaves);
				System.out.println("Aeroporto atualizado...");
				
				clientes.add(new LigacaoCliente(in, out));
				atualizarClientes();
				
			} catch (Exception e) {
				try {
					if(socket != null){
						socket.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void atualizarClientes() {
		Iterator<LigacaoCliente> it = clientes.iterator();
		int num = 0;
		while(it.hasNext()){
			LigacaoCliente c = it.next();
			try {
				c.getOut().writeObject(servidor.getAeroporto());
				c.getOut().flush();
				num++;
			} catch (IOException e) {
				System.out.println("Cliente desligou-se, removido da lista...");
				it.remove();
			}			
		}
		System.out.println("Enviei atualizacao do aeroporto para " + num + " clientes...");
	}

	class LigacaoCliente{
		
		private ObjectInputStream in;
		private ObjectOutputStream out;
		
		public LigacaoCliente(ObjectInputStream in, ObjectOutputStream out) {
			this.in = in;
			this.out = out;
		}

		public ObjectInputStream getIn() {
			return in;
		}
		
		public ObjectOutputStream getOut() {
			return out;
		}
	}
}
