package net.sf.RecordEditor.test.cobolbatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import junit.framework.TestCase;

import net.sf.RecordEditor.re.db.Record.ExtendedRecordDB;
import net.sf.RecordEditor.re.db.Record.RecordRec;
import net.sf.RecordEditor.test.TstConstants;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.common.ReConnection;



public class Data {
	
	public static final String COBOL_DIRECTORY_1 = TstConstants.TEMP_DIRECTORY + "TmpCobol1/";
	public static final String COBOL_DIRECTORY_2 = TstConstants.TEMP_DIRECTORY + "TmpCobol2/";
	public static final String COBOL_NAME_PREFIX = "CblTst";
	public static final String COBOL_EXTENSION   = ".cbl";

	public final String [][] cobolCopyBooks1 = { {
			"          01  CblTst1.",
			"              03  field-1-1                  pic xx.",
			"              03  field-1-2                  pic 9(4) comp.",
			"              03  field-1-3                  pic xxx.",
	}, 	{
			"          01  CblTst2.",
			"              03  field-2-1                  pic xx.",
			"              03  field-2-2                  pic 9(5) comp3.",
	}, {
			"          01  CblTst3.",
			"              03  field-3-1                  pic xx.",
			"              03  field-3-3                  pic 999."
			,
	}};

	public final String [][] cobolFields = { 
			{"field-1-1", "field-1-2", "field-1-3"},
			{"field-2-1", "field-2-2"},
			{"field-3-1", "field-3-3"},
	};
	
	public final ExtendedRecordDB recordDb = new ExtendedRecordDB();
	
	
	public Data() {
		recordDb.setConnection(new ReConnection(Common.getConnectionIndex()));
	}
	
	
	public void checkCopybook1(String id) {
		checkCopybook(id, COBOL_NAME_PREFIX + 1, cobolFields[0]);
	}
	
	
	public void checkCopybook2(String id) {
		checkCopybook(id, COBOL_NAME_PREFIX + 2, cobolFields[1]);
	}

	
	public void checkCopybook3(String id) {
		checkCopybook(id, COBOL_NAME_PREFIX + 3, cobolFields[2]);
	}

	private void checkCopybook(String id, String name, String[] fields) {
		
		recordDb.resetSearch();
		recordDb.setSearchRecordName(ExtendedRecordDB.opEquals, name);
		recordDb.open();
		
		RecordRec rec = recordDb.fetch();
		
		TestCase.assertTrue(id + ": record does not exist: " + name, rec != null);
		TestCase.assertEquals(id + " Record Count ", 0, rec.getValue().getNumberOfRecords());
		TestCase.assertEquals(id + " Field Count", fields.length, rec.getValue().getNumberOfRecordFields());
		
		for (int i = 0; i < fields.length; i++) {
			TestCase.assertEquals(id + " Field Name", fields[i], rec.getValue().getRecordField(i).getName());
		}
	}
	
	public final void deleteCopybooks1() {
		RecordRec rec;
		for (int i = 0; i < cobolCopyBooks1.length; i++) { 
			recordDb.setSearchRecordName(ExtendedRecordDB.opEquals, COBOL_NAME_PREFIX + (i+1));
			while ((rec = recordDb.fetch()) != null) {
				recordDb.delete(rec);
			}
		}
	}
	
	public void writeCopybooks1() throws IOException {
		writeCopybooks(COBOL_DIRECTORY_1, cobolCopyBooks1);
	}
	
	public void writeCopybooks(String cobolDirectory, String [][] cobolCopyBooks) throws IOException {
		File cobolDir = new File(cobolDirectory);
		BufferedWriter writer;
		
		if (! cobolDir.exists() ) {
			cobolDir.mkdir();
		} else if (! cobolDir.isDirectory()) {
			throw new IOException("File already exists with the same name: " + cobolDirectory);
		}
		
		for (int i = 0; i < cobolCopyBooks.length; i++) {
			writer = new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(
											cobolDirectory + COBOL_NAME_PREFIX + (i+1) + COBOL_EXTENSION)),
							4096 * 4);
			
			for (String line : cobolCopyBooks[i]) {
				writer.write(line);
				writer.newLine();
			}
			
			writer.close();
		}
	}
}
