package fieldformats;

import java.awt.Point;

public class PDFFieldCordinations {
	/**
	 * フィールドの左上
	 */
	private Point upperLeft = new Point();

	/**
	 * フィールドの右上
	 */
	private Point upperRight = new Point();

	/**
	 * フィールドの左下
	 */
	private Point lowerLeft = new Point();

	/**
	 * フィールドの右下
	 */
	private Point lowerRight = new Point();

	public Point getUpperLeft() {
		return upperLeft;
	}

	public void setUpperLeft(int x, int y) {
		this.upperLeft.x = x;
		this.upperLeft.y = y;
	}

	public int getStartX() {
		return this.upperLeft.x;
	}

	public int getStartY() {
		return this.upperLeft.y;
	}

	public Point getUpperRight() {
		return upperRight;
	}

	public void setUpperRight(int x, int y) {
		this.upperRight.x = x;
		this.upperRight.y = y;
	}

	public Point getLowerLeft() {
		return lowerLeft;
	}

	public void setLowerLeft(int x, int y) {
		this.lowerLeft.x = x;
		this.lowerLeft.y = y;
	}

	public Point getLowerRight() {
		return lowerRight;
	}

	public void setLowerRight(int x, int y) {
		this.lowerRight.x = x;
		this.lowerRight.y = y;
	}

	public void calcLowerRight() {
		this.lowerRight.x = this.upperRight.x;
		this.lowerRight.y = this.lowerLeft.y;
	}

	public int getEndX() {
		return this.lowerRight.x;
	}

	public int getEndY() {
		return this.lowerRight.y;
	}
}
