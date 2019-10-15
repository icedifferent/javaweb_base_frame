package com.pouruan.common.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import magick.CompositeOperator;
import magick.DrawInfo;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.PixelPacket;

import org.apache.commons.io.FileUtils;

import com.pouruan.common.image.ImageUtils;
import com.pouruan.common.image.MagickImageScale;
import com.pouruan.common.image.ImageUtils.Position;

public class MagickImageScale {
	/**
	 * ��СͼƬ
	 * 
	 * @param srcFile
	 *            ԭͼƬ
	 * @param destFile
	 *            Ŀ��ͼƬ
	 * @param boxWidth
	 *            ����ͼ�����
	 * @param boxHeight
	 *            ����ͼ���߶�
	 * @throws IOException
	 * @throws MagickException
	 */
	public static void resizeFix(File srcFile, File destFile, int boxWidth,
			int boxHeight) throws IOException, MagickException {
		ImageInfo info = new ImageInfo(srcFile.getAbsolutePath());
		MagickImage image = new MagickImage(info);
		// ������С���
		Dimension dim = image.getDimension();
		int width = (int) dim.getWidth();
		int height = (int) dim.getHeight();
		int zoomWidth;
		int zoomHeight;
		if ((float) width / height > (float) boxWidth / boxHeight) {
			zoomHeight=boxWidth;
			zoomWidth=Math.round((float)width*boxWidth/height);
			/*zoomWidth = boxWidth;
			zoomHeight = Math.round((float) boxWidth * height / width);*/
		} else {
			/*zoomWidth = Math.round((float) boxHeight * width / height);
			zoomHeight = boxHeight;*/
			zoomWidth=boxHeight;
			zoomHeight=Math.round((float)height*boxHeight/width);
		}
		// ��С
		MagickImage scaled = image.scaleImage(zoomWidth, zoomHeight);
		// ���
		scaled.setFileName(destFile.getAbsolutePath());
		scaled.writeImage(info);
		scaled.destroyImages();
	}

	public static void resizeFix(File srcFile, File destFile) throws IOException, MagickException {
		ImageInfo info = new ImageInfo(srcFile.getAbsolutePath());
		MagickImage image = new MagickImage(info);
		// ������С���
		Dimension dim = image.getDimension();
		int width = (int) dim.getWidth();
		int height = (int) dim.getHeight();
		int zoomWidth;
		int zoomHeight;
		if ( width / height >=1) {
			zoomWidth = 180;
			zoomHeight = 180;
		} else {
			zoomWidth = 180;
			zoomHeight = 180;
		}
		// ��С
		MagickImage scaled = image.scaleImage(zoomWidth, zoomHeight);
		// ���
		scaled.setFileName(destFile.getAbsolutePath());
		scaled.writeImage(info);
		scaled.destroyImages();
	}

	
	/**
	 * �ü�����С
	 * 
	 * @param srcFile
	 *            ԭ�ļ�
	 * @param destFile
	 *            Ŀ���ļ�
	 * @param boxWidth
	 *            ����ͼ�����
	 * @param boxHeight
	 *            ����ͼ���߶�
	 * @param cutTop
	 *            �ü�TOP
	 * @param cutLeft
	 *            �ü�LEFT
	 * @param cutWidth
	 *            �ü����
	 * @param catHeight
	 *            �ü��߶�
	 * @throws IOException
	 * @throws MagickException
	 */
	public static void resizeFix(File srcFile, File destFile, int boxWidth,
			int boxHeight, int cutTop, int cutLeft, int cutWidth, int catHeight)
			throws IOException, MagickException {
		ImageInfo info = new ImageInfo(srcFile.getAbsolutePath());
		MagickImage image = new MagickImage(info);
		// ����
		Rectangle rect = new Rectangle(cutTop, cutLeft, cutWidth, catHeight);
		// ����ѹ�����
		MagickImage cropped = image.cropImage(rect);
		Dimension dim = cropped.getDimension();
		int width = (int) dim.getWidth();
		int height = (int) dim.getHeight();
		int zoomWidth;
		int zoomHeight;
		if ((float) width / height > (float) boxWidth / boxHeight) {
			zoomWidth = boxWidth;
			zoomHeight = Math.round((float) boxWidth * height / width);
		} else {
			zoomWidth = Math.round((float) boxHeight * width / height);
			zoomHeight = boxHeight;
		}
		// ��С
		MagickImage scaled = cropped.scaleImage(zoomWidth, zoomHeight);
		// ���
		scaled.setFileName(destFile.getAbsolutePath());
		scaled.writeImage(info);
		scaled.destroyImages();
	}

