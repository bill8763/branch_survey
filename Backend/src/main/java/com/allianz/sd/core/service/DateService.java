package com.allianz.sd.core.service;

import com.allianz.sd.core.service.bean.DateType;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 7:06
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DateService {
    private static List<String> dateFormatList = new ArrayList<String>();
    static{

        dateFormatList.add("MM/dd/yyyy HH:mm:ss");
        dateFormatList.add("MM-dd-yyyy HH:mm:ss");

        dateFormatList.add("MM/dd/yyyy");
        dateFormatList.add("MM-dd-yyyy");

        dateFormatList.add("yyyy/MM/dd HH:mm:ss");
        dateFormatList.add("yyyy/MM/dd HH:mm");
        dateFormatList.add("yyyy/MM/dd");

        dateFormatList.add("yy/MM/dd HH:mm:ss");
        dateFormatList.add("yy/MM/dd HH:mm");
        dateFormatList.add("yy/MM/dd");
        dateFormatList.add("yyyy-MM-dd HH:mm:ss");
        dateFormatList.add("yyyy-MM-dd HH:mm");

        dateFormatList.add("yy-MM-dd HH:mm:ss");
        dateFormatList.add("yy-MM-dd HH:mm");
        dateFormatList.add("yyyy-MM-dd");
        dateFormatList.add("yy-MM-dd");
        dateFormatList.add("yyyy-MM");
        dateFormatList.add("yyyy/MM");

        dateFormatList.add("cy/MM/dd HH:mm:ss");
        dateFormatList.add("cy/MM/dd HH:mm");
        dateFormatList.add("cy/MM/dd");

        dateFormatList.add("cy-MM-dd HH:mm:ss");
        dateFormatList.add("cy-MM-dd HH:mm");
        dateFormatList.add("cy-MM-dd");
    }

    public Date setYear(int year , Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.YEAR,year);

        return c.getTime();
    }

    public Date setMonth(int month , Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.MONTH,month-1);

        return c.getTime();
    }

    public Date setDate(int day , Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DATE,day);

        return c.getTime();
    }

    public Date setWholeHour(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.HOUR,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);

        return c.getTime();
    }

    public int getFullYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);

        return c.get(Calendar.YEAR);
    }

    public java.util.Date getTodayDate() {
        return new GregorianCalendar().getTime();
    }

    public java.util.Date getDate(int year , int month , int date) {
        return getDate(year,month,date,0,0,0);
    }

    public java.util.Date getDate(int year , int month , int date , int hour , int min , int sec) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DATE,date);
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,min);
        c.set(Calendar.SECOND,sec);

        return c.getTime();
    }

    public int getCurrentYear() {
        return new GregorianCalendar().get(Calendar.YEAR);
    }

    public int getCurrentMonth() {
        return new GregorianCalendar().get(Calendar.MONTH) + 1;
    }

    public int getCurrentDate() {
        return new GregorianCalendar().get(Calendar.DATE);
    }



    public String getTodayString() {
        java.util.Date now = new GregorianCalendar().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(now);
    }

    public String toDateString(java.util.Date date) {
        if(date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    public String toDateString(java.util.Date date,String format) {
        if(date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public String toDateTimeFormatString(java.util.Date date) {
        if(date == null) return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        return format.format(date);
    }
    
    public String getMonthString(java.util.Date date) {
        if(date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("M");
        return sdf.format(date);
    }
    
    public Date toDateTimeFormatDate(String dateString) {

        Date date = null;

        if(dateString.contains("z") || dateString.contains("Z")) {
            date = Date.from( Instant.parse( dateString ));
        }
        else {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            try{
                date = format.parse(dateString);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        return date;
    }

    public Date addDate(Date date, int mode, int interval) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(mode, interval);

        return gc.getTime();

    }

    public int getBetweenTime(Date startTime,Date endTime,DateType type) {
        int betweenTime = -1;

        try{

            GregorianCalendar gc1 = new GregorianCalendar();
            GregorianCalendar gc2 = new GregorianCalendar();
            gc1.setTime(startTime);
            gc2.setTime(endTime);

            long l = gc2.getTimeInMillis() - gc1.getTimeInMillis();

            switch(type) {
                case YEAR : {
                    int startYear = gc1.get(Calendar.YEAR);
                    int endYear = gc2.get(Calendar.YEAR);
                    return endYear - startYear;
                }
                case MONTH : {
                    int betweenYear = getBetweenTime(startTime, endTime, DateType.YEAR);
                    int startMonth = gc1.get(Calendar.MONTH);
                    int endMonth = gc2.get(Calendar.MONTH);

                    if(endMonth > startMonth) {
                        return ((betweenYear * 12) + (endMonth - startMonth));
                    }
                    else {
                        return ((betweenYear * 12) - (startMonth - endMonth));
                    }
                }
                case DAY : {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                    String begin = sdf.format(startTime);
                    startTime  = sdf.parse(begin);

                    String end= sdf.format(endTime);
                    endTime= sdf.parse(end);

                    betweenTime = (int)((endTime.getTime()-startTime.getTime())/(24*60*60*1000));

                    break;
                }
                case HOUR: betweenTime = (int)(l / 1000 / 60 / 60);break;
                case MIN : betweenTime = (int)(l / 1000 / 60);break;
                case SEC : betweenTime = (int)(l / 1000);break;
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return betweenTime;
    }

    public Date[] getCurrentWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);

        Date start = null;
        Date end = null;

        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);

        // 所在周开始日期
        start = cal.getTime();

        cal.add(Calendar.DAY_OF_WEEK, 6);

        // 所在周结束日期
        end = cal.getTime();

        return new Date[]{start,end};
    }

    public static void main(String[] args) {
        DateService dateService = new DateService();

        int currentDate = dateService.getCurrentDate();
        int currentMonth = dateService.getCurrentMonth();
        System.out.println(currentMonth);
        Date startDate = dateService.getDate(2019,currentMonth,currentDate);

        System.out.println(startDate);

        Date[] week = dateService.getCurrentWeek();

        System.out.println(week[0]);
        System.out.println(week[1]);

//        System.out.println(dateService.getCurrentWeek()[0]+"  "+dateService.getCurrentWeek()[1]);


//        Date date = dateService.getDate("2019/02/22 11:45:00");
//        System.out.println(date);
//
//        System.out.println(dateService.toDateString(dateService.getTodayDate(),"yyyy-MM-dd"));
//        Date date = dateService.getDate("1990-01-26");
//        System.out.println(date);
//        System.out.println(date.getYear());
//        System.out.println(dateService.toDateTimeFormatDate("1970-01-01T00:00:00.000Z"));
//        System.out.println(dateService.toDateTimeFormatDate("2019-05-04T16:04:32+0800"));
//
//        System.out.println(        dateService.toDateTimeFormatDate(dateService.toDateString(dateService.getTodayDate(), "yyyy-MM-dd'T'00:00:00+0800") ));

//        System.out.println(dateService.toDateTimeFormatString(new Date()));

//        System.out.println(Date.from( Instant.parse( "2019-05-07T06:50:00.000Z" )));
//        System.out.println(Date.from( Instant.parse( "2019-05-04T16:04:32+0800" )));

    }

    public Date getDate(String originalDateString) {

        String format = getDateFormat(originalDateString);
        if(format != null) {
            try{
                return new SimpleDateFormat(format).parse(originalDateString);
            }catch(Exception e) {
                return null;
            }
        }
        return null;

    }


    public Date parseDateString2Date(String dateString) {
        Date ret = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        dateString = dateString.replaceAll("\\p{Punct}", "");
        dateString = dateString.replaceAll("$0*", "");
        if (dateString.length() == 8) {
            dateString += "000000";
        } else if (dateString.length() == 14) {
            ;
        } else {
            dateString = "";
        }
        try {
            ret = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Date getDateByTimestamp(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return calendar.getTime();
    }

    public String getDateFormat(String originalDateString) {

        for(String format : dateFormatList) {

            boolean isSameFormat = false;
            if(originalDateString.indexOf("-") != -1 && format.indexOf("-") != -1) {
                int firstLength = originalDateString.split("-")[0].length();
                int formatLength = format.split("-")[0].length();
                if(firstLength == formatLength) isSameFormat = true;
            }

            if(originalDateString.indexOf("/") != -1 && format.indexOf("/") != -1) {
                int firstLength = originalDateString.split("/")[0].length();
                int formatLength = format.split("/")[0].length();
                if(firstLength == formatLength) isSameFormat = true;
            }

            if(isSameFormat) {
//                System.out.println("format = " + format);

                try{
                    if(format.startsWith("cy")) {
                        format = format.replaceAll("cy","yyyy");
                    }

                    new SimpleDateFormat(format).parse(originalDateString);
                    return format;
                }catch(Exception e) {
                    ;
                }
            }


        }

        return null;
    }

    public Date getThisWeekFirstDate() {
        Calendar calendar = Calendar.getInstance();
        int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK);
        int current = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_WEEK, (min - current) + 1);
        Date start = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6);

        return start;
    }

    public Date getThisWeekEndDate() {
        Calendar calendar = Calendar.getInstance();
        int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK);
        int current = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_WEEK, (min - current) + 1);

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date end = calendar.getTime();

        return end;
    }

    public Date getQurarterStartDate(int Quarter) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, (Quarter - 1) * 3);
        Date StartDate = calendar.getTime();

        return StartDate;
    }

    public List<Integer>getForecastDate(int Quarter,int Month) {
        //int month=Month%3;
        List<Integer> timeBaseSeq = new ArrayList<Integer>();
        //timeBaseSeq.add(e);
        for(int a=Month+1;a<=Quarter*3;a++)
        {
            timeBaseSeq.add(a);

        }
        return timeBaseSeq;
    }

    public List<Integer>getForecastQuarter(int Quarter) {

        List<Integer> timeBaseSeq = new ArrayList<Integer>();

        for(int a=Quarter+1;a<=4;a++)
        {
            timeBaseSeq.add(a);
        }
        if(Quarter==4)
            timeBaseSeq.add(0);

        return timeBaseSeq;
    }

    public Date getQurarterEndDate(int Quarter) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        calendar.set(Calendar.MONTH, (Quarter - 1) * 3 + 2);

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        Date EndDate = calendar.getTime();

        return EndDate;
    }

    public Date getQurarterEndDate(int Quarter,int Month) {
        int remainder=Month%3;
        int month=0;
        if(remainder==1)
            month=-2;
        else if(remainder==2)
            month=-1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        calendar.set(Calendar.MONTH, (Quarter - 1) * 3 + 2+month);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date EndDate = calendar.getTime();

        return EndDate;
    }

    public Date getStartDate(int Month,int Date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DATE, +Date);
        calendar.set(Calendar.MONTH, +Month);
        Date StartDate = calendar.getTime();

        return StartDate;
    }
    public Date getMonthStartDate(int Month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.MONTH, +Month-1);
        Date StartDate = calendar.getTime();

        return StartDate;
    }
    public Date getMonthEndDate(int Month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        calendar.set(Calendar.MONTH, +Month-1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date StartDate = calendar.getTime();

        return StartDate;
    }
    
    public Date getDateFormatCISL(String date) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.FRANCE);
    	Date dateTime = null;
    	try {
    		dateTime = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	return dateTime;
    }
}
