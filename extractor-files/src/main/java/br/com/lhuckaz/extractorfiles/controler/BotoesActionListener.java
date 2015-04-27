package br.com.lhuckaz.extractorfiles.controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.com.lhuckaz.extractorfiles.gui.JExtratorFiles;
import br.com.lhuckaz.extractorfiles.util.Diretorios;

public class BotoesActionListener implements ActionListener {

	private static Logger logger = Logger.getLogger(BotoesActionListener.class);
	JFileChooser chooser;
	private JExtratorFiles jExtratorFiles;

	public BotoesActionListener(JExtratorFiles jExtratorFiles) {
		this.jExtratorFiles = jExtratorFiles;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(Diretorios.retornaDiretorioCorrente()));
		chooser.setSelectedFile(new File(Diretorios.retornaArquivoParaSalvar()));

		Object open = e.getSource();
		try {
			if (open == jExtratorFiles.getSalvarButton()) {
				int code = chooser.showSaveDialog(jExtratorFiles.getFrame());
				if (code == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					if(selectedFile.exists()) {
						int confirmar = JOptionPane.showConfirmDialog(null, "Arquivo já existe\nDeseja sobrescrever ?", "Salvar", JOptionPane.YES_NO_OPTION);
						if (confirmar == 0) {
							salvarArquivo(selectedFile);
						}
					} else {
						salvarArquivo(selectedFile);
					}
				}
			}

			if (open == jExtratorFiles.getLimparButton()) {
				jExtratorFiles.getConteudoPainel().setText("");
				logger.info("Limpando documento");
			}
		} catch (Exception f) {
			logger.error(f);
		}

	}

	private void salvarArquivo(File selectedFile) throws IOException {
		FileOutputStream fos = new FileOutputStream(selectedFile);
		OutputStreamWriter out = new OutputStreamWriter(fos, Charset.forName("UTF-8"));
		out.write(jExtratorFiles.getConteudoPainel().getText());
		out.close();
		logger.info("Salvando arquivo em: " + chooser.getSelectedFile().getAbsolutePath());
		Diretorios.setDiretorioCorrente(chooser.getSelectedFile().getAbsolutePath());
		
	}
}
