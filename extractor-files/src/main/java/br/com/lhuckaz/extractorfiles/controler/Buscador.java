package br.com.lhuckaz.extractorfiles.controler;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import br.com.lhuckaz.extractorfiles.util.Diretorios;
import br.com.lhuckaz.extractorfiles.util.Erro;

public class Buscador {
	private static Logger logger = Logger.getLogger(Buscador.class);

	public String buscaComParser(String parametro) {
		StringBuffer resultados = new StringBuffer();
		try {
			Directory diretorio = new SimpleFSDirectory(new File(Diretorios.retornaIndice()));
			// IndexReader: classe abstrata responsável por acessar o índice;
			IndexReader leitor = DirectoryReader.open(diretorio);
			// IndexSearcher: implementa os métodos necessários para realizar
			// buscas em um índice;
			IndexSearcher buscador = new IndexSearcher(leitor);
			Analyzer analisador = new StandardAnalyzer(Version.LUCENE_47);
			// QueryParser/Query: representa a consulta do usuário. Outros
			// exemplos de query podem ser vistos no Javadoc;
			QueryParser parser = new QueryParser(Version.LUCENE_47, "Texto", analisador);
			Query consulta = parser.parse(parametro);
			long inicio = System.currentTimeMillis();
			// Realiza a busca e armazena o resultado em um TopDocs;
			TopDocs resultado = buscador.search(consulta, 100);
			long fim = System.currentTimeMillis();
			int totalDeOcorrencias = resultado.totalHits;
			resultados.append("Total de documentos encontrados:" + totalDeOcorrencias + "\n");
			resultados.append("Tempo total para busca: " + (fim - inicio) + "ms" + "\n");
			logger.info("Total de documentos encontrados:" + totalDeOcorrencias);
			logger.info("Tempo total para busca: " + (fim - inicio) + "ms");
			// ScoreDoc: representa cada um dos documentos retornados na busca.
			for (ScoreDoc sd : resultado.scoreDocs) {
				Document documento = buscador.doc(sd.doc);
				resultados.append("Caminho:" + documento.get("Caminho") + "\n");
				resultados.append("Última modificação:" + documento.get("UltimaModificacao") + "\n");
				resultados.append("Score:" + sd.score + "\n");
				resultados.append("--------" + "\n");
				logger.info("Caminho:" + documento.get("Caminho"));
				logger.info("Última modificação:" + documento.get("UltimaModificacao"));
				logger.info("Score:" + sd.score);
				logger.info("--------");
			}
			diretorio.close();
			leitor.close();
			analisador.close();
		} catch (Exception e) {
			Erro.mostraMensagem();
			logger.error(e);
		}
		return resultados.toString();
	}
}