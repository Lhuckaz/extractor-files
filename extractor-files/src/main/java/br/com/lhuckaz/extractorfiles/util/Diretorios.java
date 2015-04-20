package br.com.lhuckaz.extractorfiles.util;

public class Diretorios {

	public static String retornaUserHome() {
		return System.getProperty("user.home");
	}

	public static String retornaUserDocuments() {
		return "\\Documents\\";
	}

	public static String retornaIndice() {
		return System.getProperty("user.home") + "\\extratorfiles-indice";
	}

}
