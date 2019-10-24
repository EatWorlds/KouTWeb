package com.cutout.server.utils;

import cn.hutool.core.util.ReUtil;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

@Component
public class Bases {

    /**
     * 获取系统秒时间 @Title: getSystemSeconds @Description:
     * TODO @param: @return @return: long @throws
     */
    public int getSystemSeconds() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 按分隔符分割字符串
     *
     * @param s
     *            待分割的字符串
     * @param delimiter
     *            分隔符
     * @return 分割后的字符数组
     */
    public String[] split(String s, String delimiter) {
        if (s == null) {
            return null;
        }
        int delimiterLength;
        int stringLength = s.length();
        if (delimiter == null || (delimiterLength = delimiter.length()) == 0) {
            return new String[] { s };
        }

        // a two pass solution is used because a one pass solution would
        // require the possible resizing and copying of memory structures
        // In the worst case it would have to be resized n times with each
        // resize having a O(n) copy leading to an O(n^2) algorithm.

        int count;
        int start;
        int end;

        // Scan s and count the tokens.
        count = 0;
        start = 0;
        while ((end = s.indexOf(delimiter, start)) != -1) {
            count++;
            start = end + delimiterLength;
        }
        count++;

        // allocate an array to return the tokens,
        // we now know how big it should be
        String[] result = new String[count];

        // Scan s again, but this time pick out the tokens
        count = 0;
        start = 0;
        while ((end = s.indexOf(delimiter, start)) != -1) {
            result[count] = (s.substring(start, end));
            count++;
            start = end + delimiterLength;
        }
        end = stringLength;
        result[count] = s.substring(start, end);

        return (result);
    }

    /**
     * md5加密 http://blog.csdn.net/rongwenbin/article/details/42462441
     *
     * @param inputStr
     *            需要加密的字符串
     * @return
     */
    public static String getMD5Str(String inputStr) {
        String md5Str = inputStr;
        try {
            if (inputStr != null) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(inputStr.getBytes());
                BigInteger hash = new BigInteger(1, md.digest());
                md5Str = hash.toString(16);
//				if ((md5Str.length() % 2) != 0) {
//					md5Str = "0" + md5Str;
//				}
                // 2018-08-18 加密修改
                while (md5Str.length() < 32) {
                    md5Str = "0" + md5Str;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return md5Str;
    }

    /**
     * 对应php date('Ymd', $starttime)
     *
     * @Title: transferLongToDate
     * @Description: TODO
     * @param dateFormat
     * @param millSec
     * @return
     */
    public String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 生成指定范围的随机数 @Title: getRandom @Description: TODO @param: @param min
     * 随机数对应的最小值 @param: @param max 随机数对应的最大值 @param: @return @return:
     * int @throws
     */
    public int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 判断是否是正确的邮箱格式
     * @param content
     * @return
     */
    public boolean isEmail(String content) {
        boolean isMatch = ReUtil.isMatch("\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?",content);
        return isMatch;
    }

    /**
     *
     * @Title: getMonthTime
     * @Description: 获取当月某天某时的时间
     * @param day
     * @param hour
     * @param min
     * @param second
     * @return
     * @return: int
     * @throws
     */
    public int getCurrentMonthTime(int day,int hour, int min, int second) {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.DAY_OF_MONTH, day);// 设置为1号,当前日期即为本月第一天
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, second);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     *
     * @Title: getMonthTime
     * @Description: 获取某月某天某时的时间,day传0，即为最后一天
     * @param month
     * @param day
     * @param hour
     * @param min
     * @param second
     * @return int
     * @throws
     */
    public int getMonthTime(int month,int day,int hour,int min,int second) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day == 0 ? cal.getActualMaximum(Calendar.DAY_OF_MONTH) : day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, second);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * 获取本月最后一天 23:59:59的时间
     * @return
     */
    public int getMonthLastTime() {
        return getMonthTime(0,0,23,59,59);
    }
}
