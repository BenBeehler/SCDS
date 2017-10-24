package com.benbeehler.scds.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.ben.configapi.ConfigAPI;
import com.benbeehler.scds.core.instance.DBServer;
import com.benbeehler.scds.listener.ClientConnect;
import com.benbeehler.scds.listener.ClientDisconnect;
import com.benbeehler.scds.listener.DataSend;
import com.benbeehler.scds.listener.ListenerManager;
import com.benbeehler.scds.listener.instance.ClientConnectEvent;
import com.benbeehler.scds.listener.instance.ClientDataSendEvent;
import com.benbeehler.scds.listener.instance.ClientDisconnectEvent;
import com.benbeehler.scds.listener.instance.Event;
import com.benbeehler.scds.utils.Log;

public class SCDSCore {

	public static int PORT = 4000;
	public static DBServer SERVER = new DBServer(PORT);
	public static boolean RUNNING = false;
	public static String PASSWORD = "password";
	public static Executor THREADPOOL = Executors.newCachedThreadPool();
	public static ArrayList<ServerSocket> SUBSERVERS = new ArrayList<>();
	public static boolean isMasterServer = false;
		
	public static void init() {
		initSetup();
		initListeners();
		
		start();
	}
	
	public static void initSetup() {
		if(ConfigAPI.config.getFile().exists()) {
			if(ConfigAPI.config.pathExists("system.password")) {
				PASSWORD = ConfigAPI.config.getValueFromPath("system.password");
			} else {
				Log.warning("The path (system.password) does not exist! You can set it with update system.password (password str)");
			}
			
			if(ConfigAPI.config.pathExists("system.port")) {
				PORT = ConfigAPI.config.getInteger("system.port");
				SERVER = new DBServer(PORT);
			} else {
				Log.warning("The path (system.port) does not exist! You can set it with update system.port (portnumber)");
			}
		} else {
			Log.warning("The file does not exist and could not be auto-generated, you need to create it in /files/config.yml");
		}
	}
	
	public static void initListeners() {
		ListenerManager.addListener(new ClientConnect());
		ListenerManager.addListener(new ClientDisconnect());
		ListenerManager.addListener(new DataSend());
	}
	
	public static void start() {
		Log.info("Executing server on " + PORT);
		Log.info("Server password is " + PASSWORD);
		
		RUNNING = true;
		
		new Thread(() -> {
			while(RUNNING) {
				handleConnections();
			}
		}).start();
		
		new Thread(() -> {
			while(RUNNING) {
				handleRequests();
			}
		}).start();
	}
	
	public static void handleConnections() {
		Socket socket;
		try {
			if(SERVER.getServer() != null) {
				if(!SERVER.getServer().isClosed()) {
					socket = SERVER.getServer().accept();
					
					ListenerManager.LISTENERS.stream().forEach(listener -> {
						if(listener instanceof ClientConnectEvent) {
							THREADPOOL.execute(() -> {
								listener.callEvent(new Event(socket));
							});
						}
					});
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void handleRequests() {
		ArrayList<Socket> remove = new ArrayList<>();
		
		for(int i = 0; i < SERVER.getClients().size(); i++) {
			Socket socket = SERVER.getClients().get(i);
			
			try {
				if(socket != null) {
					if(!socket.isClosed()) {
						if(SERVER.getInputStream(socket).available() != 0) {
							String data = SERVER.getInputStream(socket).readUTF();
									
							Event event = new Event(socket);
							event.setData(data);
								
							ListenerManager.LISTENERS.stream().forEach(listener -> {
								if(listener instanceof ClientDataSendEvent) {
									THREADPOOL.execute(() -> {
										listener.callEvent(event);
									});
								}
							});
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		remove.stream().forEach(client -> {
			SERVER.removeClient(client);
			
			ListenerManager.LISTENERS.stream().forEach(listener -> {
				if(listener instanceof ClientDisconnectEvent) {
					THREADPOOL.execute(() -> {
						listener.callEvent(new Event(client));
					});
				}
			});
		});
		
		remove.clear();
	}

	public static void setIsMaster(boolean master) {
		SCDSCore.isMasterServer = master;
	}

	public static boolean isMaster() {
		return SCDSCore.isMasterServer;
	}
}
