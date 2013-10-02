package net.sf.RecordEditor.re.util.csv;

public class CharsetDetails {
	public final String charset;
	public final String[] likelyCharsets;


	public CharsetDetails(String charset, String[] likelyCharsets) {
		super();
		this.charset = charset;
		this.likelyCharsets = likelyCharsets;
	}
}
