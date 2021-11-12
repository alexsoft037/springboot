package xin.xiaoer.common.utils;

import java.io.*;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public Utils() {
    }

    public static String inputStreamToString(InputStream is) {
        BufferedReader in = null;
        StringBuffer buffer = new StringBuffer();
        String line = "";

        try {
            in = new BufferedReader(new InputStreamReader(is));

            while((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException var15) {
            var15.printStackTrace();
        } catch (OutOfMemoryError var16) {
            var16.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

                in = null;
            }

        }

        return buffer.toString();
    }

    public static Calendar parseTimeToCalendar(String strTime) {
        if (strTime == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;

            try {
                date = sdf.parse(strTime);
            } catch (ParseException var4) {
                var4.printStackTrace();
            }

            Calendar timeCalendar = Calendar.getInstance();
            timeCalendar.setTime(date);
            return timeCalendar;
        }
    }

    public static String OSD2Time(Calendar OSDTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(OSDTime.getTimeInMillis());
    }

    public static long get19TimeInMillis(String createTime) {
        Calendar calendar = convert19Calender(createTime);
        return calendar != null ? calendar.getTimeInMillis() : 0L;
    }

    public static Calendar convert14Calender(String stringTime) {
        if (stringTime != null && stringTime.length() >= 14 && isNumeric(stringTime)) {
            String year = stringTime.substring(0, 4);
            String month = stringTime.substring(4, 6);
            String day = stringTime.substring(6, 8);
            String hour = stringTime.substring(8, 10);
            String minute = stringTime.substring(10, 12);
            String second = stringTime.substring(12, 14);

            try {
                GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second));
                return calendar;
            } catch (NumberFormatException var8) {
                var8.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static Calendar convert16Calender(String szStartTime) {
        if (szStartTime != null && szStartTime.length() >= 15) {
            String year = szStartTime.substring(0, 4);
            String month = szStartTime.substring(4, 6);
            String day = szStartTime.substring(6, 8);
            String hour = szStartTime.substring(9, 11);
            String minute = szStartTime.substring(11, 13);
            String second = szStartTime.substring(13, 15);

            try {
                GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second));
                return calendar;
            } catch (NumberFormatException var8) {
                var8.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static Calendar convert19Calender(String createTime) {
        if (createTime != null && createTime.length() >= 19) {
            try {
                int year = Integer.parseInt(createTime.substring(0, 4));
                int month = Integer.parseInt(createTime.substring(5, 7));
                int day = Integer.parseInt(createTime.substring(8, 10));
                int hourOfDay = Integer.parseInt(createTime.substring(11, 13));
                int minute = Integer.parseInt(createTime.substring(14, 16));
                int second = Integer.parseInt(createTime.substring(17, 19));
                GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day, hourOfDay, minute, second);
                return calendar;
            } catch (NumberFormatException var8) {
                var8.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static String calendar2String(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(calendar.getTime());
        return dateStr;
    }

    public static String date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    private static String A(String IP) {
        while(IP.startsWith(" ")) {
            IP = IP.substring(1, IP.length()).trim();
        }

        while(IP.endsWith(" ")) {
            IP = IP.substring(0, IP.length() - 1).trim();
        }

        return IP;
    }

    public static boolean isIp(String IP) {
        if (IP != null && !IP.isEmpty()) {
            boolean b = false;
            IP = A(IP);
            if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
                String[] s = IP.split("\\.");
                if (Integer.parseInt(s[0]) < 255 && Integer.parseInt(s[1]) < 255 && Integer.parseInt(s[2]) < 255 && Integer.parseInt(s[3]) < 255) {
                    b = true;
                }
            }

            return b;
        } else {
            return false;
        }
    }

    public static String getUrlValue(String url, String startStr, String endStr) {
        if (url != null && startStr != null) {
            int startIndex = url.indexOf(startStr);
            if (startIndex < 0) {
                return null;
            } else {
                int endIndex = endStr != null ? url.indexOf(endStr, startIndex) : url.length();
                if (startIndex >= endIndex) {
                    endIndex = url.length();
                }

                try {
                    return url.substring(startIndex + startStr.length(), endIndex);
                } catch (IndexOutOfBoundsException var6) {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    public static void parseTestConfigFile(String filePath, Map<String, String> map) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filePath));

            for(String lineStr = br.readLine(); lineStr != null; lineStr = br.readLine()) {
                String[] values = lineStr.split("\\$");
                if (values.length == 2) {
                    map.put(values[0], values[1]);
                }
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
            }

        }

    }

    public static boolean isEZOpenProtocol(String url) {
        return url.startsWith("ezopen://");
    }

    private static Map<String, String[]> b(String queryString, String enc) {
        Map<String, String[]> paramsMap = new HashMap();
        if (queryString != null && queryString.length() > 0) {
            int lastAmpersandIndex = 0;

            int ampersandIndex;
            do {
                ampersandIndex = queryString.indexOf(38, lastAmpersandIndex) + 1;
                String subStr;
                if (ampersandIndex > 0) {
                    subStr = queryString.substring(lastAmpersandIndex, ampersandIndex - 1);
                    lastAmpersandIndex = ampersandIndex;
                } else {
                    subStr = queryString.substring(lastAmpersandIndex);
                }

                String[] paramPair = subStr.split("=");
                String param = paramPair[0];
                String value = paramPair.length == 1 ? "" : paramPair[1];

                try {
                    value = URLDecoder.decode(value, enc);
                } catch (UnsupportedEncodingException var12) {
                    ;
                }

                String[] newValues;
                if (paramsMap.containsKey(param)) {
                    String[] values = (String[])paramsMap.get(param);
                    int len = values.length;
                    newValues = new String[len + 1];
                    java.lang.System.arraycopy(values, 0, newValues, 0, len);
                    newValues[len] = value;
                } else {
                    newValues = new String[]{value};
                }

                paramsMap.put(param, newValues);
            } while(ampersandIndex > 0);
        }

        return paramsMap;
    }
}
