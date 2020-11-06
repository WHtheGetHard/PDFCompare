package functions;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

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
}
