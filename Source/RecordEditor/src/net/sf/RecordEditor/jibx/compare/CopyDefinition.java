package net.sf.RecordEditor.jibx.compare;

public class CopyDefinition extends BaseCopyDif {

	public static final String STANDARD_COPY = "StandardCopy";
	public static final String COBOL_COPY    = "CobolCopy";
	public static final String DELIM_COPY    = "DelimCopy";
	public static final String VELOCITY_COPY  = "Velocity";
	public static final String XML_COPY   = "Xml";

	public String delimiter = ",";
	public String quote = "";
	public boolean namesOnFirstLine = true;
	public String velocityTemplate = "";
	public String font = "";
	
	public String fieldErrorFile = "";
	public int maxErrors = -1;
	
	public RecordTree treeDefinition = new RecordTree();
}
