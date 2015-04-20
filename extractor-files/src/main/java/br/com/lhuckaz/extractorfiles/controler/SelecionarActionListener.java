package br.com.lhuckaz.extractorfiles.controler;

import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.*;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

import br.com.lhuckaz.extractorfiles.util.AutoDetector;
import br.com.lhuckaz.extractorfiles.util.Diretorios;
import br.com.lhuckaz.extractorfiles.view.JExtratorFiles;

public class SelecionarActionListener implements ActionListener {

	private static Logger logger = Logger.getLogger(SelecionarActionListener.class);
	private String currentDirectory = Diretorios.retornaUserDocuments();
	private Indexador indexador = new Indexador();
	private Buscador buscador = new Buscador();
	private JExtratorFiles extratorFiles;
	private Detector detector;
	private Tika tika;

	public SelecionarActionListener(JExtratorFiles extratorFiles) {
		this.extratorFiles = extratorFiles;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(currentDirectory));
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		// Para selecionar apenas um diretorio ou arquivo
		chooser.setMultiSelectionEnabled(false);
		// TODO Usar filtro no chooser ?
		// Filtro de extencoes
		// FileNameExtensionFilter filter = new FileNameExtensionFilter("txt",
		// "java", "doc", "jpg", "pdf", "txt", "xls",
		// "xml", "odt", "docx", "ppt", "pptx", "rtf", "html", "ooxml", "epub",
		// "zip", "mp3", "mp4", "png", "gif",
		// "bmp", "3gpp");
		// chooser.setFileFilter(filter);

		Object open = e.getSource();
		try {
			// MenuItem indexar
			if (open == extratorFiles.getIndexarMenuItem()) {
				int code = chooser.showOpenDialog(chooser);
				if (code == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					if (selectedFile.isFile()) {
						indexador.indexaArquivosDoDiretorio(selectedFile.getAbsolutePath());
						JOptionPane.showMessageDialog(null, "Indexado arquivo " + selectedFile.getAbsolutePath());
					} else if (selectedFile.isDirectory()) {
						indexador.indexaArquivosDoDiretorio(selectedFile.getAbsolutePath());
						JOptionPane.showMessageDialog(null, "Indexado diretorio " + selectedFile.getAbsolutePath());
					}
				}
			}

			// MenuItem buscar
			if (open == extratorFiles.getBuscarMenuItem()) {
				int code = chooser.showOpenDialog(chooser);
				if (code == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					String busca = JOptionPane.showInputDialog("Consulta");
					if (selectedFile.isFile()) {
						// TODO implemntar indexar antes com thread
						// indexador.indexaArquivosDoDiretorio(selectedFile.getAbsolutePath());
						// JOptionPane.showMessageDialog(null,
						// "Indexado arquivo " +
						// selectedFile.getAbsolutePath());
						String resultado = buscador.buscaComParser(busca);
						extratorFiles.getConteudoPainel().setText(resultado);
					} else if (selectedFile.isDirectory()) {
						// indexador.indexaArquivosDoDiretorio(selectedFile.getAbsolutePath());
						// JOptionPane.showMessageDialog(null,
						// "Indexado arquivo " +
						// selectedFile.getAbsolutePath());
						String resultado = buscador.buscaComParser(busca);
						extratorFiles.getConteudoPainel().setText(resultado);
					}
				}
			}

			// MenuItem conteudo
			if (open == extratorFiles.getConteudoMenuItem()) {
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int code = chooser.showOpenDialog(chooser);
				if (code == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					String conteudo = getTika().parseToString(selectedFile);
					extratorFiles.getConteudoPainel().setText(conteudo);
				}
			}

			// MenuItem metadados
			if (open == extratorFiles.getMetadadosMenuItem()) {
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int code = chooser.showOpenDialog(chooser);
				if (code == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					String imprimirMetaDados = AutoDetector.metaDados(selectedFile);
					extratorFiles.getConteudoPainel().setText(imprimirMetaDados);
				}
			}
			
			currentDirectory = chooser.getSelectedFile().getAbsolutePath();
		} catch (Exception f) {
			logger.error(f);
		}
	}

	public Tika getTika() {
		if (tika == null) {
			tika = new Tika();
		}
		return tika;
	}

}
