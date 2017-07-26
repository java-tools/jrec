package net.sf.RecordEditor.edit;

import net.sf.JRecord.Common.CommonBits;
import net.sf.JRecord.Common.Conversion;
import net.sf.RecordEditor.utils.params.Parameters;

public class EditCommon {


	public static void doStandardInit() {
		String s;
//		net.sf.JRecord.CsvParser.ParserManager.setUseNewCsvParsers(true);
	    CommonBits.setUseCsvLine(false);

		if (Conversion.DEFAULT_CHARSET_DETAILS.isMultiByte) {
			s = Parameters.getString(Parameters.PROPERTY_DEFAULT_SINGLE_BYTE_CHARSET);
			if (s != null && s.length() > 0) {
				Conversion.setDefaultSingleByteCharacterset(s);
				s = Parameters.getString(Parameters.PROPERTY_USE_SINGLE_BYTE_CHARSET);
				Conversion.setAlwaysUseDefaultSingByteCharset("y".equalsIgnoreCase(s));
			}
		}
		
		s = Parameters.getString(Parameters.PROPERTY_DEFAULT_EBCDIC_CHARSET);
		if (s != null && s.length() > 0) {
			Conversion.setDefaultEbcidicCharacterset(s);
		}
	}
	

}
