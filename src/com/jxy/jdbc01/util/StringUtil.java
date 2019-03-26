package com.ganjiangps.wangdaibus.common.util;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ganjiangps.wangdaibus.model.SmsChannel;
import com.ganjiangps.wangdaibus.model.SmsRecord;

public class StringUtil {
	
	public static void main(String[] args) {
 
		String dd = "1045";
		System.out.println(dd.lastIndexOf("天") > -1);
		String dd2 = dd.substring(0,dd.indexOf("天"));
		System.out.println(dd2);
	}
	
	public static Map<String,String> getUrlReq(String url){
		Map<String,String> hh = new HashMap<>();
  		if(url != null && url.indexOf("?") != -1){
  			url = url.substring(url.indexOf("?")+1);
   			String[] ss = url.split("&");
 			for(String s:ss){
  				String key = s.split("=")[0];
 				String value = s.split("=")[1];
 				hh.put(key, value);
  			}
		}
		return hh;
	}
	
	/**
     * 将一个数字处理为以万为单位的字符串，保留两位小数
     *
     * @param num
     * @return
     */
    public static String getTenThousandOfANumber(BigDecimal numB) {
     	Double num = numB.doubleValue();
        if (num < 10000) {
            return String.valueOf(num);
        }
        String numStr = new DecimalFormat("#.00").format(num / 10000d);
        String[] ss = numStr.split("\\.");
        if ("00".equals(ss[1])) {
            return ss[0] + "万";
        } else if ('0' == (ss[1].charAt(1))) {
            return ss[0] + "." + ss[1].charAt(0) + "万";
        } else {
            return numStr + "万";
        }
    }

	/**
	 * 将一个数字处理为以万为单位的字符串，保留两位小数
	 * 不足一万用元表示(xiaomifeng首页累计投资累计返利)
	 *
	 * @param
	 * @return
	 */
	public static String getParseOfNumber(BigDecimal numB) {
		Double num = numB.doubleValue();
		if (num==0){
			return "0元";
		}
		if (num < 10000) {
			String numStr = new DecimalFormat("#.00").format(num );
			String[] ss = numStr.split("\\.");
			if ("00".equals(ss[1])) {
				return ss[0] + "元";
			} else if ('0' == (ss[1].charAt(1))) {
				return ss[0] + "." + ss[1].charAt(0) + "元";
			} else {
				return numStr + "元";
			}
		}
		String numStr = new DecimalFormat("#.00").format(num / 10000d);
		String[] ss = numStr.split("\\.");
		if ("00".equals(ss[1])) {
			return ss[0] + "万";
		} else if ('0' == (ss[1].charAt(1))) {
			return ss[0] + "." + ss[1].charAt(0) + "万";
		} else {
			return numStr + "万";
		}
	}
	
	//获取2-200范围的随机数
	public static String getRandom(int min, int max){
	    Random random = new Random();
	    int s = random.nextInt(max) % (max - min + 1) + min;
	    return String.valueOf(s);

	}
	
	//向上取整
	public static BigDecimal getCeil(Double bd) {
		double ceil = Math.ceil(bd);
		return BigDecimal.valueOf(ceil);
	}
	
	public static  boolean isEmpty(Object str) {
		return str == null || "".equals(str) || String.valueOf(str).length() == 0
				|| String.valueOf(str).matches("\\s*");
	}
	
	public static boolean isNotEmpty(Object str){
		return !isEmpty(str);
	}
	
	public static boolean isEmail(String email) {
		String regular = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w)*";
		Pattern pattern = Pattern.compile(regular);
		boolean flag = false;
		if (StringUtils.isNotBlank(email)) {
			Matcher matcher = pattern.matcher(email);
			flag = matcher.matches();
		}

