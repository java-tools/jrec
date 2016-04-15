package net.sf.RecordEditor.layoutEd.load;

import java.util.Collection;
import java.util.TreeMap;

import javax.swing.JTabbedPane;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.Def.ExternalField;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.JRecord.Option.ICobolSplitOptions;
import net.sf.JRecord.cg.common.CCode;
import net.sf.cb2xml.def.Cb2xmlConstants;


public class CblGenJRecord {
	

	private EditPane ioBuilderPane = new EditPane();
	private EditPane constantPane  = new EditPane();
	
	final JTabbedPane pane = new JTabbedPane();
	//final JTextArea javaCode = new J
	private final CblLoadData data;

	protected CblGenJRecord(CblLoadData data) {
		super();
		this.data = data;
		
		pane.addTab("JRecord IoBuilder", ioBuilderPane.scrollPane);
		pane.addTab("Cobol Field Constants", constantPane.scrollPane);
	}
	
	void generate() {
		genIoBuilder();
		genFieldNames();
		pane.setSelectedIndex(0);
	}

	/**
	 * 
	 */
	private void genIoBuilder() {
		StringBuilder b = new StringBuilder();
		
		String font = data.fontNameCombo.getText();
		int dialect = data.getDialect();
		int splitOption = data.getSplitOption();
		int format = data.getCopybookFormat();
		
		b.append("\n// This is the Java code to create a JRecord Cobol-ioBuilder\n//")
		 .append("\n// JRecord is an open source java package for")
		 .append("\n// reading and writing Cobol Data files using a Cobol Copybook\n//")
		 .append("\n// JRecord is available at https://sourceforge.net/projects/jrecord/ \n\n");
		
		b.append("/*----------------  Required Imports  ----------------------\n\n")
		 .append("import net.sf.JRecord.JRecordInterface1;\n")
		 .append("import net.sf.JRecord.Details.AbstractLine;\n")
		 .append("import net.sf.JRecord.IO.AbstractLineReader;\n")
		 .append("import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;\n\n")
		 .append(" *----------------------------------------------------------*/\n\n");

		b.append("\n    ICobolIOBuilder iob = JRecordInterface1.COBOL")
		 .append("\n                                 .newIOBuilder(\"" 
				 					+ Conversion.replace(data.copybookFileCombo.getText(), "\\", "/") + "\")");
		
		
		if (dialect != ICopybookDialects.FMT_MAINFRAME) {
			b.append("\n                                      .setDialect(" + CCode.getDialectName(dialect) + ")");
		}
		
		if (splitOption != ICobolSplitOptions.SPLIT_NONE) {
			b.append("\n                                      .setSplitCopybook(" + CCode.getSplitName(splitOption) + ")");
		}
		
        if (! "".equals(font)) {
        	b.append("\n                                      .setFont(\"" + font +"\")");
        }
        
		if (format != Cb2xmlConstants.USE_STANDARD_COLUMNS) {
			b.append("\n                                      .setCopybookFileFormat(" + CCode.getCopybookFormatName(format) + ")");
		}

		b.append(";\n    AbstractLine line;\n\n");
		
		String sampleFileName = Conversion.replace(data.sampleFileCombo.getText(), "\\", "/").toString();
		
		b.append("\n//     AbstractLineReader reader  = iob.newReader(\"" + sampleFileName + "\");")
		 .append("\n//     while ((line = reader.read()) != null) {}\n")
		 .append("\n// or \n")
		 .append("\n//     AbstractLineWriter writer  = iob.newWriter(\"" + sampleFileName + "\");")
		 .append("\n//     line =  iob.newLine();")
		 .append("\n//     line.getFieldValue(\"...\").set(...);");
		
		ioBuilderPane.textArea.setText(b.toString());
		ioBuilderPane.textArea.setCaretPosition(0);
	}
	
