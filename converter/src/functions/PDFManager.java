package functions;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	 * @brief	2つのPDFを比較して、比較結果をページ単位でpngで出力する
	 * @param	基準となるPDFファイル
	 * @param	比較対象のPDFファイル
	 * @param	比較結果を出力するフォルダのパス
	 * @return	2つのPDFファイルが一致するかどうか
	 */
	public static boolean comparePDF(PDFInfo basePDF, PDFInfo compPDF, String dir) {
		boolean isMatched = true;

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

			if (basePageNumber != compPageNumber) {
				return false;
			}

			for(int i = 0; i < basePageNumber; ++i) {
				BufferedImage baseImg = baseRend.renderImageWithDPI(i, 144, ImageType.RGB);
				BufferedImage compImg = compRend.renderImageWithDPI(i, 144, ImageType.RGB);

				Path resultDir = Paths.get(dir + "\\diff-" + fileName + i + ".png");

				try (OutputStream os = Files.newOutputStream(resultDir)) {
					isMatched = compareImage(baseImg,compImg, os);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return isMatched;
	}
}
