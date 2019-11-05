package net.yundingwei.dszw.app.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class StringUtils {

	/**
	 * 判断是否为空(ture-->为空;false-->不为空)
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value == null || "".equals(value) || "null".equals(value.toLowerCase());
	}
	
	/**
	 * 得到格式化日期 格式 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getFormatDate(){
		SimpleDateFormat mSDFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return mSDFormat.format(new Date());
	}

	/**
	 * 得到格式化日期 格式 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getFormatDate(Date date){
		SimpleDateFormat mSDFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return mSDFormat.format(date);
	}
	
	
	/**
	 * 得到格式化日期
	 * @param format 自定义格式
	 * @return
	 */
	public static String getFormatDate(String format){
		SimpleDateFormat mSDFormat = new SimpleDateFormat(format);
		return mSDFormat.format(new Date());
	}
	
	/**
	 * 得到格式化日期
	 * @param format 自定义格式
	 * @return
	 */
	public static String getFormatDate(String format, long time){
		SimpleDateFormat mSDFormat = new SimpleDateFormat(format);
		return mSDFormat.format(new Date(time));
	}
	
	
	/**
	 * 得到格式化日期
	 * @param newFormat 新日期格式
	 * @param oldFormat 旧日期格式
	 * @param strDate 字符串日期
	 * @return
	 */
	public static String getFormatDate(String newFormat, String oldFormat, String strDate){
		try{
			SimpleDateFormat mSDOldFormat = new SimpleDateFormat(oldFormat);
			Date date = mSDOldFormat.parse(strDate);
			SimpleDateFormat mSDNewFormat = new SimpleDateFormat(newFormat);
			return mSDNewFormat.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到格式化日期
	 * @param format 日期格式
	 * @param strDate 字符串日期
	 * @return
	 */
	public static long getDateTime(String format, String strDate){
		try{
			SimpleDateFormat mSDOldFormat = new SimpleDateFormat(format);
			Date date = mSDOldFormat.parse(strDate);
			return date.getTime();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * 得到当前日期 格式 yyyy-MM-dd
	 */
	public static String getCurrenDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return format.format(date);
	}
	
	public static long getTimestamp(){
		return new Date().getTime();
	}
	
	
	public static long getTimestampForTen() {
		long time = new Date().getTime();
		return time / 1000;
	}
	
	/**
	 * 得到当前日期 格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrenTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
	
	public static String getParams(Map<String,String> params){
		if(params != null &&params.size() > 0){
			StringBuilder sbuilder = new StringBuilder();
			Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
			while(iter.hasNext()){
				Map.Entry<String, String> entry = iter.next();
				String key = entry.getKey();
				String value = entry.getValue();
				if(!isEmpty(value)){
					sbuilder.append("&").append(key).append("=").append(value);
				}
			}
			return sbuilder.toString();
		}
		return "";
	}
	
	/**
	 * 整数长度不足用0补齐
	 * @param length
	 * @param number
	 * @return
	 */
	public static String padded(int length, int number){
         String f = "%0" + length + "d";
         return String.format(f, number);
     }
	
	
	/**
	 * 字符串日期转日期
	 * @param strDate
	 * @param format
	 */
	public static Date strToDate(String strDate, String format){
		try{
			SimpleDateFormat mDateFormat = new SimpleDateFormat(format);
			return mDateFormat.parse(strDate);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String dateToString(Date date, String format){
		try{
			SimpleDateFormat mDateFormat = new SimpleDateFormat(format);
			return mDateFormat.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String replace(String content, String regex, String txt){
		return content.replaceAll(regex, "");
	}
	
	
	/**
	 * 获取随机数
	 * @param num 位数
	 * @return
	 */
	public static int getRandom(int num){
		try{
			if(num > 1){
				int numcode = (int) ((Math.random() * 9 + 1) * Math.pow(10, num - 1));
				return numcode;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 格式化字符串
	 * @param regex
	 * @param text
	 * @return
	 */
	public static String getRegexString(String regex, String text){
		String value = text.replaceAll(regex, "$1 ");
		return value;
	}

	/**
	 * 返回间隔为4的字符串
	 * 如:1108 1002 3333
	 * @param text
	 * @return
	 */
	public static String getInterval4String(String text){
		return getRegexString("(.{4})", text);
	}


	/**
	 * 给TextView设置不同的前景颜色
	 * @param start
	 * @param end
	 * @param color
	 */
	public static SpannableStringBuilder setTextForeColor(String text, int start, int end, int color){
		SpannableStringBuilder sbuilder = new SpannableStringBuilder(text);
		ForegroundColorSpan mForeColor = new ForegroundColorSpan(color);
		sbuilder.setSpan(mForeColor, start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sbuilder;
	}


	/**
	 * 设置float类型保留小数
	 * 例：保留两位小数: "0.00"
	 * @return
	 */
	public static String formatFloat(float value, String format){
		DecimalFormat mDecFormat= new DecimalFormat(format);//构造方法的字符格式这里如果小数不足2位,会以0补足.
		return mDecFormat.format(value);//format 返回的是字符串
	}

	/**
	 * 设置float类型保留小数
	 * 例：保留两位小数: "0.00"
	 * @return
	 */
	public static String formatFloat(double value, String format){
		DecimalFormat mDecFormat= new DecimalFormat(format);//构造方法的字符格式这里如果小数不足2位,会以0补足.
		return mDecFormat.format(value);//format 返回的是字符串
	}

	/**
	 * 获取最大数值
	 * @param strNum
	 * @return
	 */
	public static String getMaxNumber(String strNum){

		if(!StringUtils.isEmpty(strNum)) {
			int num = Integer.parseInt(strNum);
			if (num > 99) {
				return "99+";
			}
		}else{
			return "0";
		}
		return strNum;
	}

	/**
	 * 获取最大数值
	 * @int num
	 * @return
	 */
	public static String getMaxNumber(int num){
		if (num > 99) {
			return "99+";
		}
		return "" + num;
	}

	/**
	 * 获取MD5值
	 *
	 * @param source
	 * @param bit 32为，16位
	 * @return
	 */
	public static String getMD5(byte[] source, int bit) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			StringBuffer buf = new StringBuffer();
			for (byte b : md.digest())
				buf.append(String.format("%02x", b & 0xff));
			return bit == 32 ? buf.toString() : buf.substring(8, 24);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取进程名
	 * @param context
	 * @param pid
     * @return
     */
	public static String getProcessName(Context context, int pid) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
		if (runningApps == null) {
			return null;
		}

		for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
			if (procInfo.pid == pid) {
				return procInfo.processName;
			}
		}
		return null;
	}

	/**
	 * 是否是数字
	 *
	 * @param ch
	 * @return
	 */
	public static boolean isNumeric(char ch) {
		return Character.isDigit(ch);
	}

	/**
	 * 判断纯字母的
	 */
	public static boolean isLetter(char ch) {
		if (((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))) {
			return true;
		} else {
			return false;
		}
	}

	public static String convertUnicode(String str) {
		str = (str == null ? "" : str);
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			sb.append("\\u");
			j = (c >>> 8); // 取出高8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
			j = (c & 0xFF); // 取出低8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);

		}
		return (new String(sb));
	}

}
