package com.belk.payon.util.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class CalTest {
	/*
	 * public static void main(String[] argv) {
	 * 
	 * LocalDate todayDate = LocalDate.now(); int JulianDate =
	 * todayDate.getDayOfYear();
	 * 
	 * System.out.println("julianDate :"+JulianDate);
	 * 
	 * 
	 * int julianDate = convertToJulian("10/09/2019");
	 * System.out.println("julianDate :"+julianDate);
	 * 
	 * GregorianCalendar gc = new GregorianCalendar();
	 * gc.set(GregorianCalendar.DAY_OF_MONTH, 10); gc.set(GregorianCalendar.MONTH,
	 * GregorianCalendar.SEPTEMBER); gc.set(GregorianCalendar.YEAR, 2019);
	 * System.out.println(gc.get(GregorianCalendar.DAY_OF_YEAR));
	 * 
	 * 
	 * }
	 */

	public  int convertToJulian(String unformattedDate) {
		/* Unformatted Date: ddmmyyyy */
		int resultJulian = 0;
		if (unformattedDate.length() > 0) {
			/* Days of month */
			int[] monthValues = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

			String dayS, monthS, yearS;
			dayS = unformattedDate.substring(0, 2);
			monthS = unformattedDate.substring(3, 5);
			yearS = unformattedDate.substring(6, 10);

			/* Convert to Integer */
			int day = Integer.valueOf(dayS);
			int month = Integer.valueOf(monthS);
			int year = Integer.valueOf(yearS);

			// Leap year check
			if (year % 4 == 0) {
				monthValues[1] = 29;
			}
			// Start building Julian date
			String julianDate = "1";
			// last two digit of year: 2012 ==> 12
			julianDate += yearS.substring(2, 4);

			int julianDays = 0;
			for (int i = 0; i < month - 1; i++) {
				julianDays += monthValues[i];
			}
			julianDays += day;

			if (String.valueOf(julianDays).length() < 2) {
				julianDate += "00";
			}
			if (String.valueOf(julianDays).length() < 3) {
				julianDate += "0";
			}

			julianDate += String.valueOf(julianDays);
			resultJulian = Integer.valueOf(julianDate);
		}
		return resultJulian;
	}
}