	private void genFieldNames() {
		StringBuilder b = new StringBuilder();
		
		ExternalRecord xRecord = data.getXRecordJR();
		
		boolean hasArrays = false;
		
		if (xRecord.getNumberOfRecords() == 0) {
			hasArrays = hasArrayFields(xRecord);
		} else {
			for (int i = xRecord.getNumberOfRecords() - 1; i >= 0 && hasArrays == false; i--) {
				hasArrays = hasArrayFields(xRecord.getRecord(i));
			}
		}

		if (hasArrays) {
			b.append("\nimport net.sf.JRecord.cgen.impl.ArrayFieldName;\n")
			 .append("import net.sf.JRecord.cgen.def.*;\n\n");
		}

		b.append("/**\n")
		 .append(" * This class holds the Cobol fields names as constants.\n")
		 .append(" * So rather than having to remember the Cobol-Names use your\n" )
		 .append(" * IDE's auto assist instead.\n")
		 .append(" *\n")
		 .append(" * There could be duplicate field names, I will leave you to sort this out\n")
		 .append(" *\n")
		 .append(" * This class is intended for use in JRecord\n")
		 .append(" * (https://sourceforge.net/projects/jrecord/)\n")
		 .append(" *\n")
		 .append(" * Code Generated by the Recordeditor (Author: Bruce Martin)\n")
		 .append(" */\n\n");
		b.append("public class ").append(toClassName(cobolName2JavaName(xRecord.getRecordName())))
		 .append(" {\n\n");
		
		if (xRecord.getNumberOfRecords() == 0) {
			printField(b, xRecord);
			printChildClass(b, xRecord.getRecordName(), xRecord);
		} else {
			for (int i = 0; i < xRecord.getNumberOfRecords(); i++) {
				printField(b, xRecord.getRecord(i));
			}
			for (int i = 0; i < xRecord.getNumberOfRecords(); i++) {
				printChildClass(b, xRecord.getRecordName(), xRecord.getRecord(i));
			}
		}
		
		b.append("\n}\n");
		constantPane.textArea.setText(b.toString());
		constantPane.textArea.setCaretPosition(0);
	}

	/**
	 * @param xRecord
	 * @param hasArrays
	 * @return
	 */
	private boolean hasArrayFields(ExternalRecord xRecord) {
		boolean hasArrays = false;
		String n;
		for (int i = 0; i < xRecord.getNumberOfRecordFields(); i++) {
			n = xRecord.getRecordField(i).getName();
			
			if ( n.indexOf(" (") > 0) {
				hasArrays = true;
				break;
			}
		}
		return hasArrays;
	}
	
	
	private void printField(StringBuilder b,  ExternalRecord xRecord) {
		StringBuilder nameBldr = cobolName2JavaName(xRecord.getRecordName());
		String className = childClassName(nameBldr);
		b.append("    public static final ")
		 .append(className)		 .append(" ")           .append(toRecordName(nameBldr))
		 .append(" = new ")		 .append(className)		.append("();\n");
	}
	
	
	private void printChildClass(StringBuilder b,  String pRecName, ExternalRecord xRecord) {
		String recordName = xRecord.getRecordName();

		String copyBook = xRecord.getCopyBook();
		if (recordName.startsWith(copyBook)) {
			recordName.substring(copyBook.length());
		}
		StringBuilder nameBldr = cobolName2JavaName(recordName);
		String className = childClassName(nameBldr);
		TreeMap<String, ExternalField> arrayMap = new TreeMap<String, ExternalField>();
		int idx;
		String n;

		b.append("\n\n    public static final class ")
		 .append(className)		.append(" { \n");

		b.append("\n        public final String recordName = \"")
         .append(recordName)	
         .append("\";\n");
		
		for (int i = 0; i < xRecord.getNumberOfRecordFields(); i++) {
			n = xRecord.getRecordField(i).getName();
			n = removeRecordName(pRecName, n);
			idx = n.indexOf(" (");
			if (idx < 0) {
				addFieldName(b, n, xRecord.getRecordField(i).getCobolName()); 
			} else {
				n = n.substring(0, idx);
				String key = n.toLowerCase();
				if (! arrayMap.containsKey(key)) {
					arrayMap.put(key, xRecord.getRecordField(i));
				}
			}
		}
		
		Collection<ExternalField> arrayFields = arrayMap.values();
		
		if (arrayFields.size() > 0) {
			b.append("\n\n   // Array Fields follow\n");
			for (ExternalField v : arrayFields) {
				addArrayField(b, pRecName, v);
			}
		}
		
		b.append("\n\n    }\n");
	}

