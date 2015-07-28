package br.com.lhuckaz.extractorfiles.main;

import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import br.com.lhuckaz.extractorfiles.gui.JExtratorFiles;
import br.com.lhuckaz.extractorfiles.util.Tradutor;

/**
 * @author Lucas
 */
public class Main {
	private static Logger logger = Logger.getLogger(Main.class);
	public static final int INSTANCIA_UNICA = 9581;
	private static ServerSocket instancia;

	public static void main(String[] args) throws Exception {
		// Trocando o visual de acordo com o SO
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Tradutor.traduz();
		try {
			instancia = new ServerSocket(INSTANCIA_UNICA);
			logger.debug("Aplicacao aberta no socket: " + INSTANCIA_UNICA);
			// Rodar em outra Thread
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new JExtratorFiles();
				}
			});
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Aplicação já aberta em outra janela", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
			logger.info("Aplicação já aberta em outra janela");
		}
	}
	
	static ServerSocket getInstancia() {
		return instancia;
	}
}
