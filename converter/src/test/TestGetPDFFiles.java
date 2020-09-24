package test;

import java.util.ArrayList;

import fieldformats.PDFInfo;
import functions.FolderManager;

public class TestGetPDFFiles {
	public static void main(String[] args) {
		String targetDir = "C:\\javaDevs_Test\\PDF_PNG_Converter\\getPDFFiles";

		ArrayList<PDFInfo> pdfInfos = FolderManager.getPDFFiles(targetDir);

		for(PDFInfo pdfInfo : pdfInfos) {
			System.out.println(pdfInfo.getFileFullPath() + " : " + pdfInfo.getFileDir() + " : " + pdfInfo.getFileName());
		}
	}
}
