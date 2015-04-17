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
import org.apache.lucene.document.StringField;
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
	// Diret�rio que ir� guardar o �ndice;
	private String diretorioDosIndices = Diretorios.retornaIndice();
	// Diret�rio que cont�m os documentos que ser�o indexados;
	private String diretorioParaIndexar = "C:\\Users\\lucas.fernandes\\Desktop\\Arquivos";
	// IndexWriter: cria e mant�m o �ndice;
	private IndexWriter writer;
	// Biblioteca que extrai texto de diversos formatos conhecidos;
	private Tika tika;

	public void indexaArquivosDoDiretorio() {
		try {
			File diretorio = new File(diretorioDosIndices);
			apagaIndices(diretorio);
			// Directory: representa o diret�rio do �ndice;
			Directory d = new SimpleFSDirectory(diretorio);
			logger.info("Diret�rio do �ndice: " + diretorioDosIndices);
			// Analyser/StandardAnalyser: fazem o pr�-processamento do texto.
			// Existem analisadores inclusive em portugu�s;
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
			// IndexWriterConfig: configura��es para cria��o do �ndice. No
			// projeto ser�o utilizados os valores padr�o;
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, analyzer);
			// Inicializa o IndexWriter para grava��o;
			writer = new IndexWriter(d, config);
			long inicio = System.currentTimeMillis();
			indexaArquivosDoDiretorio(new File(diretorioParaIndexar));
			// {12}
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

	public void indexaArquivosDoDiretorio(File raiz) {
		FilenameFilter filtro = new FilenameFilter() {
			public boolean accept(File arquivo, String nome) {
				if (nome.toLowerCase().endsWith(".pdf") || nome.toLowerCase().endsWith(".odt")
						|| nome.toLowerCase().endsWith(".doc") || nome.toLowerCase().endsWith(".docx")
						|| nome.toLowerCase().endsWith(".ppt") || nome.toLowerCase().endsWith(".pptx")
						|| nome.toLowerCase().endsWith(".xls") || nome.toLowerCase().endsWith(".txt")
						|| nome.toLowerCase().endsWith(".rtf")
						//|| nome.toLowerCase().endsWith("")
						) {
					return true;
				}
				return false;
			}
		};
		for (File arquivo : raiz.listFiles(filtro)) {
			if (arquivo.isFile()) {
				StringBuffer msg = new StringBuffer();
				msg.append("Indexando o arquivo ");
				msg.append(arquivo.getAbsoluteFile());
				msg.append(", ");
				msg.append(arquivo.length() / 1000);
				msg.append("kb");
				logger.info(msg);
				try {
					// Extrai o conte�do do arquivo com o Tika;
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
		// Monta um Document para indexa��o
		// Field.Store.YES: armazena uma c�pia do texto no �ndice, aumentando
		// muito o seu tamanho;
		// Field.Index.ANALYZED: utilizado quando o campo � de texto livre;
		// Field.Index.NOT_ANALYZED: utilizado quando o campo � um ID, data ou
		// n�merico.
		Document documento = new Document();
		// documento.add(new StringField("UltimaModificacao", ultimaModificacao, Field.Store.YES));
		// documento.add(new StringField("Caminho", arquivo.getAbsolutePath(), Field.Store.YES));
		// documento.add(new StringField("Texto", textoExtraido, Field.Store.YES));
		
		documento.add(new TextField("UltimaModificacao", ultimaModificacao, Field.Store.YES));
		documento.add(new TextField("Caminho", arquivo.getAbsolutePath(), Field.Store.YES));
		documento.add(new TextField("Texto", textoExtraido, Field.Store.YES));
		try {
			// Adiciona o Document no �ndice, mas este s� estar� dispon�vel para
			// consulta ap�s o commit.
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

	public static void main(String[] args) {
		Indexador indexador = new Indexador();
		indexador.indexaArquivosDoDiretorio();
	}
}