package pl.devmate.utils.random.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import pl.devmate.utils.random.word.WordProducer;

class AbstractPdfWriter {

	static final PDFont FONT = PDType1Font.HELVETICA;
	static final float FONT_SIZE = 12;
	static final float LEADING = -1.5f * FONT_SIZE;
	static final float MARGIN_Y = 80;
	static final float MARGIN_X = 60;
	boolean justify = true;

	protected WordProducer wordProducer;
	
	protected float width;
	protected float startX;
	protected float startY;
	
	AbstractPdfWriter(WordProducer wordProducer) {
		this.wordProducer = wordProducer;
	}

	protected List<String> parseLines(String text, float width) throws IOException {
		List<String> lines = new ArrayList<>();
		int lastSpace = -1;
		while (text.length() > 0) {
			int spaceIndex = text.indexOf(' ', lastSpace + 1);
			if (spaceIndex < 0)
				spaceIndex = text.length();
			String subString = text.substring(0, spaceIndex);
			float size = FONT_SIZE * FONT.getStringWidth(subString) / 1000;
			if (size > width) {
				if (lastSpace < 0) {
					lastSpace = spaceIndex;
				}
				subString = text.substring(0, lastSpace);
				lines.add(subString);
				text = text.substring(lastSpace).trim();
				lastSpace = -1;
			} else if (spaceIndex == text.length()) {
				lines.add(text);
				text = "";
			} else {
				lastSpace = spaceIndex;
			}
		}
		return lines;
	}

	protected void calculatePageParameters(PDPage page) {
		PDRectangle mediaBox = page.getMediaBox();
		width = mediaBox.getWidth() - 2 * MARGIN_X;
		startX = mediaBox.getLowerLeftX() + MARGIN_X;
		startY = mediaBox.getUpperRightY() - MARGIN_Y;
	}

	protected PDPage createNextPage(final PDDocument doc) {
		PDPage page = new PDPage();
		doc.addPage(page);
		calculatePageParameters(page);
		return page;
	}

	protected PDPageContentStream createPageContentStream(final PDDocument doc, PDPage page) throws IOException {
		PDPageContentStream contentStream;
		contentStream = new PDPageContentStream(doc, page);
		contentStream.beginText();
		contentStream.setFont(FONT, FONT_SIZE);
		contentStream.newLineAtOffset(startX, startY);
		return contentStream;
	}

	protected float calculateCharacterSpacing(boolean justify, boolean lastLine, String line) throws IOException {
		float charSpacing = 0;
		if (justify && line.length() > 1) {
			float size = FONT_SIZE * FONT.getStringWidth(line) / 1000;
			float free = width - size;
			
			if (free > 0 && !lastLine) {
				charSpacing = free / (line.length() - 1);
			}
		}
		return charSpacing;
	}

	protected String createRandomWords(Integer wordsInParagraph) {
		StringBuilder paragraphTextBuilder = new StringBuilder();
		for (int wordIndex = 0; wordIndex < wordsInParagraph; wordIndex++) {
			String word = wordProducer.randomWord();
			paragraphTextBuilder.append(word + " ");
		}
		paragraphTextBuilder.append("\n\n");
		return paragraphTextBuilder.toString();
	}
}
