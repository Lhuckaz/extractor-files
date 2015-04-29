package br.com.lhuckaz.extractorfiles.controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;

import br.com.lhuckaz.extractorfiles.gui.JExtratorFiles;
import br.com.lhuckaz.extractorfiles.util.AutoDetector;
import br.com.lhuckaz.extractorfiles.util.Diretorios;
import br.com.lhuckaz.extractorfiles.util.EditorGUI;
import br.com.lhuckaz.extractorfiles.util.JOptionPanes;

public class SelecionarActionListener implements ActionListener {

	private static Logger logger = Logger.getLogger(SelecionarActionListener.class);
	private Indexador indexador = new Indexador();
	private Buscador buscador = new Buscador();
	private JExtratorFiles extratorFiles;
	private JFileChooser chooser;
	private Tika tika;

	public SelecionarActionListener(JExtratorFiles extratorFiles) {
		this.extratorFiles = extratorFiles;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!EditorGUI.preparado()) {
			extratorFiles.prepareGUI();
		}
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(Diretorios.retornaDiretorioCorrente()));
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		// Para selecionar apenas um diretorio ou arquivo
		chooser.setMultiSelectionEnabled(false);

		// // Filtro de extencoes
		// FileNameExtensionFilter filter = new FileNameExtensionFilter("txt",
		// "java", "doc", "jpg", "pdf", "txt", "xls",
		// "xml", "odt", "docx", "ppt", "pptx", "rtf", "html", "ooxml", "epub",
		// "zip", "mp3", "mp4", "png", "gif",
		// "bmp", "3gpp");
		// chooser.setFileFilter(filter);

		Object open = e.getSource();
		// MenuItem indexar
		if (open == extratorFiles.getIndexarMenuItem()) {
			abrirIndexarChooser();
		}

		// MenuItem buscar
		if (open == extratorFiles.getBuscarMenuItem()) {
			abrirBuscarChooser();
		}

		// MenuItem buscar e indexar
		if (open == extratorFiles.getBuscarEIndexarMenuItem()) {
			abrirBuscarEIndexarChooser();
		}

		// MenuItem conteudo
		if (open == extratorFiles.getConteudoMenuItem()) {
			abrirConteudoChooser();
		}

		// MenuItem metadados
		if (open == extratorFiles.getMetadadosMenuItem()) {
			abrirMetadadosChooser();
		}

	}

	public Tika getTika() {
		if (tika == null) {
			tika = new Tika();
		}
		return tika;
	}

	private void abrirIndexarChooser() {
		// Alterando o texto JFileChooser para o 'Selecionar'
		int code = chooser.showDialog(extratorFiles.getFrame(), "Selecionar");
		if (code == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			indexarFile(selectedFile);
		}

	}

	private void indexarFile(File selectedFile) {
		String caminhoArquivo = selectedFile.getAbsolutePath();
		indexador.indexaArquivos(caminhoArquivo);
		JOptionPanes.arquivoIndexado(caminhoArquivo);
		extratorFiles.atualizaDiretorioIndexado();
		Diretorios.setDiretorioCorrente(chooser.getSelectedFile().getAbsolutePath());
	}

	private void abrirBuscarChooser() {
		if (Indexador.getDiretorioIndexado().equals("")) {
			JOptionPanes.semArquivoIndexado();
		} else {
			String busca = JOptionPanes.busca();
			if (busca != null) {
				String resultado = buscador.buscaComParser(busca);
				extratorFiles.getConteudoPainel().setText(resultado);
			} else {
				JOptionPanes.semResultados();
			}
		}
	}

	private void abrirBuscarEIndexarChooser() {
		// Alterando o texto JFileChooser para o 'Selecionar'
		int code = chooser.showDialog(extratorFiles.getFrame(), "Selecionar");
		if (code == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			// Indexando no diretório ou arquivo
			indexarFile(selectedFile);
			String busca = JOptionPanes.busca();
			if (busca != null) {
				String resultado = buscador.buscaComParser(busca);
				extratorFiles.getConteudoPainel().setText(resultado);
				Diretorios.setDiretorioCorrente(chooser.getSelectedFile().getAbsolutePath());
			} else {
				JOptionPanes.semResultados();
			}
		}
	}

	private void abrirConteudoChooser() {
		try {
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int code = chooser.showOpenDialog(extratorFiles.getFrame());
			if (code == JFileChooser.APPROVE_OPTION) {
				File selectedFile = chooser.getSelectedFile();
				String conteudo = getTika().parseToString(selectedFile);
				logger.info("Exibindo conteúdo de " + selectedFile.getAbsolutePath());
				extratorFiles.getConteudoPainel().setText(conteudo);
				Diretorios.setDiretorioCorrente(chooser.getSelectedFile().getAbsolutePath());
			}
		} catch (Exception e) {
			JOptionPanes.mensagemDeErro();
			logger.error(e);
		}
	}

	private void abrirMetadadosChooser() {
		try {
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int code = chooser.showOpenDialog(extratorFiles.getFrame());
			if (code == JFileChooser.APPROVE_OPTION) {
				File selectedFile = chooser.getSelectedFile();
				String imprimirMetaDados = AutoDetector.metaDados(selectedFile);
				logger.info("Exibindo metadados de " + selectedFile.getAbsolutePath());
				extratorFiles.getConteudoPainel().setText(imprimirMetaDados);
				Diretorios.setDiretorioCorrente(chooser.getSelectedFile().getAbsolutePath());
			}
		} catch (Exception e) {
			JOptionPanes.mensagemDeErro();
			logger.error(e);
		}
	}
}
