package pl.devmate.utils.random.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import pl.devmate.utils.random.word.RandomWordProducer;
import pl.devmate.utils.random.word.WordProducer;

public class PdfGenerator {

	private final WordProducer wordProducer;
	
	public static PdfGenerator createDefault() {
		try {
			return new PdfGenerator(new RandomWordProducer());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public PdfGenerator(WordProducer wordProducer) {
		this.wordProducer = wordProducer; 
	}
	
	public void writeParagraphs(Integer wordsInParagraph, Integer paragraphsCount, OutputStream outputStream) throws IOException {
		PdfWriterParagraphs paragraphWriter = new PdfWriterParagraphs(wordProducer);
		paragraphWriter.writeRandomParagraphs(wordsInParagraph, paragraphsCount, outputStream);
	}

	public void writePages(int pagesCount, FileOutputStream fos) throws IOException {
		PdfWriterPages pagesWriter = new PdfWriterPages(wordProducer);
		pagesWriter.writeRandomPages(pagesCount, fos);
	}
	
}