	public static void imageMark(File srcFile, File destFile, int minWidth,
			int minHeight, int pos, int offsetX, int offsetY,
			String markContent, Color markColor, int markSize, int alpha)
			throws IOException, MagickException {
		ImageInfo info = new ImageInfo(srcFile.getAbsolutePath());
		MagickImage image = new MagickImage(info);

		Dimension dim = image.getDimension();
		int width = (int) dim.getWidth();
		int height = (int) dim.getHeight();
		if (width < minWidth || height < minHeight) {
			image.destroyImages();
			if (!srcFile.equals(destFile)) {
				FileUtils.copyFile(srcFile, destFile);
			}
		} else {
			imageMark(image, info, width, height, pos, offsetX, offsetY,
					markContent, markColor, markSize, alpha);
			image.setFileName(destFile.getAbsolutePath());
			image.writeImage(info);
			image.destroyImages();
		}
	}

	public static void imageMark(File srcFile, File destFile, int minWidth,
			int minHeight, int pos, int offsetX, int offsetY, File markFile)
			throws IOException, MagickException {
		ImageInfo info = new ImageInfo(srcFile.getAbsolutePath());
		MagickImage image = new MagickImage(info);

		Dimension dim = image.getDimension();
		int width = (int) dim.getWidth();
		int height = (int) dim.getHeight();
		if (width < minWidth || height < minHeight) {
			image.destroyImages();
			if (!srcFile.equals(destFile)) {
				FileUtils.copyFile(srcFile, destFile);
			}
		} else {
			imageMark(image, info, width, height, pos, offsetX, offsetY,
					markFile);
			image.setFileName(destFile.getAbsolutePath());
			image.writeImage(info);
			image.destroyImages();
		}
	}

	private static void imageMark(MagickImage image, ImageInfo info, int width,
			int height, int pos, int offsetX, int offsetY, String text,
			Color color, int size, int alpha) throws MagickException {
		Position p = ImageUtils.markPosition(width, height, pos, offsetX,
				offsetY);
		DrawInfo draw = new DrawInfo(info);
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		draw.setFill(new PixelPacket(r * r, g * g, b * b,
				65535 - (alpha * 65535 / 100)));
		draw.setPointsize(size);
		draw.setTextAntialias(true);
		draw.setText(text);
		draw.setGeometry("+" + p.getX() + "+" + p.getY());
		image.annotateImage(draw);
	}

	private static void imageMark(MagickImage image, ImageInfo info, int width,
			int height, int pos, int offsetX, int offsetY, File markFile)
			throws MagickException {
		Position p = ImageUtils.markPosition(width, height, pos, offsetX,
				offsetY);
		MagickImage mark = new MagickImage(new ImageInfo(markFile
				.getAbsolutePath()));
		image.compositeImage(CompositeOperator.AtopCompositeOp, mark, p.getX(),
				p.getY());
		mark.destroyImages();
	}

	public static void main(String[] args) throws Exception {
		long time = System.currentTimeMillis();
		MagickImageScale.resizeFix(
				new File("test/com/jeecms/common/util/1.bmp"), new File(
						"test/com/jeecms/common/util/1-n-3.bmp"), 310, 310, 50,
				50, 320, 320);
		time = System.currentTimeMillis() - time;
		System.out.println("resize new img in " + time + "ms");
	}
}
