package com.benbeehler.scds.core.instance;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class DBServer {

	private ServerSocket server;
	private ArrayList<Socket> clients = 
			new ArrayList<>();
	
	public DBServer(int port) {
		try {
			setServer(new ServerSocket(port));	
		} catch (IOException e) {
		}
	}
	
	public DataInputStream getInputStream(Socket socket) throws IOException {
		return new DataInputStream(socket.getInputStream());
	}
	
	public DataOutputStream getOutStream(Socket socket) throws IOException {
		return new DataOutputStream(socket.getOutputStream());
	}

	public ArrayList<Socket> getClients() {
		return clients;
	}

	public void setClients(ArrayList<Socket> clients) {
		this.clients = clients;
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}
	
	public void addClient(Socket socket) {
		clients.add(socket);
	}
	
	public void removeClient(Socket socket) {
		clients.remove(socket);
	}
}
