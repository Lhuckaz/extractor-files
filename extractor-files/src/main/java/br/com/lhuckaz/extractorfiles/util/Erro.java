package br.com.lhuckaz.extractorfiles.util;

import javax.swing.JOptionPane;

public class Erro {

	/**
	 * Retorna a tela de erro
	 */
	public static void mostraMensagem() {
		JOptionPane.showMessageDialog(null, "Ocorreu um erro", "Erro", JOptionPane.ERROR_MESSAGE);
	}

}
