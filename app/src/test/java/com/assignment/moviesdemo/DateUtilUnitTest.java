package com.assignment.moviesdemo;

import com.assignment.moviesdemo.utils.DateUtil;

import org.junit.Test;
import static org.junit.Assert.*;

public class DateUtilUnitTest {

    @Test
    public void formatDate_CorrectFormatInput(){
        String actualResult = DateUtil.formatDate("1987-02-23");
        String expectedResult = "Feb 23, 1987";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void formatDate_NullInput(){
        String actualResult = DateUtil.formatDate(null);
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void formatDate_EmptyInput(){
        String actualResult = DateUtil.formatDate("");
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void formatDate_NotADate(){
        String actualResult = DateUtil.formatDate("Hello");
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void formatDate_NotADateSample2(){
        String actualResult = DateUtil.formatDate("08012004");
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void formatDate_MissingLeadingZeroes(){
        String actualResult = DateUtil.formatDate("2004-1-1");
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void formatDate_NotAValidDay(){
        String actualResult = DateUtil.formatDate("1984-12-49");
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void formatDate_NotAValidMonth(){
        String actualResult = DateUtil.formatDate("1984-13-09");
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void formatDate_SlashInPlaceOfDashes(){
        String actualResult = DateUtil.formatDate("1984/05/09");
        String expectedResult = "May 09, 1984";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void formatDate_NotInProperFormat(){
        String actualResult = DateUtil.formatDate("09-05-1984");
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void formatDate_NotInProperFormatSample2(){
        String actualResult = DateUtil.formatDate("09-1984-05");
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void formatDate_GreaterYear(){
        String actualResult = DateUtil.formatDate("09-2300-05");
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    /* Unit tests for date pattern match method*/

    @Test
    public void datePatternMatches_CorrectPattern(){
        assertTrue(DateUtil.datePatternMatches("2004-12-30"));
    }

    @Test
    public void datePatternMatches_NullDate(){
        assertFalse(DateUtil.datePatternMatches(null));
    }

    @Test
    public void datePatternMatches_EmptyDate(){
        assertFalse(DateUtil.datePatternMatches(""));
    }

    @Test
    public void datePatternMatches_NotADate(){
        assertFalse(DateUtil.datePatternMatches("hello"));
    }

    @Test
    public void datePatternMatches_NotADateSample2(){
        assertFalse(DateUtil.datePatternMatches("20040101"));
    }

    @Test
    public void datePatternMatches_MissingLeadingZeroes(){
        assertFalse(DateUtil.datePatternMatches("2004-1-1"));
    }

    @Test
    public void datePatternMatches_NotAValidDay(){
        assertFalse(DateUtil.datePatternMatches("1984-12-49"));
    }

    @Test
    public void datePatternMatches_NotAValidMonth(){
        assertFalse(DateUtil.datePatternMatches("1984-13-09"));
    }

    @Test
    public void datePatternMatches_SlashInPlaceOfDashes(){
        assertFalse(DateUtil.datePatternMatches("1984/05/09"));
    }

    @Test
    public void datePatternMatches_NotInProperFormat(){
        assertFalse(DateUtil.datePatternMatches("09-05-1984"));
    }

    @Test
    public void datePatternMatches_NotInProperFormatSample2(){
        assertFalse(DateUtil.datePatternMatches("09-1984-05"));
    }

    /* Unit tests for method isSmallerEqualToCurrentYear */

    @Test
    public void isSmallerEqualToCurrentYear_Correct(){
        assertTrue(DateUtil.isSmallerEqualToCurrentYear("2022"));
    }

    @Test
    public void isSmallerEqualToCurrentYear_CorrectSample2(){
        assertTrue(DateUtil.isSmallerEqualToCurrentYear("1990"));
    }

    @Test
    public void isSmallerEqualToCurrentYear_Null(){
        assertFalse(DateUtil.isSmallerEqualToCurrentYear(null));
    }

    @Test
    public void isSmallerEqualToCurrentYear_Empty(){
        assertFalse(DateUtil.isSmallerEqualToCurrentYear(""));
    }

    @Test
    public void isSmallerEqualToCurrentYear_NotAYear(){
        assertFalse(DateUtil.isSmallerEqualToCurrentYear("hello"));
    }

    @Test
    public void isSmallerEqualToCurrentYear_YearGreater(){
        assertFalse(DateUtil.isSmallerEqualToCurrentYear("2025"));
    }

}
