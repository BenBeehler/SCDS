package com.benbeehler.scds.listener;

import com.benbeehler.scds.listener.instance.Event;

public interface Listener {

	public void callEvent(Event event);
	
}
