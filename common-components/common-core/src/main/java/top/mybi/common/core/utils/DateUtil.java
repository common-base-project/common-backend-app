package top.mybi.common.core.utils;


import top.mybi.common.core.constants.GlobalConstants;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

/**
 * 日期工具
 * @Author mustang
 * @version 1.0
 */
public class DateUtil {

    static  DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern(GlobalConstants.DEFAULT_DATE_TIME_PATTEN);
    static  DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern(GlobalConstants.DEFAULT_DATE_PATTEN);

    static DateFormat dateTimeFormat = new SimpleDateFormat(GlobalConstants.DEFAULT_DATE_TIME_PATTEN);
    static DateFormat dateFormat = new SimpleDateFormat(GlobalConstants.DEFAULT_DATE_PATTEN);

	static  DateTimeFormatter yyyyMMddHHmmss = DateTimeFormatter.ofPattern(GlobalConstants.DATE_PATTEN_SIXTH);
    /**
     * 日期格式
     * @param accessor
     * @return
     */
    public static String formatDate(TemporalAccessor accessor){
        if (Objects.isNull(accessor)){
            return "";
        }
        return localDateFormatter.format(accessor);
    }

    /**
     * 日期时间格式
     * @param accessor
     * @return
     */
    public static String formatDateTime(TemporalAccessor accessor){
        if (Objects.isNull(accessor)){
            return "";
        }
        return localDateTimeFormatter.format(accessor);
    }

    @SneakyThrows
    public static Date covertDate(LocalDate localDate){
        String timeStr = formatDate(localDate);
        if (StringUtils.isEmpty(timeStr)){
            return null;
        }
        return dateFormat.parse(timeStr);
    }

    /**
     * 字符串转日期
     * @param timeStr
     * @return
     */
    public static LocalDate toLocalDate(String timeStr){
        return LocalDate.parse(timeStr,localDateFormatter);
    }

    /**
     * 字符串转时间
     * @param timeStr
     * @return
     */
    public static LocalDateTime toLocalDateTime(String timeStr){
        return LocalDateTime.parse(timeStr,localDateTimeFormatter);
    }

	/**
	 * 字符串转时间
	 * @param timeStr
	 * @return
	 */
	public static LocalDateTime toLocalDateTime2(String timeStr){
		return LocalDateTime.parse(timeStr,yyyyMMddHHmmss);
	}

	/**
	 * @Description: 时间戳转日期
	 *
	 * @Author: mustang
	 */
	public static LocalDateTime toLocalDateTime(Long timestamp){
		if (timestamp == 0)
			return null;
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone
				.getDefault().toZoneId());
	}

    @SneakyThrows
    public static Date covertDateTime(LocalDateTime localDateTime){
        String timeStr = formatDateTime(localDateTime);
        if (StringUtils.isEmpty(timeStr)){
            return null;
        }
        return dateTimeFormat.parse(timeStr);
    }

    public static String toChineseDate(LocalDate localDate) {
    	String chineseDate="";
		String datestr = formatDate(localDate);
		String[] strs = datestr.split("-");
		// 年
		for (int i = 0; i < strs[0].length(); i++) {
			chineseDate += formatDigit(strs[0].charAt(i));
		}
		// chineseDate = chineseDate+"年";
		// 月
		char c1 = strs[1].charAt(0);
		char c2 = strs[1].charAt(1);
		String newmonth = "";
		if (c1 == '0') {
			newmonth = String.valueOf(formatDigit(c2));
		} else if (c1 == '1' && c2 == '0') {
			newmonth = "十";
		} else if (c1 == '1' && c2 != '0') {
			newmonth = "十" + formatDigit(c2);
		}
		chineseDate = chineseDate + "年" + newmonth + "月";
		// 日
		char d1 = strs[2].charAt(0);
		char d2 = strs[2].charAt(1);
		String newday = "";
		if (d1 == '0') {//单位数天
			newday = String.valueOf(formatDigit(d2));
		} else if (d1 != '1' && d2 == '0') {//几十
			newday = String.valueOf(formatDigit(d1)) + "十";
		} else if (d1 != '1' && d2 != '0') {//几十几
			newday = formatDigit(d1) + "十" + formatDigit(d2);
		} else if (d1 == '1' && d2 != '0') {//十几
			newday = "十" + formatDigit(d2);
		} else {//10
			newday = "十";
		}
		chineseDate = chineseDate + newday + "日";
		return chineseDate;
    }
    
    public static char formatDigit(char sign) {
		if (sign == '0')
			sign = '〇';
		if (sign == '1')
			sign = '一';
		if (sign == '2')
			sign = '二';
		if (sign == '3')
			sign = '三';
		if (sign == '4')
			sign = '四';
		if (sign == '5')
			sign = '五';
		if (sign == '6')
			sign = '六';
		if (sign == '7')
			sign = '七';
		if (sign == '8')
			sign = '八';
		if (sign == '9')
			sign = '九';
		return sign;
    }
    
    public static void main(String[] args) {
		String d=toChineseDate(LocalDate.now());
    	//System.out.println(d);
	}

	/**
	 *
	 * @param localDateTime
	 * @return 返回中文年月日时分秒 like 20年10月26日15时28分xx秒
	 */
	public static String toChineseDateTime(LocalDateTime localDateTime) {
		String s = localDateTime.toString();
		char[] chars = s.toCharArray();
		boolean hour = true;
		boolean year = true;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '-'){
				if (year){
					chars[i] = '年';
					year  = false;
				}else {
					chars[i] = '月';
				}
			}else if (chars[i] == 'T'){
				chars[i] = '日';
			}else if (chars[i] == ':'){
				if (hour){
					chars[i] = '时';
					hour = false;
				}else {
					chars[i] = '分';
				}
			}else if (chars[i] == '.'){
				chars[i] = '秒';
			}
		}
		String chineseDateTime = new String(chars).substring(2, chars.length - 3);
		return chineseDateTime;
	}
	/**
	 *
	 * @param localDateTime
	 * @return 返回中文年月日时分秒 like 20年10月26日15时28分xx秒
	 */
	public static String toChineseDateTime2(LocalDateTime localDateTime) {
		String s = localDateTime.toString();
		char[] chars = s.toCharArray();
		System.out.println(chars);
		boolean hour = true;
		boolean year = true;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '-'){
				if (year){
					chars[i] = '年';
					year  = false;
				}else {
					chars[i] = '月';
				}
			}else if (chars[i] == 'T'){
				chars[i] = '日';
			}else if (chars[i] == ':'){
				if (hour){
					chars[i] = '时';
					hour = false;
				}else {
					chars[i] = '分';
				}
			}else if (chars[i] == '.'){
				chars[i] = '秒';
			}
		}
		String chineseDateTime = new String(chars);
		return chineseDateTime;
	}

	/**
	 * 将localDateTime转换成String
	 * @param localDateTime
	 * @return
	 */
	public static String parseToString(LocalDateTime localDateTime){
		return localDateTime.format(localDateTimeFormatter);
	}

	/**
	 * 将localDateTime转换成String
	 * @param localDateTime
	 * @return
	 */
	public static String parseToString2(LocalDateTime localDateTime){
		return localDateTime.format(yyyyMMddHHmmss);
	}

}


