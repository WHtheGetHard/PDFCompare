package functions;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import fieldformats.PDFInfo;

public class FolderManager {
	/**
	 * フォルダからPDFファイル一覧を取得する
	 * @param	PDFファイルがあるフォルダ
	 * @return	フォルダ内のPDFファイルの情報一覧
	 */
	public static ArrayList<PDFInfo> getPDFFiles(String dir) {
		ArrayList<PDFInfo> pdfInfos = new ArrayList<PDFInfo>();

		File file = new File(dir);
		File[] files = file.listFiles(pdfFilter);

		for(File pdf_file : files) {
			PDFInfo pdfInfo = new PDFInfo();
			pdfInfo.setFileFullPath(pdf_file.getPath());
			pdfInfo.setFileDir(dir);
			pdfInfo.setFileName(pdf_file.getName());
			pdfInfos.add(pdfInfo);
		}
		return pdfInfos;
	}

	private static FilenameFilter pdfFilter = new FilenameFilter() {
		public boolean accept(File file, String str) {
			int index = str.lastIndexOf(".");

			String ext = str.substring(index+1).toLowerCase();

			if ("pdf".equals(ext)) {
				return true;
			} else {
				return false;
			}
		}
	};

	/**
	 * フォルダからimage一覧を取得する
	 * @param	imageファイルがあるフォルダ
	 * @return	フォルダ内のimageファイル一覧
	 */
	public static File[] getImageFiles(String dir) {
		File file = new File(dir);
		File[] files = file.listFiles(imageFilter);
		return files;
	}

	private static FilenameFilter imageFilter = new FilenameFilter() {
		public boolean accept(File file, String str) {
			int index = str.lastIndexOf(".");

			String ext = str.substring(index+1).toLowerCase();

			if ("png".equals(ext) || "jpg".equals(ext)) {
				return true;
			} else {
				return false;
			}
		}
	};

	/**
	 * 2つのフォルダのPDFファイルが一致しているかを確認
	 * @param	基準のフォルダ
	 * @param	比較対象のフォルダ
	 * @return	比較結果
	 */
	public static String hasSamePDFFiles(String baseDir, String compDir) {
		ArrayList<String> baseFileNames = getPDFFileNames(baseDir);
		ArrayList<String> compFileNames = getPDFFileNames(compDir);

		boolean isCorresponded = baseFileNames.equals(compFileNames);

		if (isCorresponded) return "File Number : Corresponded.\n";

		StringBuilder differentDetail = new StringBuilder("File Number : Different.\n");
		differentDetail.append(nonInTheOtherFolder(baseFileNames, compFileNames, true));
		differentDetail.append(nonInTheOtherFolder(compFileNames, baseFileNames, false));

		return differentDetail.toString();
	}

	private static ArrayList<String> getPDFFileNames(String dir) {
		File file = new File(dir);
		File[] files = file.listFiles(pdfFilter);
		ArrayList<String> fileNames = new ArrayList<String>();
		for(File f : files) fileNames.add(f.getName());

		return fileNames;
	}

	private static String nonInTheOtherFolder(ArrayList<String> folder1, ArrayList<String> folder2, boolean isBaseFolder) {
		StringBuilder nonList = isBaseFolder ? new StringBuilder("compDir hasn't : ") : new StringBuilder("baseDir hasn't : ");

		for(String fileName : folder1) {
			if (!folder2.contains(fileName)) nonList.append(fileName + " ");
		}

		nonList.append("\n");

		return nonList.toString();
	}
}
