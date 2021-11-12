package xin.xiaoer.common.utils;


import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 说明：日期处理
 * 创建人：FH Q313596790
 * 修改时间：2015年11月24日
 * @version
 */
public class DateUtile {

    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取YYYY格式
     * @return
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     * @return
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }

    /**
     * 获取YYYYMMDD格式
     * @return
     */
    public static String getDays(){
        return sdfDays.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }

    /**
     * @Title: compareDate
     * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @author fh
     */
    public static boolean compareDate(String s, String e) {
        if(fomatDate(s)==null||fomatDate(e)==null){
            return false;
        }
        return fomatDate(s).getTime() >=fomatDate(e).getTime();
    }

    /**
     * 格式化日期
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 校验日期是否合法
     * @return
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    /**
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDiffYear(String startTime,String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //long aa=0;
            int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        //System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static void main(String[] args) {
        System.out.println(getDays());
        System.out.println(getAfterDayWeek("3"));
    }

    private static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

    public static Integer getAge(String idcard) {
        if (StringUtils.isEmpty(idcard)) {
            throw new IllegalArgumentException("身份证不能为空");
        }
        if (idcard.length() != 15 & idcard.length() != 18) {
            throw new IllegalArgumentException("身份证长度不对");
        }
        String birstr = null;
        if (idcard.length() == 18) {
            birstr=idcard.substring(6, 14);
        }else{
            birstr=idcard.substring(6, 12);
            birstr = 19 + birstr;
        }
        try {
            Date date=sf.parse(birstr);
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());

            int year=ca.get(Calendar.YEAR);
            int month = ca.get(Calendar.MONTH);
            int day = ca.get(Calendar.DAY_OF_MONTH);

            int n_year=now.get(Calendar.YEAR);
            int n_month = now.get(Calendar.MONTH);
            int n_day = now.get(Calendar.DAY_OF_MONTH);

            if (year > n_year||(year==n_year&&month>n_month)||(year==n_year&&month==n_month&&day>n_day)) {
                throw new RuntimeException("身份证错误");
            }

            if ((month == n_month && day <= n_day) || month < n_month) {
                return n_year - year + 1;
            }else{
                return n_year - year;
            }

        } catch (ParseException e) {
            throw new RuntimeException("身份证格式不正确");
        }
    }


    public static String addDays(String date, int i) {
        try {
            Date day=sdfDay.parse(date);
            Calendar c=Calendar.getInstance();
            c.setTime(day);
            c.add(Calendar.DAY_OF_MONTH,i);
            return sdfDay.format(c.getTime());
        } catch (ParseException e) {
            throw new RuntimeException("解析时间错误");
        }
    }
}

