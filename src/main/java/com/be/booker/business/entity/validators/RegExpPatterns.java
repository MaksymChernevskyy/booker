package com.be.booker.business.entity.validators;

import java.util.regex.Pattern;

public class RegExpPatterns {
  static Pattern namePattern = Pattern.compile("[A-Za-z]{1,50}");
  static Pattern surnamePattern = Pattern.compile("[A-Za-z]{1,100}");
  static Pattern locationPattern = Pattern.compile("^([A-Za-z][a-z]*)+(?:[\\s-][A-Za-z][a-z])*$");
  static Pattern loginPattern = Pattern.compile(".{1,100}");
  static Pattern passwordPattern = Pattern.compile(".{6,100}");
  static Pattern phoneNumber = Pattern.compile("^([0-9]*)+(?:[\\s-][0-9]*)*$");
}