		return flag;
	}

	public static boolean isMobile(String mobile) {
		String regular = "1[3,4,5,7,8,9]{1}\\d{9}";
		Pattern pattern = Pattern.compile(regular);
		boolean flag = false;
		if (StringUtils.isNotBlank(mobile)) {
			Matcher matcher = pattern.matcher(mobile);
			flag = matcher.matches();
		}
		return flag;
	}
	
	public static boolean isNumeric(String str){   
	   Pattern pattern = Pattern.compile("[0-9]*");   
	   Matcher isNum = pattern.matcher(str);  
	   if( !isNum.matches() ){  
	       return false;   
	   }   
	   return true;   
	}
	
	/**
	 * 通过JSON向前台发送数据
	 *
	 * @param data
	 * @throws IOException
	 */
	public static void sendJsonData(HttpServletResponse response, String data) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(data);
		out.flush();
		out.close();
	}
	
	  public static Map<String, Object> beanToMap(Object obj) {
	        Map<String, Object> params = new HashMap<String, Object>(0);
	        try {
	            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
	            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
	            for (int i = 0; i < descriptors.length; i++) {
	                String name = descriptors[i].getName();
	                if (!"class".equals(name)) {
	                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return params;
	 }
	  
	 public static String getRandomStr(int length) {
		String[] arr = { "0","1","2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
				"l", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		while (sb.length() < length) {
			String str = arr[random.nextInt(34)];
			if (sb.indexOf(str) == -1) {
				sb.append(str);
			}
		}
		return sb.toString();
	}
	 

		/**
		 * 获取4-8位随机数
		 *
		 * @return
		 */
		public static int getN(int num) {
			Random random = new Random();
			int number = 0;
			switch (num) {
			case 4:
				number = random.nextInt(8999) + 1000;
				break;
			case 5:
				number = random.nextInt(89999) + 10000;
				break;
			case 6:
				number = random.nextInt(899999) + 100000;
				break;
			case 7:
				number = random.nextInt(8999999) + 1000000;
				break;
			case 8:
				number = random.nextInt(8999999) + 10000000;
				break;
			default:
				number = random.nextInt(8999999) + 1000000;// 默认7位
				break;
			}
			return number;
		}
	
		/**
		 * 对短信记录信息进行处理并返回
		 *
		 * @author :gengfl
		 * @date :2018-01-23 17:38
		 */
		public static SmsRecord GetSmsRecord(SmsChannel smsChannel, String mobile, String message, String code) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			SmsRecord smsRecord = new SmsRecord();
			// 将用户基本信息的用户名放进实体类中
			/* smsRecord.setUsername(userAccountInfo.getUsername()); */
			// 发送号码(无)
			/* smsRecord.setMobile(userAccountInfo.getMobile()); */
			// 接收信息电话
			smsRecord.setMobile(mobile);
			// 验证码
			smsRecord.setVercode(code);
			// 发送时间
			smsRecord.setSendtime(System.currentTimeMillis());
			// 短信内容
			smsRecord.setSmscontent(message);
			// 短信公司接口
			smsRecord.setSmsccompany(smsChannel.getSmsccompany());
			// 发送方式（1:手工 2:系统）
			smsRecord.setSendtype(1);
			// 生成短信流水号
			smsRecord.setOrderno("DX" + formatter.format(new Date()) + StringUtil.game(2));
			return smsRecord;
		}	
		/**
		 * 产生不重复的四位数的随机数
		 * 
		 * @param @param
		 *            count 你要生成的数字的位数
		 * @param @return
		 * @return String
		 * @author jiangxueyou
		 */
		public static String game(int count) {
			StringBuffer sb = new StringBuffer();
			String str = "123456789";
			Random r = new Random();
			for (int i = 0; i < count; i++) {
				int num = r.nextInt(str.length());
				sb.append(str.charAt(num));
				str = str.replace((str.charAt(num) + ""), "");
			}
			return sb.toString();
		}
		
		/**
		 * 校验银行卡卡号是否合法
		 *
		 * @param @param
		 *            bankCard
		 * @param @return
		 *            设定文件
		 * @return boolean 返回类型
		 * @throws @Title:
		 *             checkBankCard
		 * @Description: TODO(这里用一句话描述这个方法的作用)
		 */
		public static boolean checkBankCard(String bankCard) {
			if (bankCard.length() < 15 || bankCard.length() > 19) {
				return false;
			}
			char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
			if (bit == 'N') {
				return false;
			}
			System.out.println(bankCard.charAt(bankCard.length() - 1));
			return bankCard.charAt(bankCard.length() - 1) == bit;
		}
		/**
		 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
		 *
		 * @param nonCheckCodeBankCard
		 * @return char 返回类型
		 * @Title: getBankCardCheckCode
		 * @Description: TODO(从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位)
		 */
		public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
			if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
					|| !nonCheckCodeBankCard.matches("\\d+")) {
				// 如果传的不是数据返回N
				return 'N';
			}
			char[] chs = nonCheckCodeBankCard.trim().toCharArray();
			int luhmSum = 0;
			for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
				int k = chs[i] - '0';
				if (j % 2 == 0) {
					k *= 2;
					k = k / 10 + k % 10;
				}
				luhmSum += k;
			}
			return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
		}
		
		public static String getOrderNo3(Long BaseId) {

			if (BaseId == null || BaseId == 0) {
				BaseId = 1l;
			}
			String idStr = String.valueOf(BaseId);
			if (idStr.length() == 6) {
				idStr = "6" + idStr;
			} else if (idStr.length() == 5) {
				idStr = "5" + idStr;
			} else if (idStr.length() == 4) {
				idStr = "4" + idStr;
			} else if (idStr.length() == 3) {
				idStr = "3" + idStr;
			} else if (idStr.length() == 2) {
				idStr = "2" + idStr;
			} else if (idStr.length() == 1) {
				idStr = "1" + idStr;
			}

			String timeStr = String.valueOf(System.currentTimeMillis());

			return idStr + timeStr;
		}
		
		/**
		 * @param @param
		 *            request
		 * @param @return
		 *            参数说明
		 * @return String 返回类型
		 * @throws @Title:
		 *             getBasePath
		 * @Description: TODO(动态获取项目路径)
		 * @author chenjiaming
		 */
		public static String getBasePath(HttpServletRequest request) {
			String path = request.getContextPath();
			String basePath;
			int prot = request.getServerPort();
			if (prot == 80) {
				basePath = request.getScheme() + "://" + request.getServerName() + path;
			} else {
				basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
			}
			return basePath;
		}
		
		/**
		 * ip
		 */
		public static String getIpAddr(HttpServletRequest request) {
			String ip = request.getHeader("HTTP_X_REAL_IP");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("X-Forwarded-For");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			// System.out.println("ip----------------"+ip);
			return ip;

		}

	/**
	 * 生成订单编号 16位
	 *
	 * @param
	 * @return 字符串
	 */
	public static String getNo16() {
		String getNo = getNo();
		return getNo.substring(0, getNo.length() - 4);
	}

	/**
	 * 生成订单编号(年月日时分秒+随机6位数)
	 * @param
	 * @return 字符串
	 */
	public static String getNo() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hours = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String m = String.valueOf(c.get(Calendar.MINUTE));
		String s = String.valueOf(c.get(Calendar.SECOND));
		if (month.length() == 1) {
			month = "0" + month;
		}

		if (day.length() == 1) {
			day = "0" + day;
		}
		if (hours.length() == 1) {
			hours = "0" + hours;
		}
		if (m.length() == 1) {
			m = "0" + m;
		}
		if (s.length() == 1) {
			s = "0" + s;
		}
		return year + month + day + hours + m + s + getN(6);
	}

	/**
	 * @param @param
	 *            dateStr 日期字符串 2016-08-16
	 * @param @param
	 *            format 格式化的类型(如 yyyy-MM-dd, yyyy-MM-dd hh:mm:ss)
	 * @param @return
	 *            参数说明
	 * @return Date 返回类型
	 * @throws @Title:
	 *             getDateByString
	 * @Description: TODO(将日期字符串转换为Date类型)
	 * @author chenjiaming
	 */
	public static Date getDateByString(String dateStr, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期格式化
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (date != null) {
			String dateString = new SimpleDateFormat(pattern).format(date);
			return dateString;
		} else {
			return "";
		}
	}
	/**
	 * 处理金额
	 *
	 * @param amount
	 * @return
	 */
	public static BigDecimal handleAmount(Double amount){
		BigDecimal receptMaxtransferAmoun = BigDecimal.valueOf(amount);
		//保留四位小数
		BigDecimal amountfee = receptMaxtransferAmoun.setScale(4, BigDecimal.ROUND_HALF_UP);

		return amountfee;

	}
	
	/**
	 * 根据具体名字设置cookie
	 *
	 * @param response
	 * @param name
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期 以秒为单位
	 */
	public static void addCookieByName(HttpServletResponse response, HttpServletRequest request,String name,Long times) {
		String domain = request.getServerName();
		if(StringUtil.isEmpty(name)) {
			name = "wangdaibus.com";
		}
		String value = null;
		if(StringUtil.isEmpty(times)) {
			value = System.currentTimeMillis()+"";
		}else {
			value = System.currentTimeMillis()+"_"+times;
		}
		int maxAge = 365 * 24 * 3600;

		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0)
			cookie.setMaxAge(maxAge);
		cookie.setDomain(domain);
		response.addCookie(cookie);
	}

	/**
	 * 设置cookie
	 *
	 * @param response
	 * @param name
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期 以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, HttpServletRequest request) {
		String domain = request.getServerName();
		String name = "wangdaibus" + domain;
		String value = name + System.currentTimeMillis();
		int maxAge = 365 * 24 * 3600;

		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0)
			cookie.setMaxAge(maxAge);
		cookie.setDomain(domain);
		response.addCookie(cookie);
	}

	/**
	 * 设置Cookie
	 *
	 * @return
	 */
	public static String setCookie(HttpServletRequest request,HttpServletResponse response)
	{
	/*	request.setAttribute("cookieName", );*/
		String regcookie = StringUtil.getCookieValue(request);
		if (StringUtil.isEmpty(regcookie))
		{
			StringUtil.addCookie(response, request);

		}
		return regcookie;
	}

	/**
	 * 根据名字获取cookie值
	 *
	 * @param request
	 * @param //cookie名字
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request) {
		String name = "wangdaibus" + request.getServerName();
		Cookie c = getCookieByName(request, name);
		String cookieValue = "";
		if (c != null && (c.getName().equalsIgnoreCase(name))) {
			cookieValue = c.getValue();
		}
		return cookieValue;
	}
	
	/**
	 * 根据具体名字获取cookie值
	 *
	 * @param request
	 * @param //cookie名字
	 * @return
	 */
	public static String getCookieValueByName(HttpServletRequest request,String name) {
		if(StringUtil.isEmpty(name)) {
			name = "wangdaibus.com";
		}
		Cookie c = getCookieByName(request, name);
		String cookieValue = "";
		if (c != null && (c.getName().equalsIgnoreCase(name))) {
			cookieValue = c.getValue();
		}
		return cookieValue;
	}



	/**
	 * 根据名字获取cookie
	 *
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/**
	 * 将cookie封装到Map里面
	 *
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}



	public static String getSubStr(String source, int len) {

		return getSubStr(source, len, ".", "...");

	}
	/**
	 * 输出固定字节长度的字符串
	 *
	 * @param source
	 *            String
	 * @param len
	 *            int
	 * @param exChar
	 *            String
	 * @param exStr
	 *            String
	 * @return String
	 */

	public static String getSubStr(String source, int len, String exChar,

								   String exStr) {

		if (source == null || getByteLength(source) <= len) {

			return source;

		}

		StringBuffer result = new StringBuffer();

		char c = '\u0000';

		int i = 0, j = 0;

		for (; i < len; j++) {

			result.append(c);

			c = source.charAt(j);

			i += isDoubleByte(c) ? 2 : 1;

		}

		/**
		 *
		 * 到这里i有两种情况：等于len或是len+1，如果是len+1，说明是双字节，并多出一个字节
		 *
		 * 这时候就只能append(exChar)，否则就append(c)
		 *
		 */

		if (i > len) {

			result.append(exChar);

		} else {

			result.append(c);

		}

		result.append(exStr);

		return result.toString();

	}


	/**
	 * 得到字符串的字节长度
	 *
	 * @param source
	 *            字符串
	 * @return 字符串的字节长度
	 * @since 0.6
	 */

	public static int getByteLength(String source) {

		int len = 0;

		for (int i = 0; i < source.length(); i++) {

			char c = source.charAt(i);

			int highByte = c >>> 8;

			len += highByte == 0 ? 1 : 2;

		}

		return len;

	}

	/**
	 * 判断字符是否为双字节字符，如中文
	 *
	 * @param c
	 *            char
	 * @return boolean
	 */

	public static boolean isDoubleByte(char c) {

		return !((c >>> 8) == 0);

	}

	/**
	 * 设置占位符字符串 =  450  => 00000450
	 *
	 * @param str
	 * @param idex
	 *            下标
	 * @return
	 */
	public static String settingAavatarPlaceholder(String str, int length) {
		while (str.length() < length) {
			 str = "0"+str;
 		}
		return str;
	}
	/**
	 * 用于生成推广码的相关方法
	 * @param @param n
	 * @param @return
	 * @return int
	 * @author jiangxueyou
	 */
	 public static int getNumer(Integer n) {
		int count = 0;
		while (Math.abs(n) % 10 > 0 || Math.abs(n) != 0) {
			count++;
			n = n / 10;
		}
		return count + 1;
	}

	/**
	 * String 转 Long
	 */
	public static Long stringToLong(String time){
		SimpleDateFormat sf =new SimpleDateFormat("yyyyMMddHHmmss");
		Long longTime = null;
		try {
			longTime = sf.parse(time).getTime();//格式转换
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return longTime;
	}
	
	/**
	 * 生成包含大写字母小写字母数字 的16位数的随机密码
	 * @param @return
	 * @return char[]
	 * @author jiangxueyou
	 */
	public static char[] generate() {

	    char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
	            'K', 'L', 'M', 'N', 'O', 'P','Q', 'R', 'S', 'T', 'U', 'V',
	            'W', 'X', 'Y', 'Z','0','1','2','3','4','5','6','7','8','9'};
	    boolean[] flags = new boolean[letters.length];
	    char[] chs = new char[16];
	    for (int i = 0; i < chs.length; i++) {
	        int index;
	        do {
	            index = (int) (Math.random() * (letters.length));
	        } while (flags[index]);// 判断生成的字符是否重复
	        chs[i] = letters[index];
	        flags[index] = true;
	    }
	    return chs;
	}
}
