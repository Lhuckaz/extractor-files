package br.com.lhuckaz.extractorfiles.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class JExtratorFilesOld {

	private JFrame frame;

	public JExtratorFilesOld() {
		prepareGUI();
	}

	private void prepareGUI() {
		frame = new JFrame("Extractor Files");
		JPanel pGrid = new JPanel(new GridLayout(2, 2, 5, 5));
		pGrid.setBorder(new EmptyBorder(60, 0, 0, 0));

		JButton button = new JButton("Indexar Arquivos");
		JButton button2 = new JButton("Buscar termo em arquivos");
		JButton button3 = new JButton("Mostrar conteúdo");
		JButton button4 = new JButton("Mostrar metadados");
		
		JEditorPane jPane = new JEditorPane();

		pGrid.add(button);
		pGrid.add(button2);
		pGrid.add(button3);
		pGrid.add(button4);

		frame.setLayout(new FlowLayout());
		frame.getContentPane().add(pGrid, BorderLayout.CENTER);

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(400, 250);
		frame.setVisible(true);

	}

}
