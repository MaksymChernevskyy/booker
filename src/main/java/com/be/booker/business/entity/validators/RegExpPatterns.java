package com.be.booker.business.entity.validators;

import java.util.regex.Pattern;

public class RegExpPatterns {
  static Pattern namePattern = Pattern.compile("[A-Za-z]{1,50}");
  static Pattern locationPattern = Pattern.compile("^([A-Za-z][a-z]*)+(?:[\\s-][A-Za-z][a-z])*$");
  static Pattern phoneNumber = Pattern.compile("^([0-9]*)+(?:[\\s-][0-9]*)*$");
}
