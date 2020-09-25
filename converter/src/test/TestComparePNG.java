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

		String outputDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\comparePNG\\1.output";

		String result = PDFManager.comparePDF(basePDFInfos, compPDFInfos, outputDir);
		System.out.println(result);
	}
}
