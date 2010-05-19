/*
 * @Author Bruce Martin
 * Created on 29/08/2005
 *
 * Purpose:
 */
package net.sf.JRecord.Details;

/**
 * A <b>LineProvider</b> creates lines for the calling program.
 * By creating your own <b>LineProvider</b>, you can use your
 * own <b>Line's</b> rather than the System <b>Line</b> / XmlLine class.
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("unchecked")
public interface LineProvider<Layout extends AbstractLayoutDetails> {

    /**
     * Create a null line
     *
     * @param recordDescription record description
     *
     * @return new line
     */
    public abstract AbstractLine<Layout> getLine(Layout recordDescription);


    /**
     * Line Providers provide lines to the calling program
     *
     * @param recordDescription record layout details
     * @param linesText text to create the line from
     *
     * @return line
     */
    public abstract AbstractLine<Layout> getLine(Layout recordDescription, String linesText);

    /**
     * Build a Line
     *
     * @param recordDescription record layout details
     * @param lineBytes bytes to create the line from
     *
     * @return line
     */
    public abstract AbstractLine<Layout> getLine(Layout recordDescription, byte[] lineBytes);
}