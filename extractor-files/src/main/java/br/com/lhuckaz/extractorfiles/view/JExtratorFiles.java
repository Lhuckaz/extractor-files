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

		// menu indexar
		indexarMenu = new JMenu("Indexar");
		indexarMenuItem = new JMenuItem("Selecionar...");
		indexarMenuItem.addActionListener(new SelecionarActionListener(this));
		indexarMenu.add(indexarMenuItem);

		// menu buscar
		buscarMenu = new JMenu("Buscar");
		buscarMenuItem = new JMenuItem("Selecionar...");
		buscarMenuItem.addActionListener(new SelecionarActionListener(this));
		buscarMenu.add(buscarMenuItem);

		// menu conteudo
		conteudoMenu = new JMenu("Conteúdo");
		conteudoMenuItem = new JMenuItem("Selecionar...");
		conteudoMenuItem.addActionListener(new SelecionarActionListener(this));
		conteudoMenu.add(conteudoMenuItem);

		// menu metadados
		metadadosMenu = new JMenu("Metadados");
		metadadosMenuItem = new JMenuItem("Selecionar...");
		metadadosMenuItem.addActionListener(new SelecionarActionListener(this));
		metadadosMenu.add(metadadosMenuItem);

		// adionando menus ao menubar
		menuBar.add(indexarMenu);
		menuBar.add(buscarMenu);
		menuBar.add(conteudoMenu);
		menuBar.add(metadadosMenu);

		// colocar o menubar no frame
		frame.setJMenuBar(menuBar);

		// criando textarea
		conteudoPainel = new JTextArea();
		// Quebra de linha automatica
		conteudoPainel.setLineWrap(true); 
		// Nao quebra no meio da palvra
		conteudoPainel.setWrapStyleWord(true);
		conteudoPainel.setText("Bem vindo ao Extractor Files 1.0...");
		conteudoPainel.setAutoscrolls(true);
		
		// adicionando textarea no scrollpane
		scrollPainel = new JScrollPane(conteudoPainel);
		frame.add(scrollPainel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 * peferivel usar o metodo setPreferredSize do que : frame.setSize(400, 250);
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
