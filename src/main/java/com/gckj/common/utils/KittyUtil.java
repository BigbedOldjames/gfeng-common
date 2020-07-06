package com.gckj.common.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class KittyUtil {

	public static Object getProperty(Object target, String name)
			throws Exception {

		String method_name = "get" + name.substring(0, 1).toUpperCase()
				+ name.substring(1);
		Method method = target.getClass().getMethod(method_name, new Class[0]);
		return method.invoke(target, new Object[0]);

	}

	public static <T> T getObjectByIntParam(T[] array, String name, int value)
			throws Exception {
		String method_name = "get" + name.substring(0, 1).toUpperCase()
				+ name.substring(1);
		if (array.length == 0) {
			return null;
		}
		Method method = array[0].getClass()
				.getMethod(method_name, new Class[0]);
		for (int i = 0; i < array.length; i++) {
			Integer returnValue = (Integer) method.invoke(array[i],
					new Object[0]);
			if (returnValue.intValue() == value) {
				return array[i];
			}
		}
		return null;
	}

	public static int[] fetchIntArray(Object[] objectArray, String name)
			throws Exception {
		if (objectArray.length == 0) {
			return new int[0];
		}
		String method_name = "get" + name.substring(0, 1).toUpperCase()
				+ name.substring(1);
		Method method = objectArray[0].getClass().getMethod(method_name,
				new Class[0]);
		int[] array = new int[objectArray.length];
		for (int i = 0; i < objectArray.length; i++) {
			Integer value = (Integer) method.invoke(objectArray[i],
					new Object[0]);
			array[i] = value.intValue();
		}
		return array;
	}

	public static int[] sortByAsc(int[] array) {
		for (int i = 0; i < array.length; ++i)
			for (int j = i; j < array.length - 1; ++j) {
				int q = 0;
				if (array[i] > array[(j + 1)]) {
					q = array[i];
					array[i] = array[(j + 1)];
					array[(j + 1)] = q;
				}
			}

		return array;
	}

	public static int[] sortByDesc(int[] array) {
		for (int i = 0; i < array.length; ++i)
			for (int j = i; j < array.length - 1; ++j) {
				int q = 0;
				if (array[i] < array[(j + 1)]) {
					q = array[i];
					array[i] = array[(j + 1)];
					array[(j + 1)] = q;
				}
			}

		return array;
	}

	public static int getLocation(String[] array, String target) {
		if (array == null)
			return -1;
		for (int i = 0; i < array.length; ++i)
			if (array[i].equals(target))
				return i;

		return -1;
	}

	public static int getLocation(long[] array, long target) {
		if (array == null)
			return -1;

		for (int i = 0; i < array.length; ++i)
			if (array[i] == target)
				return i;

		return -1;
	}

	public static int getLocation(int[] array, int target) {
		if (array == null)
			return -1;

		for (int i = 0; i < array.length; ++i)
			if (array[i] == target)
				return i;

		return -1;
	}

	public static int getLocationScope(int[] array, int target) {
		if (array == null)
			return -1;

		if (array.length == 1)
			return 0;

		for (int i = 0; i < array.length - 1; ++i)
			if ((array[i] < target) && (target < array[(i + 1)]))
				return i;

		return (array.length - 1);
	}

	public static String[] dividStr(String origStr, String flag) {
		String str = origStr;
		if (str == null)
			str = "";

		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(str, flag);
		while (token.hasMoreTokens())
			list.add(token.nextToken());

		String[] array = new String[list.size()];
		for (int i = 0; i < array.length; ++i)
			array[i] = ((String) list.get(i));

		return array;
	}

	public static int[] Str2Array(String str) {
		if ((str == null) || (str.equals("")))
			return new int[0];

		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(str, ":");
		while (token.hasMoreTokens())
			list.add(token.nextToken());

		int[] array = new int[list.size()];
		int value = 0;
		for (int i = 0; i < array.length; ++i) {
			try {
				value = Integer.parseInt((String) list.get(i));
			} catch (NumberFormatException e) {
				value = -1;
			}
			array[i] = value;
		}
		return array;
	}

	public static String Array2Str(String[] array) {
		if (array == null)
			return "";

		String str = "";
		for (int i = 0; i < array.length; ++i)
			if (i == 0)
				str = array[i];
			else
				str = str + ":" + array[i];

		return str;
	}

	public static String Array2Str(int[] array, String flag) {
		if (array == null)
			return "";

		String str = "";
		for (int i = 0; i < array.length; ++i)
			if (i == 0)
				str = "";
			else
				str = str + flag + array[i];

		return str;
	}

	public static String Array2Str(String[] array, String flag) {
		if (array == null)
			return "";

		String str = "";
		for (int i = 0; i < array.length; ++i)
			if (i == 0)
				str = array[i];
			else
				str = str + flag + array[i];

		return str;
	}

	public static String Array2Str(int[] array) {
		if (array == null)
			return "";

		String str = "";
		for (int i = 0; i < array.length; ++i)
			if (i == 0)
				str = array[i] + "";
			else
				str = str + ":" + array[i];

		return str;
	}

	public static Date parseDate(String orig, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			return simpleDateFormat.parse(orig);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public static long parseLong(String str, long instead) {
		long id = -1;
		try {
			id = Long.parseLong(str);
		} catch (Exception e) {
			id = instead;
		}
		return id;
	}

	public static float parseFloat(String str,float instead){
		float value = 0;
		try {
			value = Float.parseFloat(str);
		} catch (Exception e) {
			value = instead;
		}
		return value;
	}
	
	public static double parseDouble(String str,float instead){
		double value = 0;
		try {
			value = Double.parseDouble(str);
		} catch (Exception e) {
			value = instead;
		}
		return value;
	}
	
	public static int parseInt(String str, int instead) {
		int id = -1;
		try {
			id = Integer.parseInt(str);
		} catch (Exception e) {
			id = instead;
		}
		return id;
	}

	public static Date parseDate(String orig, String format, Date instead) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			return simpleDateFormat.parse(orig);
		} catch (Exception e) {
			return instead;
		}
	}

	public static String formatDate(Date date, String format) {
		if(date==null){
			return "";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			return simpleDateFormat.format(date);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public static Object newInstance(String className) {
		try {

			return Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public static boolean parseBoolean(String str, boolean instead) {
        if(str == null || str.trim().equals(""))
            return instead;
        str = str.trim();
        if(str.equalsIgnoreCase("true"))
            return true;
        else if(str.equalsIgnoreCase("false"))
            return false;
        else 
            return instead;
    }

	public static void main(String[] args) {
//		int[] ss = { 3, 6, 1, 7, 2, 19, 24 };
//		int[] tt = sortByDesc(ss);
//		for (int i = 0; i < tt.length; ++i)
//			System.out.println(tt[i]);
	}
}