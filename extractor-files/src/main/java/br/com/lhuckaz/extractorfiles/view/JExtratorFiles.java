package br.com.lhuckaz.extractorfiles.view;

import java.awt.*;

import javax.swing.*;

import br.com.lhuckaz.extractorfiles.controler.SelecionarActionListener;

public class JExtratorFiles {

	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu indexarMenu;
	private JMenu buscarMenu;
	private JMenu conteudoMenu;
	private JMenu metadadosMenu;
	private JMenuItem indexarMenuItem;
	private JMenuItem buscarMenuItem;
	private JMenuItem conteudoMenuItem;
	private JMenuItem metadadosMenuItem;
	private JButton salvarButton;
	private JTextArea conteudoPainel;
	private JScrollPane scrollPainel;

	public JExtratorFiles() {
		prepareGUI();
	}

	private void prepareGUI() {
		frame = new JFrame("Extractor Files");
		menuBar = new JMenuBar();
		SelecionarActionListener listener = new SelecionarActionListener(this);

		// Menu indexar
		indexarMenu = new JMenu("Indexar");
		indexarMenuItem = new JMenuItem("Selecionar...");
		indexarMenuItem.addActionListener(listener);
		indexarMenu.add(indexarMenuItem);

		// Menu buscar
		buscarMenu = new JMenu("Buscar");
		buscarMenuItem = new JMenuItem("Selecionar...");
		buscarMenuItem.addActionListener(listener);
		buscarMenu.add(buscarMenuItem);

		// Menu conteudo
		conteudoMenu = new JMenu("Conteúdo");
		conteudoMenuItem = new JMenuItem("Selecionar...");
		conteudoMenuItem.addActionListener(listener);
		conteudoMenu.add(conteudoMenuItem);

		// Menu metadados
		metadadosMenu = new JMenu("Metadados");
		metadadosMenuItem = new JMenuItem("Selecionar...");
		metadadosMenuItem.addActionListener(listener);
		metadadosMenu.add(metadadosMenuItem);

		// Adionando menus ao MenuBar
		menuBar.add(indexarMenu);
		menuBar.add(buscarMenu);
		menuBar.add(conteudoMenu);
		menuBar.add(metadadosMenu);

		// Colocar o MenuBar no frame
		frame.setJMenuBar(menuBar);

		// Criando TextArea
		conteudoPainel = new JTextArea();
		// Quebra de linha automatica
		conteudoPainel.setLineWrap(true); 
		// Nao quebra linha no meio da palvra
		conteudoPainel.setWrapStyleWord(true);
		conteudoPainel.setText("Bem vindo ao Extractor Files 1.0...");
		conteudoPainel.setAutoscrolls(true);
		
		// Adicionando TextArea no ScrollPane
		scrollPainel = new JScrollPane(conteudoPainel);
		frame.add(scrollPainel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 * Preferivel usar o metodo setPreferredSize do que : frame.setSize(400, 250);
		 */
		frame.setPreferredSize(new Dimension(400, 250));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public JMenuItem getIndexarMenuItem() {
		return indexarMenuItem;
	}

	public JMenuItem getBuscarMenuItem() {
		return buscarMenuItem;
	}

	public JMenuItem getConteudoMenuItem() {
		return conteudoMenuItem;
	}

	public JMenuItem getMetadadosMenuItem() {
		return metadadosMenuItem;
	}

	public JTextArea getConteudoPainel() {
		return conteudoPainel;
	}

}
