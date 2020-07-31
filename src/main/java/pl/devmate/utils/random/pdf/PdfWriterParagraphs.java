package pl.devmate.utils.random.pdf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import pl.devmate.utils.random.word.WordProducer;

/**
 * Based on example from https://memorynotfound.com/apache-pdfbox-adding-multiline-paragraph/
 */
class PdfWriterParagraphs extends AbstractPdfWriter {
	
	PdfWriterParagraphs(WordProducer wordProducer) {
		super(wordProducer);
	}

	void writeRandomParagraphs(Integer wordsInParagraph, Integer paragraphsCount, OutputStream outputStream) throws IOException {
		try (final PDDocument doc = new PDDocument()) {
			PDPage page = createNextPage(doc);

			PDPageContentStream contentStream = createPageContentStream(doc, page);
			float currentYOffset = startY;
			
			for (int parIndex = 0; parIndex < paragraphsCount; parIndex++) {

				String paragraphText = createRandomWords(wordsInParagraph);
				List<String> lines = parseLines(paragraphText, width);
				
				for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
					String line = lines.get(lineIndex);
					boolean lastLine = lineIndex == lines.size() - 1;
					float charSpacing = calculateCharacterSpacing(justify, lastLine, line);
					contentStream.setCharacterSpacing(charSpacing);
					contentStream.showText(line);
					contentStream.newLineAtOffset(0, LEADING);
					currentYOffset += LEADING;
					
					if (currentYOffset < 60) {
						// close filled page
						contentStream.endText();
						contentStream.close();
						
						// start next page
						page = createNextPage(doc);
						contentStream = createPageContentStream(doc, page);
						
						currentYOffset = startY;
					}
				}
			}
			contentStream.endText();
			contentStream.close();
			
			doc.save(outputStream);
		}
	}
	
}
