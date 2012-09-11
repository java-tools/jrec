package net.sf.RecordEditor.po;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import net.sf.JRecord.Details.AbstractLayoutDetails;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.IO.AbstractLineWriter;
import net.sf.RecordEditor.po.def.PoField;
import net.sf.RecordEditor.po.def.PoLine;

/**
 * Writes a PoLine to the output file. It draws heavily on the PoWriter class
 * from the jgettext sub-project of the tennera project
 *
 * @author Bruce Martin
 * License GPL
 */
public class PoMessageLineWriter extends AbstractLineWriter {

	private OutputStream outStream;
	private OutputStreamWriter stdWriter = null;
	private BufferedWriter writer = null;


	@Override
	public void open(OutputStream outputStream) throws IOException {
		outStream = outputStream;
	}

	@Override
	public void write(@SuppressWarnings("rawtypes") AbstractLine line) throws IOException {
    	@SuppressWarnings("rawtypes")
		AbstractLayoutDetails layout =  line.getLayout();

	    if (stdWriter == null) {
	    	if (layout == null || "".equals(layout.getFontName())) {
	    		stdWriter = new OutputStreamWriter(outStream);
			} else {
				stdWriter = new OutputStreamWriter(outStream, layout.getFontName());
			}
	        writer = new BufferedWriter(stdWriter);

	        if (layout instanceof LayoutDetail) {
	        	Object o = ((LayoutDetail)layout).getExtraDetails();
	        	if (o != null && o instanceof PoLine) {
	        		write1line((PoLine) o);
	        	}
	        }
	    }
	    write1line(line);
	}

	private void write1line(@SuppressWarnings("rawtypes") AbstractLine line) throws IOException {

		String fuzzy = fix(line.getField(0, PoField.fuzzy.fieldIdx));
		Object flags = line.getField(0, PoField.flags.fieldIdx);
		if (! "".equals(fuzzy)) {
			if (flags == null || "".equals(flags)) {
				flags = PoField.FUZZY;
			} else {
				flags = flags + ", " + PoField.FUZZY;
			}
		}
		writeComment("# " , line.getField(0, PoField.comments.fieldIdx));
		writeComment("#. ", line.getField(0, PoField.extractedComments.fieldIdx));
		writeComment("#: ", line.getField(0, PoField.reference.fieldIdx));
		writeComment("#, ", flags);

    	String prefix = "";
    	Object o = line.getField(0, PoField.obsolete.fieldIdx);
    	if (o != null && "Y".equals(o.toString())) {
    		prefix = "#~ ";
    	}

    	writeMsgctxt( prefix,  line.getField(0, PoField.msgctxt.fieldIdx) );

    	writeMsgid( prefix, line.getField(0, PoField.msgid.fieldIdx) );
    	Object msgIdPlural = line.getField(0, PoField.msgidPlural.fieldIdx);
    	if ( msgIdPlural != null) {
    		List<String> msgstrPlural = null;
    		if (line instanceof PoLine) {
    			Object oo = ((PoLine) line).getMsgstrPlural();
    			if (oo instanceof List) {
    				msgstrPlural = (List<String>) oo;
    			}
    		}

    		writeMsgidPlural( prefix, line.getField(0, PoField.msgidPlural.fieldIdx) );
    		writeMsgstrPlurals( prefix, msgstrPlural );
    	} else {
    		writeMsgstr( prefix, line.getField(0, PoField.msgstr.fieldIdx) );
    	}

		writer.newLine();
    	writer.flush();
	}



	protected void writeComment(String prefix, Object comment) throws IOException {

		if (comment != null) {
			String[] lines = comment.toString().split("\n");
			for (String line : lines) {
				writer.write(prefix);
				writer.write(line);
				writer.newLine();;
			}
		}
	}


	protected void writeMsgctxt(String prefix, Object ctxt) throws IOException {
		if (ctxt != null && ! "".equals(ctxt.toString())) {
			String msgSpace = "msgctxt ";
			writer.write( prefix + msgSpace);
			writeString( prefix, ctxt.toString(), msgSpace.length());
		}
	}

	protected void writeMsgid(String prefix, Object objMsgid) throws IOException {
		String msgid = fix(objMsgid);

		String msgSpace = "msgid ";
		writer.write( prefix + msgSpace);
		writeString( prefix, msgid, msgSpace.length());

	}

