package com.benbeehler.scds;

import com.ben.configapi.ConfigAPI;
import com.benbeehler.scds.core.SCDSCore;

public class SCDS {

	public static void main(String[] args) {
		ConfigAPI.start("./files/config.yml");
		
		SCDSCore.init();
	}
	
}
