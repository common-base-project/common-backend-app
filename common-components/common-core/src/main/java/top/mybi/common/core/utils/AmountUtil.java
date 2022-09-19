package top.mybi.common.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 金额工具
 * @Author mustang
 * @version 1.0
 */
public class AmountUtil {

    /**
     * 元与分的进制
     */
    private static final BigDecimal YUAN_FRO_CENT = new BigDecimal(100);

    /**
     * 将分转换以元为单位，会去除小数点后面的0，保留指定的小数位数，并添加千分符
     * @param amount 以分为单位的金额
     */
    public static String centToYuan(Long amount){
        if (amount == null) {
            return "0.00";
        }
        BigDecimal bigDecimal = new BigDecimal(amount);
        BigDecimal bigDecimalDivide = bigDecimal.divide(YUAN_FRO_CENT, 4,BigDecimal.ROUND_HALF_UP);
        DecimalFormat format = new DecimalFormat("#,##0.00");
        return format.format(bigDecimalDivide);
    }
    /**
     * 将金额转换为分金额*100
     * @param amount
     * @return
     */
    public static Long centToFen(String amount) {
    	if (amount == null) {
            return 0L;
        }
    	amount=amount.replaceAll(",", "");
    	if(!isNumber(amount)) {
    		return 0L;
    	}
        BigDecimal bigDecimal = new BigDecimal(amount);
        BigDecimal bigDecimalDivide = bigDecimal.multiply(YUAN_FRO_CENT);
        return bigDecimalDivide.longValue();
    }

    /**
     * 将分转换以元为单位，会去除小数点后面的0，保留指定的小数位数
     * @param amount 以分为单位的金额
     */
    public static String centToFloatStr(Long amount){
        if (amount == null) {
            return "0.00";
        }
        BigDecimal bigDecimal = new BigDecimal(amount);
        BigDecimal bigDecimalDivide = bigDecimal.divide(YUAN_FRO_CENT, 4,BigDecimal.ROUND_HALF_UP);
        DecimalFormat format = new DecimalFormat("##0.00");
        return format.format(bigDecimalDivide);
    }
    
  //金额验证
    public static boolean isNumber(String str) 
    { 
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        java.util.regex.Matcher match=pattern.matcher(str); 
        if(match.matches()==false) 
        { 
           return false; 
        } 
        else 
        { 
           return true; 
        } 
    }

    public static void main(String[] args) {
        System.out.println(centToFloatStr(123456789012344L));
    }
}
