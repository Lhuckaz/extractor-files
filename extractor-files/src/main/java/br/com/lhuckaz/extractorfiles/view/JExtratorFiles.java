package br.com.lhuckaz.extractorfiles.view;

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

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(400, 250);
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

}
