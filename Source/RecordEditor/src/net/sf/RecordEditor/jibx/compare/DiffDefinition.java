package net.sf.RecordEditor.jibx.compare;

public class DiffDefinition extends BaseCopyDif {
	
	public static final String TYPE_SINGLE_LAYOUT = "SingleLayout";
	public static final String TYPE_TWO_LAYOUTS   = "TwoLayouts";

	public String htmlFile     = "";

	public boolean allRows     = false;
	public boolean allFields   = false;
	public boolean singleTable = false;

}
