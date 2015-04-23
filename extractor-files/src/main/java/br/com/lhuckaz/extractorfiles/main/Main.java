package br.com.lhuckaz.extractorfiles.main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import br.com.lhuckaz.extractorfiles.gui.JExtratorFiles;

/**
 * @author Lucas
 */
public class Main {

	public static void main(String[] args) throws Exception {
		// Trocando o visual de acordo com o SO
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// Rodar em outra Thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new JExtratorFiles();
			}
		});

	}

}
