package fieldformats;

public class PDFInfo {
	/**
	 * ファイルのフルパス
	 */
	private String fileFullPath;

	/**
	 * ファイルが格納されているフォルダのフルパス
	 */
	private String fileDir;

	/**
	 * ファイル名
	 */
	private String fileName;

	/**
	 * PDFファイルのページ数
	 */
	private int pageNumber;

	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}