	/**
	 * @param recordName
	 * @param n
	 * @return
	 */
	private String removeRecordName(String recordName, String n) {
		if (n.length() > recordName.length() && recordName.equalsIgnoreCase(n.substring(0, recordName.length()))) {
			n = n.substring(recordName.length());
		}
		if (n.startsWith("-")) {
			n = n.substring(1);
		}
		return n;
	}

	private String dropIdx(String n) {
		int indexOf = n.indexOf(" (");
		if (indexOf < 0) {
			return n;
		}
		return n.substring(0, indexOf);
		
	}
	/**
	 * @param b
	 * @param n
	 */
	private void addFieldName(StringBuilder b, String n, String cobolName) {
		b.append("\n        public final String ")
		 .append(toFieldName(cobolName2JavaName(n)))
		 .append(" = \"")  .append(cobolName)   .append("\";");
	}

	private void addArrayField(StringBuilder b, String pRecName, ExternalField f) {
		int numCommas = 0;
		String name = f.getName();
		int idx = name.indexOf(',');
		while (idx >= 0) {
			numCommas += 1;
			idx = name.indexOf(',', idx+1);
		}
		String fldName = removeRecordName(pRecName, dropIdx(f.getName()));
		switch (++numCommas) {
		case 1:
		case 2:
		case 3:
		case 4:
			//public final IFieldName1Dimension dcNumber  = new ArrayFieldName("DC-Number");
			b.append("\n        public final IFieldName").append(numCommas).append("Dimension ")
			 .append(toFieldName(cobolName2JavaName(fldName)))
			 .append(" = new ArrayFieldName(\"")  .append(f.getCobolName())   .append("\");");
			break;
		default:
			addFieldName(b, fldName, f.getCobolName());
		}
	}


	private String childClassName(StringBuilder nameBldr) {
		StringBuilder b = new StringBuilder("Class").append(nameBldr);
		b.setCharAt(5, Character.toUpperCase(b.charAt(5)));
		return b.toString();
	}
	
	private static String toFieldName(StringBuilder b) {
		return toJavaId(b, 'f');
	}
	
	private static String toClassName(StringBuilder b) {
		if ((b.charAt(0) >= 'a' && b.charAt(0) <= 'z')) {
			b.setCharAt(0, Character.toUpperCase(b.charAt(0)));
		} else if (b.charAt(0) < 'A' || b.charAt(0) > 'Z') {
			b = new StringBuilder(b).insert(0, 'c');
		}
		return b.toString();
	}

	
	private static String toRecordName(StringBuilder b) {
		b = new StringBuilder(b);
		Conversion.replace(b, "-", "_");
		if ((b.charAt(0) >= 'a' && b.charAt(0) <= 'z')
		||  (b.charAt(0) >= 'A' && b.charAt(0) <= 'Z')) {
			
		} else {
			b.insert(0, 'R');
		}
		return b.toString().toUpperCase();
	}

	
	private static String toJavaId(StringBuilder b, char pref) {
		if (b == null || b.length() == 0) {
			return "";
		}
		b = new StringBuilder(b);
		
		if ((b.charAt(0) >= 'A' && b.charAt(0) <= 'Z')) {
			b.setCharAt(0, Character.toLowerCase(b.charAt(0)));
		} else if (b.charAt(0) < 'a' || b.charAt(0) > 'z') {
			b.insert(0, pref);
		}
		return b.toString();
	}

	private static StringBuilder cobolName2JavaName(String cobolName) {
		String lcCobolName = cobolName.toLowerCase();
		String ucCobolName = cobolName.toUpperCase();
		int length = cobolName.length();
		StringBuilder b = new StringBuilder(length);

		boolean toUCase = false; 
		char c;
		
		for (int i = 0; i < length; i++) {
			c = lcCobolName.charAt(i);
			switch (c) {
			case ':':
			case ';':
			case '.':
			case '*':
			case '=':
			case '+':
			case '\'':
			case '\"':
			case '~':
			case '!':
			case '|':
			case '@':
			case '#':
			case '$':
			case '%':
			case ')':
			case '[':
			case ']':
				break;
			case '(':
			case ',':
				b.append('_');
				toUCase = false;
				break;
			case ' ':
			case '-':
			case '_':
				toUCase = true;
				break;
			default:
				if (toUCase) {
					b.append(ucCobolName.charAt(i));
					toUCase = false;
				} else {
					b.append(c);
				}
			}
		}
		return b;
	}

}
