package br.com.lhuckaz.extractorfiles.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class AutoDetector {

	public static String metaDados(File file) throws IOException, SAXException, TikaException {
		FileInputStream fileInputStream = new FileInputStream(file);
		ContentHandler contenthandler = new BodyContentHandler();
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
