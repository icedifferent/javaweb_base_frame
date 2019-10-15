package com.pouruan.common.ucpaasSMS;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
     * ���������ڸ�ʽ������ - ��ʽ:yyyy/MM/dd
     */
	public static final int DEFAULT = 0;
	public static final int YM = 1;

    /**
     * ���������ڸ�ʽ������ - ��ʽ:yyyy-MM-dd
     * 
     */
    public static final int YMR_SLASH = 11;

    /**
     * ���������ڸ�ʽ������ - ��ʽ:yyyyMMdd
     * 
     */
    public static final int NO_SLASH = 2;

    /**
     * ���������ڸ�ʽ������ - ��ʽ:yyyyMM
     * 
     */
    public static final int YM_NO_SLASH = 3;

    /**
     * ���������ڸ�ʽ������ - ��ʽ:yyyy/MM/dd HH:mm:ss
     * 
     */
    public static final int DATE_TIME = 4;

    /**
     * ���������ڸ�ʽ������ - ��ʽ:yyyyMMddHHmmss
     * 
     */
    public static final int DATE_TIME_NO_SLASH = 5;

    /**
     * ���������ڸ�ʽ������ - ��ʽ:yyyy/MM/dd HH:mm
     * 
     */
    public static final int DATE_HM = 6;

    /**
     * ���������ڸ�ʽ������ - ��ʽ:HH:mm:ss
     * 
     */
    public static final int TIME = 7;

    /**
     * ���������ڸ�ʽ������ - ��ʽ:HH:mm
     * 
     */
    public static final int HM = 8;
    
    /**
     * ���������ڸ�ʽ������ - ��ʽ:HHmmss
     * 
     */
    public static final int LONG_TIME = 9;
    /**
     * ���������ڸ�ʽ������ - ��ʽ:HHmm
     * 
     */
    
    public static final int SHORT_TIME = 10;

    /**
     * ���������ڸ�ʽ������ - ��ʽ:yyyy-MM-dd HH:mm:ss
     */
    public static final int DATE_TIME_LINE = 12;
	public static String dateToStr(Date date, int type) {
        switch (type) {
        case DEFAULT:
            return dateToStr(date);
        case YM:
            return dateToStr(date, "yyyy/MM");
        case NO_SLASH:
            return dateToStr(date, "yyyyMMdd");
        case YMR_SLASH:
        	return dateToStr(date, "yyyy-MM-dd");
        case YM_NO_SLASH:
            return dateToStr(date, "yyyyMM");
        case DATE_TIME:
            return dateToStr(date, "yyyy/MM/dd HH:mm:ss");
        case DATE_TIME_NO_SLASH:
            return dateToStr(date, "yyyyMMddHHmmss");
        case DATE_HM:
            return dateToStr(date, "yyyy/MM/dd HH:mm");
        case TIME:
            return dateToStr(date, "HH:mm:ss");
        case HM:
            return dateToStr(date, "HH:mm");
        case LONG_TIME:
            return dateToStr(date, "HHmmss");
        case SHORT_TIME:
            return dateToStr(date, "HHmm");
        case DATE_TIME_LINE:
            return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
        default:
            throw new IllegalArgumentException("Type undefined : " + type);
        }
    }
	public static String dateToStr(Date date,String pattern) {
	       if (date == null || date.equals(""))
	    	 return null;
	       SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	       return formatter.format(date);
    } 

    public static String dateToStr(Date date) {
        return dateToStr(date, "yyyy/MM/dd");
    }
}
