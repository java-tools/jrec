/*
 * @Author Bruce Martin
 * Created on 29/08/2005
 *
 * Purpose:
 */
package net.sf.JRecord.Details;



/**
 * This class returns a Standard <b>Line</b> to the calling program.
 *
 * @author Bruce Martin
 *
 */
public class DefaultLineProvider implements LineProvider<LayoutDetail, Line> {


    /**
     * Build a Line
     *
     * @param recordDescription record layout details
     *
     * @return line just created
     */
    public Line getLine(LayoutDetail recordDescription) {
        return new Line(recordDescription);
    }

    /**
     * Build a Line
     *
     * @param recordDescription record layout details
     * @param linesText text to create the line from
     *
     * @return line
     */
    public Line getLine(LayoutDetail recordDescription, String linesText) {
        return new Line(recordDescription, linesText);
    }


    /**
     * Build a Line
     *
     * @param recordDescription record layout details
     * @param lineBytes bytes to create the line from
     *
     * @return line
     */
    public Line getLine(LayoutDetail recordDescription, byte[] lineBytes) {
        return new Line(recordDescription, lineBytes);
    }
}
