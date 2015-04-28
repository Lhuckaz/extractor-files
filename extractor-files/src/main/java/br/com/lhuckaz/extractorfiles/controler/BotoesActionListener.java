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
import br.com.lhuckaz.extractorfiles.util.Erro;

public class BotoesActionListener implements ActionListener {

	private static final int SALVAR = 0;
	private static Logger logger = Logger.getLogger(BotoesActionListener.class);
	private JExtratorFiles jExtratorFiles;
	private JFileChooser chooser;

	public BotoesActionListener(JExtratorFiles jExtratorFiles) {
		this.jExtratorFiles = jExtratorFiles;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		jExtratorFiles.prepareGUI();
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(Diretorios.retornaDiretorioCorrente()));
		chooser.setSelectedFile(new File(Diretorios.retornaArquivoParaSalvar()));

		Object open = e.getSource();
		// Implementacao botao salvar
		if (open == jExtratorFiles.getSalvarButton()) {
			abrirSalvarChooser();
		}
		// Implementacao botao limpar
		if (open == jExtratorFiles.getLimparButton()) {
			jExtratorFiles.getConteudoPainel().setText("");
			logger.info("Limpando documento");
		}

	}

	private void abrirSalvarChooser() {
		int code = chooser.showSaveDialog(jExtratorFiles.getFrame());
		if (code == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			verficaSeExisteESalva(selectedFile);
		}
	}

	private void verficaSeExisteESalva(File selectedFile) {
		if (selectedFile.exists()) {
			int confirmar = JOptionPane.showConfirmDialog(null, "Arquivo já existe\nDeseja sobrescrever ?", "Salvar",
					JOptionPane.YES_NO_OPTION);
			desejaSobrescrever(confirmar, selectedFile);

		} else {
			salvarArquivo(selectedFile);
		}

	}

	private void desejaSobrescrever(int confirmar, File selectedFile) {
		if (confirmar == SALVAR) {
			salvarArquivo(selectedFile);
		} else {
			abrirSalvarChooser();
		}

	}

	private void salvarArquivo(File selectedFile) {
		try (FileOutputStream fos = new FileOutputStream(selectedFile);
				OutputStreamWriter out = new OutputStreamWriter(fos, Charset.forName("UTF-8"))) {
			out.write(jExtratorFiles.getConteudoPainel().getText());
			logger.info("Salvando arquivo em: " + chooser.getSelectedFile().getAbsolutePath());
			Diretorios.setDiretorioCorrente(selectedFile.getAbsolutePath());
		} catch (Exception e) {
			Erro.mostraMensagem();
			logger.error(e);
		}
	}
}
