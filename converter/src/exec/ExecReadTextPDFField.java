package exec;

import java.util.ArrayList;

import fieldformats.PDFFieldCordinations;
import fieldformats.PDFInfo;
import functions.FolderHandler;
import functions.PDFHandler;

public class ExecReadTextPDFField {
	public static void main(String[] args) {
		String baseDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\Test_ExecReadTextPDFField\\0.input\\0.base";

		PDFInfo basePDF = FolderHandler.getPDFFiles(baseDir).get(0);
		basePDF.setPageNumber(PDFHandler.getPDFPage(basePDF));
		ArrayList<PDFFieldCordinations> pdfFieldCordinationsList = PDFHandler.getFieldRange(basePDF);

		String compDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\Test_ExecReadTextPDFField\\0.input\\1.comp";

		ArrayList<PDFInfo> compPDFList = FolderHandler.getPDFFiles(compDir);
		for (PDFInfo compPDF : compPDFList) {
			compPDF.setPageNumber(PDFHandler.getPDFPage(compPDF));
		}

		ArrayList<String> texts = PDFHandler.getFieldText(pdfFieldCordinationsList, compPDFList);
		for (String text : texts) {
			System.out.println(text);
		}
	}
}
