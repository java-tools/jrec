/*
 * @Author Bruce Martin
 * Created on 2/06/2005
 *
 * Purpose:
 */
package net.sf.RecordEditor.test;

import net.sf.JRecord.Common.Conversion;
import junit.framework.TestCase;

/**
 *
 *
 * @author Bruce Martin
 *
 */
public class TstCommon extends TestCase {

    public void testGetDBConnection() {
    }

    public void testGetSourceId() {
    }

    public void testSetConnectionId() {
    }

    public void testGetResultSet() {
    }

    public void testIsInt() {
    }

    public void testCntInt() {
    }

    public void testNumTrim() {

        checkAssert("1", "876", "876");
        checkAssert("2", "876", "0876");
        checkAssert("3", "876", " 876 ");
        checkAssert("4", "876", " 0876");
        checkAssert("5", "-876", "-876");
        checkAssert("6", "-876", " -0876");
        checkAssert("7", "-876.01", " -0876.01");
        checkAssert("8", "-876.01", " -0876.01");
        checkAssert("9", "-876.01", " -0876.01");
        checkAssert("11", "876.01", "876.01");
        checkAssert("12", "876.01", "0876.01");
        checkAssert("13", "876.01", " 876.01 ");
        checkAssert("14", "876.01", " 0876.01");
        checkAssert("15", "-0.0127", "-0.0127");
        checkAssert("16", "0.0127", "0.0127");
    }

    private void checkAssert(String id, String expected, String test) {
        String actual = Conversion.numTrim(test);

        assertEquals(id + " Expected '" + expected + "' Actual '" + actual + "'",
                expected, actual);
    }

    public void testSetConnection() {
    }

}
