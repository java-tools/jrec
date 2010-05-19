/*
 * Created on 26/10/2005
 *
 * Requirements:
 *
 * 1) You must define the new File Structure in the FileStructure Table
 *    in the LayoutEditor
 * 2) You must then define a Layout that uses the file structure
 * 3) Check the values in Constants.java are correct !!!
 *
 */
package net.sf.RecordEditor.examples;

import net.sf.RecordEditor.edit.EditRec;
import net.sf.JRecord.ByteIO.VbDumpByteWriter;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.IO.LineWriterWrapper;
import net.sf.RecordEditor.utils.CopyBookDbReader;
import net.sf.RecordEditor.utils.common.Common;

/**
 * This is a silly example of introducing a new file structure
 * (Mainframe VB with a maximum block size of 9040)
 *
 * @author B Martin
 *
 */
public class XmplFileStructure1 extends LineIOProvider {

	private static final int MY_FILE_STRUCTURE = 1001;
	private static final int BLOCKSIZE = 9040;

	/**
	 *
	 */
	public AbstractLineReader getLineReader(int fileStructure) {

		if (fileStructure == MY_FILE_STRUCTURE) {
			return super.getLineReader(Common.IO_VB_DUMP);
		}
		return super.getLineReader(fileStructure);
	}



	public AbstractLineWriter getLineWriter(int fileStructure) {

		if (fileStructure == MY_FILE_STRUCTURE) {
			return new LineWriterWrapper(new VbDumpByteWriter(BLOCKSIZE));
		}
		return super.getLineWriter(fileStructure);
	}


    /**
     * Example of defining your own Types
     * @param args program arguments
     */
    public static void main(String[] args) {

        CopyBookDbReader copybook = new CopyBookDbReader();

        try {
            new EditRec("", 1, new XmplFileStructure1(), copybook);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
