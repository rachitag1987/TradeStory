package com.db.trade.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static Date convertStringToDate(String date) throws ParseException {
		if (null == date)
			return null;
		return new SimpleDateFormat("dd/MM/yyyy").parse(date);
	}
}
