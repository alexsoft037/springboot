package xin.xiaoer.common.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtility {

	public static final Object NULL = new Object() {
		@Override public boolean equals(Object o) {
			return o == this || o == null; // API specifies this broken equals implementation
		}
		@Override public String toString() {
			return "null";
		}
	};

	public static String cardinal(int value) {
		if (value < 0) {
			return Integer.toString(value);
		}
		int i1, i2;
		String result = "";
		i1 = value;
		do {
			if (i1 < 1000) {
				i2 = i1;
				result = Integer.toString(i2) + " " + result;
				i1 = i1 / 1000;
			} else {
				i2 = i1 % 1000;
				result = String.format("%1$03d", i2) + " " + result;
				i1 = i1 / 1000;
			}
		} while (i1 > 0);
		return result;
	}

	public static boolean checkAttackString(String pAttackString) {
		final String[] excludeRegularStrings = { "%", "..", "\\", "'", "null", " union ", "select ", "from ", " and ",
				" or ", "script" };
		if ("".equals(pAttackString))
			return false;
		if (StringUtility.isAlphabetic(pAttackString))
			return false;
		String temp = pAttackString.toLowerCase();
		for (String regString : excludeRegularStrings) {
			if (temp.indexOf(regString) != -1) {
				return true;
			}
		}
		return false;
	}

	public static String doubleToString(double value) {
		String tempStr = String.format("%.1f", value);
		if (tempStr.substring(tempStr.length() - 1).equals("0"))
			return String.format("%.0f", value);
		else
			return String.format("%.1f", value);
	}

	public static String escapeSpecCHs(String str_source, String str_specs) {
		if (str_source == null || str_source.isEmpty())
			return str_source;

		for (int i = 0; i < str_specs.length(); i++) {
			str_source = str_source.replace(String.format("%c", str_specs.charAt(i)), "");
		}
		return str_source;
	}

	public static String findByReExp(String pPattern, String pSource) {
		if (isEmpty(pPattern) || isEmpty(pSource))
			return "";
		Pattern compiledPattern;
		try {
			compiledPattern = Pattern.compile(pPattern);
		} catch (PatternSyntaxException e) {
			return "";
		}
		Matcher matcher = compiledPattern.matcher(pSource);
		if (matcher.find()) {
			String matchedText = matcher.group();
			return matchedText;
		} else {
			return "";
		}
	}

	public static String findStringSubBlock(String pVal, String pParam) {
		if (isEmpty(pVal) || isEmpty(pParam))
			return null;
		int first_index = pVal.indexOf(pParam);
		int second_index = 0;
		if (first_index >= 0) {
			second_index = pVal.indexOf(pParam, first_index + 1);
			if (second_index >= 0)
				return pVal.substring(first_index, second_index + 1);
		}
		return null;
	}

	private static String fullCharsConvertToHalf(String stringValue) {
		String fullChars = "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＲＳＴＵＶＷＸＹＺ０１２３４５６７８９　，．";
		String halfChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 ,.";

		char[] fullCharArray = fullChars.toCharArray();
		for (char compChar : fullCharArray) {
			int offset = stringValue.indexOf(Character.toString(compChar));
			if (offset != -1) {
				char replaceChar = halfChars.charAt(fullChars.indexOf(Character.toString(compChar)));
				stringValue.replace(compChar, replaceChar);
			}
		}
		return stringValue;

	}

	public static String getLastName(String pSource, String pSeparator) {
		if (isEmpty(pSource) || isEmpty(pSeparator))
			return "";
		int lastIndex = pSource.lastIndexOf(pSeparator);
		if (lastIndex != -1)
			return pSource.substring(lastIndex + 1);
		return "";
	}

	public static String htmlSpecialChars(String str_source) {
		if (str_source == null || str_source.isEmpty())
			return str_source;

		str_source = str_source.replace("&", "&amp;");
		str_source = str_source.replace("\"", "&quot;");
		str_source = str_source.replace("\'", "&#039;");
		str_source = str_source.replace("<", "&lt;");
		str_source = str_source.replace(">", "&gt;");

		return str_source;
	}

	public static String InsertString(String Source, String InsertStr, int index) {
		if (index >= Source.length())
			return Source + InsertStr;
		String ctPrev;
		if (Source.length() != 0)
			ctPrev = Source.substring(0, index);
		else
			ctPrev = "";
		ctPrev += InsertStr;
		try {
			return ctPrev + Source.substring(index);
		} catch (StringIndexOutOfBoundsException e) {
			return ctPrev;
		}
	}

	public static String IntegerToString(String strPlus, int iPlus, int iSameLen) {
		int iResult = 0;
		try {
			iResult = Integer.parseInt(strPlus) + iPlus;
		} catch (Exception e) {
			return null;
		}
		String strResult = iResult + "";
		while (strResult.length() < iSameLen) {
			strResult = "0" + strResult;
		}
		return strResult;
	}

	public static Boolean isAlphabetic(String pStr) {
		if (isEmpty(pStr))
			return false;
		char chars[] = pStr.toCharArray();
		for (char tmpChar : chars) {
			if ((tmpChar >= 'A' && 'z' >= tmpChar) || (tmpChar >= '0' && '9' >= tmpChar) || tmpChar == '-'
					|| tmpChar == '_' || tmpChar == '.')
				continue;
			else
				return false;
		}

		return true;
	}

	public static boolean isEmpty(String pVal) {
		if (pVal == null)
			return true;
		if (pVal.isEmpty())
			return true;
		return false;
	}

	public static boolean isNotEmpty(String pVal) {
		return !isEmpty(pVal);
	}

	public static boolean isIpAddress(String pIp) {
		if (isEmpty(pIp))
			return false;
		List<String> list = new ArrayList<String>();
		list = splitToList(pIp, "\\.");
		if (list.isEmpty())
			return false;
		if (list.size() != 4)
			return false;
		for (String i : list) {
			if (!isNumeric(i))
				return false;
		}
		return true;

	}

	public static Boolean isNumeric(String pStr) {
		if (isEmpty(pStr))
			return false;
		char chars[] = pStr.toCharArray();
		for (char tmpChar : chars)
			if (!Character.isDigit(tmpChar))
				return false;
		return true;
	}

	public static String listToString(List<String> pSource, String pSeparator) {
		if (pSource == null || pSource.size() == 0)
			return "";
		if (isEmpty(pSeparator))
			return "";
		String retString = "";
		for (String str : pSource)
			retString += (retString.isEmpty() ? "" : pSeparator) + str;
		return retString;
	}

	private static String removeDoubleSpace(String pVal) {
		String doubleSpace = "  ";
		int offset = pVal.indexOf(doubleSpace);
		if (offset != -1)
			pVal.replaceAll(doubleSpace, " ");
		return pVal;
	}

	public static String replaceSpecStrs(String str_source, String str_escape_strs, String str_separator,
			String str_replace) {
		if (str_source == null || str_source.isEmpty())
			return str_source;

		List<String> list_escape_strs = splitToList(str_escape_strs, str_separator);

		for (int i = 0; i < list_escape_strs.size(); i++) {
			str_source = str_source.replace(list_escape_strs.get(i), str_replace);
		}

		return str_source;
	}

	public static List<String> splitToList(String pSource, String pSeparator) {
		if (pSource.length() == 0)
			return null;
		List<String> retList = new ArrayList<String>();
		String[] actionArray = pSource.split(pSeparator);
		for (String action : actionArray)
			retList.add(action);
		return retList;
	}

	public static String toUtf8Decoding(String pVal) {
		if (pVal == null)
			return null;
		String ret;
		try {
			ret = new String(pVal.getBytes("8859_1"), "utf8");
		} catch (Exception e) {
			return null;
		}
		return ret;
	}

	public static String toUtf8Encoding(String pVal) {
		if (pVal == null)
			return null;
		String ret;
		try {
			ret = new String(pVal.getBytes("utf8"), "8859_1");
		} catch (Exception e) {
			return null;
		}
		return ret;
	}

	public static String ucFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	public static String validatePath(String pPath) {
		if (isEmpty(pPath))
			return "";
		pPath.replaceAll(" ", "\\ ");
		pPath.replaceAll("'", "\\'");
		return pPath;
	}

	public static String validateString(String str_source) {
		if (str_source == null || str_source.isEmpty())
			return str_source;

		str_source = str_source.trim();

		str_source = fullCharsConvertToHalf(str_source);

		str_source = removeDoubleSpace(str_source);
		return str_source;
	}

	public static String checkInteger(String str){
		if (isNumeric(str)){
			return str;
		} else {
			return null;
		}
	}
	public static boolean isNull(String name) {
		LinkedHashMap<String, Object> nameValuePairs = new LinkedHashMap<String, Object>();
		Object value = nameValuePairs.get(name);
		return value == null || value == NULL;
	}
}
