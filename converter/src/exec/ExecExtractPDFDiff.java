package exec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import fieldformats.PDFInfo;
import functions.FolderManager;
import functions.PDFManager;
import functions.ReadImageText;
import net.sourceforge.tess4j.TesseractException;

public class ExecExtractPDFDiff {
	public static void main(String[] args) throws IOException, TesseractException {
		String baseDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\Test_getPDFsDiff\\0.input\\0.base";
		String compDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\Test_getPDFsDiff\\0.input\\1.comp";
		String outputDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\Test_getPDFsDiff\\1.output";


		PDFInfo basePDF = FolderManager.getPDFFiles(baseDir).get(0);
		basePDF.setPageNumber(PDFManager.getPDFPage(basePDF));

		ArrayList<PDFInfo> compPDFs = FolderManager.getPDFFiles(compDir);
		for (PDFInfo pdfInfo : compPDFs) {
			pdfInfo.setPageNumber(PDFManager.getPDFPage(pdfInfo));
		}

		PDFManager.getPDFsDiff(basePDF, compPDFs, outputDir);

		File[] imageFiles = FolderManager.getImageFiles(outputDir);

		for (File file : imageFiles) {
			String text = ReadImageText.ReadTexts(file.getAbsolutePath());
			System.out.println(file.getName() + "に定義されている文字列");
			System.out.println(text);
			System.out.println("---------------------------------------------");
		}
	}
}
