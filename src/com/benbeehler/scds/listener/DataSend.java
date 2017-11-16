package com.benbeehler.scds.listener;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

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
		
		String[] split = data.toString().split(SCDSCore.getDelimeter());
		if(split.length >= 3) {
			String protocol = split[0];
			
			if(protocol.trim().toUpperCase().equals("PULLINT")) {
				try {
					int value = SCDSCore.MAIN_CONFIG.getInteger(split[1]);
					String session = split[2];
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						Log.info("Password checks out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("PULLINT" + SCDSCore.getDelimeter() + value + SCDSCore.getDelimeter() + session);
					} else {
						Log.info("Password does not check out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR + " + SCDSCore.getDelimeter() + "Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the user disconnected?");
				}
			} else if(protocol.trim().toUpperCase().equals("PULLDOUBLE")) {
				try {
					double value = SCDSCore.MAIN_CONFIG.getDouble(split[1]);
					String session = split[2];
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						Log.info("Password checks out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("PULLDOUBLE" + SCDSCore.getDelimeter() + String.valueOf(value) + SCDSCore.getDelimeter() + session);
					} else {
						Log.info("Password does not check out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR" + SCDSCore.getDelimeter() + "Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the user disconnected?");
				}
			} else if(protocol.trim().toUpperCase().equals("PULLBOOLEAN")) {
				try {
					boolean value = SCDSCore.MAIN_CONFIG.getBoolean(split[1]);
					String session = split[2];
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						Log.info("Password checks out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("PULLBOOLEAN" + SCDSCore.getDelimeter() + String.valueOf(value) + SCDSCore.getDelimeter() + session);
					} else {
						Log.info("Password does not check out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR" + SCDSCore.getDelimeter() + "Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the user disconnected?");
				}
			} else if(protocol.trim().toUpperCase().equals("PULLLIST")) {
				try {
					List<String> value = SCDSCore.MAIN_CONFIG.getStringList(split[1]);
					String session = split[2];
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						Log.info("Password checks out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("PULLLIST" + SCDSCore.getDelimeter() + value.toString() + SCDSCore.getDelimeter() + session);
					} else {
						Log.info("Password does not check out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR" + SCDSCore.getDelimeter() + "Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the user disconnected?");
				}
			} else if(protocol.trim().toUpperCase().equals("PULLFLOAT")) {
				try {
					float value = SCDSCore.MAIN_CONFIG.getFloat(split[1]);
					String session = split[2];
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						Log.info("Password checks out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("PULLFLOAT" + SCDSCore.getDelimeter() + value + SCDSCore.getDelimeter() + session);
					} else {
						Log.info("Password does not check out");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR" + SCDSCore.getDelimeter() + "Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the user disconnected?");
				}
			} else if(protocol.trim().toUpperCase().equals("PULLSTRING")) {
				try {
					String value = SCDSCore.MAIN_CONFIG.getValueFromPath(split[1]);
					String session = split[2];
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						System.out.println("INFO -> sent password = \"" + password + "\" DOES match server password = \"" + SCDSCore.PASSWORD + "\"");
						SCDSCore.SERVER.getOutStream(client).writeUTF("PULLSTRING" + SCDSCore.getDelimeter() + value + SCDSCore.getDelimeter() + session);
					} else {
						System.out.println("INFO -> sent password = \"" + password + "\" does NOT match server password = \"" + SCDSCore.PASSWORD + "\"");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR" + SCDSCore.getDelimeter() + "Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the user disconnected?");
				}
			} else if(protocol.trim().toUpperCase().equals("PULLMAP")) {
				try {
					String value = SCDSCore.MAIN_CONFIG.getValueFromPath(split[1]);
					String session = split[2];
					String password = split[3];
					
					if(password.trim().equals(SCDSCore.PASSWORD.trim())) {
						System.out.println("INFO -> sent password = \"" + password + "\" DOES match server password = \"" + SCDSCore.PASSWORD + "\"");
						SCDSCore.SERVER.getOutStream(client).writeUTF("PULLMAP" + SCDSCore.getDelimeter() + value + SCDSCore.getDelimeter() + session);
					} else {
						System.out.println("INFO -> sent password = \"" + password + "\" does NOT match server password = \"" + SCDSCore.PASSWORD + "\"");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR" + SCDSCore.getDelimeter() + "Invalid Server Password");
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
						SCDSCore.SERVER.getOutStream(client).writeUTF("INFO" + SCDSCore.getDelimeter() + "Updated " + path);
						
						SCDSCore.MAIN_CONFIG.setValueToPath(path, value);
					} else {
						System.out.println("INFO -> sent password = \"" + password + "\" does NOT match server password = \"" + SCDSCore.PASSWORD + "\"");
						SCDSCore.SERVER.getOutStream(client).writeUTF("ERR" + SCDSCore.getDelimeter() + "Invalid Server Password");
					}
				} catch(IOException e) {
					Log.error("IO Error, has the data been sent properly?");
				}
			}
		} else {
		}

	}

}
