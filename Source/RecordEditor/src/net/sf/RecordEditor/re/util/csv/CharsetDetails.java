package net.sf.RecordEditor.re.util.csv;

public class CharsetDetails {
	public final String charset;
	public final String[] likelyCharsets;


	public CharsetDetails(String charset, String[] likelyCharsets) {
		super();
		this.charset = charset;
		this.likelyCharsets = likelyCharsets;
	}
	
	public String[] getLikelyCharsets(String font, int max) {
		if (likelyCharsets == null || likelyCharsets.length == 0) {
			return new String[] {font};
		}
		
		int noCharSets = this.likelyCharsets.length + 1;
		int j = 0;

		for (String c : this.likelyCharsets) {
			String trim = c.trim();
			if (font.equalsIgnoreCase(trim) || trim.length() == 0) {
				noCharSets -= 1;
				break;
			}
		}

		String[] items;
		if (! "".equals(font)) {
			items = new String[Math.min(10, noCharSets)];
			items[j++] = font;
		} else {
			items = new String[Math.min(10, noCharSets - 1)];
		}
		for (int i = 0; j < items.length && i < this.likelyCharsets.length; i++) {
			String cs = this.likelyCharsets[i].trim();
			if (cs.length() > 0 && ! font.equalsIgnoreCase(cs)) {
				items[j++] =  cs;
			}
		}
		return items;
	}
}
