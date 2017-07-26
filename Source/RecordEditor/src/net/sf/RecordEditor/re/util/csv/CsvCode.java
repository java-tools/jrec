package net.sf.RecordEditor.re.util.csv;

import net.sf.JRecord.Common.Conversion;
import net.sf.JRecord.Common.RecordException;
import net.sf.RecordEditor.utils.msg.UtMessages;

public class CsvCode {

	

    public static String checkDelimiter(String txt, String fontName) {
    	String id = "Delimitier";
		if (txt == null || txt.length() < 2 || isValidUnicode(txt) || isSpecialChar(txt)) {
		} else if (isHex(txt)) {
			checkHex(txt, fontName, id);
		} else {
			throw new RecordException(UtMessages.SHOULD_BE_HEX_OR_SINGLE_CHARCTER.get(id));
		}

		return txt;
	}

    public static String checkQuote(String txt, String fontName) {
    	String id = "Quote";
		if (txt == null || isValidUnicode(txt)) {
		} else if (isHex(txt)) {
			checkHex(txt, fontName, id);
//		} else {
//			throw new RecordException(UtMessages.SHOULD_BE_QUOTE.get(id));
		}

		return txt;
	}
    
    public static boolean isSpecialChar(String s) {
    	if (s == null || s.length() < 2 ) {return false;}
    	
    	s = s.toLowerCase();
    	
    	return 		"\\t".equals(s)
      			||	"<tab>".equals(s)
      			||	"<space>".equals(s)
      			||	"<default>".equals(s)    			
    			;
    }

	public static void checkHex(String txt, String fontName, String id) {
		try {
			Conversion.getByteFromHexString(txt);
		} catch (Exception e) {
			throw new RecordException(UtMessages.INVALID_HEX_STRING.get(id, txt.substring(2, 3)));
		}
		if (fontName != null && Conversion.isMultiByte(fontName)) {
			throw new RecordException(UtMessages.INVALID_HEX_WITH_MULTIBYTE_FONT.get(id, fontName, txt));
		}
	}

	public static boolean isUnicode(String txt) {
		return txt != null && (txt.startsWith("\\u") || txt.startsWith("\\U"));
	}
	
	

	public static boolean isValidUnicode(String txt) {
		if (isUnicode(txt)) {
			try {
				Integer.parseInt(txt.substring(2), 16);
				return true;
			} catch (Exception e) {
				throw new RecordException(UtMessages.INVALID_UNICODE_CHAR.get(txt));
			}
		}
		return false;
	}

	public static boolean isHex(String txt) {
		return isLikelyHex(txt) && (txt.length() == 5);
	}

	
	public static boolean isLikelyHex(String txt) {
		return txt != null && txt.toLowerCase().startsWith("x'") && txt.endsWith("'");
	}

}
