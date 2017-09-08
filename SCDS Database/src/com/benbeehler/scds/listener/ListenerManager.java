package com.benbeehler.scds.listener;

import java.util.ArrayList;

public class ListenerManager {

	public static ArrayList<Listener> LISTENERS = 
			new ArrayList<>();
	
	public static void addListener(Listener listener) {
		LISTENERS.add(listener);
	}
	
	public static void removeListener(Listener listener) {
		LISTENERS.remove(listener);
	}
}
