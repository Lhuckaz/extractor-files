package br.com.lhuckaz.extractorfiles.util;

import javax.swing.JOptionPane;

public class JOptionPanes {

	/**
	 * Retorna a tela de erro
	 */
	public static void mensagemDeErro() {
		JOptionPane.showMessageDialog(null, "Ocorreu um erro!", "Erro", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Abri tela de busca e valida String
	 */
	public static String busca() {
		String retorno;
		retorno = JOptionPane.showInputDialog(null, "Consulta", "Digite sua busca", JOptionPane.PLAIN_MESSAGE);
		if (!validarString(retorno)) {
			retorno = null;
		}
		return retorno;
	}

	public static void semResultados() {
		JOptionPane.showMessageDialog(null, "Digite um valor válido", "Sem resultados", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Valida se o campos é diferente de null e diferente de vazio ("")
	 * @param String valor
	 * @return ret
	 */
	public static boolean validarString(Object valor) {
		boolean retorno = false;
		if (valor != null) {
			String aux = String.valueOf(valor);
			retorno = (!aux.trim().equals("") && !aux.equalsIgnoreCase(null));
		}
		return retorno;
	}

	public static void arquivoIndexado(String selectedFile) {
		JOptionPane.showMessageDialog(null, "Indexado caminho: " + selectedFile, "Indexado", JOptionPane.INFORMATION_MESSAGE);
		
	}

	public static int sobrescreverArquivo() {
		return JOptionPane.showConfirmDialog(null, "Arquivo já existe\nDeseja sobrescrever ?", "Salvar",
				JOptionPane.YES_NO_OPTION);
	}

	public static void semArquivoIndexado() {
		JOptionPane.showMessageDialog(null, "Você deve indexar algum arquivo", "Sem arquivo indexado", JOptionPane.INFORMATION_MESSAGE);
	}

}
