package exec;

import java.util.ArrayList;
import java.util.Scanner;

import fieldformats.PDFInfo;
import functions.FolderHandler;
import functions.PDFManager;

public class ExecComparePDF {
	public static void main(String[]args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Input base folder: ");
		String baseDir = sc.nextLine();
		System.out.print("Input comp folder: ");
		String compDir = sc.nextLine();
		System.out.print("Input output folder: ");
		String outputDir = sc.nextLine();

		StringBuilder comparedResult = new StringBuilder();
		comparedResult.append(FolderHandler.hasSamePDFFiles(baseDir, compDir));

		ArrayList<PDFInfo> basePDFs = FolderHandler.getPDFFiles(baseDir);
		for(PDFInfo pdfInfo : basePDFs) {
			pdfInfo.setPageNumber(PDFManager.getPDFPage(pdfInfo));
		}

		ArrayList<PDFInfo> compPDFs = FolderHandler.getPDFFiles(compDir);
		for(PDFInfo pdfInfo : compPDFs) {
			pdfInfo.setPageNumber(PDFManager.getPDFPage(pdfInfo));
		}

		comparedResult.append(PDFManager.comparePDFs(basePDFs, compPDFs, outputDir));

		sc.close();

		System.out.println("Compare Result");
		System.out.println(comparedResult.toString());
	}
}
