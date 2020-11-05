package functions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ReadImageText {
	/**
	 * 指定された画像の文字列を読み取る
	 * @param	画像のパス
	 * @return	画像内の文字列
	 * @throws IOException
	 * @throws TesseractException
	 */
	public static String ReadTexts(String filePath) throws IOException, TesseractException {

		File imageFile = new File(filePath);
		BufferedImage image = ImageIO.read(imageFile);

		ITesseract tesseract = new Tesseract();
		tesseract.setDatapath("C:\\others_source\\Tess4J-3.4.8-src\\Tess4J\\tessdata");
		tesseract.setLanguage("jpn");
		String str = tesseract.doOCR(image);

		return str;
	}
}
