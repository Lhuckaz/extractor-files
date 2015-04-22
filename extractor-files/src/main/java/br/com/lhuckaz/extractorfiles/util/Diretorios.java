package br.com.lhuckaz.extractorfiles.util;

public class Diretorios {

	/**
	 * 
	 * @return Retorna o caminho para User Home
	 */
	public static String retornaUserHome() {
		return System.getProperty("user.home");
	}

	/**
	 * 
	 * @return Retorna uma string para abrir o JChooser em Documentos
	 */
	public static String retornaUserDocuments() {
		return "\\Documents\\";
	}

	/**
	 * 
	 * @return Retorna caminho para pasta dos índices
	 */
	public static String retornaIndice() {
		return System.getProperty("user.home") + "\\extratorfiles-indice";
	}

}
