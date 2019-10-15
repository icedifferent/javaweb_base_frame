package com.pouruan.common.image;

import java.awt.Color;
import java.io.File;
import java.io.IOException;


/**
 * ͼƬ��С�ӿ�
 * 
 * @author liufang
 * 
 */
public interface ImageScale {
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
	 */
	public void resizeFix(File srcFile, File destFile, int boxWidth,
			int boxHeight) throws Exception;

	/**
	 * ��С���ü�ͼƬ
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
	 */
	public void resizeFix(File srcFile, File destFile, int boxWidth,
			int boxHeight, int cutTop, int cutLeft, int cutWidth, int catHeight)
			throws Exception;

	public void imageMark(File srcFile, File destFile, int minWidth,
			int minHeight, int pos, int offsetX, int offsetY, String text,
			Color color, int size, int alpha) throws Exception;

	public void imageMark(File srcFile, File destFile, int minWidth,
			int minHeight, int pos, int offsetX, int offsetY, File markFile)
			throws Exception;
}
