package rede;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import aeronaves.Aeronave;
import aeroporto.Aeroporto;
import main.Cliente;

public class ClienteGestorLigacoes extends Thread{

	private Cliente cliente;
	private List<Aeronave> aeronaves;
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public ClienteGestorLigacoes(Cliente cliente, List<Aeronave> aeronaves) {
		this.cliente = cliente;
		this.aeronaves = aeronaves;
	}

	@Override
	public void run() {
		while(!interrupted()){
			if(socket == null){
				estabeleceLigacao();
			}else{
				escutaLigacao();
			}
		}
	}

	private void estabeleceLigacao() {
		try{
			InetAddress endereco = InetAddress.getByName(null);
			System.out.println("Endereco = " + endereco);
			socket = new Socket(endereco, ServidorGestorLigacoes.PORTO);
			System.out.println("Socket = " + socket);
			
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
			System.out.println("Preparar envio...");
			out.writeObject(aeronaves);
			out.flush();
			System.out.println("Enviou aeronaves... " + aeronaves);
			
		}catch(IOException e){
			System.out.println("O servidor está em baixo...");
		}
	}

	private void escutaLigacao() {

		System.out.println("Estou a escuta...");
		while (!interrupted()){
			try {
				Aeroporto novoAeroporto = (Aeroporto) in.readObject();
				while(novoAeroporto == null){
					novoAeroporto = (Aeroporto) in.readObject();
				}
				System.out.println("Recebi um aeroporto... " + novoAeroporto.getAeronaves());
				cliente.updateAeroporto(novoAeroporto);
			} catch (EOFException e) {
				   // ... this is fine
			} catch (ClassNotFoundException e1){
				System.err.println("Class not found..");
			} catch (IOException e2){
				System.err.println("A ligacao nao esta estabelecida...");
			}
		}
	}

}
