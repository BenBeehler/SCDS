package com.benbeehler.scds.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {

	public static String read(InputStream stream) {
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		
		String string = "";
		
		try {
			while((string = br.readLine()) != null) {
				string = br.readLine();
				break;
			}
		} catch (IOException e) {
		}
		
		return string;
	}
	
}
