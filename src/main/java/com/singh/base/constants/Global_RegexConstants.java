package com.singh.base.constants;

public class Global_RegexConstants {
	public static final String stringNotContainsDigit = "^[a-zA-Z]+$" ;
	public static final String postalCode = "^[1-9][0-9]{5}$";
	public static final String MobileNumber = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
	public static final String OnlyDigits = "\\d{10}";

}
