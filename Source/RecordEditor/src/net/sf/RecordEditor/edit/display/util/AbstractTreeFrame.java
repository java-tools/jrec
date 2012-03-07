package net.sf.RecordEditor.edit.display.util;

import net.sf.RecordEditor.edit.file.AbstractLineNode;

public interface AbstractTreeFrame<LNode extends AbstractLineNode> {

	public abstract LNode getRoot();

}