package pl.devmate.utils.random.pdf;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class PdfGeneratorTest {

	PdfGenerator pdfGenerator = PdfGenerator.createDefault();
	
	@Test
	void generatePdfParagraphs() throws IOException {
		try (FileOutputStream fos = new FileOutputStream(new File("target/pdfSampleParagraphs.pdf"))) {
			pdfGenerator.writeParagraphs(3000, 20, fos);
		}
		assertTrue(new File("target/pdfSampleParagraphs.pdf").length() > 0);
	}
	
	@Test
	void generatePdfPages() throws IOException {
		try (FileOutputStream fos = new FileOutputStream(new File("target/pdfSamplePages.pdf"))) {
			pdfGenerator.writePages(20, fos);
		}
		assertTrue(new File("target/pdfSamplePages.pdf").length() > 0);
	}

}
