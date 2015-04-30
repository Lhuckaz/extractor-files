package br.com.lhuckaz.extractorfiles.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.com.lhuckaz.extractorfiles.controler.BotoesActionListener;
import br.com.lhuckaz.extractorfiles.controler.Indexador;
import br.com.lhuckaz.extractorfiles.controler.SelecionarActionListener;
import br.com.lhuckaz.extractorfiles.util.Diretorios;

public class JExtratorFiles {

	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu indexarMenu;
	private JMenu buscarMenu;
	private JMenu conteudoMenu;
	private JMenu metadadosMenu;
	private JMenuItem indexarMenuItem;
	private JMenuItem buscarEIndexarMenuItem;
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
	private JLabel diretorioIndexado;

	public JExtratorFiles() {
		prepareBoasVindas();
	}

	private void prepareBoasVindas() {
		frame = new JFrame("Extractor Files");
		frame.getContentPane().setBackground(Color.WHITE);
		selecionarListener = new SelecionarActionListener(this);
		botoesListener = new BotoesActionListener(this);
		
		ImageIcon ico = new ImageIcon(getClass().getClassLoader().getResource("ico.png"));
		frame.setIconImage(ico.getImage());

		diretorioIndexado = new JLabel();
		frame.add(diretorioIndexado, BorderLayout.NORTH);
		
		icone = new ImageIcon(getClass().getClassLoader().getResource("logo.jpg"));
		telaPrincipal = new JLabel(icone);
		frame.add(telaPrincipal);
		adicionarMenuBar();

		adicionarEventoFinal();

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*
		 * Preferivel usar o metodo setPreferredSize do que : frame.setSize(x,
		 * y);
		 */
		frame.setPreferredSize(new Dimension(750, 500));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void atualizaDiretorioIndexado() {
		diretorioIndexado.setText(" Caminho indexado: " + Indexador.getDiretorioIndexado());
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
		buscarMenuItem = new JMenuItem("Consulta");
		buscarEIndexarMenuItem = new JMenuItem("Selecionar...");
		buscarMenuItem.addActionListener(selecionarListener);
		buscarEIndexarMenuItem.addActionListener(selecionarListener);
		buscarMenu.add(buscarMenuItem);
		buscarMenu.add(buscarEIndexarMenuItem);

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

	private void adicionarEventoFinal() {
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame, "Deseja sair ?", "Saindo", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					Indexador.apagaIndices(new File(Diretorios.retornaIndice()));
					System.exit(0);
				}
			}
		});

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

	public JMenuItem getBuscarEIndexarMenuItem() {
		return buscarEIndexarMenuItem;
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
