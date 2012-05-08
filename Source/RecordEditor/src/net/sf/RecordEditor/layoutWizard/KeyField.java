package net.sf.RecordEditor.layoutWizard;

import net.sf.JRecord.Types.Type;

public class KeyField {
	public final static Integer CHAR_TYPE = Integer.valueOf(Type.ftChar);

    public String keyName = "";
    public int keyStart;
    public int keyLength;
    public Integer keyType;

    public KeyField() {
    	this("", 0, 0, CHAR_TYPE);
    }

    public KeyField(String name, int start, int length, Integer type) {
    	keyName = name;
    	keyStart = start;
    	keyLength = length;
    	keyType = type;
    }
}
