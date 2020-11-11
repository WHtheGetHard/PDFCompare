package functions;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import fieldformats.PDFFieldCordinations;

public class ImageHandler {
	/**
	 * 2つのイメージを比較して、差異箇所をマジェンタにする
	 * @param	基準となるイメージ
	 * @param	比較対象となるイメージ
	 * @param	出力先
	 * @return	2つのイメージに差異があるかどうか
	 * @throws	IOException
	 */
	public static boolean compareImage(BufferedImage baseImg, BufferedImage compImg, OutputStream os) throws IOException {
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
	 * compImgとbaseImgと異なる点のみを抜き取ってpngとして保存する
	 * @param baseImg
	 * @param compImg
	 * @param blankImg
	 * @param os
	 * @throws IOException
	 */
	public static void saveDiffImage(BufferedImage baseImg, BufferedImage compImg, BufferedImage blankImg, OutputStream os) throws IOException {
		for (int x = 0; x < baseImg.getWidth(); ++x) {
			for (int y = 0; y < baseImg.getHeight(); ++y) {
				int basePix = baseImg.getRGB(x, y);
				int compPix = compImg.getRGB(x, y);

				if (basePix != compPix) {
					blankImg.setRGB(x, y, compPix);
				}
			}
		}

		if (os != null) {
			ImageIO.write(blankImg, "png", os);
		}
	}

	// 以下テスト
	/**
	 * イメージからフィールド（灰色：RGBが-3092786）のリストを取得する
	 * @param	フィールドが定義されている原本のイメージバッファ
	 * @return	フィールドの範囲で表されるリスト
	 */
	public static ArrayList<PDFFieldCordinations> getFieldPartList(BufferedImage baseImg) {

		ArrayList<PDFFieldCordinations> pdfFieldCordinationsList = new ArrayList<PDFFieldCordinations>();
		for (int x = 0; x < baseImg.getWidth(); ++x) {
			for (int y = 0; y < baseImg.getHeight(); ++y) {
				int blankPix = baseImg.getRGB(x, y);

				if (blankPix == -5264469) {
					boolean isAlreadySearched = alreadySearched(pdfFieldCordinationsList, x, y);
					if (isAlreadySearched) continue;
					pdfFieldCordinationsList.add(getFieldRange(baseImg, x, y));
				}
			}
		}

		return pdfFieldCordinationsList;
	}

	/**
	 * 現在の座標は既にフィールドとして情報を持っているかどうか
	 * @param	既に取得済みのフィールドの座標
	 * @param	x座標
	 * @param	y座標
	 * @return	既にフィールドとして情報を持っている
	 */
	private static boolean alreadySearched(ArrayList<PDFFieldCordinations> pdfFieldCordinationsList, int x, int y) {
		for (PDFFieldCordinations pdfFieldCordinations : pdfFieldCordinationsList) {
			int startX = pdfFieldCordinations.getStartX();
			int endX = pdfFieldCordinations.getEndX();

			int startY = pdfFieldCordinations.getStartY();
			int endY = pdfFieldCordinations.getEndY();

			if (startX <= x && x <= endX && startY <= y && y <= endY) return true;
		}
		return false;
	}

	/**
	 * フィールドの範囲を取得する
	 * @param	blankImg
	 * @param	フィールドの始点となるX座標
	 * @param	フィールドの始点となるY座標
	 * @return	フィールドの範囲
	 */
	private static PDFFieldCordinations getFieldRange(BufferedImage blankImg, int startX, int startY) {
		int endY = blankImg.getHeight();
		for (int y = startY; y < blankImg.getHeight(); ++y) {
			int fieldPix = blankImg.getRGB(startX, y);
			if (fieldPix != -5264469) {
				endY = y - 1;
				break;
			}
		}

		int endX = blankImg.getWidth();
		for (int x = startX; x < blankImg.getWidth(); ++x) {
			int fieldPix = blankImg.getRGB(x, startY);
			if (fieldPix != -5264469) {
				endX = x - 1;
				break;
			}
		}

		PDFFieldCordinations pdfFieldCordinations = new PDFFieldCordinations();
		pdfFieldCordinations.setUpperLeft(startX, startY);
		pdfFieldCordinations.setUpperRight(endX, startY);
		pdfFieldCordinations.setLowerLeft(startX, endY);
		pdfFieldCordinations.calcLowerRight();

		return pdfFieldCordinations;
	}

	/**
	 * 入力されたフィールドのみをpngとして保存する
	 * @param pdfFieldCordinationsList
	 * @param compImg
	 * @param blankImg
	 * @param outputDir
	 * @param compFileName
	 */
	public static void saveInputField(ArrayList<PDFFieldCordinations> pdfFieldCordinationsList, BufferedImage compImg, BufferedImage blankImg, String outputDir, String compFileName) {
		int counter = 0;
		for (PDFFieldCordinations pdfFieldCordinations : pdfFieldCordinationsList) {
			Path resultDir = Paths.get(outputDir + "\\Diff-" + compFileName + counter + ".png");

			for (int x = pdfFieldCordinations.getStartX(); x < pdfFieldCordinations.getEndX(); ++x) {
				for (int y = pdfFieldCordinations.getStartY(); y < pdfFieldCordinations.getEndY(); ++y) {
					blankImg.setRGB(x, y, compImg.getRGB(x, y));
				}
			}

			try (OutputStream os = Files.newOutputStream(resultDir)) {
				ImageIO.write(blankImg, ".png", os);
			} catch (IOException e) {
				e.printStackTrace();
			}

			++counter;
		}
	}
}