	protected void writeMsgidPlural(String prefix, Object objMsgidPlural) throws IOException {
		String msgSpace = "msgid_plural ";
		String msgidPlural = fix(objMsgidPlural);
		writer.write( prefix + msgSpace);
		writeString(prefix, msgidPlural, msgSpace.length());
	}

	protected void writeMsgstr(String prefix, Object objMsgstr) throws IOException {
		String msgstr = fix(objMsgstr);

		String msgSpace = "msgstr ";
		writer.write( prefix + msgSpace);
		writeString(prefix, msgstr, msgSpace.length());
	}

	protected void writeMsgstrPlurals(String prefix, List<String> msgstrPlurals) throws IOException {
		int i = 0;
		for ( String msgstr : msgstrPlurals ) {
			String msgSpace = "msgstr[" + i + "] ";
			writer.write( prefix + msgSpace);
			writeString(prefix, msgstr, msgSpace.length());
			i++;
		}
	}

	private String fix(Object o) {
		String s = "";
		if (o != null) {
			s = o.toString();
		}
		return s;
	}
	protected void writeString(String prefix, String s, int firstLineContextWidth) throws IOException {
		writeString(prefix, s, firstLineContextWidth, 80, 0);
	}


	/*
	 * method partially copied from jgettext !!!
	 */
	/**
	 * @param prefix for obsolete entry
	 * @param s not null string to output
	 * @param firstLineContextWidth number of characters 'context' (e.g. 'msgid ' or 'msgstr ')
	 * @param colWidth width of each line in characters
	 * @param indent number of characters to indent each line
	 * @throws IOException
	 */
	protected void writeString(String prefix, String s, int firstLineContextWidth, int colWidth, int indent) throws IOException {
		//This is for obsolete entry processing. When the first line
		//is not empty, it doesn't need to output "#~".
		boolean firstline = true;
		boolean wrap = true;
		writer.write('\"');

		// check if we should output a empty first line
		int firstLineEnd = s.indexOf('\n');
		if(wrap &&
				((firstLineEnd != -1 && firstLineEnd > (colWidth - firstLineContextWidth-4) ) || s.length()> (colWidth - firstLineContextWidth-4) )){
			firstline=false;
			writer.write('\"');
			writer.newLine();;
			if(prefix.isEmpty())
				writer.write('\"');
		}

		StringBuilder currentLine = new StringBuilder(100);

		int lastSpacePos = 0;

		for(int i=0;i<s.length();i++){
			char currentChar = s.charAt(i);

			switch(currentChar){
			case '\n':
				currentLine.append('\\');
				currentLine.append('n');
				if(wrap && i != s.length()-1){
					writeCurrentLine(prefix, currentLine.toString(), firstline, true);

					firstline = false;
					lastSpacePos = 0;
					currentLine.delete(0, currentLine.length());
				}
				break;
			case '\\':
				currentLine.append(currentChar);
				currentLine.append(currentChar);
				break;
			case '\r':
				currentLine.append('\\');
				currentLine.append('r');
				break;
			case '\t':
				currentLine.append('\\');
				currentLine.append('t');
				break;
			case '"':
				currentLine.append('\\');
				currentLine.append(currentChar);
				break;
			case ':':
			case '.':
			case '/':
			case '-':
			case '=':
			case ' ':
				lastSpacePos = currentLine.length();
				currentLine.append(currentChar);
				break;
			default:
				currentLine.append(currentChar);
			}

			if(wrap && currentLine.length() > colWidth-4 && lastSpacePos != 0){
				writeCurrentLine(prefix, currentLine.substring(0, lastSpacePos+1), firstline, true);

				firstline = false;
				currentLine.delete(0, lastSpacePos+1);
				lastSpacePos = 0;
			}
		}

		writeCurrentLine(prefix, currentLine.toString(), firstline, false);
	}

	private void writeCurrentLine(String prefix, String currline, boolean firstline, boolean openNextLine)
	throws IOException {

		if(prefix.isEmpty()) {
			writer.write(currline);
			writer.write('\"');
			writer.newLine();;
			if (openNextLine) {
				writer.write('\"');
			}
		} else if (firstline) {
			writer.write(currline);
			writer.write('\"');
			writer.newLine();;
		} else {
			writer.write(prefix);
			writer.write('\"');
			writer.write(currline);
			writer.write('\"');
			writer.newLine();;
		}
	}

	@Override
	public void close() throws IOException {
		writer.close();
		stdWriter.close();
	}

}
