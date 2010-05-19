package net.sf.RecordEditor.copy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.AbstractRecordDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.Details.XmlLine;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.AbstractLineIOProvider;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.JRecord.IO.LineIOProvider;
import net.sf.JRecord.Types.Type;
import net.sf.RecordEditor.jibx.compare.CopyDefinition;
import net.sf.RecordEditor.jibx.compare.Record;
import net.sf.RecordEditor.utils.common.Common;
import net.sf.RecordEditor.utils.openFile.AbstractLayoutSelection;
import net.sf.RecordEditor.utils.openFile.LayoutSelectionFile;

public final class DoCopy {
	
	private static final byte[] noBytes = {};
	private  byte[] eolBytes;
	                    
	private LayoutDetail dtl1;
	private LayoutDetail dtl2;
	private CopyDefinition cpy;
	
	private int[][] fromTbl; 
	private int[][] toTbl;
	
	private String[][] defaultValues;

	private int[] toIdx;
	private int[] fromIdx;

	private String fieldSep;
	private byte[] fieldSepByte;
	
	private AbstractLineWriter writer;
	private boolean ok;
	
	/**
	 * Do Copy
	 * 
	 * @param layoutReader1 layout reader 
	 * @param layoutReader2 layout reader 
	 * @param cpy Copy definition
	 * 
	 * @throws Exception any error
	 */
	public static final boolean copy(AbstractLayoutSelection layoutReader1, 
			AbstractLayoutSelection layoutReader2, CopyDefinition copy) 
	throws Exception {
		boolean ret = true;

		if (CopyDefinition.STANDARD_COPY.equals(copy.type)) {
			ret = new DoCopy(copy,
						getLayout(layoutReader1, copy.oldFile.layoutDetails.name, copy.oldFile.name),
						getLayout(layoutReader2, copy.newFile.layoutDetails.name, copy.newFile.name)
			).copy2Layouts();
		} else if (CopyDefinition.COBOL_COPY.equals(copy.type)) {
			ret = new DoCopy(copy,
								getLayout(new LayoutSelectionFile(true), copy.oldFile.layoutDetails.name, copy.oldFile.name),
								getLayout(new LayoutSelectionFile(true), copy.newFile.layoutDetails.name, copy.newFile.name)
			).cobolCopy();
		} else if (CopyDefinition.DELIM_COPY.equals(copy.type)) {
			new DoCopy(copy,
					getLayout(layoutReader1, copy.oldFile.layoutDetails.name, copy.oldFile.name),
					null
			).copy2BinDelim();
		} else if (CopyDefinition.VELOCITY_COPY.equals(copy.type)) {
			net.sf.RecordEditor.utils.RunVelocity.getInstance()      
                	.processFile(layoutReader1.getRecordLayout(""),
                		copy.oldFile.name, copy.velocityTemplate, copy.newFile.name);
		} else if (CopyDefinition.XML_COPY.equals(copy.type)) {
			net.sf.RecordEditor.copy.DoCopy2Xml.newCopy().copyFile(layoutReader1, copy);
 		} else {
			new RuntimeException("Invalid type of Copy --> " + copy.type);
		}
		
		return ret;
	}


	/**
	 * Get a record layout
	 *  
	 * @param layoutReader Layout Reader
	 * @param name Layout name
	 * @param fileName Sample file name
	 * 
	 * @return requested layout
	 * @throws Exception any error
	 */
	private static AbstractLayoutDetails getLayout(AbstractLayoutSelection layoutReader,
			String name, String fileName) throws Exception {
		
		try {
			return layoutReader.getRecordLayout(name, fileName);
		} catch (Exception e) {
			String s = "Error Loading Layout";
			e.printStackTrace();
			Common.logMsg(s, e);
			throw e;
		}
	}

	
	public DoCopy(CopyDefinition copy, AbstractLayoutDetails detail1, AbstractLayoutDetails detail2) {
		cpy = copy;
		dtl1 = (LayoutDetail) detail1;
		dtl2 = (LayoutDetail) detail2;
	}

	
	/**
	 * Copy files using 2 layouts
	 * @throws IOException any IO Error
	 * @throws RecordException any record-editor exception
	 */
	private final boolean copy2Layouts() 
	throws IOException, RecordException {
		int idx, lineNo;
		
		AbstractLineIOProvider ioProvider = LineIOProvider.getInstance();
		AbstractLineReader reader;
		AbstractLine in;
		
		ok = true;

		buildTranslations();
		
		reader = ioProvider.getLineReader(dtl1.getFileStructure());
		writer = ioProvider.getLineWriter(dtl2.getFileStructure());
		
		reader.open(cpy.oldFile.name, dtl1);
		writer.open(cpy.newFile.name);
		
		//System.out.println();
		//System.out.println();
		lineNo = 0;
		if (dtl1.getRecordCount() < 2) {
			while ((in = reader.read()) != null) {
				writeRecord(in, 0, lineNo++);
			}
		} else {
			while ((in = reader.read()) != null) {
				idx = in.getPreferredLayoutIdx();
				if (idx >= 0) {
					writeRecord(in, idx, lineNo++);
				}
			}			
		}

		reader.close();
		writer.close();
		return ok;
	}
	
