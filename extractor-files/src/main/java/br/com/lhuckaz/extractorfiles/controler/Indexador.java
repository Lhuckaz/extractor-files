package br.com.lhuckaz.extractorfiles.controler;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.tika.Tika;

import br.com.lhuckaz.extractorfiles.util.Diretorios;

public class Indexador {
	private static Logger logger = Logger.getLogger(Indexador.class);
	// IndexWriter: cria e mantém o índice;
	private IndexWriter writer;
	// Biblioteca que extrai texto de diversos formatos conhecidos;
	private Tika tika;

	public void indexaArquivosDoDiretorio(String diretorioParaIndexar) {
		try {
			File diretorio = new File(Diretorios.retornaIndice());
			apagaIndices(diretorio);
			// Directory: representa o diretório do índice;
			Directory d = new SimpleFSDirectory(diretorio);
			logger.info("Diretório do índice: " + Diretorios.retornaIndice());
			// Analyser/StandardAnalyser: fazem o pré-processamento do texto.
			// Existem analisadores inclusive em português;
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
			// IndexWriterConfig: configurações para criação do índice. No
			// projeto serão utilizados os valores padrão;
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, analyzer);
			// Inicializa o IndexWriter para gravação;
			writer = new IndexWriter(d, config);
			long inicio = System.currentTimeMillis();
			indexaArquivosDoDiretorio(new File(diretorioParaIndexar));
			writer.commit();
			writer.close();
			long fim = System.currentTimeMillis();
			logger.info("Tempo para indexar: " + ((fim - inicio) / 1000) + "s");
		} catch (IOException e) {
			logger.error(e);
		}
	}

	private void apagaIndices(File diretorio) {
		if (diretorio.exists()) {
			File arquivos[] = diretorio.listFiles();
			for (File arquivo : arquivos) {
				arquivo.delete();
			}
		}
	}

	private void indexaArquivosDoDiretorio(File raiz) {
		FilenameFilter filtro = new FilenameFilter() {
			public boolean accept(File arquivo, String nome) {
				if (nome.toLowerCase().endsWith(".pdf") || nome.toLowerCase().endsWith(".odt")
						|| nome.toLowerCase().endsWith(".doc") || nome.toLowerCase().endsWith(".docx")
						|| nome.toLowerCase().endsWith(".ppt") || nome.toLowerCase().endsWith(".pptx")
						|| nome.toLowerCase().endsWith(".xls") || nome.toLowerCase().endsWith(".txt")
						|| nome.toLowerCase().endsWith(".rtf") || nome.toLowerCase().endsWith("")) {
					return true;
				}
				return false;
			}
		};
		// TODO usar filtro para indexar ?
		File[] listFiles = raiz.listFiles();
		if(raiz.listFiles() == null) {
			indexaUmArquivo(raiz);
		} else {
		indexaUmDiretorio(raiz);
		}
		
		
	}

	private void indexaUmArquivo(File arquivo) {
			if (arquivo.isFile()) {
				StringBuffer msg = new StringBuffer();
				msg.append("Indexando o arquivo ");
				msg.append(arquivo.getAbsoluteFile());
				msg.append(", ");
				msg.append(arquivo.length() / 1000);
				msg.append("kb");
				logger.info(msg);
				try {
					// Extrai o conteúdo do arquivo com o Tika
					String textoExtraido = getTika().parseToString(arquivo);
					indexaArquivo(arquivo, textoExtraido);
				} catch (Exception e) {
					logger.error(e);
				}
			} else {
				indexaArquivosDoDiretorio(arquivo);
			}
		
		
	}

	private void indexaUmDiretorio(File raiz) {
		// for (File arquivo : raiz.listFiles(filtro)) {
		for (File arquivo : raiz.listFiles()) {
			if (arquivo.isFile()) {
				StringBuffer msg = new StringBuffer();
				msg.append("Indexando o arquivo ");
				msg.append(arquivo.getAbsoluteFile());
				msg.append(", ");
				msg.append(arquivo.length() / 1000);
				msg.append("kb");
				logger.info(msg);
				try {
					// Extrai o conteúdo do arquivo com o Tika
					String textoExtraido = getTika().parseToString(arquivo);
					indexaArquivo(arquivo, textoExtraido);
				} catch (Exception e) {
					logger.error(e);
				}
			} else {
				indexaArquivosDoDiretorio(arquivo);
			}
		}
	}

	private void indexaArquivo(File arquivo, String textoExtraido) {
		SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
		String ultimaModificacao = formatador.format(arquivo.lastModified());
		// Monta um Document para indexação
		// Field.Store.YES: armazena uma cópia do texto no índice, aumentando
		// muito o seu tamanho;
		// Field.Index.ANALYZED: utilizado quando o campo é de texto livre;
		// Field.Index.NOT_ANALYZED: utilizado quando o campo é um ID, data ou
		// númerico.
		Document documento = new Document();

		documento.add(new TextField("UltimaModificacao", ultimaModificacao, Field.Store.YES));
		documento.add(new TextField("Caminho", arquivo.getAbsolutePath(), Field.Store.YES));
		documento.add(new TextField("Texto", textoExtraido, Field.Store.YES));
		try {
			// Adiciona o Document no índice, mas este só estará disponível para
			// consulta após o commit.
			getWriter().addDocument(documento);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public Tika getTika() {
		if (tika == null) {
			tika = new Tika();
		}
		return tika;
	}

	public IndexWriter getWriter() {
		return writer;
	}

}