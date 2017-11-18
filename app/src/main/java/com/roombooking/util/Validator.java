package com.roombooking.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	private static Pattern pattern;
    private static Matcher matcher;


	private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validateEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		return matcher.find();
	}
	public static boolean validatePhoneNumber(String phoneNo) {
		//validate phone numbers of format "1234567890"
		if (phoneNo.matches("\\d{10}")) return true;
		return false;
	}
}