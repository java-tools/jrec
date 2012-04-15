/*
 * @Author Bruce Martin
 * Created on 11/02/2007
 *
 * Purpose:
 */
package net.sf.RecordEditor.test.record;

import java.util.Date;
import java.util.GregorianCalendar;

import com.zbluesoftware.java.bm.ZDateField;

import junit.framework.TestCase;

/**
 *
 *
 * @author Bruce Martin
 *
 */
public class TstDateField extends TestCase {
    private static Date[] dates = {
            getDate(2000, 02, 01), getDate(1999, 02, 01), getDate(2007, 02, 01),
            getDate(2000, 11, 01), getDate(2006, 12, 21)
    };

    private static String[] formats = {
            "ddMMyy", "ddMMyyyy", "dd.MMM.yy", "dd.MMM.yyyy", "dd/MM/yy", "dd/MM/yyyy",
            "yyMMdd", "yyyyMMdd", "yy.MMM.dd", "yyyy.MMM.dd", "yy/MM/dd", "yyyy/MM/dd"
    };


    private static String[][] results = {
            {"010300", "010399", "010307", "011200", "210107", },
            {"01032000", "01031999", "01032007", "01122000", "21012007", },
            {"01.Mar.00", "01.Mar.99", "01.Mar.07", "01.Dec.00", "21.Jan.07", },
            {"01.Mar.2000", "01.Mar.1999", "01.Mar.2007", "01.Dec.2000", "21.Jan.2007", },
            {"01/03/00", "01/03/99", "01/03/07", "01/12/00", "21/01/07", },
            {"01/03/2000", "01/03/1999", "01/03/2007", "01/12/2000", "21/01/2007", },
            {"000301", "990301", "070301", "001201", "070121", },
            {"20000301", "19990301", "20070301", "20001201", "20070121", },
            {"00.Mar.01", "99.Mar.01", "07.Mar.01", "00.Dec.01", "07.Jan.21", },
            {"2000.Mar.01", "1999.Mar.01", "2007.Mar.01", "2000.Dec.01", "2007.Jan.21", },
            {"00/03/01", "99/03/01", "07/03/01", "00/12/01", "07/01/21", },
            {"2000/03/01", "1999/03/01", "2007/03/01", "2000/12/01", "2007/01/21", },
    };

    private static String[][] textInput = {
            {"10300", "10399", "10307", "11200", "210107", },
            {"1032000", "1031999", "1032007", "1122000", "21012007", },
            {"1.Mar.00", "1.Mar.99", "1.Mar.07", "1.Dec.00", "21.Jan.07", },
            {"1.Mar.2000", "1.Mar.1999", "1.Mar.2007", "1.Dec.2000", "21.Jan.2007", },
            {"1/03/00", "1/03/99", "1/03/07", "1/12/00", "21/01/07", },
            {"1/03/2000", "1/03/1999", "1/03/2007", "1/12/2000", "21/01/2007", },
            {"301", "990301", "70301", "1201", "70121", },
            {"20000301", "19990301", "20070301", "20001201", "20070121", },
            {".Mar.01", "99.Mar.01", "7.Mar.01", ".Dec.01", "7.Jan.21", },
            {"2000.Mar.01", "1999.Mar.01", "2007.Mar.01", "2000.Dec.01", "2007.Jan.21", },
            {"/03/01", "99/03/01", "7/03/01", "/12/01", "7/01/21", },
            {"2000/03/01", "1999/03/01", "2007/03/01", "2000/12/01", "2007/01/21", },
    };

    public void testDates1() {
        ZDateField fld;
        int j;
        boolean ok = true;
        //String s;

        System.out.println("-------  Test 1 -----------");
        System.out.println();
        for (int i = 0; i < formats.length; i++) {
            fld = new ZDateField(formats[i]);
//            System.out.print("    {");
            for (j = 0; j < dates.length; j++) {
                fld.setDate(dates[j]);

                if (! fld.getDate().equals(dates[j])) {
                    ok = false;
                    System.out.println("Failure " + formats[i] + " " + j
                                + " a) expected " + dates[j] + " got " + fld.getDate());
                }
                fld.setText(fld.getText());
                if (! fld.getDate().equals(dates[j])) {
                    ok = false;
                    System.out.println("Failure " + formats[i] + " " + j
                                + " b) expected " + dates[j] + " got " + fld.getDate());
                }
                if (! results[i][j].equals(fld.getText())) {
                    ok = false;
                    System.out.println("Failure " + formats[i] + " " + j
                            + " d) expected " + results[i][j] + " got " + fld.getText());
                }
//                if (ok) {
//                    System.out.print("\"" + fld.getText() + "\", ");
//                }
            }
//            System.out.println("},");
        }
        assertTrue("See above failures in test 1", ok);
    }

    public void testDates2() {
        ZDateField fld;
        int j;
        boolean ok = true;

        System.out.println();
        System.out.println("-------  Test 2 -----------");
        System.out.println();


        for (int i = 0; i < formats.length; i++) {
            fld = new ZDateField(formats[i]);
//            System.out.print("    {");
            for (j = 0; j < dates.length; j++) {
                fld.setText(textInput[i][j]);

                if (! textInput[i][j].equals(fld.getText())) {
                    ok = false;
                    System.out.println("Failure " + formats[i] + " " + j
                                + " 0) expected " + textInput[i][j] + " got " + fld.getText());
                }
                if (! fld.getDate().equals(dates[j])) {
                    ok = false;
                    System.out.println("Failure " + formats[i] + " " + j
                                + " a) expected " + dates[j] + " got " + fld.getDate());
                }
                fld.setText(fld.getText());
                if (! fld.getDate().equals(dates[j])) {
                    ok = false;
                    System.out.println("Failure " + formats[i] + " " + j
                                + " b) expected " + dates[j] + " got " + fld.getDate());
                }
                fld.setDate(fld.getDate());
                if (! results[i][j].equals(fld.getText())) {
                    ok = false;
                    System.out.println("Failure " + formats[i] + " " + j
                                + " c) expected " + results[i][j] + " got " + fld.getText());
                }
            }
        }
        assertTrue("See above failures in test 2", ok);
    }

    private static Date getDate(int year, int month, int day) {
        return new GregorianCalendar(year, month, day).getTime();

    }
}