	/**
	 * Write one Record
	 * 
	 * @param in record that was readin
	 * @param idx record index
	 * @param lineNo line number
	 * @throws IOException any IO Error that occurs
	 */
	private void writeRecord(AbstractLine in, int idx, int lineNo) throws IOException {
		int i1 = fromIdx[idx];
		int i2 = toIdx[idx];	
		
		if (i1 >= 0 && i2 >= 0) {
			AbstractLine out;
			Object o = null;
			
			int[] fromFields = fromTbl[i1];
			int[] toFields = toTbl[i1];

			if (dtl2.isXml()) {
				out = new XmlLine(dtl2, i2);
			} else {
				out = new Line(dtl2);
				
				try {
					FieldDetail selField = dtl2.getRecord(i2).getSelectionField();
					if (selField != null) { 
						out.setField(selField, dtl2.getRecord(i2).getSelectionValue());
					}
				} catch (Exception e) {
				}
			}
			
			if (defaultValues[i1] != null) {
				for (int i = 0; i <  defaultValues[i1].length; i++) {
					if (defaultValues[i1][i] != null) {
						try {
							out.setField(i2, i, defaultValues[i1][i]);
						} catch (Exception e) {
							System.out.println("Error > "  + i2 + " " + i + " " 
									+  dtl2.getRecord(i2).getField(i).getName() + " "+ defaultValues[i2][i] + "<");
							e.printStackTrace();
							ok = false;
						}
					}
				}
			}
			
			for (int i = 0; i < fromFields.length; i++) {
				try {
					o = in.getField(idx, fromFields[i]);
					if (o == null) {
						o = "";
						if (dtl2.getRecord(i2).getFieldsNumericType(toFields[i]) == Type.NT_NUMBER) {
							o = "0";
						}
					}
//					try {
//					System.out.println(i2 +", " +  toFields[i] + " " + dtl2.getRecord(i2).getField( toFields[i]).getName()
//							+" = " + o + " (" + idx + ", " + fromFields[i] + " " + in.getLayout().getRecord(idx).getField(fromFields[i]).getName());
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
					out.setField(i2, toFields[i], o);
				} catch (Exception e) { 
					Common.logMsg("Error Line " + lineNo + " Field Number " + i + " - "+ e.getMessage() + " : " + o, null);
					//e.printStackTrace();
					}
			}
			writer.write(out);
		}
		
	}

