package com.benbeehler.scds.listener;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.ben.configapi.ConfigAPI;
import com.benbeehler.scds.core.SCDSCore;
import com.benbeehler.scds.listener.instance.ClientDataSendEvent;
import com.benbeehler.scds.listener.instance.Event;
import com.benbeehler.scds.utils.Log;

public class DataSend extends ClientDataSendEvent implements Listener {

	@Override
	public void callEvent(Event event) {
		/*
		 * Protocol:
		 * 
		 * PULLID#$PATH#$SESSIONID#$PASSWORD
		 * UPDATE#$PATH#$VALUE#$PASSWORD
		 * 
		 */
		
		Socket client = event.getClient();
		String data = event.getData();
		
		Log.info(client.getInetAddress() + " -> " + data);
		
		String[] split = data.toString().split("#");
		if(split.length >= 3) {
			String protocol = split[0];
			
			if(protocol.trim().toUpperCase().equals("PULLINT")) {
				try {
					int value = ConfigAPI.config.getInteger(split[1]);
					String session = split[2];
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						Log.info("Password checks out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("PULLINT#" + value + "#" + session);
					} else {
						Log.info("Password does not check out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR$Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the user disconnected?");
				}
			} else if(protocol.trim().toUpperCase().equals("PULLDOUBLE")) {
				try {
					double value = ConfigAPI.config.getDouble(split[1]);
					String session = split[2];
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						Log.info("Password checks out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("PULLDOUBLE#" + String.valueOf(value) + "#" + session);
					} else {
						Log.info("Password does not check out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR#Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the user disconnected?");
				}
			} else if(protocol.trim().toUpperCase().equals("PULLBOOLEAN")) {
				try {
					boolean value = ConfigAPI.config.getBoolean(split[1]);
					String session = split[2];
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						Log.info("Password checks out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("PULLBOOLEAN#" + String.valueOf(value) + "#" + session);
					} else {
						Log.info("Password does not check out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR#Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the user disconnected?");
				}
			} else if(protocol.trim().toUpperCase().equals("PULLLIST")) {
				try {
					List<String> value = ConfigAPI.config.getStringList(split[1]);
					String session = split[2];
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						Log.info("Password checks out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("PULLLIST#" + value.toString() + "#" + session);
					} else {
						Log.info("Password does not check out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR#Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the user disconnected?");
				}
			} else if(protocol.trim().toUpperCase().equals("PULLFLOAT")) {
				try {
					float value = ConfigAPI.config.getFloat(split[1]);
					String session = split[2];
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						Log.info("Password checks out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("PULLFLOAT#" + value + "#" + session);
					} else {
						Log.info("Password does not check out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR#Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the user disconnected?");
				}
			} else if(protocol.trim().toUpperCase().equals("PULLSTRING")) {
				try {
					String value = ConfigAPI.config.getValueFromPath(split[1]);
					String session = split[2];
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						System.out.println("INFO -> sent password = \"" + password + "\" DOES match server password = \"" + SCDSCore.PASSWORD + "\"");
						SCDSCore.SERVER.getOutStream(client).writeUTF("PULLSTRING#" + value + "#" + session);
					} else {
						System.out.println("INFO -> sent password = \"" + password + "\" does NOT match server password = \"" + SCDSCore.PASSWORD + "\"");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR#Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the user disconnected?");
				}
			} else if(protocol.trim().toUpperCase().equals("UPDATE")) {
				try {
					String path = split[1];
					String value = split[2];
					
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						System.out.println("INFO -> sent password = \"" + password + "\" DOES match server password = \"" + SCDSCore.PASSWORD + "\"");
						SCDSCore.SERVER.getOutStream(client).writeUTF("INFO#Updated " + path);
						
						ConfigAPI.config.setValueToPath(path, value);
					} else {
						System.out.println("INFO -> sent password = \"" + password + "\" does NOT match server password = \"" + SCDSCore.PASSWORD + "\"");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR#Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the data been sent properly?");
				}
			}
		} else {
		}

	}

}
