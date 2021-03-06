package exec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import fieldformats.PDFFieldCordinations;
import fieldformats.PDFInfo;
import functions.FolderHandler;
import functions.PDFHandler;
import functions.ReadImageText;
import net.sourceforge.tess4j.TesseractException;

public class ExecExtractPDFTextPart {
	public static void main(String[] args) throws IOException, TesseractException {
		String baseDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\Test_ExecExtractPDFTextPart\\0.input\\0.base";

		PDFInfo basePDF = FolderHandler.getPDFFiles(baseDir).get(0);
		basePDF.setPageNumber(PDFHandler.getPDFPage(basePDF));
		ArrayList<PDFFieldCordinations> pdfFieldCordinationsList = PDFHandler.getFieldRange(basePDF);

		String compDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\Test_ExecExtractPDFTextPart\\0.input\\1.comp";
		String outputDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\Test_ExecExtractPDFTextPart\\1.output";

		ArrayList<PDFInfo> compPDFs = FolderHandler.getPDFFiles(compDir);
		for (PDFInfo pdfInfo : compPDFs) {
			pdfInfo.setPageNumber(PDFHandler.getPDFPage(pdfInfo));
		}

		PDFHandler.saveCompFieldPart(pdfFieldCordinationsList, compPDFs, outputDir);

		File[] imageFiles = FolderHandler.getImageFiles(outputDir);

		ArrayList<String> textList = new ArrayList<String>();
		for (File file : imageFiles) {
			String text = ReadImageText.ReadTexts(file.getAbsolutePath());
			textList.add(text);
		}
	}
}
