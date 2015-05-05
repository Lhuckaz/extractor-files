package br.com.lhuckaz.extractorfiles.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

public class AutoDetector {

	/**
	 * @param file
	 *            Recebe um arquivo para reotrnar os metadados
	 * @return Retorna a String com os metadados
	 * @throws Exception
	 */
	public static String metaDados(File file) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(file);
		// Alterando tamanho maximo suportado do arquivo
		ContentHandler contenthandler = new BodyContentHandler(10*1024*1024);
		Metadata metadata = new Metadata();
		Parser parser = new AutoDetectParser();
		parser.parse(fileInputStream, contenthandler, metadata, new ParseContext());

		StringBuffer msg = new StringBuffer();

		for (String nome : metadata.names()) {
			msg.append(nome + " : " + metadata.get(nome) + "\n");
		}
		
		return msg.toString();

	}

}
