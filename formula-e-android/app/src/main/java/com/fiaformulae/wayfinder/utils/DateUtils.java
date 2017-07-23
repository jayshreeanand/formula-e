package com.fiaformulae.wayfinder.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

  public static String getTimeString(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(date);
  }
}
