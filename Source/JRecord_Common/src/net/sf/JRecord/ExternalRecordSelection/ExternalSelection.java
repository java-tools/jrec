package net.sf.JRecord.ExternalRecordSelection;

public interface ExternalSelection {

	public int TYPE_ATOM = 1;
	public int TYPE_AND = 2;
	public int TYPE_OR = 3;
	public int TYPE_AND_OR = 4;
	public int TYPE_OR_AND = 5;
	
	public int getType();
	
	public int getSize();

}
