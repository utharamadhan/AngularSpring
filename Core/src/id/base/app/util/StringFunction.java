package id.base.app.util;

import id.base.app.SystemConstant;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringFunction extends DateTimeFunction {
	private static final Pattern pwdPattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()-_+=]).{8,256})");
	
	private static final String APOSTROPHE_CODE="'";
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static String trim(String s) {
    	if (s != null) {
    		return s.trim();
    	}
    	return null;
    }
    
    public static boolean isEmpty (String s) {
        boolean empty = false;
        if ((s == null) || s.trim().length() == 0) {
            empty = true;
        }
        return empty;
    }

    public static String[] split(String str, String delim) {

      StringTokenizer strtok = new StringTokenizer(str, delim);
      String[] result = new String[strtok.countTokens()];

      for (int i = 0; i < result.length; i++) {
        result[i] = strtok.nextToken();
      }

      return result;
    }

    public static String replace2(String str, String what, String onwhat) {

        int beginIndex = 0;
        int endIndex = 0;
        String Result = "";

        endIndex = str.indexOf(what, beginIndex);

        while (endIndex != -1) {
            Result = Result + str.substring(beginIndex, endIndex) + onwhat;
            beginIndex = endIndex + what.length();
            endIndex = str.indexOf(what, beginIndex);
        }

        Result = Result + str.substring(beginIndex, str.length());

        return Result;
    }

    public static String htmlEscape(String str) {
        str = replace(str, "&", "&amp;");
        str = replace(str, "<", "&lt;");
        str = replace(str, ">", "&gt;");
        str = replace(str, "\"", "&quot;");
        str = replace(str, "%","&#37;");
        return str;
    }

    public static String preformat(String str) {
        //System.out.println("StringFunction::preformat(): " + str);
        str = replace(str, "\n", "<br/>");
        return str;
    }

    /**
     * Concat two arrays of Strings,
     * part2 is appended to part1
     */
    public static String[] concat(String[] part1, String[] part2) {
        String[] full = new String[part1.length + part2.length];
        System.arraycopy(part1, 0, full, 0, part1.length);
        System.arraycopy(part2, 0, full, part1.length, part2.length);
        return full;
    }
    
    /** insert alias for every element on string array
     * @param list
     * @param alias
     * @return
     */
    public static String[] insertAlias(String[] list, String alias){
    	String[] aliasList = new String[list.length];
        for (int i = 0; i < list.length; i++) {
        	aliasList[i] = alias+"."+list[i];
          }
    	return aliasList;
    }

    public static String replace3(String inputString, String oldString, String newString) {
        StringBuffer desc = new StringBuffer();
        int index = inputString.indexOf(oldString);
        int last = 0;
        while (index != -1) {
            desc.append(inputString.substring(last, index));
            desc.append(newString);
            last = index + oldString.length();
            index = inputString.indexOf(oldString, last);
        }
        desc.append(inputString.substring(last));
        return desc.toString();
    }

    /**
     *  Replace all instances of a String in a String.
     *   @param  s  String to alter.
     *   @param  f  String to look for.
     *   @param  r  String to replace it with, or null to just remove it.
     */
    public static String replace(String s, String f, String r) {
        if (s == null)
            return s;
        if (f == null)
            return s;
        if (r == null)
            r = "";

        int index01 = s.indexOf(f);
        while (index01 != -1) {
            s = s.substring(0, index01) + r + s.substring(index01 + f.length());
            index01 += r.length();
            index01 = s.indexOf(f, index01);
        }
        return s;
    }


    public static long[] splitIntoLong(String str, String delimiter) {
        StringTokenizer st = new StringTokenizer(str, delimiter);
        long[] value = new long[st.countTokens()];
        int size = st.countTokens();
        for (int i=0; i<size; i++) {
            value[i] = Long.parseLong(st.nextToken());
        }
        return value;
    }

    public static int[] splitIntoInt(String str, String delimiter) {
        StringTokenizer st = new StringTokenizer(str, delimiter);
        int[] value = new int[st.countTokens()];
        int size = st.countTokens();
        for (int i=0; i<size; i++) {
            value[i] = Integer.parseInt(st.nextToken());
        }
        return value;
    }

    public static String null2String(Object obj) {
        if ( obj == null ) {
            return "";
        } else {
            return obj.toString();
        }
    }
    
    public static String null2Zero(Object obj) {
        if ( null2String(obj) == "" ) {
            return "0";
        } else {
            return obj.toString();
        }
    }

    public static double[] splitIntoDouble(String str, String delimiter) {
        StringTokenizer st = new StringTokenizer(str, delimiter);
        double[] value = new double[st.countTokens()];
        double size = st.countTokens();
        for (int i=0; i<size; i++) {
            value[i] = Double.parseDouble(st.nextToken());
        }
        return value;
    }

    /**
     * This method is used to evaluate data String
     * If parameter is null or the length of trim of parametes is 0 the method will return null value
     * else value of parameter will returned
     * @param stInput
     * @return
     */
    public static String checkString(String stInput) {
        if (StringUtils.isEmpty(stInput)) {
            return null;
        }

        if (StringUtils.isBlank(stInput)) {
            return null;
        }

        return stInput;
    }

    /**
     * Compare value of two String object
     * @param s1
     * @param s2
     * @return boolean
     */
    public static boolean compareString(String s1, String s2) {
        boolean bResult;
        if (s1 == null) {
            if (s2 == null) {
                bResult = true;
            } else {
                bResult = false;
            }
        } else {
            bResult = s1.equals(s2);
        }
        return bResult;
    }



    /**
     * to secure and save input string into sql statement
     * @param s
     * @return
     */
    public static String toSQLString(String s) {
        return replace(s,"'", "''");
    }
    private static String buildStringWithApostrophe(Object[] obj,String del){
        if ( obj.length > 0) {
            if ( obj.length == 1) {
                return APOSTROPHE_CODE+obj[0].toString()+APOSTROPHE_CODE;
            } else {
                StringBuffer buff = new StringBuffer("'"+obj[0].toString()+APOSTROPHE_CODE);
                for (int i=1; i<obj.length; i++) {
                    buff.append(del);
                    buff.append(APOSTROPHE_CODE);
                    buff.append(obj[i].toString());
                    buff.append(APOSTROPHE_CODE);
                }
                return buff.toString();

            }

        } else {
            return null;
        }

    }

    public static String buildStringList(Object[] obj, String del,boolean isApostrophe) {
        if (del == null) del = ",";
        if(!isApostrophe) return buildStringListFromArray(obj,del);
        else return buildStringWithApostrophe(obj,del);

    }


    /**
     * build "1,2,3,4" from array of String {"1", "2", "3", "4"} with delimiter ','
     * @param obj
     * @param del Delimiter. default value is comma
     * @return
     */
    public static String buildStringListFromArray(Object[] obj, String del) {
        if (del == null) del = ",";
        if ( obj.length > 0) {
            if ( obj.length == 1) {
                return obj[0].toString();
            } else {
                StringBuffer buff = new StringBuffer(obj[0].toString());
                for (int i=1; i<obj.length; i++) {
                    buff.append(del);
                    buff.append(obj[i].toString());
                }
                return buff.toString();

            }

        } else {
            return null;
        }
    }

    public static String hexaToAscii(String strHex){
        String result = null;
        if((strHex != null) && (strHex.length() > 2)){
            StringBuffer sb = new StringBuffer();
            String str = strHex.substring(2);
            boolean isComplete = false;
            int idx = 0;
            while(!isComplete){
                String s    = str.substring(idx, idx+2);
                int i       = Integer.parseInt(s, 16);
                char c      =(char)i;
                sb.append(c);
                idx = idx + 2;
                if(idx > (str.length()-1)){
                    isComplete = true;
                }
            }
            result = sb.toString();
        }

        return result;
    }

    public static boolean isNumber(String s){
        try {
            Long.parseLong(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * sample format 082-100123
     * @param s=082-100123
     * @param allowChar='-'
     * @return true if all characters in string is numeric, and only one character is not numeric
     */
    public static boolean isPhoneNumber(String s,String allowChar){
    	String tempString=s.replace(allowChar, "");
    	try {
             Long.parseLong(tempString);
             return true;
         }
         catch (NumberFormatException e) {
             return false;
         }
    }

    private static final Pattern areaPattern = Pattern.compile("[a-zA-Z0-9]{2}");
    private static final Pattern areaRegionPattern = Pattern.compile("[a-zA-Z0-9]{3}-[a-zA-Z0-9]{2}");
    private static final Pattern noAreaPattern = Pattern.compile("[a-zA-Z0-9]{3}--");

    public static boolean isValidAreaFormat(String value){
        Matcher matcher = areaPattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isValidAreaRegionFormat(String value) {
        Matcher matcher1 = areaRegionPattern.matcher(value);
        Matcher matcher2 = noAreaPattern.matcher(value);
        if(matcher1.matches())
        	return true;
        else
        	return matcher2.matches();
    }	
    
    public static Long[] splitIntoLongObject(String str, String delimiter) {
        StringTokenizer st = new StringTokenizer(str, delimiter);
        Long[] value = new Long[st.countTokens()];
        int size = st.countTokens();
        for (int i=0; i<size; i++) {
            value[i] = Long.parseLong(st.nextToken());
        }
        return value;
    }
    
    public static List<String> convertIntoListOfString(List<Object> objs){
    	List<String> strList = new ArrayList<String>();
    	for (Object object : objs) {
			strList.add(object.toString());
		}
    	
		return strList;
    	
    }

	public static String formatNumber(long number, String pattern) {
		NumberFormat fmt = new DecimalFormat(pattern);
		return fmt.format(number);
	}
	
	public static String safeStringConvertion(Object obj) {
		if(obj != null) {
			return obj.toString();
		}
		return null;
	}
	
	public static String safeString(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}
	
    public static String safeTrim(String value) {
    	if (value == null) {
    		return null;
    	}
    	return value.trim();
    }
    
    public static String convertNewlineToBr(String text) {
		return replace(text, "\n", "<br />");
	}
    
    public static String convertNewline(String text, String replacement) {
		return replace(text, "\n", replacement);
	}

    /**
     * Coalesce whitespace into single space. 
     * Main purpose is to coalesce tabs and spaces into a single space for displaying in HTML.
     * 
     * @param text string to convert
     * @return string where all the whitespaces are coalesced
     */
	public static String coalesceWhitespace(String text) {
		if (text == null || text.length() == 0) {
			return text;
		}
		return text.replaceAll("[ \\t\\x0B\\f\\r]+", " ");
	}

	/**
	 * Trim and coalesce all whitespaces except newline
	 * @param text String to convert
	 * @return
	 */
	public static String safeTrimAndCoalesceWhitespace(String text) {
		String result = safeTrim(text);
		if (result == null || result.length() == 0) {
			return result;
		}
		
		return result.replaceAll("[ \\t\\x0B\\f\\r]+", " ");
	}
	
	public static String replaceSpecialChars(String text,String replacerText){
        if(text!=null){
			String pattern = "[^A-Z ^a-z ^0-9]";
	        String strippedString = text.replaceAll(pattern, replacerText);
	        strippedString = strippedString.replaceAll("[ ]+", " ");
			return strippedString;
        }else{
        	return null;
        }
	}
	
	public static Boolean isValidPassword(String value){
		Matcher matcher = pwdPattern.matcher(value);
        return matcher.matches();
	}
	
	public static String getBooleanValue(Boolean val){
		if(val!=null){
			if(val) return "1";
		}
		return "0";
	}
	
	public static String removeHTMLTags(String htmlString){
		if(htmlString!=null){
			return htmlString.replaceAll("\\<.*?\\>", "");
		}
		return htmlString;
	}
	
	public static String replaceHtmlCode(String htmlString){
		return htmlString.replace(SystemConstant.PERCENTAGE_HTML_NUMBER, SystemConstant.PERCENTAGE_STRING);
	}
	
	public static String printStackTraceToString(Throwable t){
		StringWriter errors = new StringWriter();
		t.printStackTrace(new PrintWriter(errors));		
		return errors.toString();
	}
	
	public static Boolean isBoolean(String value) {
		try {
			if((value.equalsIgnoreCase("false") || value.equalsIgnoreCase("true"))){
				return Boolean.TRUE;
			}
		} catch (Exception e) {}
		return Boolean.FALSE;
	}
	
	public static String encryptString(String value) {
		StringBuilder sb = new StringBuilder();
		try {
			String str1 = value + DateTimeFunction.date2String(Calendar.getInstance().getTime(), SystemConstant.SYSTEM_DATE_TIME_NO_DELIMITER);
			MessageDigest md = MessageDigest.getInstance("MD5");
		    byte[] array = md.digest(str1.getBytes("UTF-8"));
		    for (byte b : array) {
		        sb.append(String.format("%02X", b));
		    }
		} catch (Exception e) {}
		return sb.toString();
	}
	
}
