package com.pouruan.common.image;

import java.io.InputStream;
import java.util.Locale;

import com.pouruan.common.image.ImageInfo;
import com.pouruan.common.image.ImageUtils.Position;

/**
 * ͼƬ������
 * 
 * @author liufang
 * 
 */
public abstract class ImageUtils {
	/**
	 * ͼƬ�ĺ�׺
	 */
	public static final String[] IMAGE_EXT = new String[] { "jpg", "jpeg",
			"gif", "png", "bmp" };

	/**
	 * �Ƿ���ͼƬ
	 * 
	 * @param ext
	 * @return "jpg", "jpeg", "gif", "png", "bmp" Ϊ�ļ���׺����ΪͼƬ
	 */
	public static boolean isValidImageExt(String ext) {
		ext = ext.toLowerCase(Locale.ENGLISH);
		for (String s : IMAGE_EXT) {
			if (s.equalsIgnoreCase(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the underlying input stream contains an image.
	 * 
	 * @param in
	 *            input stream of an image
	 * @return <code>true</code> if the underlying input stream contains an
	 *         image, else <code>false</code>
	 */
	public static boolean isImage(final InputStream in) {
		ImageInfo ii = new ImageInfo();
		ii.setInput(in);
		return ii.check();
	}

	/**
	 * ���ˮӡλ��
	 * 
	 * @param width
	 *            ԭͼ���
	 * @param height
	 *            ԭͼ�߶�
	 * @param p
	 *            ˮӡλ�� 1-5������ֵΪ�����1�����ϣ�2�����ϣ�3�����£�4�����£�5�����롣
	 * @param offsetx
	 *            ˮƽƫ�ơ�
	 * @param offsety
	 *            ��ֱƫ�ơ�
	 * @return ˮӡλ��
	 */
	public static Position markPosition(int width, int height, int p,
			int offsetx, int offsety) {
		if (p < 1 || p > 5) {
			p = (int) (Math.random() * 5) + 1;
		}
		int x, y;
		switch (p) {
		// ����
		case 1:
			x = offsetx;
			y = offsety;
			break;
		// ����
		case 2:
			x = width + offsetx;
			y = offsety;
			break;
		// ����
		case 3:
			x = offsetx;
			y = height + offsety;
			break;
		// ����
		case 4:
			x = width + offsetx;
			y = height + offsety;
			break;
		// ����
		case 5:
			x = (width / 2) + offsetx;
			y = (height / 2) + offsety;
			break;
		default:
			throw new RuntimeException("never reach ...");
		}
		return new Position(x, y);
	}

	/**
	 * ˮӡλ��
	 * 
	 * �������ƫ�������ұ�ƫ������
	 * 
	 * @author liufang
	 * 
	 */
	public static class Position {
		private int x;
		private int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}

}
