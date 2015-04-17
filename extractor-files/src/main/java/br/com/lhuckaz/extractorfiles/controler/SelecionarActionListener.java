package br.com.lhuckaz.extractorfiles.controler;

import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import br.com.lhuckaz.extractorfiles.util.Diretorios;
import br.com.lhuckaz.extractorfiles.view.JExtratorFiles;

public class SelecionarActionListener implements ActionListener {

	private JExtratorFiles extratorFiles;

	public SelecionarActionListener(JExtratorFiles extratorFiles) {
		this.extratorFiles = extratorFiles;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String fileName = "";

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(Diretorios.retornaUserDocuments()));
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		// Para selecionar apenas um diretorio ou arquivo
		chooser.setMultiSelectionEnabled(false);
		// Filtro de extensoes
		// FileNameExtensionFilter filter = new FileNameExtensionFilter("txt",
		// "java", "doc", "jpg", "pdf", "txt", "xls", "xml", "odt", "docx",
		// "ppt", "pptx", "rtf");
		// chooser.setFileFilter(filter);

		Object open = e.getSource();
		try {
			if (open == extratorFiles.getIndexarMenuItem()) {
				int code = chooser.showOpenDialog(chooser);
				if (code == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					if (selectedFile.isFile()) {
						fileName = selectedFile.getName();
						FileInputStream fis = new FileInputStream(selectedFile);
						InputStreamReader in = new InputStreamReader(fis, Charset.forName("UTF-8"));
						char[] buffer = new char[1024];
						int n = in.read(buffer);
						String text = new String(buffer, 0, n);
						// extratorFiles.text.setText(text);
						Indexador indexador = new Indexador();
						indexador.indexaArquivosDoDiretorio();
						System.out.println("Selected file: " + selectedFile.getAbsolutePath());
						in.close();
					} else if (selectedFile.isDirectory()) {
						File[] selectedFiles = chooser.getSelectedFiles();
						System.out.println(selectedFiles.length);
						if (selectedFiles.length > 0) {
							for (File file : selectedFiles) {
								System.out.println(file.getAbsolutePath());
							}
						} else {
							//selectedFiles[0].getAbsolutePath();
							File selectedFil = chooser.getSelectedFile();
							System.out.println(selectedFil.getAbsolutePath());
						}
						Indexador indexador = new Indexador();
						indexador.indexaArquivosDoDiretorio();
					}

				}
			}
			// else if (cmd == cmdSave) {
			// int code = chooser.showOpenDialog(myPane);
			// if (code == JFileChooser.APPROVE_OPTION) {
			// File selectedFile = chooser.getSelectedFile();
			// fileName = selectedFile.getName();
			// FileOutputStream fos = new FileOutputStream(selectedFile);
			// OutputStreamWriter out = new OutputStreamWriter(fos,
			// Charset.forName("UTF-8"));
			// out.write(myPane.getText());
			// out.close();
			// }
			// }
		} catch (Exception f) {
			f.printStackTrace();
		}
	}

}
