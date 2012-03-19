/*
 * @Author Bruce Martin
 * Created on 6/08/2005
 *
 * Purpose:
 */
package net.sf.RecordEditor.utils.swing;

import java.io.BufferedWriter;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

//import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.sf.RecordEditor.utils.common.TranslateXmlChars;

/**
 * This class writes a Table Model as a HTML table
 *
 * @author Bruce Martin
 *
 */
public class TableModel2HTML {

    private String seperator;
    private TableModel tblModel;
    private BufferedWriter writer;
    private Writer suppliedWriter;

    private int firstColumn = 0;
    private boolean tblBorder;

    /**
     * This class writes a Table Model as a HTML table
     *
     * @param output output writer
     * @param headerSep header line seperator character
     * @param model table model to be written
     * @param border wether the tablehas a border
     */
    public TableModel2HTML(final Writer output,
            			   final String headerSep,
            			   final TableModel model,
            			   final boolean border) {
        super();

        suppliedWriter = output;
        seperator = headerSep;
        tblModel  = model;
        tblBorder = border;
        writer    = new BufferedWriter(output);

    }


    /**
     * Writes start of HTML file
     *
     * @throws IOException any IO Error that occurs
     */
    public void writeHtmlStart()  throws IOException  {

        writer.write("<html><body>");
    }


    /**
     * Writes end of HTML file
     *
     * @throws IOException any IO Error that occurs
     */
    public void writeHtmlEnd()  throws IOException  {

        writer.write("</body></html>");
        writer.close();
        suppliedWriter.close();
    }


    /**
     * Writes a TableModel to the File as a HTML Table
     *
     * @throws IOException any IO Error that occurs
     */
    public void writeTable() throws IOException  {
        int i;

        if (tblModel.getRowCount() > 0) {
	        writeTableHeader();
	
	        //System.out.println("Write Table " + tblModel.getRowCount()
	        //        + " ");
	        for (i = 0; i < tblModel.getRowCount(); i++) {
	            writeRow(i);
	        }
        }

        writer.write("</table><P>&nbsp;</P>");
    }


    /**
     * writes selected rows to the file in a HTML table
     *
     * @param rows rows to write
     *
     * @throws IOException any IO Error that occurs
     */
    public void writeTable(int[] rows) throws IOException  {
        int i;

        writeTableHeader();

        for (i = 0; i < rows.length; i++) {
            writeRow(rows[i]);
        }

        writer.write("</table><P>&nbsp;</P>");
    }



    /**
     * Write Table Header
     *
     * @throws IOException any IO Error that occurs
     */
    public void writeTableHeader() throws IOException {
        int col;

        if (tblBorder) {
        	writer.write("<table BORDER=\"1\" CELLSPACING=\"1\"><tr>");
        } else {
        	writer.write("<table><tr>");
        }

        for (col = firstColumn; col < tblModel.getColumnCount(); col++) {
            writeText(correctValue(tblModel.getColumnName(col)), "th", true);
        }

        writer.write("</tr>");
        writer.newLine();
    }

    /**
     * Writes a row to the output file
     *
     * @param rowId row to be written
     *
     * @throws IOException - IO error
     */
    private void writeRow(int rowId) throws IOException {
        int col;

        writer.write("<tr>");

        for (col = firstColumn; col < tblModel.getColumnCount(); col++) {
            writeText(correctValue(tblModel.getValueAt(rowId, col)), "td", false);
        }

        writer.write("</tr>");
        writer.newLine();
    }


    /**
     * Writing text to the HTML file
     *
     * @param text Text to be written
     *
     * @throws IOException any IO error
     */
    public void writeText(String text) throws IOException {
        writer.write(text);
    }

    /**
     * Writes Text to the output file
     *
     * @param text to be written
     * @param tag HTML tag to write
     * @param inHeader wether this is a header field
     *
     * @throws IOException - IO error
    */
    private void writeText(String text, String tag, boolean inHeader) throws IOException {

        tag += ">";

        writer.write("<" + tag);
        if ("".equals(text.trim())) {
            writer.write("&nbsp;");
        } else {
            StringBuilder buf = TranslateXmlChars.replaceXmlChars(new StringBuilder(text));

 
            if (inHeader && ! "".equals(seperator)) {
            	TranslateXmlChars.replace(buf, seperator, "<br/>");
            }


            writer.write(buf.toString());
        }
        writer.write("</" + tag);
    }


    /**
     * Changes null to "" other wise return the input value
     *
     * @param val value to check / return
     *
     * @return value corrected for nulls;
     */
    private String correctValue(Object val) {
        if (val == null) {
            return "";
        }
        return val.toString();
    }


    /**
     * Set the first column to print
     *
     * @param newFirstColumn first column to print
     */
    public void setFirstColumn(int newFirstColumn) {
        this.firstColumn = newFirstColumn;
    }


/*    public static void main(final String[] args) {
        Object[][] list = {{"0", "Text 0", "amp", "&&"},
                           {"1", "Text 1", "lt", "<"},
                           {"2", "Text 2", "gt", ">"}};
        String[] columns = {"line", "text", "HTML char name", "HTML char"};
        DefaultTableModel mdl = new DefaultTableModel(list, columns);

        try {
            TableModel2HTML wHTML = new TableModel2HTML(new FileWriter("e://tmp//tmp.html"),
                									"", mdl);

            wHTML.writeHtmlStart();
            wHTML.writeTable();
            wHTML.writeHtmlEnd();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //StringBuffer buf = new StringBuffer("Here is an && it will be &");

        //replace(buf, "&", "&amp;");

        //System.out.println(buf.toString());
    }*/

}
