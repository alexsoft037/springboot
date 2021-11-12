package xin.xiaoer.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class DateUtility {
	@SuppressWarnings("unused")
	private static final String timeZone = "KP";

	public static final int UNIT_DAY = 4;

	public static final int UNIT_HOUR = 3;

	public static final int UNIT_MINUTE = 2;

	public static final int UNIT_MONTH = 6;

	public static final int UNIT_SECOND = 1;

	public static final int UNIT_WEEK = 5;

	public static final int UNIT_YEAR = 7;

	private static Boolean checkDateString(String pDate) {
		if (pDate.length() != 10)
			return false;
		String args[] = pDate.split("-");
		if (args.length != 3)
			return false;
		for (String str : args)
			if (!StringUtility.isNumeric(str))
				return false;
		return true;
	}

	public static int dayCountOfCurrentMonth(int year, int month) {
		int dayCount = 0;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			dayCount = 31;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			dayCount = 30;
		} else if (month == 2) {
			if (year % 400 == 0) {
				dayCount = 29;
			} else if (year % 100 == 0) {
				dayCount = 28;
			} else if (year % 4 == 0) {
				dayCount = 29;
			} else {
				dayCount = 28;
			}
		}
		return dayCount;
	}

	public static Timestamp getCurrentTime() {
		Date d = new Date();
		java.sql.Date now = new java.sql.Date(d.getTime());
		Timestamp curTime = new Timestamp(now.getTime());
		curTime.setNanos(0);

		return curTime;
	}

	public static Date getDaysAfter(Date pDate, int pAfterDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(pDate);
		cal.add(Calendar.DAY_OF_MONTH, pAfterDays);
		return cal.getTime();
	}

	public static int getDistance(Date pFromDate, Date pToDate) {
		Calendar calFrom = Calendar.getInstance();
		calFrom.setTime(pFromDate);
		Calendar calTo = Calendar.getInstance();
		calTo.setTime(pToDate);
		if (calFrom.compareTo(calTo) == 0)
			return 0;
		if (calTo.compareTo(calFrom) < 0)
			return -1;
		long from = calFrom.getTimeInMillis();
		long to = calTo.getTimeInMillis();
		long distanceInMilisecond = to - from;
		double distanceDates = distanceInMilisecond / 1000 / 3600 / 24;
		return (int) Math.round(distanceDates);

	}

	public static long getDistance(int unit, Date fromTime, Date toTime) throws Exception {
		if (fromTime == null || toTime == null) {
			throw new Exception();
		}
		Calendar calFrom = Calendar.getInstance();
		calFrom.setTime(fromTime);
		Calendar calTo = Calendar.getInstance();
		calTo.setTime(toTime);

		long diff = (calTo.getTimeInMillis() - calFrom.getTimeInMillis()) / 1000;
		switch (unit) {
		case UNIT_SECOND:
			break;
		case UNIT_MINUTE:
			diff = diff / 60;
			break;
		case UNIT_HOUR:
			diff = diff / 3600;
			break;
		case UNIT_DAY:
			diff = diff / 86400;
			break;
		case UNIT_WEEK:
			diff = diff / 86400 / 7;
			break;
		default:
			break;
		}
		return diff;
	}

	public static int getDistanceToNow(Date pFromDate) {
		return getDistance(pFromDate, nowDate());
	}

	public static Date getMonday(Date pDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(pDate);
		cal.add(Calendar.DAY_OF_MONTH, -(cal.get(Calendar.DAY_OF_WEEK) - 2));
		return cal.getTime();
	}

	public static List<String> getMonths(Date fromDate, Date toDate) {
		List<String> monthList = new ArrayList<String>();
		int fromYear = Integer.parseInt(toDateString(fromDate).substring(0, 4));
		int fromMonth = Integer.parseInt(toDateString(fromDate).substring(5, 7));
		int toYear = Integer.parseInt(toDateString(toDate).substring(0, 4));
		int toMonth = Integer.parseInt(toDateString(toDate).substring(5, 7));

		int arrIndex = 0;
		int currentYear = fromYear;
		int currentMonth = fromMonth;
		if (toYear > fromYear) {
			for (currentYear = fromYear; currentYear <= toYear; currentYear++) {
				int minMonth;
				int maxMonth;
				if (currentYear == fromYear) {
					minMonth = fromMonth;
					maxMonth = 12;
				} else if (currentYear > fromYear && currentYear < toYear) {
					minMonth = 1;
					maxMonth = 12;
				} else {
					minMonth = 1;
					maxMonth = toMonth;
				}
				for (currentMonth = minMonth; currentMonth <= maxMonth; currentMonth++) {
					monthList.add(arrIndex, Integer.toString(currentYear) + "-" + Integer.toString(currentMonth));
					arrIndex++;
				}
			}
		} else if (toYear == fromYear) {
			if (toMonth >= fromMonth) {
				for (currentMonth = fromMonth; currentMonth <= toMonth; currentMonth++) {
					monthList.add(arrIndex, Integer.toString(currentYear) + "-" + Integer.toString(currentMonth));
					arrIndex++;
				}
			}
		}

		return monthList;
	}

	@SuppressWarnings("deprecation")
	public static String getYearAfterWithFormat(int pAfterYear) {
		Date nowdate = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		nowdate.setYear(nowdate.getYear() + pAfterYear);
		nowdate.setMonth(0);
		nowdate.setDate(1);
		return sdf.format(nowdate);
	}

	public static void main(String[] args) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(getDaysAfter(nowDate(), 100));
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(nowDate());
		System.out.println(getDistance(calendar2.getTime(), calendar1.getTime()));

	}

	public static Date nowDate() {
		return new Date();
	}

	public static int nowMonth() {
		Date nowDate = nowDate();
		String strDate = toDateString(nowDate);
		String args[] = strDate.split("-");
		return Integer.parseInt(args[1]);
	}
	public static int nowYear() {
		Date nowDate = nowDate();
		String strDate = toDateString(nowDate);
		String args[] = strDate.split("-");
		return Integer.parseInt(args[0]);
	}
	public static Integer string2Integer(String strValue) {
		Integer number = null;
		try {
			number = Integer.parseInt(strValue);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return number;
	}
	public static Timestamp string2Timestamp(String strDateTime) {
		Timestamp timestamp = null;
		timestamp = getCurrentTime();
		return timestamp;
	}
	public static Date toDate(String dateString) {
		Calendar cal = Calendar.getInstance();
		if (dateString == null)
			return null;
		if (dateString.length() > 10)
			dateString = dateString.substring(0, 10);
		if (!checkDateString(dateString))
			return null;
		String args[] = dateString.split("-");
		int year = Integer.parseInt(args[0]);
		int month = Integer.parseInt(args[1]) - 1;
		int day = Integer.parseInt(args[2]);
		cal.set(year, month, day);
		return cal.getTime();
	}
	public static String toDateString(Date date) {
		if (date == null)
			return "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);
		String monthStr;
		String dayStr;
		String yearStr;
		if (month < 10) {
			monthStr = "0" + month;
		} else {
			monthStr = "" + month;
		}
		if (day < 10) {
			dayStr = "0" + day;
		} else {
			dayStr = "" + day;
		}
		yearStr = "" + year;
		return yearStr + "-" + monthStr + "-" + dayStr;
	}
	public static String toDateString(Date date, String sf) {
		if (date == null)
			return "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);
		String monthStr;
		String dayStr;
		String yearStr;
		if (month < 10) {
			monthStr = "0" + month;
		} else {
			monthStr = "" + month;
		}
		if (day < 10) {
			dayStr = "0" + day;
		} else {
			dayStr = "" + day;
		}
		yearStr = "" + year;
		return yearStr + sf + monthStr + sf + dayStr;
	}

	public static String toFullDateString(String pDate) {
		if (!checkDateString(pDate))
			return null;
		String args[] = pDate.split("-");
		return args[0] + "年" + args[1] + "月" + args[2] + "日";
	}

	public static String toHalfDateString(String pDate) {
		if (!checkDateString(pDate))
			return null;
		String args[] = pDate.split("-");
		return args[1] + "/" + args[2];
	}

	public static String toSelDateString(Date pDate, String flag) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(pDate);
		int iYear = calendar.get(Calendar.YEAR);
		int iMonth = calendar.get(Calendar.MONTH);
		int iDay = calendar.get(Calendar.DAY_OF_MONTH);
		if (flag.equals("d"))
			return String.format("%1$d-%2$d-%3$d", iYear, iMonth, iDay);
		else if (flag.equals("m"))
			return String.format("%1$d-%2$d-%", iYear, iMonth);
		else
			return String.format("%1$d-%", iYear);
	}

	public static String toTimeString(Date date) {
		if (date == null)
			return "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return (toTimeString(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
				calendar.get(Calendar.SECOND)));
	}

	private static String toTimeString(int hour, int minute, int second) {
		String hourStr;
		String minuteStr;
		String secondStr;

		if (hour < 10) {
			hourStr = "0" + hour;
		} else {
			hourStr = "" + hour;
		}
		if (minute < 10) {
			minuteStr = "0" + minute;
		} else {
			minuteStr = "" + minute;
		}
		if (second < 10) {
			secondStr = "0" + second;
		} else {
			secondStr = "" + second;
		}
		if (second == 0)
			return hourStr + ":" + minuteStr + ":00";
		else
			return hourStr + ":" + minuteStr + ":" + secondStr;
	}
}
