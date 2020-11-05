package exec;

import java.util.ArrayList;
import java.util.Scanner;

import fieldformats.PDFInfo;
import functions.FolderManager;
import functions.PDFManager;

public class ExecExtractPDFDiff {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Input base folder: ");
		String baseDir = sc.nextLine();
		System.out.print("Input comp folder: ");
		String compDir = sc.nextLine();
		System.out.print("Input output folder: ");
		String outputDir = sc.nextLine();

		sc.close();

		PDFInfo basePDF = FolderManager.getPDFFiles(baseDir).get(0);
		basePDF.setPageNumber(PDFManager.getPDFPage(basePDF));

		ArrayList<PDFInfo> compPDFs = FolderManager.getPDFFiles(compDir);
		for (PDFInfo pdfInfo : compPDFs) {
			pdfInfo.setPageNumber(PDFManager.getPDFPage(pdfInfo));
		}

		PDFManager.getPDFsDiff(basePDF, compPDFs, outputDir);
	}
}
