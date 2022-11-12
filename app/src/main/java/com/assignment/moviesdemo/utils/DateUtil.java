package com.assignment.moviesdemo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateUtil {

    public static String formatDate(String inputDate){
        if(inputDate == null || inputDate.equals("") || !isSmallerEqualToCurrentYear(inputDate.substring(0,4)))
            return "";
        if(!datePatternMatches(inputDate = inputDate.replaceAll("/","-")))
            return "";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy",Locale.US);
        String formattedDate ;
        try{
            Date date = inputFormat.parse(inputDate);
            formattedDate = outputFormat.format(date);
        }
        catch(ParseException e){
            return "";
        }
        return formattedDate;
    }

    public static boolean datePatternMatches(String date){
        if(date!=null){
            Pattern DATE_PATTERN  =Pattern.compile("^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
            return DATE_PATTERN.matcher(date).matches();
        }
        return false;
    }

    public static boolean isSmallerEqualToCurrentYear(String year){
        if(year!=null){
            try{
                int current = Calendar.getInstance().get(Calendar.YEAR);
                return Integer.parseInt(year)<=current;
            }
            catch (NumberFormatException e){
                return false;
            }
        }
        return false;
    }
}
