package com.benbeehler.scds.listener;

import java.net.Socket;

import com.benbeehler.scds.listener.instance.ClientDisconnectEvent;
import com.benbeehler.scds.listener.instance.Event;

public class ClientDisconnect extends ClientDisconnectEvent implements Listener {
	

	@Override
	public void callEvent(Event event) {
		Socket socket = event.getClient();
		
		System.out.println("New Disconnect -> " + socket.getInetAddress());
	}

}