	/**
	 * build translation table
	 */
	private void buildTranslations() {
		AbstractRecordDetail rec1, rec2;
		Record recList1, recList2;
		int i, j, size, idx1, idx2, ix;
		boolean noDefault;

		toIdx = new int[dtl1.getRecordCount()];
		fromIdx = new int[dtl1.getRecordCount()];
		
		defaultValues = new String[dtl2.getRecordCount()][];
		
		fromTbl = new int[cpy.oldFile.layoutDetails.records.size()][];
		toTbl = new int[fromTbl.length][];
		
		for (i = 0; i < toIdx.length; i++) {
			toIdx[i] = -1;
		}

		for (i = 0; i< fromTbl.length; i++) {
			recList1 = cpy.oldFile.layoutDetails.records.get(i);
			recList2 = cpy.newFile.layoutDetails.records.get(i);
			idx1= dtl1.getRecordIndex(recList1.name);
			idx2= dtl2.getRecordIndex(recList2.name);
			
			fromIdx[idx1] = i;
			toIdx[idx1] = idx2;
			rec1 = dtl1.getRecord(idx1);
			rec2 = dtl2.getRecord(idx2);
			
			size = recList1.fields.length;
			fromTbl[i] = new int[size];
			toTbl[i] = new int[size];

			
			defaultValues[i] = new String[rec2.getFieldCount()];
			for (j =0; j < defaultValues[i].length; j++) {
				if (rec2.getFieldsNumericType (j) == Type.NT_NUMBER) {
					defaultValues[i][j] = "0";
				} else {
					defaultValues[i][j] = "";
				}
			}
			
			//System.out.println("For idx " + idx1 + " to index " + idx2 + " " + rec1.getRecordName() + " ~ " + rec2.getRecordName() );
			for (j = 0; j < size; j++) {
				fromTbl[i][j] = rec1.getFieldIndex(recList1.fields[j]);
				//System.out.print("  ===> " + recList1.fields[j] + " -> " +  " " + fromTbl[i][j]);
				
				ix = rec2.getFieldIndex(recList2.fields[j]);
				toTbl[i][j] = ix;
				defaultValues[i][ix] = null;
				//System.out.println(" ~~ " + ix + " ~ " + recList2.fields[j]);
			}
			//System.out.println();
			
			noDefault = true;
			for (j =0; j < defaultValues[i].length; j++) {
				if (defaultValues[i][j] != null) {
					noDefault = false;
					break;
				}
			}
			
			if (noDefault) {
				defaultValues[i] = null;
			}
		}
	}
	

	/**
	 * build translation table
	 */
	private void buildTranslations1layout() {
		AbstractRecordDetail rec1;
		Record recList1;
		int i, j, size, idx1;

		toIdx = new int[dtl1.getRecordCount()];
		fromIdx = new int[dtl1.getRecordCount()];
		
		size = dtl1.getRecordCount();
		if (cpy.oldFile.layoutDetails.records == null) {
			fromTbl = new int[dtl1.getRecordCount()][];
			
			for (i = 0; i< fromTbl.length; i++) {
				fromIdx[i] = i;
				size = dtl1.getRecord(i).getFieldCount();
				fromTbl[i] = new int[size];
	
				for (j = 0; j < size; j++) {
					fromTbl[i][j] = j;
				}
			}
		} else {
			fromTbl = new int[cpy.oldFile.layoutDetails.records.size()][];

			for (i = 0; i< fromTbl.length; i++) {
				recList1 = cpy.oldFile.layoutDetails.records.get(i);
				//recList2 = cpy.newFile.layoutDetails.records.get(i);
				idx1= dtl1.getRecordIndex(recList1.name);
				//idx2= dtl2.getRecordIndex(recList2.name);
				
				fromIdx[idx1] = i;
				rec1 = dtl1.getRecord(idx1);
				
				size = recList1.fields.length;
				fromTbl[i] = new int[size];
	
				for (j = 0; j < size; j++) {
					fromTbl[i][j] = rec1.getFieldIndex(recList1.fields[j]);
				}
			}
		}
	}

	private final boolean cobolCopy() 
	throws IOException, RecordException {
		int idx;
		
		AbstractLineIOProvider ioProvider = LineIOProvider.getInstance();
		AbstractLineReader reader;
		AbstractLine in;
		ok = true;
		
		reader = ioProvider.getLineReader(dtl1.getFileStructure());
		writer = ioProvider.getLineWriter(dtl2.getFileStructure());
		
		reader.open(cpy.oldFile.name, dtl1);
		writer.open(cpy.newFile.name);
		
		if (dtl1.getRecordCount() < 2) {
			while ((in = reader.read()) != null) {
				writeCobRecord(in, 0);
			}
		} else {
			while ((in = reader.read()) != null) {
				idx = in.getPreferredLayoutIdx();
				if (idx >= 0) {
					writeCobRecord(in, idx);
				}
			}			
		}

		reader.close();
		writer.close();
		return ok;
	}

