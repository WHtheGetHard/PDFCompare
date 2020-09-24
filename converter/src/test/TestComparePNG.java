package test;

import java.util.ArrayList;

import fieldformats.PDFInfo;
import functions.FolderManager;
import functions.PDFManager;

public class TestComparePNG {
	public static void main(String[] args) {
		String baseDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\comparePNG\\0.input\\base";
		ArrayList<PDFInfo> basePDFInfos = FolderManager.getPDFFiles(baseDir);

		for(PDFInfo basePDFInfo : basePDFInfos) {
			basePDFInfo.setPageNumber(PDFManager.getPDFPage(basePDFInfo));
		}

		String compDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\comparePNG\\0.input\\comp";
		ArrayList<PDFInfo> compPDFInfos = FolderManager.getPDFFiles(compDir);

		for(PDFInfo compPDFInfo : compPDFInfos) {
			compPDFInfo.setPageNumber(PDFManager.getPDFPage(compPDFInfo));
		}

		int pdfFileNumber = basePDFInfos.size();
		String outputDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\comparePNG\\1.output";
		for(int i = 0; i < pdfFileNumber; ++i) {
			boolean isMatched = PDFManager.comparePDF(basePDFInfos.get(i), compPDFInfos.get(i), outputDir);
			if (isMatched) {
				System.out.println(basePDFInfos.get(i).getFileName() + " and " + compPDFInfos.get(i).getFileName() + " is same.");
			} else {
				System.out.println(basePDFInfos.get(i).getFileName() + " and " + compPDFInfos.get(i).getFileName() + " isn't same.");
			}
		}
	}
}
