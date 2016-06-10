package com.csmbs.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateUtils
{
  private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
  
  public static String getCurrentFormattedDate(String format)
  {
    LOGGER.info(":Inside getCurrentFormattedDate() --> ");
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Date Format : {} ", new String[] { format });
    }
    String formattedDate = "";
    Calendar calendar = Calendar.getInstance();
    Date creationDate = calendar.getTime();
    SimpleDateFormat outputDf = new SimpleDateFormat(format);
    formattedDate = outputDf.format(creationDate);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Today's date after formatting : {} ", new String[] { formattedDate });
    }
    LOGGER.info(":Exiting getCurrentFormattedDate() --> ");
    return formattedDate;
  }
  
  public static String getDateTimeDisplayFomat(Date date)
  {
    LOGGER.info(":Inside getDateTimeDisplayFomat() --> ");
    String displayString = null;
    if (date != null)
    {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm aaa");
      displayString = formatter.format(date);
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("date after formatting : {} ", new String[] { displayString });
    }
    LOGGER.info(":Exiting getDateTimeDisplayFomat() --> ");
    return displayString;
  }
  
  public static String getDateTimeDisplayFomat(Date date, String language, String location)
  {
    LOGGER.info(":Inside getDateTimeDisplayFomat() --> ");
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("language : {} , location : {} ", new String[] { language, location });
    }
    String displayString = null;
    if ((date != null) && (language != null) && (location != null))
    {
      Locale ll = new Locale(language.toLowerCase(), location.toUpperCase());
      displayString = DateTimeFormat.forPattern("dd-MMM-yyyy hh:mm aaa").withLocale(ll)
        .print(date.getTime());
    }
    else if ((language == null) || (location == null))
    {
      displayString = getDateTimeDisplayFomat(date);
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("date after formatting : {} ", new String[] { displayString });
    }
    LOGGER.info(":Exiting getDateTimeDisplayFomat() --> ");
    return displayString;
  }
  
  public static String getDateTimeDisplayFomat(String dateString)
  {
    LOGGER.info(":Inside getDateTimeDisplayFomat() --> ");
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("dateString  : {} ", new String[] { dateString });
    }
    SimpleDateFormat formatterCq = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    Date date = null;
    String displayFormat = null;
    try
    {
      date = formatterCq.parse(dateString);
      displayFormat = getDateTimeDisplayFomat(date);
    }
    catch (ParseException e)
    {
      LOGGER.error("ParseException : ", e);
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Display format : {} ", new String[] { displayFormat });
    }
    LOGGER.info(":Exiting getDateTimeDisplayFomat() --> ");
    return displayFormat;
  }
  
  public static String getDateTimeDisplayFomat(String dateString, String language, String location)
  {
    LOGGER.info(":Inside getDateTimeDisplayFomat() --> ");
    SimpleDateFormat formatterCq = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    Date date = null;
    String displayFormat = null;
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("date : {} , language : {} , location : {} ", new String[] { dateString, language, location });
    }
    try
    {
      date = formatterCq.parse(dateString);
      displayFormat = getDateTimeDisplayFomat(date, language, location);
    }
    catch (ParseException e)
    {
      LOGGER.error("ParseException : ", e);
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Display format : {} ", new String[] { displayFormat });
    }
    LOGGER.info(":Exiting getDateTimeDisplayFomat() --> ");
    return displayFormat;
  }
  
  public static String getDateDisplayFomat(Date date)
  {
    LOGGER.info(":Inside getDateDisplayFomat() --> ");
    String displayString = null;
    if (date != null)
    {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
      displayString = formatter.format(date);
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("date after formatting : {} ", new String[] { displayString });
    }
    LOGGER.info(":Exiting getDateDisplayFomat() --> ");
    return displayString;
  }
  
  public static String getDateDisplayFomat(String dateString)
  {
    LOGGER.info(":Inside getDateDisplayFomat() --> ");
    SimpleDateFormat formatterCq = new SimpleDateFormat("MM/dd/yy");
    Date date = null;
    String displayFormat = null;
    try
    {
      date = formatterCq.parse(dateString);
      displayFormat = getDateDisplayFomat(date);
    }
    catch (ParseException e)
    {
      LOGGER.error("ParseException : ", e);
    }
    LOGGER.info(":Exiting getDateDisplayFomat() --> ");
    return displayFormat;
  }
  
  public static String getbigNumberToDate(String value)
  {
    LOGGER.info(":Inside getbigNumberToDate() --> ");
    String displayFormat = " ";
    if (!ValidationUtil.isEmpty(value))
    {
      try
      {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        
        long milliSeconds = Long.parseLong(value);
        LOGGER.info("milliSeconds value :" + milliSeconds);
        
        Calendar calendar = Calendar.getInstance();
        LOGGER.info("Time zone is: " + calendar.getTimeZone().getDisplayName());
        calendar.setTimeInMillis(milliSeconds);
        
        displayFormat = formatter.format(calendar.getTime());
        LOGGER.info("Afert changing milliSeconds into date :" + displayFormat);
      }
      catch (Exception e)
      {
        LOGGER.error("ParseException : ", e);
      }
      LOGGER.info(":Existing getbigNumberToDate() --> ");
      return displayFormat;
    }
    LOGGER.info(":Existing getbigNumberToDate() --> ");
    return displayFormat;
  }
  
  public static String getDateFromMilliSec(String value)
  {
    LOGGER.info(":Inside getDateFromMilliSec() --> ");
    String displayFormat = " ";
    if (!ValidationUtil.isEmpty(value))
    {
      LOGGER.info("Befor converting into milliSeconds :" + value);
      long milliSeconds = Long.parseLong(value);
      LOGGER.info("After converting into milliSeconds :" + milliSeconds);
      DateTime jodaTime = new DateTime(milliSeconds);
      DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MMM-yyyy");
      displayFormat = dtf.print(jodaTime);
      LOGGER.info("Joda time is" + displayFormat);
      return displayFormat;
    }
    LOGGER.info("Values is empty" + displayFormat);
    return displayFormat;
  }
  
  public static String getDateZoneDisplay(Date date)
  {
    LOGGER.info(":Inside getDateDisplayFomat() --> ");
    String displayString = null;
    if (date != null)
    {
      SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
      displayString = formatter.format(date);
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("date after formatting : {} ", new String[] { displayString });
    }
    LOGGER.info(":Exiting getDateDisplayFomat() --> ");
    return displayString;
  }
  
  public static String getMonthFromMilliSec(String value)
  {
    LOGGER.info(":Inside getDateFromMilliSec() --> ");
    String displayFormat = " ";
    if (!ValidationUtil.isEmpty(value))
    {
      LOGGER.info("Befor converting into milliSeconds :" + value);
      long milliSeconds = Long.parseLong(value);
      LOGGER.info("After converting into milliSeconds :" + milliSeconds);
      DateTime jodaTime = new DateTime(milliSeconds);
      DateTimeFormatter dtf = DateTimeFormat.forPattern("MMM yyyy");
      displayFormat = dtf.print(jodaTime);
      LOGGER.info("Joda time is" + displayFormat);
      return displayFormat;
    }
    LOGGER.info("Values is empty" + displayFormat);
    return displayFormat;
  }
}
