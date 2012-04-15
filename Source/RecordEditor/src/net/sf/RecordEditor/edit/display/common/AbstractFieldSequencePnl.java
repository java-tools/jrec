package net.sf.RecordEditor.edit.display.common;

import net.sf.RecordEditor.jibx.compare.FieldSequence;
import net.sf.RecordEditor.re.script.AbstractFileDisplay;

public interface AbstractFieldSequencePnl extends AbstractFileDisplay {

	public abstract FieldSequence getFieldSequence();

	public abstract void setFieldSequence(FieldSequence seq);

}