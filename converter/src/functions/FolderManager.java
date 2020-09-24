package functions;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import fieldformats.PDFInfo;

public class FolderManager {
	/**
	 * @brief	フォルダからPDFファイル一覧を取得する
	 * @param	PDFファイルがあるフォルダ
	 * @return	フォルダ内のPDFファイルの情報一覧
	 */
	public static ArrayList<PDFInfo> getPDFFiles(String dir) {
		ArrayList<PDFInfo> pdfInfos = new ArrayList<PDFInfo>();

		File file = new File(dir);
		File[] files = file.listFiles(filter);

		for(File pdf_file : files) {
			PDFInfo pdfInfo = new PDFInfo();
			pdfInfo.setFileFullPath(pdf_file.getPath());
			pdfInfo.setFileDir(dir);
			pdfInfo.setFileName(pdf_file.getName());
			pdfInfos.add(pdfInfo);
		}
		return pdfInfos;
	}

	private static FilenameFilter filter = new FilenameFilter() {
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
}
