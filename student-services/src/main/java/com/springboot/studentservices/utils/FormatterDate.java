package com.springboot.studentservices.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class FormatterDate {
	
	public String formatDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
		String formattedString = date.format(formatter);
		return formattedString;
	}
	
}
