package br.com.lhuckaz.extractorfiles.util;

public class EditorGUI {

	/**
	 * Confirma se Editor ja foi criado
	 */
	private static boolean confirmar = false;

	/**
	 * 
	 * @return Retorna a confirmacao se e Editor ja foi criado
	 */
	public static boolean preparado() {
		if (confirmar == false) {
			confirmar = true;
			return false;
		}
		return confirmar;
	}

}
