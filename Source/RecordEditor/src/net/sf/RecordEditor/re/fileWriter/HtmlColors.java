package net.sf.RecordEditor.re.fileWriter;

public class HtmlColors {
	public static final HtmlColors BOORING_COLORS  = new HtmlColors(null, null, null, null, null, null);
	public static final HtmlColors STANDARD_COLORS = new HtmlColors(null, "#DADADA", "#F3F3F3",  null, null, null);
	public static final HtmlColors MONEY_COLORS    = new HtmlColors(null, "#CEC6B5", "#FFFBF0",  "#DEE7DE", null, null);
	public static final HtmlColors BRIGHT_COLORS   = new HtmlColors("#E6E7FF", "#0000FF", "#FFFFB0",  "#FFFFE0", "#FF0000", "#FFFFFF");


	public final String backgroundColor, headingBackground, evenBackground, oddBackground, borderColor, headingColor;

	public HtmlColors(String backgroundColor, String headingBackground,
			String evenBackground, String oddColor,
			String borderColor, String headingColor) {
		super();
		this.backgroundColor = backgroundColor;
		this.headingBackground = headingBackground;
		this.evenBackground = evenBackground;
		this.oddBackground = oddColor;
		this.borderColor = borderColor;
		this.headingColor = headingColor;
	}
}
