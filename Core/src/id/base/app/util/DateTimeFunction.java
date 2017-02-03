/*
 * Created on Jul 1, 2005
 *
 */
package id.base.app.util;

import id.base.app.SystemConstant;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateTimeFunction {
    private static String timeDelimiter = ":";
    public static final int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
    private static DatatypeFactory df;
    
    static {
    	try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			// assume always success, no fallback plan :)
			e.printStackTrace();
		}
    }
    
    public static XMLGregorianCalendar convertDateToXmlGregorian(Date date) {
    	if (date == null) {
    		return null;
    	}
    	GregorianCalendar gregCal = new GregorianCalendar();
    	gregCal.setTimeInMillis(date.getTime());
    	return df.newXMLGregorianCalendar(gregCal);
    }
    
    public static Date convertXmlGregorianToDate(XMLGregorianCalendar timestamp) {
    	if (timestamp == null) {
    		return null;
    	}
		return timestamp.toGregorianCalendar().getTime();
	}
    
    public static Calendar getCalendar() {
    	return Calendar.getInstance();
    }
    
    public static Calendar getCalendar(Long millis) {
    	Calendar cal = Calendar.getInstance();
    		cal.setTime(new Date(millis));
    	return cal;
    }
    
    public static Calendar getCalendar(Date date) {
    	Calendar cal = Calendar.getInstance();
    		cal.setTime(date);
    	return cal;
    }
    
    public static Date getCurrentDateWithoutTime() {
    	Calendar now = Calendar.getInstance();
    	now.set(Calendar.HOUR_OF_DAY, 0);
    	now.set(Calendar.MINUTE, 0);
    	now.set(Calendar.SECOND, 0);
    	now.set(Calendar.MILLISECOND, 0);
    	return now.getTime();
	}
    
    public static Date getCurrentDate(){
    	Calendar now = Calendar.getInstance();
    	return now.getTime();
    }
    
    public static Integer getCurrentYear(){
    	Calendar now = Calendar.getInstance();
    	return now.get(Calendar.YEAR);
    }
    
    public static Integer getCurrentMonth(){
    	Calendar now = Calendar.getInstance();
    	return now.get(Calendar.MONTH);
    }
    
    public static List<Integer> getYears(int start, int end){
    	List<Integer> years = new LinkedList<Integer>();
    	do {
    		years.add(start); start++;
		} while (start!=end);
    	return years;
    }
    
    public static Date getNextDate(Date d){
    	d.setTime(d.getTime()+MILLIS_IN_DAY);
    	return d;
    }
    
    public static Date getPrevDate(Date d){
    	d.setTime(d.getTime()-MILLIS_IN_DAY);
    	return d;
    }
    
    public static Date getDateFromMillis(long millis) {
    	Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
    }

    public static String getStringOfTimesNoDelimiter(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        Date toDate = convertToUtilDate(timestamp);
        return (new SimpleDateFormat(SystemConstant.HOUR_MINUTE_SECOND_MASK_NO_DELIMITER)).format(toDate);
    }

    public static int[] stringToTimeArray(String time) throws Exception {
        int[] t = new int[3];
        StringTokenizer st = new StringTokenizer(time, timeDelimiter);
        if (st.countTokens() == 2) {
            try {
                String hour = st.nextToken();
                t[0] = Integer.parseInt(hour);

                String minute = st.nextToken();
                t[1] = Integer.parseInt(minute);
            } catch (NumberFormatException nfe) {
                throw nfe;
            }
        } else if (st.countTokens() >= 3) {
            try {
                String hour = st.nextToken();
                t[0] = Integer.parseInt(hour);

                String minute = st.nextToken();
                t[1] = Integer.parseInt(minute);

                String second = st.nextToken();
                t[2] = Integer.parseInt(second);

            } catch (NumberFormatException nfe) {
                throw nfe;
            }
        } else {
            throw new Exception("Unknown time format");
        }
        return t;
    }
    
    /**
     * To extract Hour from given date time. 
     * Note that date and time is separated by space character.
     * 
     * @param dateTime String from which hour will be extracted. E.g "28-02-2011 22:33:11" 
     * @return Hour in int, from the example: 22
     * @throws Exception
     */
    public static int getHourFromDateTime(String dateTime) throws Exception {
    	String[] str = dateTime.split(" ");
    	if(str.length > 1) {
    		int[] time = stringToTimeArray(str[1]);
    		return time[0];
    	}
    	
    	return 0;
    }
    
    /**
     * To extract Minute from given date time. 
     * Note that date and time is separated by space character.
     * 
     * @param dateTime String from which minute will be extracted. E.g "28-02-2011 22:33:11" 
     * @return Minute in int, from the example: 33
     * @throws Exception
     */
    public static int getMinuteFromDateTime(String dateTime) throws Exception {
    	String[] str = dateTime.split(" ");
    	if(str.length > 1) {
    		int[] time = stringToTimeArray(str[1]);
    		return time[1];
    	}
    	
    	return 0;
    }
    
    /**
     * To extract Second from given date time. 
     * Note that date and time is separated by space character.
     * 
     * @param dateTime String from which second will be extracted. E.g "28-02-2011 22:33:11" 
     * @return Second in int, from the example: 11
     * @throws Exception
     */
    public static int getSecondFromDateTime(String dateTime) throws Exception {
    	String[] str = dateTime.split(" ");
    	if(str.length > 1) {
    		int[] time = stringToTimeArray(str[1]);
    		return time[2];
    	}
    	
    	return 0;
    }

    public static String date2String(Date date, String mask) {
        SimpleDateFormat sdf = new SimpleDateFormat(mask);
        return date == null ? "" : sdf.format(date);
    }
    
    public static String date2StringIDLocale(Date date, String mask){
    	Locale l=new Locale("ID");
    	DateFormat formatter = new SimpleDateFormat(mask, l);
        return formatter.format(date);
    }
       
    public static String time2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SystemConstant.HOUR_MINUTE_SECOND_MASK);
        return date == null ? "" : sdf.format(date);
    }

    public static String systemDate2String(Date date) {
        if (date == null) {
            return "";
        }
        return date2String(date, SystemConstant.SYSTEM_DATE_MASK);
    }
    public static Date string2Date(String dateString, String dateFormat) {
        Date d = null;
        if (StringFunction.checkString(dateString) != null) {
            ParsePosition pos = new ParsePosition(0);
            d = (new SimpleDateFormat(dateFormat)).parse(dateString, pos);
        }
        return d;
    }

    /**
     * convert string to date
     * @param dateString
     * @return
     */
    public static Date string2systemDate(String dateString) {
        return string2Date(dateString, SystemConstant.SYSTEM_DATE_MASK);
    }

    /**
     * convert from java.util.Date to java.sql.Date
     * @param dt the date
     * @return date in sql date format or null if dt is null.
     */
    public static java.sql.Date convertToSQLDate(java.util.Date dt) {
        if (dt == null)
            return null;
        return new java.sql.Date(dt.getTime());
    }

    public static Timestamp convertToTimestamp(java.util.Date dt) {
        if (dt == null)
            return null;
        return new Timestamp(dt.getTime());
    }

    /**
     * Convert sql date to util Date
     * @param dt
     * @return date in util date format or null if dt == null
     */
    public static java.util.Date convertToUtilDate(java.sql.Date dt) {
        if (dt == null)
            return null;
        return new java.util.Date(dt.getTime());
    }

    public static java.util.Date convertToUtilDate(Timestamp timestamp) {
        if (timestamp == null)
            return null;
        return new java.util.Date(timestamp.getTime());
    }

    /**
     * Convert a formated date of year date into java.util.Date
     * @param formatedDate
     * @return
     */
    public static java.util.Date convertDayOfYear(String formatedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyDDD");
        try {
            return sdf.parse(formatedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * format date that has pattern as system date mask.
     * @param str
     * @return
     * @throws ParseException
     * @see SystemConstant#SYSTEM_DATE_MASK
     */
    public static long getLongDate(String str) throws ParseException {
        if (str == null || str.equals("null"))
            return -1; // prevent uneccesary parse exception -- for Rule
        final SimpleDateFormat systemDateFormat = new SimpleDateFormat(SystemConstant.SYSTEM_DATE_MASK);
        return systemDateFormat.parse(str).getTime();
    }

    /**
     * Subtract between two date (date1 > date0), time (hour, minute, second are ignored)
     * @return number of days
     * */
    public static long calcDateDifference(int CAL_CONST, Date date0, Date date1) throws RuntimeException {
        long time = 1000;
        if (CAL_CONST == Calendar.DAY_OF_MONTH) {
            time = time * 60 * 60 * 24;
        } else if (CAL_CONST == Calendar.HOUR_OF_DAY) {
            time = time * 60 * 60;
        } else if (CAL_CONST == Calendar.MINUTE) {
            time = time * 60;
        } else {
            throw new RuntimeException("Unknown calendar constant");
        }
        return (date1.getTime() - date0.getTime()) / time;
    }
    
    public static String calcDateDifferenceString(Date date0, Date date1) throws RuntimeException {
    	String result = "";
    	try {
    		long time = 1000, millisDiff = (date1.getTime() - date0.getTime());
    		if((millisDiff / (time * 60)) < 60) {
    			result = "less than a minute ago";
    		} else if(millisDiff / (time * 60) > 60 ) {
    			if(millisDiff / (time * 60 * 60) < 1) {
    				return millisDiff / (time * 60 * 60) + " minutes ago";
    			} else {
    				if(millisDiff / (time * 60 * 60 * 24) < 1) {
    					return millisDiff / (time * 60 * 60) + " hours ago";
    				} else {
    					if(millisDiff / (time * 60 * 60 * 24 * 30) < 1) {
    						return millisDiff / (time * 60 * 60 * 24) + " days ago";
    					} else {
    						if(millisDiff / (time * 60 * 60 * 24 * 30 * 12) < 1) {
    							return millisDiff / (time * 60 * 60 * 24 * 30) + " months ago";
    						} else {
    							return millisDiff / (time * 60 * 60 * 24 * 30 * 12) + "years ago";
    						}
    					}
    				}
    			}
    		}
    	} catch (Exception e){
    		result = "x minutes ago";
    	}
    	return result;
    }
    public static String Integer2String(Integer integer) {
    	if (integer == null) {
    		return "";
    	}
    	return int2String(integer);
    }
    
    public static String int2String(int integer) {
        return long2String(integer);
    }

    public static String long2String(long number) {
        return "" + number;
    }

    public static String double2String(double d) {
        return (new DecimalFormat(SystemConstant.DECIMAL_MASK)).format(d);
    }

    /**
     * return 1'st date of current month
     * @return
     */
    public static Date getFirstDateOfCurrentMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH , 1);
        return cal.getTime();
    }
    
    /**
     * return 1'st date of current week
     * @return
     */
    public static Date getFirstDateOfCurrentWeek(){
    	Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK , 1);
        return cal.getTime();
    }
    
    /**
     * get last date of previous month
     * @return
     */
    public static Date getLastDateOfPrevMonth(){
        Calendar cal = Calendar.getInstance();
        int month  = cal.get(Calendar.MONTH);
        if(month<0) month = 11;
        cal.set(Calendar.MONTH,month-1);
        cal.set(Calendar.DATE,cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }
    /**
     * get first date of previous month
     * @return
     */
    public static Date getFirstDateOfPrevMonth(){
        Calendar cal = Calendar.getInstance();
        int month =  cal.get(Calendar.MONTH);
        if(month<0)  month =11;
        cal.set(Calendar.MONTH,month-1);
        cal.set(Calendar.DATE, 1);
        return cal.getTime();
    }
    /**
     * get last date of current month
     * @return
     */
    public static Date getLastDateOfCurrentMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE,cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }
    public static int getCurrentDayOfMonth(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    public static String getMonthName(int intMonthNumber) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, intMonthNumber - 1);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        String monthName = sdf.format(cal.getTime());
        return monthName;
    }

    /**
     * Compare two date, ignoring the time
     *
     * the value 0 if date1 is equal to date2
     * the value less than 0 date1 is before date2
     * the value greater than 0 if date1 is after date2.
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        cal1.set(Calendar.HOUR, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        cal2.set(Calendar.HOUR, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);

        return cal1.getTime().compareTo(cal2.getTime());
    }

    /**
     * @param myDate
     * @param i
     * @param j
     * @return
     */
    public static Date addDate(Date myDate, int amount, int CAL_CONSTANT) {
        Calendar c = Calendar.getInstance();
        c.setTime(myDate);
        c.add(CAL_CONSTANT, amount);
        return c.getTime();
    }

    /**
     * @param curDate
     * @return
     */
    public static Date truncateDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    public static Date setEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Date getDate(int year, int month, int day ) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }
    //add by Dodi
    //private static Pattern timePattern = Pattern.compile("([0-1]\\d|2[1-3]):[0-5]\\d:[0-5]\\d"); // 24h format: 00:00:00 - 23:59:59
    private static Pattern timePattern = Pattern.compile("([0-1]\\d|2[1-3]):[0-5]\\d"); // 24h format: 00:00:00 - 23:59:59
    public static boolean isValidTimeFormat(String value){
        Matcher matcher = timePattern.matcher(value);
        return matcher.matches();
    }
    
    /**
     * @return 
     * @keyFormat = date time format untuk di key value ex:dd-mm-yyy
     * @valueFormat = date time format untuk di value value ex:dd-mm-yyy
     * @maxRecord = maksimal record yang akan di loop
     * @rollUp = jika true maka date akan di loop ke atas, jika false akan di loop ke bawah
     */
    
	public static Map<String, String> generateDateMapPeriod(String keyFormat, String valueFormat, Integer maxRecord, Boolean rollUp){
		Map<String , String> periods = new HashMap<String, String>();
		Calendar now = Calendar.getInstance();
		for (int i = 0; i < maxRecord; i++) {
			periods.put(DateTimeFunction.date2String(now.getTime(), keyFormat), DateTimeFunction.date2String(now.getTime(), valueFormat));
			now.roll(Calendar.MONTH, rollUp);
			if(now.get(Calendar.MONTH) == 0){
				now.roll(Calendar.YEAR, rollUp);
			}
			now.setTime(now.getTime());
		}
		Map<String, String> treeMap = new TreeMap<String, String>(periods);
		return treeMap;
	}
	
	
    /** fungsi untuk mendapatkan string date dengan format tertentu, dan tanggal di rollup atau di roll down
     * @param date sample 1-1-2015
     * @param mask sample dd-mm-yyyy
     * @param rollUp sample false
     * @return 31-12-2014
     */
    public static String rollingDate2String(Date date, String mask, Boolean rollUp) {
    	Calendar now = Calendar.getInstance();
    	now.setTime(date);
    	now.roll(Calendar.MONTH, rollUp);
		if(now.get(Calendar.MONTH) == 0){
			now.roll(Calendar.YEAR, rollUp);
		}
        return DateTimeFunction.date2String(now.getTime(), mask);
    }
    
    /** fungsi untuk mendapatkan date dari string dengan format tertentu, dan tanggal di rollup atau di roll down
     * @param String sample 1-1-2015
     * @param mask sample dd-mm-yyyy
     * @param rollUp sample false
     * @return 31-12-2014
     */
    public static Date rollingString2Date(String date, String mask, Boolean rollUp) {
    	
    	Calendar now = Calendar.getInstance();
    	now.setTime(string2Date(date, mask));
    	if(rollUp){
        	now.add(Calendar.DATE, 1);
    	}else{
    		now.add(Calendar.DATE, -1);
    	}
        return now.getTime();
    }
    
    /**
     * return age of nearest birth date
     * if current date nearer to next birth date than return age + 1, otherwise return age
     * currently used for contract business validation
     * @author utha
     */
    public static Integer getAgeOfNearestBirthDate(Date personDateOfbirth){
		Calendar currDate = Calendar.getInstance();
			currDate.setTime(DateTimeFunction.truncateDate(currDate.getTime()));
		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.setTime(personDateOfbirth);
		
		int age = currDate.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
		dateOfBirth.set(Calendar.YEAR, currDate.get(Calendar.YEAR));
		if (currDate.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR))
		age--;
		
		if(dateOfBirth.get(Calendar.MONTH) < currDate.get(Calendar.MONTH) || 
				(dateOfBirth.get(Calendar.MONTH) == currDate.get(Calendar.MONTH) && dateOfBirth.get(Calendar.DAY_OF_MONTH) < currDate.get(Calendar.DAY_OF_MONTH))){
			
			dateOfBirth.set(Calendar.YEAR, currDate.get(Calendar.YEAR));
			int diffLowValue = (int) Math.abs(((double) (currDate.getTime().getTime()) - dateOfBirth.getTime().getTime()) / DateTimeFunction.MILLIS_IN_DAY);
			
			dateOfBirth.roll(Calendar.YEAR, 1);
			int diffTopValue = (int) Math.abs(((double) (dateOfBirth.getTime().getTime() - currDate.getTime().getTime())) / DateTimeFunction.MILLIS_IN_DAY);
			
			if(diffLowValue > diffTopValue){
				age++;
			}
		}else if(dateOfBirth.get(Calendar.MONTH) > currDate.get(Calendar.MONTH) ||
				(dateOfBirth.get(Calendar.MONTH) == currDate.get(Calendar.MONTH) && dateOfBirth.get(Calendar.DAY_OF_MONTH) > currDate.get(Calendar.DAY_OF_MONTH))){
			dateOfBirth.set(Calendar.YEAR, currDate.get(Calendar.YEAR));
			int diffTopValue = (int) Math.abs(((double) (dateOfBirth.getTime().getTime() - currDate.getTime().getTime())) / DateTimeFunction.MILLIS_IN_DAY);
			
			dateOfBirth.roll(Calendar.YEAR, -1);
			int diffLowValue = (int) Math.abs(((double) (currDate.getTime().getTime()) - dateOfBirth.getTime().getTime()) / DateTimeFunction.MILLIS_IN_DAY);
			
			if(diffLowValue > diffTopValue){
				age++;
			}
		}
		
		return age;
	}
    
	/**
	 * return age of next birth date
     * currently used for contract business validation
     * @author utha
	 */
	public static Integer getAgeOfNextBirthDate(Date personDateOfBirth){
		Calendar currDate = Calendar.getInstance();
		Calendar dateOfBirth = Calendar.getInstance();
		dateOfBirth.setTime(personDateOfBirth);
		
		int age = currDate.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
		dateOfBirth.set(Calendar.YEAR, currDate.get(Calendar.YEAR));
		if (currDate.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR)){
			age--;
		}
		
		return age++;
	}
}
