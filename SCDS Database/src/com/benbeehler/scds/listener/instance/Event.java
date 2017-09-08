package com.benbeehler.scds.listener.instance;

import java.net.Socket;

public class Event {

	private Socket client;
	private String data;

	public Event(Socket client) {
		this.client = client;
	}
	
	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
