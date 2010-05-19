/*
 * @Author Bruce Martin
 * Created on 8/01/2007
 *
 * Purpose:
 * File & layout details used by all the wizard screens
 *
 * Changes
 * # Version 0.61b Bruce Martin 2007/05/05
 *  - Changes o support user selecting the default type
 *
 */
package net.sf.RecordEditor.layoutWizard;

import java.io.IOException;
import java.util.ArrayList;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.RecordDetail;
import net.sf.JRecord.External.ExternalField;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.utils.common.Common;


/**
 * File & layout details used by all the wizard screens
 *
 * @author Bruce Martin
 *
 */
public class Details {
	public static final int RT_FIXED_LENGTH_FIELDS = 0;
	public static final int RT_DELIMITERED_FIELDS  = 1;
	public static final int RT_MULTIPLE_RECORDS  = 2;
	
    private final static Integer CHAR_TYPE = new Integer(Type.ftChar);
    protected  String filename         = "";
    protected     int fileStructure    = 0;
    protected     int recordType       = 0;
    protected     int system           = 0;
    protected     int recordLength     = 0;
    protected  String layoutName       = "Wizard_";
    protected  String layoutDescription = "";
    protected  String fontName         = "";
    protected Integer defaultType      = CHAR_TYPE;
    protected  String fieldSeperator   = "";
    protected  String actualSeperator  = "";
    protected  String quote            = "";
    protected  String actualQuote      = "";
    protected     int parserType       = 0;
    protected boolean fieldNamesOnLine = false;
    
    protected  String layoutDirectory       = "";
    protected     int layoutWriterIdx  = -1; 

    //protected     int numRecords = 0;
    //protected byte[][] records = new byte[30][];

    //protected ColumnList columnDtls = new ColumnList();
    //protected ArrayList<ColumnDetails> columnDtls = new ArrayList<ColumnDetails>();
    protected RecordDefinition standardRecord = new RecordDefinition();
    
    protected ArrayList<RecordDefinition> recordDtls = new ArrayList<RecordDefinition>();
    
    protected String keyName = "Record_Type";
    protected int keyStart = 0;
    protected int keyLength = 0;
    protected Integer keyType = CHAR_TYPE;
    


    public final ExternalRecord createRecordLayout() {
     	ExternalRecord rec;

    	switch (recordType) {
    	case (Details.RT_FIXED_LENGTH_FIELDS) :
    		rec = new ExternalRecord(-1, layoutName,
    				layoutDescription,
    				Common.rtRecordLayout, system, "Y", "",
    				"<Tab>", "", 0, Common.DEFAULT_STRING,
    				Common.LFCR_BYTES, fontName,
    				0, fileStructure
    		);
    		addFields(rec, standardRecord);
    	break;
    	case (Details.RT_MULTIPLE_RECORDS):
    		ExternalRecord childRecord;
    	
    		rec = new ExternalRecord(-1, layoutName,
    				layoutDescription,
    				Common.rtGroupOfRecords, system, "Y", "",
    				"<Tab>", "", 0, Common.DEFAULT_STRING,
    				Common.LFCR_BYTES, fontName,
    				0, fileStructure
    		);
    		for (RecordDefinition child : recordDtls) {
    			childRecord = new ExternalRecord(-1, child.name,
        				"",
        				Common.rtRecordLayout, system, "N", "",
        				"<Tab>", "", 0, Common.DEFAULT_STRING,
        				Common.LFCR_BYTES, fontName,
        				0, fileStructure
        		);
    			addFields(childRecord, child);
    			childRecord.setTstField(keyName);
    			if (child.keyValue != null) {
    				childRecord.setTstFieldValue(child.keyValue.toString());
    			}
    			childRecord.setNew(true);
    			
    			rec.addRecord(childRecord);
    		}
    	break;
    	default:
    		String delim = fieldSeperator;
 
	    	if ("<Default>".equalsIgnoreCase(delim)) {
	    		delim = actualSeperator;
	    	}
	    	
	    	rec = new ExternalRecord(-1, layoutName,
	    			layoutDescription,
	    			Common.rtDelimited, system, "Y", "",
	    			delim, actualQuote, 0, 
	    			Common.DEFAULT_STRING,
	    			Common.LFCR_BYTES, fontName,
	    			0, fileStructure
	    	);
	    	addFields(rec, standardRecord);
    	}
    	
    	rec.setNew(true);
    	return rec;
    }
    
    /**
     * Add Fields to a external record definition
     * @param rec external record 
     * @param recordDef Wizard Record
     */
    private void addFields(ExternalRecord rec, RecordDefinition recordDef) {
       	int i;
    	ColumnDetails column;
    	ExternalField field;
 	
    	for (i = 0; i < recordDef.columnDtls.size(); i++) {
    		column = recordDef.columnDtls.get(i);
    		if (column.include.booleanValue()) {
    			field = new ExternalField(
    					column.start,
    					column.length,
    					column.name,
    					"",
    					column.type,
    					column.decimal,
    					Common.FORMAT_DEFAULT,
    					"",
    					"",
    					"",
    					i
    			);

    			rec.addRecordField(field);
    		}
    	}
    }

    /**
     * Get a line Reader
     * @return requested line reader
     * 
     * @throws RecordException any RecordEditor Exceptions that occur
     * @throws IOException any IO Errors 
     */
    @SuppressWarnings("unchecked")
	public AbstractLineReader<LayoutDetail> getReader() throws RecordException, IOException {
        AbstractLineReader<LayoutDetail> reader = LineIOProvider.getInstance().getLineReader(fileStructure);
        LayoutDetail emptyLayout;
        RecordDetail[] recs;

        
        if (fileStructure == Common.IO_FIXED_LENGTH) {
            FieldDetail[] field = new FieldDetail[1];

            field[0] = new FieldDetail("", "", Type.ftChar, 0, "",  0, "");
            field[0].setPosLen(1, recordLength);
            recs = new RecordDetail[1];
            recs[0] = new RecordDetail(
                    	"", "", "", Common.rtBinaryRecord, "", "", "", field, 0
                    );
        } else {
            recs = new RecordDetail[0];
        }
        emptyLayout = new LayoutDetail("", recs, "",
                fileStructure,
                null, "", fontName, null,
                fileStructure
        );

        reader.open(filename, emptyLayout);
        return reader;
    }
}
