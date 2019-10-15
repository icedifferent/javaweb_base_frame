package com.pouruan.common.image;

import java.awt.Color;
import java.io.File;

import magick.Magick;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pouruan.common.image.AverageImageScale;
import com.pouruan.common.image.ImageScale;
import com.pouruan.common.image.ImageScaleImpl;
import com.pouruan.common.image.MagickImageScale;

/**
 * ͼƬ��С��
 * 
 * ���ݻ������ѡ��javaͼƬ��С��ʽ��רҵ��magickͼƬ��С��ʽ
 * 
 * @author liufang
 * 
 */
public class ImageScaleImpl implements ImageScale {
	private static final Logger log = LoggerFactory
			.getLogger(ImageScaleImpl.class);

	public void resizeFix(File srcFile, File destFile, int boxWidth,
			int boxHeight) throws Exception {
		if (isMagick) {
			if(boxWidth!=-1 && boxHeight!=-1){
				MagickImageScale.resizeFix(srcFile, destFile, boxWidth, boxHeight);
			}
			else{
				MagickImageScale.resizeFix(srcFile, destFile);
			}
			

		} else {
			if(boxWidth!=-1 && boxHeight!=-1){
				AverageImageScale.resizeFix(srcFile, destFile, boxWidth, boxHeight);
			}
			else{
				AverageImageScale.resizeFix(srcFile, destFile);
			}

			
		}
	}

	public void resizeFix(File srcFile, File destFile, int boxWidth,
			int boxHeight, int cutTop, int cutLeft, int cutWidth, int catHeight)
					throws Exception {
		if (isMagick) {
			MagickImageScale.resizeFix(srcFile, destFile, boxWidth, boxHeight,
					cutTop, cutLeft, cutWidth, catHeight);
		} else {
			AverageImageScale.resizeFix(srcFile, destFile, boxWidth, boxHeight,
					cutTop, cutLeft, cutWidth, catHeight);
		}
	}

	public void imageMark(File srcFile, File destFile, int minWidth,
			int minHeight, int pos, int offsetX, int offsetY, String text,
			Color color, int size, int alpha) throws Exception {
		if (isMagick) {
			MagickImageScale.imageMark(srcFile, destFile, minWidth, minHeight,
					pos, offsetX, offsetY, text, color, size, alpha);
		} else {
			AverageImageScale.imageMark(srcFile, destFile, minWidth, minHeight,
					pos, offsetX, offsetY, text, color, size, alpha);
		}
	}

	public void imageMark(File srcFile, File destFile, int minWidth,
			int minHeight, int pos, int offsetX, int offsetY, File markFile)
					throws Exception {
		if (isMagick) {
			MagickImageScale.imageMark(srcFile, destFile, minWidth, minHeight,
					pos, offsetX, offsetY, markFile);
		} else {
			AverageImageScale.imageMark(srcFile, destFile, minWidth, minHeight,
					pos, offsetX, offsetY, markFile);
		}
	}

	/**
	 * ����Ƿ�װmagick
	 */
	public void init() {
		if (tryMagick) {
			try {
				System.setProperty("jmagick.systemclassloader", "no");
				new Magick();
				log.info("using jmagick");
				isMagick = true;
			} catch (Throwable e) {
				log.warn("load jmagick fail, use java image scale. message:{}",
						e.getMessage());
				isMagick = false;
			}
		} else {
			log.info("jmagick is disabled.");
			isMagick = false;
		}
	}

	private boolean isMagick = false;
	private boolean tryMagick = true;

	public void setTryMagick(boolean tryMagick) {
		this.tryMagick = tryMagick;
	}
}
