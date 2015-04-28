package br.com.lhuckaz.extractorfiles.gui;

import java.awt.*;

import javax.swing.*;

import br.com.lhuckaz.extractorfiles.controler.BotoesActionListener;
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
	private JButton limparButton;
	private JTextArea conteudoPainel;
	private JScrollPane scrollPainel;
	private SelecionarActionListener selecionarListener;
	private BotoesActionListener botoesListener;
	private ImageIcon icone;
	private JLabel telaPrincipal;

	public JExtratorFiles() {
		prepareBoasVindas();
	}

	private void prepareBoasVindas() {
		frame = new JFrame("Extractor Files");
		selecionarListener = new SelecionarActionListener(this);
		botoesListener = new BotoesActionListener(this);
		
		icone = new ImageIcon(getClass().getClassLoader().getResource("logo.jpg"));
		telaPrincipal = new JLabel(icone);
		frame.add(telaPrincipal);

		adicionarMenuBar();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 * Preferivel usar o metodo setPreferredSize do que : frame.setSize(x, y);
		 */
		frame.setPreferredSize(new Dimension(750, 500));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void prepareGUI() {
		telaPrincipal.setVisible(false);

		// Criando TextArea
		conteudoPainel = new JTextArea();
		conteudoPainel.setFont(new Font("Verdana", Font.PLAIN, 14));
		// Quebra de linha automatica
		conteudoPainel.setLineWrap(true);
		// Nao quebra linha no meio da palvra
		conteudoPainel.setWrapStyleWord(true);
		conteudoPainel.setAutoscrolls(true);

		// Adicionando TextArea no ScrollPane
		scrollPainel = new JScrollPane(conteudoPainel);
		frame.add(scrollPainel);

		adicionaBotoes();
	}

	private void adicionaBotoes() {
		// Adicionando botoes
		salvarButton = new JButton("Salvar");
		salvarButton.addActionListener(botoesListener);
		limparButton = new JButton("Limpar");
		limparButton.addActionListener(botoesListener);
		JPanel painelButtons = new JPanel(new GridLayout(1, 10));
		preencheGridLayoutComEspacosVazio(painelButtons);
		painelButtons.add(limparButton);
		painelButtons.add(salvarButton);
		frame.add(painelButtons, BorderLayout.SOUTH);
	}

	private void adicionarMenuBar() {
		menuBar = new JMenuBar();

		// Menu indexar
		indexarMenu = new JMenu("Indexar");
		indexarMenuItem = new JMenuItem("Selecionar...");
		indexarMenuItem.addActionListener(selecionarListener);
		indexarMenu.add(indexarMenuItem);

		// Menu buscar
		buscarMenu = new JMenu("Buscar");
		buscarMenuItem = new JMenuItem("Selecionar...");
		buscarMenuItem.addActionListener(selecionarListener);
		buscarMenu.add(buscarMenuItem);

		// Menu conteudo
		conteudoMenu = new JMenu("Conteúdo");
		conteudoMenuItem = new JMenuItem("Selecionar...");
		conteudoMenuItem.addActionListener(selecionarListener);
		conteudoMenu.add(conteudoMenuItem);

		// Menu metadados
		metadadosMenu = new JMenu("Metadados");
		metadadosMenuItem = new JMenuItem("Selecionar...");
		metadadosMenuItem.addActionListener(selecionarListener);
		metadadosMenu.add(metadadosMenuItem);

		// Adionando menus ao MenuBar
		menuBar.add(indexarMenu);
		menuBar.add(buscarMenu);
		menuBar.add(conteudoMenu);
		menuBar.add(metadadosMenu);

		// Colocar o MenuBar no frame
		frame.setJMenuBar(menuBar);
	}

	// método usado para preencher o GridLayout para os botoes ficarem a
	// esquerda
	private void preencheGridLayoutComEspacosVazio(JPanel painelButtons) {
		for (int i = 0; i < 8; i++) {
			painelButtons.add(new JLabel());
		}

	}

	public JFrame getFrame() {
		return frame;
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

	public JButton getSalvarButton() {
		return salvarButton;
	}

	public JButton getLimparButton() {
		return limparButton;
	}

}
