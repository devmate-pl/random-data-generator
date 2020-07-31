package pl.devmate.utils.random.pdf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import pl.devmate.utils.random.word.WordProducer;

class PdfWriterPages extends AbstractPdfWriter {
	
	PdfWriterPages(WordProducer wordProducer) {
		super(wordProducer);
	}

	void writeRandomPages(Integer pagesCount, OutputStream outputStream) throws IOException {
		try (final PDDocument doc = new PDDocument()) {
			for (int pageIndex = 0; pageIndex < pagesCount; pageIndex++) {
				PDPage page = createNextPage(doc);
				PDPageContentStream contentStream = createPageContentStream(doc, page);
				float currentYOffset = startY;

				// 500 words is enough to fill a standard page.
				String pageText = createRandomWords(500);
				List<String> lines = parseLines(pageText, width);
				
				for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
					String line = lines.get(lineIndex);
					boolean lastLine = lineIndex == lines.size() - 1;
					float charSpacing = calculateCharacterSpacing(justify, lastLine, line);
					contentStream.setCharacterSpacing(charSpacing);
					contentStream.showText(line);
					contentStream.newLineAtOffset(0, LEADING);
					currentYOffset += LEADING;
					
					if (currentYOffset < 60) {
						break;
					}
				}
				contentStream.endText();
				contentStream.close();
			}
			doc.save(outputStream);
		}
	}
	
}
