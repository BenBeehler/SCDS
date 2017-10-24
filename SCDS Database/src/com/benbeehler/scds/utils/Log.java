package com.benbeehler.scds.utils;

public class Log {

    //universal logging

    public static void l(String message) {
        System.out.println(message);
    }

    public static void warning(String message) {
        System.out.println("Warning => " + message);
    }

    public static void info(String message) {
        System.out.println("Info => " + message);
    }

    public static void error(String message) {
        System.out.println("Error => " + message);
    }
}