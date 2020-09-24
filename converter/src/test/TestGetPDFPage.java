package test;

import java.util.ArrayList;

import fieldformats.PDFInfo;
import functions.FolderManager;
import functions.PDFManager;

public class TestGetPDFPage {
	public static void main(String[] args) {
		String targetDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\getPDFPage";

		ArrayList<PDFInfo> pdfInfos = FolderManager.getPDFFiles(targetDir);

		for(PDFInfo pdfInfo : pdfInfos) {
			System.out.print(pdfInfo.getFileName() + " page number is ");
			int pageNumber = PDFManager.getPDFPage(pdfInfo);
			System.out.println(pageNumber);
		}
	}
}
