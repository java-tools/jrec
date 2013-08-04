package net.sf.JRecord.Details;

import net.sf.JRecord.Common.Conversion;


/**
 * Create CharLine's from supplied line (text / bytes)
 *
 * @author Bruce Martin
 *
 */
public class CharLineProvider implements LineProvider<LayoutDetail, CharLine> {

	@Override
	public CharLine getLine(LayoutDetail recordDescription) {

		return new CharLine(recordDescription, "");
	}

	@Override
	public CharLine getLine(LayoutDetail recordDescription, String linesText) {

		return new CharLine(recordDescription, linesText);
	}

	@Override
	public CharLine getLine(LayoutDetail recordDescription, byte[] lineBytes) {
		// TODO Auto-generated method stub
		return new CharLine(recordDescription,
				Conversion.toString(lineBytes, recordDescription.getFontName()));
	}

}
