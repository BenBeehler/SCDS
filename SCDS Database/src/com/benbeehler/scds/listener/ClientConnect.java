package com.benbeehler.scds.listener;

import java.net.Socket;

import com.benbeehler.scds.core.SCDSCore;
import com.benbeehler.scds.listener.instance.ClientConnectEvent;
import com.benbeehler.scds.listener.instance.Event;

public class ClientConnect extends ClientConnectEvent implements Listener {
	

	@Override
	public void callEvent(Event event) {
		Socket socket = event.getClient();
		
		SCDSCore.SERVER.addClient(socket);
		System.out.println("New Connection -> " + socket.getInetAddress());
	}

}
