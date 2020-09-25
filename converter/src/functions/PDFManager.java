package functions;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import fieldformats.PDFInfo;
;


public class PDFManager {
	/**
	 * @brief	対象のPDFファイルのページ数を取得する
	 * @param	ページ数を取得したいPDFInfo
	 * @return	ページ数
	 */
	public static int getPDFPage(PDFInfo pdfInfo) {
		int pageNumber = 0;

		try (FileInputStream in = new FileInputStream(pdfInfo.getFileFullPath());
				PDDocument pdfDoc = PDDocument.load(in)) {
			pageNumber = pdfDoc.getNumberOfPages();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pageNumber;
	}

	private static boolean compareImage(BufferedImage baseImg, BufferedImage compImg, OutputStream os) throws IOException {
		boolean matched = true;
		for(int x = 0; x < baseImg.getWidth(); ++x) {
			for(int y = 0; y < baseImg.getHeight(); ++y) {
				int basePix = baseImg.getRGB(x, y);
				int compPix = compImg.getRGB(x, y);

				if (basePix != compPix) {
					matched = false;
					compImg.setRGB(x,  y, Color.MAGENTA.getRGB());
				}
			}
		}

		if (os != null) {
			ImageIO.write(compImg, "png", os);
		}

		return matched;
	}

	/**
	 * @brief	2つのPDFをビジュアルで比較して、比較結果をページ単位でpngで出力する
	 * @param	基準となるPDFファイル
	 * @param	比較対象のPDFファイル
	 * @param	比較結果を出力するフォルダのパス
	 * @return	2つのPDFファイルの比較結果
	 */
	public static String comparePDF(PDFInfo basePDF, PDFInfo compPDF, String dir) {
		StringBuilder comparedResult = new StringBuilder(basePDF.getFileName() + " Result: ");

		String baseFilePath = basePDF.getFileFullPath();
		String compFilePath = compPDF.getFileFullPath();

		try (FileInputStream baseStream = new FileInputStream(baseFilePath);
				FileInputStream compStream = new FileInputStream(compFilePath);
				PDDocument baseDoc = PDDocument.load(baseStream);
				PDDocument compDoc = PDDocument.load(compStream)) {


			PDFRenderer baseRend = new PDFRenderer(baseDoc);
			PDFRenderer compRend = new PDFRenderer(compDoc);

			String fileName = basePDF.getFileName();
			int basePageNumber = basePDF.getPageNumber();
			int compPageNumber = compPDF.getPageNumber();

			boolean isSameAppearance = true;
			if (basePageNumber != compPageNumber) isSameAppearance = false;

			int minPageNumber = Math.min(basePageNumber, compPageNumber);
			for(int i = 0; i < minPageNumber; ++i) {
				BufferedImage baseImg = baseRend.renderImageWithDPI(i, 144, ImageType.RGB);
				BufferedImage compImg = compRend.renderImageWithDPI(i, 144, ImageType.RGB);

				Path resultDir = Paths.get(dir + "\\diff-" + fileName + i + ".png");

				try (OutputStream os = Files.newOutputStream(resultDir)) {

					if (isSameAppearance) {
						isSameAppearance = compareImage(baseImg, compImg,os);
					} else {
						compareImage(baseImg, compImg, os);
					}
				}
			}

			if (isSameAppearance) {
				comparedResult.append("Same.\n");
			} else {
				comparedResult.append("Different.\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return comparedResult.toString();
	}

	/**
	 * @brief	2つのPDFリストをビジュアルで比較して、比較結果をページ単位でpngで出力する
	 * @param	基準となるPDFファイルのリスト
	 * @param	比較対象のPDFファイルのリスト
	 * @param	比較結果を出力するフォルダのパス
	 * @return	各PDFファイルの結果
	 */
	public static String comparePDF(ArrayList<PDFInfo> basePDFs, ArrayList<PDFInfo> compPDFs, String dir) {
		StringBuilder comparedResults = new StringBuilder();

		int baseFileNumbers = basePDFs.size();
		int compFileNumbers = compPDFs.size();

		for(int baseCounter = 0; baseCounter < baseFileNumbers; ++baseCounter) {
			for(int compCounter = 0; compCounter < compFileNumbers; ++compCounter) {
				String baseFileName = basePDFs.get(baseCounter).getFileName();
				String compFileName = compPDFs.get(compCounter).getFileName();

				if (baseFileName == null) continue;
				if (!baseFileName.equals(compFileName)) continue;

				comparedResults.append(comparePDF(basePDFs.get(baseCounter), compPDFs.get(compCounter), dir));
			}
		}

		return comparedResults.toString();
	}
}
