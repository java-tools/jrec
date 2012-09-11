package net.sf.RecordEditor.po;

import net.sf.JRecord.IO.LineIOProvider;
import net.sf.RecordEditor.edit.open.DisplayBuilderFactory;
import net.sf.RecordEditor.po.display.PoDisplayBuilder;
import net.sf.RecordEditor.tip.display.TipDisplayBuilder;

/**
 * This class add's the The GetText-PO Classes to the RecordEditor.
 * it is called by the recordeditor if present.
 *
 * @author Bruce Martin
 *
 */
public class PoInit {
	public PoInit() {
		DisplayBuilderFactory.register(new PoDisplayBuilder());
		DisplayBuilderFactory.register(new TipDisplayBuilder());
		LineIOProvider.getInstance().register(new PoLineIOProvider());
	}
}
