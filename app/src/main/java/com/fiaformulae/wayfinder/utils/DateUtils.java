package com.fiaformulae.wayfinder.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

  public static String getTimeString(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
    return dateFormat.format(calendar.getTime());
  }
}