	/**
	 * Write one Record
	 * 
	 * @param in record that was readin
	 * @param idx record index
	 * @throws IOException any IO Error that occurs
	 */
	private void writeCobRecord(AbstractLine in, int idx) throws IOException {
		AbstractLine out = new Line(dtl2);
		
		if (idx >= 0) {
			Object o = null;
			int len = in.getLayout().getRecord(idx).getFieldCount();

			for (int i = 0; i <  len; i++) {
				try {
					o = in.getField(idx, i);
					out.setField(idx, i, o);
				} catch (Exception e) {
					//e.printStackTrace();
					Common.logMsg("Error in Field " + in.getLayout().getRecord(idx).getField(i).getName() 
							+ " "  + e.getMessage() + " : " + o, null );
//					System.out.println("Error in Field " + in.getLayout().getRecord(idx).getField(i).getName() 
//							+ " "  + e.getMessage() + " : " + o);
					ok = false;
				}
			}
			writer.write(out);
		}
	}
	
//	/**
//	 * copy file to a delimited file
//	 * @throws IOException any IO error
//	 * @throws RecordException any RecordEditor conversion issues
//	 */
//	private void copy2delim() throws IOException, RecordException {
//		int idx;
//		
//		LineIOProvider ioProvider = LineIOProvider.getInstance();
//		AbstractLineReader reader;
//		AbstractLine in;
//		BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cpy.newFile.name)));
//		
//		reader = ioProvider.getLineReader(dtl1.getFileStructure());
//		
//		reader.open(cpy.oldFile.name, dtl1);
//		
//		buildTranslations1layout();
//		fieldSep = cpy.delimiter;
//		if ("<tab>".equalsIgnoreCase(fieldSep)) {
//			fieldSep = "\t";
//		} else if ("<space>".equalsIgnoreCase(fieldSep)) {
//			fieldSep = " ";
//		}
//		
//		if (cpy.namesOnFirstLine) {
//			String sep = "";
//			for (int i = 0; i < fromTbl[fromIdx[0]].length; i++) {
//				fileWriter.write(sep);
//				fileWriter.write(dtl1.getRecord(fromIdx[0]).getField(fromTbl[0][i]).getName());
//				
//				sep = fieldSep;
//			}
//			fileWriter.newLine();
//		}
//		if (dtl1.getRecordCount() < 2) {
//			while ((in = reader.read()) != null) {
//				writeCsvLine(fileWriter, in, 0);
//			}
//		} else {
//			while ((in = reader.read()) != null) {
//				idx = in.getPreferredLayoutIdx();
//				if (idx >= 0) {
//					writeCsvLine(fileWriter, in, idx);
//				}
//			}			
//		}
//
//		reader.close();
//		fileWriter.close();
//	}
//	
//
//	private void writeCsvLine(BufferedWriter fileWriter, AbstractLine in, int idx) throws IOException {
//		int i1 = fromIdx[idx];
//		
//		if (i1 >= 0) {
//			String sep = "";
//			Object o = null;
//			int[] fromFields = fromTbl[i1];
//					
//			for (int i = 0; i <  fromTbl[i1].length; i++) {
//				try {
//					o = in.getField(i1, fromFields[i]);
//					
//					fileWriter.write(sep);
//					sep = fieldSep;
//					if (o == null) {
//						
//					} else {
//						String s = o.toString();
//						if (isNumeric(dtl1.getField(i1, fromFields[i])) || "".equals(cpy.quote)) {
//	
//						} else if (s.indexOf(cpy.quote) >= 0) {
//							StringBuffer b = new StringBuffer(s);
//							String quote = cpy.quote;
//							int pos;
//							int j = 0;
//							
//							while ((pos = b.indexOf(quote, j)) >= 0) {
//								b.insert(pos, quote);
//								j = pos + 2;
//							}
//							s = quote + b.toString() + quote;
//						} 
//						fileWriter.write(s);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					System.out.println("Error " + e.getMessage() + " : " + o);
//				}
//			}
//			fileWriter.newLine();
//		}
//	}
	
	
	/**
	 * copy file to a delimited file
	 * @throws IOException any IO error
	 * @throws RecordException any RecordEditor conversion issues
	 */
	private void copy2BinDelim() throws IOException, RecordException {
		int idx, lineNo;
		
		AbstractLineIOProvider ioProvider = LineIOProvider.getInstance();
		AbstractLineReader reader;
		AbstractLine in;
		OutputStream fileWriter = new FileOutputStream(cpy.newFile.name);
		
		reader = ioProvider.getLineReader(dtl1.getFileStructure());
		
		reader.open(cpy.oldFile.name, dtl1);
		
		buildTranslations1layout();
		eolBytes = Conversion.getBytes(System.getProperty("line.separator"), cpy.font);
		fieldSepByte = Conversion.getBytes(cpy.delimiter, cpy.font);
		fieldSep = cpy.delimiter;

		if ("<tab>".equalsIgnoreCase(cpy.delimiter)) {
			fieldSepByte = Conversion.getBytes("\t", cpy.font);
			fieldSep = "\t";
		} else if ("<space>".equalsIgnoreCase(cpy.delimiter)) {
			fieldSepByte = Conversion.getBytes(" ", cpy.font);
			fieldSep = " ";
		} else if (cpy.delimiter != null && cpy.delimiter.toLowerCase().startsWith("x'")) {
			try {
//				String t = cpy.delimiter.substring(2, 4);
//				int b = Integer.parseInt(t, 16);
				fieldSepByte = new byte[1];
				fieldSepByte[0] = Conversion.getByteFromHexString(cpy.delimiter);
				try {
					fieldSep = new String(fieldSepByte);
				} catch (Exception e) {
					fieldSep = "";
				}
			} catch (Exception e) {
				Common.logMsg("Invalid Hex Seperator", null);
				e.printStackTrace();
			}
		}
		 
		if (cpy.namesOnFirstLine) {
			byte[] sep = noBytes;
			for (int i = 0; i < fromTbl[fromIdx[0]].length; i++) {
				fileWriter.write(sep);
				
				try {
					fileWriter.write(
							Conversion.getBytes(dtl1.getRecord(fromIdx[0]).getField(fromTbl[0][i]).getName(), cpy.font));
				} catch (Exception e) {
				}
				
				sep = fieldSepByte;
			}
			fileWriter.write(eolBytes);
		}
		lineNo = 0;
		if (dtl1.getRecordCount() < 2) {
			while ((in = reader.read()) != null) {
				lineNo += 1;
				writeBinCsvLine(fileWriter, in, lineNo, 0);
			}
		} else {
			while ((in = reader.read()) != null) {
				idx = in.getPreferredLayoutIdx();
				if (idx >= 0) {
					lineNo += 1;
					writeBinCsvLine(fileWriter, in, lineNo, idx);
				}
			}			
		}

		reader.close();
		fileWriter.close();
	}
	

