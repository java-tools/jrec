package net.sf.RecordEditor.utils.swing;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.table.TableModel;


/**
 * Utility JDBC functions (at the  moment code to write a Table
 * model as a CSV file).
 *
 * @author Bruce Martin
 */
public final class Utils {

public static final String TAB = "\t";
public static final String COMMA = ",";



	/**
	 * This method writes a Table model to a file  in a comma / tab
	 * seperator file
	 *
	 * @param writer output write
	 * @param model  Table model to be written
	 * @param fieldSeperator    Field seporator
	 */
	public static final void writeTable(BufferedWriter writer,
	        TableModel model,
	        String fieldSeperator) {
		int i;
		int rows = model.getRowCount();
		int cols = model.getColumnCount();

		try {
			if (rows > 0 && cols > 0) {
				for (i = 0; i < rows; i++) {
				    writeRow(writer, model, i, fieldSeperator);
				}
			}
			writer.close();
		} catch (Exception ex) {
			System.out.println("Write Table : " + ex.getMessage());
		}
	}

	/**
	 * This method writes a row to an output file
	 *
	 * @param writer output write
	 * @param model  Table model to be written
	 * @param row    row to write
	 * @param fieldSeperator    Field seporator
	 *
	 * @throws IOException any IO error
	 */
	public static final void writeRow(BufferedWriter writer, TableModel model,
	        						  int row, String fieldSeperator)
	throws IOException {
		int j;
	    int cols = model.getColumnCount();

		try {
			writer.write(model.getValueAt(row, 1).toString());
		} catch (Exception ex) { }
		for (j = 2; j < cols; j++) {
			try {
				writer.write(fieldSeperator);
				writer.write(model.getValueAt(row, j).toString());
			} catch (Exception ex) { }
		}

		writer.newLine();
	}



	/**
	 * Writes the details of a Table model to a file
	 *
	 * @param fileName output file name
	 * @param model table mode that is to be written
	 * @param sep field seperator character to use
	 *
	 */
	public static void writeTable(String fileName, TableModel model, String sep, String font) {

		try {
			Writer fw;
			if (font == null || "".equals(font)) {
				fw = new  FileWriter(fileName);
			} else {
              	try {
            		fw =new OutputStreamWriter(new FileOutputStream(fileName), font);
            	} catch (Exception e) {
            		fw = new FileWriter(fileName);
				}
			}
			BufferedWriter bufWrite = new BufferedWriter(fw);

			writeTable(bufWrite, model, sep);

			fw.close();

		} catch (Exception ex) {
			System.out.println("Write Table (a) : " + ex.getMessage());
		}
	}

}