	private void writeBinCsvLine(OutputStream fileWriter, AbstractLine in,  int lineNo, int idx) throws IOException {
		int i1 = fromIdx[idx];
		
		if (i1 >= 0) {
			byte[] sep = noBytes;
			Object o = null;
			int[] fromFields = fromTbl[i1];
					
			for (int i = 0; i <  fromTbl[i1].length; i++) {
				try {
					o = in.getField(i1, fromFields[i]);
					
					fileWriter.write(sep);
					sep = fieldSepByte;
					if (o == null) {
						
					} else {
						String s = o.toString();
						if (dtl1.getRecord(i1).getFieldsNumericType(fromFields[i]) == Type.NT_NUMBER) {
						} else if ("".equals(cpy.quote)) {		 
							if (!"".equals(fieldSep) && s.indexOf(fieldSep) >= 0) {
								StringBuffer b = new StringBuffer(s);
								Conversion.replace(b, fieldSep, "");
								Common.logMsg("Warning: on line " + lineNo + " Field " + i + ", Seperator " + fieldSep + " Dropped" , null);
								s = b.toString();
							}
						} else if (!"".equals(fieldSep) && s.indexOf(fieldSep) >= 0) {
							StringBuffer b = new StringBuffer(s);
							String quote = cpy.quote;
							int pos;
							int j = 0;
														
							while ((pos = b.indexOf(quote, j)) >= 0) {
								b.insert(pos, quote);
								j = pos + 2;
							}
							s = quote + b.toString() + quote;
						} 
						fileWriter.write(Conversion.getBytes(s, cpy.font));
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error " + e.getMessage() + " : " + o);
				}
			}
			fileWriter.write(eolBytes);
		}
	}
}
